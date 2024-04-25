package com.example.myapplication

import android.app.AlarmManager
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.icu.util.Calendar
import android.os.Build
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

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