package com.example.pollos_silver.activities

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.pollos_silver.R
import com.example.pollos_silver.databinding.ActivityMainBinding
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.biometric.BiometricPrompt
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.pollos_silver.MyApplication

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    // Handler - Timer
    private lateinit var handler: Handler
    private val interval: Long = 10000

    // Sensors
    private lateinit var sensorManager: SensorManager
    // private var accelerometerSensor: Sensor? = null
    private var gyroscopeSensor: Sensor? = null
    private val currentRotation: Array<Float> = arrayOf(0.0f, 0.0f, 0.0f)

    // BiometricPrompt
    private val REQUEST_PERMISSION = 100
    private lateinit var biometricPrompt: BiometricPrompt

    //private val accelerometerListener = object : SensorEventListener {
    //    override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {}
    //
    //    override fun onSensorChanged(event: SensorEvent) {
    //        // Manejar los cambios en el acelerómetro aquí
    //        val x = event.values[0]
    //        val y = event.values[1]
    //        val z = event.values[2]
    //
    //        // Actualizar la posición de la imagen en función de los valores del acelerómetro
    //        // binding.imageView.x = x
    //        // binding.imageView.y = y
    //
    //    }
    //}

    private val gyroscopeListener = object : SensorEventListener {
        override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {}

        override fun onSensorChanged(event: SensorEvent) {
            // Manejar los cambios en el giroscopio aquí
            val x = event.values[0]
            val y = event.values[1]

            // Calculamos rotacion
            val Sensitivity = 3f
            currentRotation[0] += x * Sensitivity
            currentRotation[1] += y * Sensitivity

            // Aplicamos rotacion
            val limiteRotation = 15
            if( -limiteRotation < currentRotation[0] && currentRotation[0] < limiteRotation)
                binding.imageView.rotationX = floatRounded(currentRotation[0])

            if( -limiteRotation < currentRotation[1] && currentRotation[1] < limiteRotation)
                binding.imageView.rotationY = floatRounded(currentRotation[1])

            binding.txtLog.text = "X: ${floatRounded(binding.imageView.rotationX)}\nY: ${floatRounded(binding.imageView.rotationY)}"
        }
    }

    private fun floatRounded(value: Float): Float = String.format("%.${3}f", value).toFloat()

    //Init
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Handler - Timers
        handler = Handler(Looper.getMainLooper())
        startTimer()

        // Sensors
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        //accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        gyroscopeSensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE)

        // BiometricPrompt
        biometricPrompt = createBiometricPrompt()
        if (MyApplication.SharedPreferences.get_tipo_usuario().isNotEmpty()) {
        // El usuario inicio sesion
            if (isBiometricAvailable()) {
                // Verificamos si se ha concedido el permiso necesario
                if (isPermissionGranted()) {
                    // Mostramos el cuadro de diálogo de autenticación biométrica
                    showBiometricPrompt()
                } else {
                    // Solicitamos el permiso necesario
                    requestPermission()
                }
            } else {
                // La autenticación biométrica no está disponible en el dispositivo
            }
        }

        // buttons
        binding.btnLogin.setOnClickListener {
            start_LoginActivity()
        }

        binding.btnRegister.setOnClickListener {
            start_RegisterActivity()
        }
    }

    override fun onResume() {
        super.onResume()
        //sensorManager.registerListener(
        //    accelerometerListener,
        //    accelerometerSensor,
        //    SensorManager.SENSOR_DELAY_NORMAL
        //)
        sensorManager.registerListener(
            gyroscopeListener,
            gyroscopeSensor,
            SensorManager.SENSOR_DELAY_NORMAL
        )
        startTimer()
    }

    override fun onPause() {
        super.onPause()
        //sensorManager.unregisterListener(accelerometerListener)
        sensorManager.unregisterListener(gyroscopeListener)
        handler.removeCallbacksAndMessages(null)
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacksAndMessages(null)
    }

    private fun startTimer() {
        handler.post(object : Runnable {
            override fun run() {
                // val currentTime = System.currentTimeMillis()
                currentRotation[0] = 0f
                binding.imageView.rotationX = currentRotation[0]
                currentRotation[1] = 0f
                binding.imageView.rotationY = currentRotation[1]

                // Programa la siguiente ejecución después del intervalo
                handler.postDelayed(this, interval)
            }
        })
    }

    // BiometricPrompt
    private fun createBiometricPrompt(): BiometricPrompt {
        // Obtenemos el executor principal para ejecutar los métodos de devolución de llamada en el subproceso principal
        val executor = ContextCompat.getMainExecutor(this)

        // Creamos un objeto de devolución de llamada para manejar los eventos de autenticación biométrica
        val callback = object : BiometricPrompt.AuthenticationCallback() {
            override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                // Se cancelo la autenticación por huella
                Toast.makeText(this@MainActivity, "No se pudo reconocer ninguna huella digital", Toast.LENGTH_SHORT).show()
            }

            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                // La autenticación fue exitosa, Huella correcta
                start_LoginActivity()
            }

            override fun onAuthenticationFailed() {
                // La autenticación falló, La huella no se reconocio
            }
        }

        // Creamos una instancia de BiometricPrompt utilizando el contexto, el executor y el callback
        return BiometricPrompt(this, executor, callback)
    }

    private fun isBiometricAvailable(): Boolean {
        // Obtenemos el administrador de paquetes
        val packageManager = packageManager

        // Comprobamos si el dispositivo tiene la característica de huella dactilar
        return packageManager.hasSystemFeature(PackageManager.FEATURE_FINGERPRINT)
    }

    private fun isPermissionGranted(): Boolean {
        // Comprobamos si se ha concedido el permiso USE_BIOMETRIC
        return ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.USE_BIOMETRIC
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermission() {
        // Solicitamos el permiso USE_BIOMETRIC al usuario
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.USE_BIOMETRIC),
            REQUEST_PERMISSION
        )
    }

    private fun showBiometricPrompt() {
        // Configuramos la información para el cuadro de diálogo de autenticación biométrica
        val promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Verificación de huella digital")
            .setSubtitle("Inicia sesión de forma segura con tu huella")
            .setNegativeButtonText("Cancelar")
            .build()

        // Mostramos el cuadro de diálogo de autenticación biométrica utilizando la información configurada
        biometricPrompt.authenticate(promptInfo)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        // Comprobamos si se ha concedido el permiso USE_BIOMETRIC
        if (requestCode == REQUEST_PERMISSION && grantResults.isNotEmpty() &&
            grantResults[0] == PackageManager.PERMISSION_GRANTED
        ) {
            // Mostramos el cuadro de diálogo de autenticación biométrica
            showBiometricPrompt()
        } else {
            // Permiso denegado, maneja la situación
        }
    }

    // xd
    private fun start_LoginActivity(){
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }
    private fun start_RegisterActivity(){
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
    }
}
