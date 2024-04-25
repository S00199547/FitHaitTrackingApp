package com.example.myapplication


import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {


    private lateinit var btnTrackPage1: Button
    private lateinit var btnTrackPage2: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        /* ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }*/

        btnTrackPage1 = findViewById(R.id.btnTrackPage1)
        btnTrackPage2 = findViewById(R.id.btnTrackPage2)

        // Set click listener for the button
        btnTrackPage1.setOnClickListener {
            // Create an Intent to start the TrackPageActivity
            val intent = Intent(this, TrackFitnessActivity::class.java)
            // Start the activity
            startActivity(intent)
        }
        btnTrackPage2.setOnClickListener {
            // Create an Intent to start the TrackPageActivity
            val intent = Intent(this, TrackHabithealthActivity::class.java)
            // Start the activity
            startActivity(intent)
        }


    }
}