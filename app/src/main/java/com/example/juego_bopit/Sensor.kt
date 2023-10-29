package com.example.juego_bopit

import android.annotation.SuppressLint
import android.app.Activity
import android.content.pm.ActivityInfo
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.widget.TextView

class Sensor : Activity(), SensorEventListener {

    private lateinit var X:TextView
    private lateinit var Y:TextView
    private lateinit var Z:TextView
    private lateinit var Accelerometro:Sensor

    private lateinit var Detector:TextView
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sensor)


        Detector = findViewById(R.id.mobimineto)
        X = findViewById(R.id.xID)
        Y = findViewById(R.id.yID)
        Z = findViewById(R.id.zID)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }
    override fun onResume() {
        super.onResume()
        val sm = getSystemService(SENSOR_SERVICE) as SensorManager
        val sensors = sm.getSensorList(Sensor.TYPE_ACCELEROMETER)

        if (sensors.isNotEmpty()) {
            Accelerometro = sensors[0]
            sm.registerListener(this, Accelerometro, SensorManager.SENSOR_DELAY_GAME)
        }
    }
    override fun onPause() {
        val mSensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        mSensorManager.unregisterListener(this, Accelerometro)
        super.onPause()
    }
    override fun onStop() {
        val mSensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        mSensorManager.unregisterListener(this, Accelerometro)
        super.onStop()
    }
    override fun onSensorChanged(event: SensorEvent) {
        X.text = "X = ${event.values[0]}"
        Y.text = "Y = ${event.values[1]}"
        Z.text = "Z = ${event.values[2]}"

        if(event.values[0] <= 0.0f && event.values[1] <= 10f && event.values[2] <= 10f) {
            Detector.text = "me mareo"
        }
        else{
            Detector.text = "no mareo"
        }
    }
    override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {}
}