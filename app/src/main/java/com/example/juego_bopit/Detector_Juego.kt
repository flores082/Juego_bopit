package com.example.juego_bopit

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.GestureDetector
import android.view.MotionEvent
import android.widget.EditText
import android.widget.TextView

class Detector_Juego : AppCompatActivity(),
    GestureDetector.OnGestureListener,
    GestureDetector.OnDoubleTapListener {

    private lateinit var mTextView: TextView
    private lateinit var gestureDetector: GestureDetector
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detector_juego)
        mTextView = findViewById(R.id.TextViewDetector)
        gestureDetector = GestureDetector(this, this)
        gestureDetector.setOnDoubleTapListener(this)

    }
    override fun onTouchEvent(event: MotionEvent): Boolean {
        gestureDetector.onTouchEvent(event)
        return super.onTouchEvent(event)
    }

    override fun onSingleTapConfirmed(motionEvent: MotionEvent): Boolean {
        mTextView.text = "onSingleTapConfirmed"
        return false
    }

    override fun onDoubleTap(motionEvent: MotionEvent): Boolean {
        mTextView.text = "onDoubleTap"
        return false
    }

    override fun onDoubleTapEvent(motionEvent: MotionEvent): Boolean {
        mTextView.text = "onDoubleTapEvent"
        return false
    }

    override fun onDown(motionEvent: MotionEvent): Boolean {
        mTextView.text = "onDown"
        return false
    }

    override fun onShowPress(motionEvent: MotionEvent) {
        mTextView.text = "onShowPress"
    }

    override fun onSingleTapUp(motionEvent: MotionEvent): Boolean {
        mTextView.text = "onSingleTapUp"
        return false
    }

    override fun onScroll(motionEvent: MotionEvent, motionEvent1: MotionEvent, v: Float, v1: Float): Boolean {
        mTextView.text = "onScroll"
        return false
    }

    override fun onLongPress(motionEvent: MotionEvent) {
        mTextView.text = "onLongPress"
    }

    override fun onFling(motionEvent: MotionEvent, motionEvent1: MotionEvent, v: Float, v1: Float): Boolean {
        mTextView.text = "onFling"
        return false
    }

    }