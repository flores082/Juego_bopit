package com.example.juego_bopit

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button



class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val buttonOKAbout = findViewById<Button>(R.id.button)
        val buttonOKAbout2 = findViewById<Button>(R.id.button2)

        buttonOKAbout.setOnClickListener {
            val intentAbout = Intent(this, About::class.java)
            startActivity(intentAbout)
        }

        buttonOKAbout2.setOnClickListener {
            val intentAbout2 = Intent(this, activity_preferencias::class.java)
            startActivity(intentAbout2)
        }
    }
}