package com.example.pollos_silver.activities

import android.app.Activity
import android.content.Context
import android.content.Intent
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

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private lateinit var handler: Handler
    private val interval: Long = 10000

    private lateinit var sensorManager: SensorManager
    // private var accelerometerSensor: Sensor? = null
    private var gyroscopeSensor: Sensor? = null
    private val currentRotation: Array<Float> = arrayOf(0.0f, 0.0f, 0.0f)

    // region accelerometerListener
    /*
    private val accelerometerListener = object : SensorEventListener {
        override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {}

        override fun onSensorChanged(event: SensorEvent) {
            // Manejar los cambios en el acelerómetro aquí
            val x = event.values[0]
            val y = event.values[1]
            val z = event.values[2]

            // Actualizar la posición de la imagen en función de los valores del acelerómetro
            // binding.imageView.x = x
            // binding.imageView.y = y

        }
    }
    */
    // endregion

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

        handler = Handler(Looper.getMainLooper())
        startTimer()

        binding.btnLogin.setOnClickListener {
            start_LoginActivity()
        }

        binding.btnRegister.setOnClickListener {
            start_RegisterActivity()
        }

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        //accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        gyroscopeSensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE)
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


    private fun start_LoginActivity(){
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }
    private fun start_RegisterActivity(){
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
    }
}
