package com.example.juego_bopit

import android.annotation.SuppressLint
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.GestureDetector
import android.view.MotionEvent
import android.widget.TextView
import java.util.Random

class Detector_Juego : AppCompatActivity(),
    GestureDetector.OnGestureListener,
    GestureDetector.OnDoubleTapListener {

    private lateinit var mTextView: TextView
    private lateinit var gestureDetector: GestureDetector

    private var random = Random()

    val palabra = arrayOf("onSingleTapConfirmed","onDoubleTap","onDoubleTapEvent",
                          "onDown","onShowPress","onSingleTapUp",
                          "onScroll","onLongPress","onFling")

    private var palabraActual: String = ""
    private var palabrasUsadas = mutableSetOf<String>()
    private var juegoTerminado = false

    private lateinit var mediaPlayerV: MediaPlayer
    private lateinit var mediaPlayerD: MediaPlayer
    private lateinit var yes: MediaPlayer
    private lateinit var no: MediaPlayer
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detector_juego)


        mediaPlayerV = MediaPlayer.create(this, R.raw.victoria)
        mediaPlayerD = MediaPlayer.create(this, R.raw.derrota)
        yes = MediaPlayer.create(this, R.raw.yes)
        no = MediaPlayer.create(this, R.raw.no)
        if(!juegoTerminado) {
            mTextView = findViewById(R.id.mobimineto)
            gestureDetector = GestureDetector(this, this)
            gestureDetector.setOnDoubleTapListener(this)
            mostrarPalabra()
        }
        else if(juegoTerminado==true)
        {
            mTextView.text = "Derrota"
            mediaPlayerD.start()
        }

    }

    private fun mostrarPalabra(){
        if(!juegoTerminado){
            val numeroAleatorio = random.nextInt(palabra.size)
            palabraActual = palabra[numeroAleatorio]
            mTextView.text = palabraActual
        }
    }
    override fun onTouchEvent(event: MotionEvent): Boolean {
        gestureDetector.onTouchEvent(event)
        return super.onTouchEvent(event)
    }

    override fun onSingleTapConfirmed(motionEvent: MotionEvent): Boolean {
        if(palabraActual=="onSingleTapConfirmed"){
            yes.start()
            mostrarPalabra()
        }
        else {
            no.start()
        }
        return true
    }

    override fun onDoubleTap(motionEvent: MotionEvent): Boolean {
        if(palabraActual=="onDoubleTap"){
            yes.start()
            mostrarPalabra()
        }
        else {
            no.start()
        }
        return true
    }

    override fun onDoubleTapEvent(motionEvent: MotionEvent): Boolean {
        if(palabraActual=="onDoubleTapEvent"){
            yes.start()
            mostrarPalabra()
        }
        else {
            no.start()
        }
        return true
    }

    override fun onDown(motionEvent: MotionEvent): Boolean {
        if(palabraActual=="onDown"){
            yes.start()
            mostrarPalabra()
        }
        else {
            no.start()
        }
        return true
    }

    override fun onShowPress(motionEvent: MotionEvent) {
        if(palabraActual=="onShowPress"){
            yes.start()
            mostrarPalabra()
        }
        else {
            no.start()
        }
    }

    override fun onSingleTapUp(motionEvent: MotionEvent): Boolean {
        if(palabraActual=="onSingleTapUp"){
            yes.start()
            mostrarPalabra()
        }
        else {
            no.start()
        }
        return true
    }

    override fun onScroll(motionEvent: MotionEvent, motionEvent1: MotionEvent, v: Float, v1: Float): Boolean {
        if(palabraActual=="onScroll"){
            yes.start()
            mostrarPalabra()
        }
        else{
            no.start()
        }
        return true
    }

    override fun onLongPress(motionEvent: MotionEvent) {
        if(palabraActual=="onLongPress"){
            yes.start()
            mostrarPalabra()
        }
        else{
            no.start()
        }
    }

    override fun onFling(motionEvent: MotionEvent, motionEvent1: MotionEvent, v: Float, v1: Float): Boolean {
        if(palabraActual=="onFling"){
            yes.start()
            mostrarPalabra()
        }
        else {
            no.start()
        }
        return true
    }

    }