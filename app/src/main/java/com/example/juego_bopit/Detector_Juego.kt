
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
    private var N = 60000
    val Tiempo= 3000L
    val Tiempo_palabra= 600L
    private val tiempoTotal = 60000L // 60 segundos
    private var tiempoRestante = tiempoTotal
    private var handler = Handler(Looper.getMainLooper())
    private var handler_derrota = Handler(Looper.getMainLooper())
    private lateinit var updateText: Runnable

    //sensor{
    private lateinit var Accelerometro: Sensor
    //}

    //val PuntajeMayor = sharedPreferences.getInt("Puntaje_Mayor", 0)

    private var temporizador: CountDownTimer? = null


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

        var pref=getSharedPreferences("Puntaje_mayor",Context.MODE_PRIVATE)
        var PM = pref.getString("PM","0")?.toIntOrNull() ?: 0
        MTextView.setText(MP+PM)



        //dificultad
        if(Puntaje>=3)
        {
            Toast.makeText(this, "Novato", Toast.LENGTH_LONG).show()
            N=45000
        }else if(Puntaje>=6)
        {
            Toast.makeText(this, "Facil", Toast.LENGTH_LONG).show()
            N=30000
        }else if(Puntaje>=9){
            Toast.makeText(this, "Normal", Toast.LENGTH_LONG).show()
            N=20000
        }
        else if(Puntaje>=12){
            Toast.makeText(this, "Dificil", Toast.LENGTH_LONG).show()
            N=10000
        }
        else if(Puntaje>=15){
            Toast.makeText(this, "Dios", Toast.LENGTH_LONG).show()
            N=5000
        }

        mostrarPalabra()
        Play()
    }

    fun Play() {
        temporizador=object : CountDownTimer(N.toLong(), 1000) {
            // 60000 milisegundos (60 segundos), 1000 milisegundos (1 segundo) entre ticks
            override fun onTick(millisUntilFinished: Long) {
                // Código que se ejecuta en cada tick del temporizador
            }

            override fun onFinish() {
                InCorrecto()
                Guardar()
            }
        }.start()
    }
    fun reiniciar(){
        temporizador?.cancel()
        Play()
    }

    private val updateTextRunnable = object : Runnable {
        override fun run() {
            handler_derrota.postDelayed(updateText, Tiempo)
        }
    }

    fun Correcto() {
        if(!juegoTerminado) {
            Puntaje++
            yes.start()
            //reiniciarTemporizador()
            reiniciar()
            //handler_derrota.removeCallbacks(updateTextRunnable,Tiempo)
            mostrarPalabra()
        }
    }
    fun InCorrecto(){

        //handler_derrota.postDelayed({
        if(!juegoTerminado) {
            mediaPlayerD.start()
            mTextView.text = "Derrota"
            juegoTerminado = true
        }//},Tiempo)
    }
    private fun mostrarPalabra() {
        handler.postDelayed({
        if (!juegoTerminado) {
            Accion = false
            val numeroAleatorio = random.nextInt(palabra.size)
            palabraActual = palabra[numeroAleatorio]
            mTextView.text = palabraActual
            TextView.text = "Puntaje:" + Puntaje
        }},Tiempo_palabra)
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
    }