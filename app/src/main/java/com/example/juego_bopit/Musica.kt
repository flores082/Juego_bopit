package com.example.juego_bopit

import android.annotation.SuppressLint
import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Button
import android.widget.SeekBar
import android.widget.TextView
import java.util.Locale

class Musica : AppCompatActivity() {

    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var mediaPlayerV: MediaPlayer
    private lateinit var mediaPlayerD: MediaPlayer
    var i: Int = 0
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_musica)

        mediaPlayer = MediaPlayer.create(this, R.raw.samba)
        mediaPlayerV = MediaPlayer.create(this, R.raw.victoria)
        mediaPlayerD = MediaPlayer.create(this, R.raw.derrota)

        val buttonOKAbout1 = findViewById<Button>(R.id.Start)
        val buttonOKAbout2 = findViewById<Button>(R.id.Stop)
        val buttonOKAbout3 = findViewById<Button>(R.id.Victoria)
        val buttonOKAbout4 = findViewById<Button>(R.id.Derrota)

        buttonOKAbout1.setOnClickListener {
            i=0
            onStart()
        }
        buttonOKAbout2.setOnClickListener {
              onPause()
        }
        buttonOKAbout3.setOnClickListener {
            i=1
            onStart()
        }
        buttonOKAbout4.setOnClickListener {
            i=2
            onStart()
        }
    }

     override fun onStart() {
        super.onStart()
         when (i) {
             0 -> {
                 mediaPlayer.start()
             }
             1 -> {
                 mediaPlayerV.start()
             }
             2 -> {
                 mediaPlayerD.start()
             }
         }
    }

    override fun onPause() {
        super.onPause()
        mediaPlayer.pause()
    }

}
