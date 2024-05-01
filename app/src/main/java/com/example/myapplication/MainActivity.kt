package com.example.myapplication


import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.icu.util.Calendar
import android.os.Build
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
        scheduleReminder()
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
    private fun scheduleReminder() {
        // Create an intent for the reminder
        val reminderIntent = Intent(this, ReminderService::class.java)
        val pendingIntent = PendingIntent.getService(
            this,
            0,
            reminderIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        // Set the alarm to trigger at 12:00 AM
        val triggerTimeMillis = getTriggerTimeFor12AM()
        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                triggerTimeMillis,
                pendingIntent
            )
        } else {
            alarmManager.setExact(
                AlarmManager.RTC_WAKEUP,
                triggerTimeMillis,
                pendingIntent
            )
        }
    }

    // Method to calculate the trigger time for 12:00 AM
    private fun getTriggerTimeFor12AM(): Long {
        val calendar = Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
            set(Calendar.HOUR_OF_DAY, 0) // 12:00 AM
            set(Calendar.MINUTE, 0) // 0 minutes
            set(Calendar.SECOND, 0) // 0 seconds
            // Optionally, add one day to ensure the alarm triggers at the next 12:00 AM
            add(Calendar.DAY_OF_YEAR, 1)
        }
        return calendar.timeInMillis
    }


}

