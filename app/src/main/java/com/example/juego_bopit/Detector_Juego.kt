package com.example.juego_bopit

import android.annotation.SuppressLint
import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.GestureDetector
import android.view.MotionEvent
import android.widget.TextView
import java.util.Random
import android.os.Handler
import android.widget.Button

class Detector_Juego : AppCompatActivity(),
    GestureDetector.OnGestureListener,
    GestureDetector.OnDoubleTapListener {

    private lateinit var TextView: TextView
    private lateinit var mTextView: TextView
    private lateinit var gestureDetector: GestureDetector

    private var random = Random()

    val palabra = arrayOf("onSingleTapConfirmed","onDoubleTap","onDoubleTapEvent",
                          "onDown","onShowPress","onSingleTapUp",
                          "onScroll","onLongPress","onFling")

    private var palabraActual: String = ""
    private var juegoTerminado = false

    private lateinit var mediaPlayerV: MediaPlayer
    private lateinit var mediaPlayerD: MediaPlayer
    private lateinit var yes: MediaPlayer
    private lateinit var no: MediaPlayer
    var Puntaje = 0
    val Tiempo= 600L

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detector_juego)

        mediaPlayerV = MediaPlayer.create(this, R.raw.victoria)
        mediaPlayerD = MediaPlayer.create(this, R.raw.derrota)
        yes = MediaPlayer.create(this, R.raw.yes)
        no = MediaPlayer.create(this, R.raw.no)

        TextView = findViewById(R.id.Puntaje)
        mTextView = findViewById(R.id.mobimineto)
        gestureDetector = GestureDetector(this, this)
        gestureDetector.setOnDoubleTapListener(this)
        mostrarPalabra()

    }

    private fun mostrarPalabra(){

        val buttonOKAbout = findViewById<Button>(R.id.Reinisio)

        if(!juegoTerminado && Puntaje<=5){
            val numeroAleatorio = random.nextInt(palabra.size)
            palabraActual = palabra[numeroAleatorio]
            mTextView.text = palabraActual
            TextView.text = "Puntaje:"+Puntaje
            buttonOKAbout.setOnClickListener {
                Puntaje=0
                juegoTerminado=false
            }
        }
        //no se toma{
        else if(juegoTerminado==true && Puntaje<=5)
        {
            mTextView.text = "Derrota"
            mediaPlayerD.start()
            buttonOKAbout.setOnClickListener {
                Puntaje=0
                juegoTerminado=false
            }
        }
        //}
        else if(Puntaje>=5)
        {
            juegoTerminado=true
            mTextView.text = "Victoria"
            mediaPlayerV.start()
            buttonOKAbout.setOnClickListener {
                Puntaje=0
                juegoTerminado=false
            }
        }
    }
    override fun onTouchEvent(event: MotionEvent): Boolean {
        gestureDetector.onTouchEvent(event)
        return super.onTouchEvent(event)
    }

    override fun onSingleTapConfirmed(motionEvent: MotionEvent): Boolean {
        val tiempoDeEspera0 = 1000L
        val tiempoDeEspera1 = 3000L
        var Accion = false
        Handler().postDelayed({
        if(palabraActual=="onSingleTapConfirmed"){
            yes.start()
            Puntaje++
            Handler().postDelayed({
                Accion=true
                mostrarPalabra()
            }, tiempoDeEspera0)
        }
        else if(!Accion ){
            Handler().postDelayed({
                no.start()
            }, tiempoDeEspera1)
        }},Tiempo)
        return true
    }

    override fun onDoubleTap(motionEvent: MotionEvent): Boolean {
        val tiempoDeEspera0 = 1000L
        val tiempoDeEspera1 = 3000L
        var Accion = false
        Handler().postDelayed({
        if(palabraActual=="onDoubleTap"){
            yes.start()
            Puntaje++
            Handler().postDelayed({
                Accion=true
                mostrarPalabra()
            }, tiempoDeEspera0)
        }
        else if(!Accion){
            Handler().postDelayed({
                no.start()
            }, tiempoDeEspera1)
        }},Tiempo)
        return true
    }

    override fun onDoubleTapEvent(motionEvent: MotionEvent): Boolean {
        val tiempoDeEspera0 = 1000L
        val tiempoDeEspera1 = 3000L
        var Accion = false
        Handler().postDelayed({
        if(palabraActual=="onDoubleTapEvent"){
            yes.start()
            Puntaje++
            Handler().postDelayed({
                Accion=true
                mostrarPalabra()
            }, tiempoDeEspera0)
        }
        else if(!Accion ){
            Handler().postDelayed({
                no.start()
            }, tiempoDeEspera1)
        }},Tiempo)
        return true
    }

    override fun onDown(motionEvent: MotionEvent): Boolean {
        val tiempoDeEspera0 = 1000L
        val tiempoDeEspera1 = 3000L
        var Accion = false
        Handler().postDelayed({
        if(palabraActual=="onDown"){
            yes.start()
            Puntaje++
            Handler().postDelayed({
                Accion=true
                mostrarPalabra()
            }, tiempoDeEspera0)
        }
        else if(!Accion){
            Handler().postDelayed({
                no.start()
            }, tiempoDeEspera1)
        }},Tiempo)
        return true
    }

    override fun onShowPress(motionEvent: MotionEvent) {
        val tiempoDeEspera0 = 1000L
        val tiempoDeEspera1 = 3000L
        var Accion = false
        Handler().postDelayed({
        if(palabraActual=="onShowPress"){
            yes.start()
            Puntaje++
            Handler().postDelayed({
                Accion=true
                mostrarPalabra()
            }, tiempoDeEspera0)
        }
        else if(!Accion){
            Handler().postDelayed({
                no.start()
            }, tiempoDeEspera1)
        }},Tiempo)
    }

    override fun onSingleTapUp(motionEvent: MotionEvent): Boolean {
        val tiempoDeEspera0 = 1000L
        val tiempoDeEspera1 = 3000L
        var Accion = false
        Handler().postDelayed({
        if(palabraActual=="onSingleTapUp"){
            yes.start()
            Puntaje++
            Handler().postDelayed({
                Accion=true
                mostrarPalabra()
            }, tiempoDeEspera0)
        }
        else if(!Accion){
            Handler().postDelayed({
                no.start()
            }, tiempoDeEspera1)
        }},Tiempo)
        return true
    }

    override fun onScroll(motionEvent: MotionEvent, motionEvent1: MotionEvent, v: Float, v1: Float): Boolean {
        val tiempoDeEspera0 = 1000L
        val tiempoDeEspera1 = 3000L
        var Accion = false
        Handler().postDelayed({
        if(palabraActual=="onScroll"){
            yes.start()
            Puntaje++
            Handler().postDelayed({
                Accion=true
                mostrarPalabra()
            }, tiempoDeEspera0)
        }
        else if(!Accion){
            Handler().postDelayed({
                no.start()
            }, tiempoDeEspera1)
        }},Tiempo)
        return true
    }

    override fun onLongPress(motionEvent: MotionEvent) {
        val tiempoDeEspera0 = 1000L
        val tiempoDeEspera1 = 3000L
        var Accion = false
        Handler().postDelayed({
        if(palabraActual=="onLongPress"){
            yes.start()
            Puntaje++
            Handler().postDelayed({
                Accion=true
                mostrarPalabra()
            }, tiempoDeEspera0)
        }
        else if(!Accion){
            Handler().postDelayed({
                no.start()
            }, tiempoDeEspera1)
        }},Tiempo)
    }

    override fun onFling(motionEvent: MotionEvent, motionEvent1: MotionEvent, v: Float, v1: Float): Boolean {
        val tiempoDeEspera0 = 1000L
        val tiempoDeEspera1 = 3000L
        var Accion = false
        Handler().postDelayed({
        if (palabraActual == "onFling") {
            yes.start()
            Puntaje++
            Handler().postDelayed({
                Accion = true
                mostrarPalabra()
            }, tiempoDeEspera0)
        }
        if (!Accion) {
            Handler().postDelayed({
                no.start()
            }, tiempoDeEspera1)
        }},Tiempo)
        return true
    }

    }