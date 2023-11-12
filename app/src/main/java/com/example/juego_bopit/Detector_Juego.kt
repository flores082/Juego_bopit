
package com.example.juego_bopit

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.content.pm.ActivityInfo
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.media.MediaPlayer
import android.media.RingtoneManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.GestureDetector
import android.view.MotionEvent
import android.widget.TextView
import java.util.Random
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Toast

class Detector_Juego : AppCompatActivity(),
    GestureDetector.OnGestureListener,
    GestureDetector.OnDoubleTapListener, SensorEventListener {

    private lateinit var sharedPreferences: SharedPreferences

    private lateinit var mainLayout: View

    private lateinit var TextView: TextView
    private lateinit var mTextView: TextView
    private lateinit var MTextView: TextView
    private lateinit var gestureDetector: GestureDetector

    private var random = Random()

    val palabra = arrayOf("onSingleTapUp","onScroll","onLongPress","onFling", "Agitar")

    val MP = "Mayor_Puntaje="

    private var palabraActual: String = ""
    private var juegoTerminado = false
    private var Accion = false

    private lateinit var mediaPlayerV: MediaPlayer
    private lateinit var mediaPlayerD: MediaPlayer
    private lateinit var yes: MediaPlayer
    private lateinit var no: MediaPlayer
    var Puntaje = 0
    var Puntaje_Guardado = 0
    val Tiempo= 3000L
    val Tiempo_Cambio= 600L
    val Tiempo_Error= 500L
    private val tiempoTotal = 60000L // 60 segundos
    private var tiempoRestante = tiempoTotal
    private var handler = Handler(Looper.getMainLooper())
    private lateinit var updateText: Runnable
    private lateinit var updateTextC: Runnable
    private lateinit var updateTextR: Runnable
    private lateinit var runnableTiempo: Runnable

    //sensor{
    private lateinit var X:TextView
    private lateinit var Y:TextView
    private lateinit var Z:TextView
    private lateinit var Accelerometro: Sensor

    private lateinit var Detector:TextView
    //}

    //val PuntajeMayor = sharedPreferences.getInt("Puntaje_Mayor", 0)

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detector_juego)

        mediaPlayerV = MediaPlayer.create(this, R.raw.victoria)
        mediaPlayerD = MediaPlayer.create(this, R.raw.derrota)
        yes = MediaPlayer.create(this, R.raw.yes)
        no = MediaPlayer.create(this, R.raw.no)

        //sensor
        //X = findViewById(R.id.xID)
        //Y = findViewById(R.id.yID)
        //Z = findViewById(R.id.zID)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        //Accion
        gestureDetector = GestureDetector(this, this)
        gestureDetector.setOnDoubleTapListener(this)
        TextView = findViewById(R.id.Puntaje)
        mTextView = findViewById(R.id.mobimineto)
        MTextView = findViewById(R.id.PuntajeMayor)

        updateText = updateTextRunnable
        updateTextC = updateTextRunnable
        updateTextR = updateTextRunnable

        var pref=getSharedPreferences("Puntaje_mayor",Context.MODE_PRIVATE)
        var PM = pref.getString("PM","0")?.toIntOrNull() ?: 0
        MTextView.setText(MP+PM)

        mostrarPalabra()
        //iniciarTemporizador()

        if(juegoTerminado==true)
        {
            Guardar()
        }


    }

    fun Play() {

        object : CountDownTimer(60000, 1000) {
            // 60000 milisegundos (60 segundos), 1000 milisegundos (1 segundo) entre ticks
            override fun onTick(millisUntilFinished: Long) {
                // Código que se ejecuta en cada tick del temporizador
                val segundosRestantes = (millisUntilFinished / 1000).toInt()
            }
        }
    }

    private val updateTextRunnable = object : Runnable {
        override fun run() {
            handler.postDelayed(updateText, Tiempo)
        }
    }
    private val updateTextRunnableC = object : Runnable {
        override fun run() {
            handler.postDelayed(updateTextC, Tiempo_Cambio)
        }
    }
    private val updateTextRunnableR = object : Runnable {
        override fun run() {
            handler.postDelayed(updateTextR, Tiempo_Error)
        }
    }

    fun Correcto() {
        if(!juegoTerminado) {
            Puntaje++
            yes.start()
            //reiniciarTemporizador()
            handler.post(updateTextC)
            handler.removeCallbacks(updateTextR)
            mostrarPalabra()
        }
    }
    fun InCorrecto(){
        if(!juegoTerminado) {
            handler.postDelayed(updateTextR, Tiempo_Error)
            mediaPlayerD.start()
            juegoTerminado = true
            mTextView.text = "Derrota"
        }
    }
    private fun mostrarPalabra() {
        if (!juegoTerminado) {
            Accion = false
            val numeroAleatorio = random.nextInt(palabra.size)
            palabraActual = palabra[numeroAleatorio]
            mTextView.text = palabraActual
            TextView.text = "Puntaje:" + Puntaje
        }
    }

    fun Guardar(){
            var preferences = getSharedPreferences("Puntaje_mayor", Context.MODE_PRIVATE)
            var editor = preferences.edit()
            editor.putString("PM", Puntaje?.toString())
            editor.commit()
            Toast.makeText(this, "Guardado Puntaje mayor", Toast.LENGTH_LONG).show()
    }

    //sensor{
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
        //X.text = "X = ${event.values[0]}"
        //Y.text = "Y = ${event.values[1]}"
        //Z.text = "Z = ${event.values[2]}"
        val x = event.values[0]
        val y = event.values[1]
        val z = event.values[2]

        val magnitudAceleracion = Math.sqrt((x * x + y * y + z * z).toDouble()).toFloat()
        val umbralMovimientoRapido = 18.0

        if(magnitudAceleracion>umbralMovimientoRapido && palabraActual=="Agitar") {
            Correcto()
        }
         else if(magnitudAceleracion>umbralMovimientoRapido && !Accion){
            InCorrecto()
        }
    }
    override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {}
    //}
    override fun onTouchEvent(event: MotionEvent): Boolean {
        gestureDetector.onTouchEvent(event)
        return super.onTouchEvent(event)
    }
    override fun onSingleTapConfirmed(motionEvent: MotionEvent): Boolean {

        return true
    }

    override fun onDoubleTap(motionEvent: MotionEvent): Boolean {

        return true
    }

    override fun onDoubleTapEvent(motionEvent: MotionEvent): Boolean {

        return true
    }

    override fun onDown(motionEvent: MotionEvent): Boolean {

        return true
    }

    override fun onShowPress(motionEvent: MotionEvent) {
    }
    override fun onSingleTapUp(motionEvent: MotionEvent): Boolean {
        if(palabraActual=="onSingleTapUp") {
            Correcto()
        }
        else if(!Accion){
            InCorrecto()
        }
        return true
    }

    override fun onScroll(motionEvent: MotionEvent, motionEvent1: MotionEvent, v: Float, v1: Float): Boolean {
        if(palabraActual=="onScroll") {
           Correcto()
        }
        else if(!Accion){
            InCorrecto()
        }
        return true
    }

    override fun onLongPress(motionEvent: MotionEvent) {
        if(palabraActual=="onLongPress") {
            Correcto()
        }
        else if(!Accion){
            InCorrecto()
        }
    }

    override fun onFling(motionEvent: MotionEvent, motionEvent1: MotionEvent, v: Float, v1: Float): Boolean {
        if(palabraActual=="onFling") {
            Correcto()
        }
        else if(!Accion){
            InCorrecto()
        }
        return true
    }

    private fun iniciarTemporizador() {
        runnableTiempo = object : Runnable {
            override fun run() {
                // Actualiza el tiempo restante
                tiempoRestante -= 1000L

                // Programa la próxima ejecución después de 1 segundo
                handler.postDelayed(this, 1000L)

            }
        }

        // Programa la primera ejecución
        handler.postDelayed(runnableTiempo, 1000L)
    }
    private fun reiniciarTemporizador() {
        tiempoRestante = tiempoTotal
        handler.removeCallbacks(runnableTiempo)
        iniciarTemporizador()
    }
    }