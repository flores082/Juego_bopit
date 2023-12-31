package com.example.juego_bopit

import android.annotation.SuppressLint
import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button



class MainActivity : AppCompatActivity() {

    private lateinit var mediaPlayer : MediaPlayer

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mediaPlayer = MediaPlayer.create(this, R.raw.samba)
        mediaPlayer.start()

        val buttonOKAbout = findViewById<Button>(R.id.button)
        val buttonOKAbout2 = findViewById<Button>(R.id.button2)
        val buttonOKAbout3 = findViewById<Button>(R.id.button4)
        val buttonOKAbout4 = findViewById<Button>(R.id.button5)
        val buttonOKAbout5 = findViewById<Button>(R.id.Sensor)

        buttonOKAbout.setOnClickListener {
            val intentAbout = Intent(this, About::class.java)
            startActivity(intentAbout)
        }
        buttonOKAbout2.setOnClickListener {
            val intentAbout2 = Intent(this, activity_preferencias::class.java)
            startActivity(intentAbout2)
        }
        buttonOKAbout3.setOnClickListener {
            val intentAbout3 = Intent(this, Musica::class.java)
            startActivity(intentAbout3)
            mediaPlayer.stop()
        }
        buttonOKAbout4.setOnClickListener {
            val intentAbout4 = Intent(this, Detector_Juego::class.java)
            startActivity(intentAbout4)
            mediaPlayer.stop()
        }
        buttonOKAbout5.setOnClickListener {
            val intentAbout5 = Intent(this, Sensor::class.java)
            startActivity(intentAbout5)
            mediaPlayer.stop()
        }
    }
}