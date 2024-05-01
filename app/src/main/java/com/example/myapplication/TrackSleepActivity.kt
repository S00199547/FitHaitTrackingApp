package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class TrackSleepActivity : AppCompatActivity() {

    private lateinit var btnSaveSleep: Button
    private lateinit var etSleepDate: EditText
    private lateinit var etSleepTime: EditText
    private lateinit var etSleepDuration: EditText
    private lateinit var btnback: Button

    private lateinit var dbHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_track_sleep)
      /*  ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }*/
        // Initialize views
        btnSaveSleep = findViewById(R.id.btnSaveSleep)
        etSleepDate = findViewById(R.id.etSleepDate)
        etSleepTime = findViewById(R.id.etSleepTime)
        etSleepDuration = findViewById(R.id.etSleepDuration)
        btnback = findViewById(R.id.btnbackt)

        dbHelper = DatabaseHelper(this)

        btnSaveSleep.setOnClickListener {
            saveSleep()
        }
    }

    private fun saveSleep() {
        val date = etSleepDate.text.toString()
        val time = etSleepTime.text.toString()
        val duration = etSleepDuration.text.toString().toIntOrNull()

        if (date.isNotEmpty() && time.isNotEmpty() && duration != null) {
            val sleepEntry = SleepEntry(
                id = 0,
                date = date,
                time = time,
                duration = duration
            )

            val success = dbHelper.addSleepEntry(sleepEntry)

            if (success) {
                Toast.makeText(this, "Sleep entry saved successfully!", Toast.LENGTH_SHORT).show()
                clearInputFields()
            } else {
                Toast.makeText(this, "Failed to save sleep entry.", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
        }
        //backbutton
        btnback.setOnClickListener {
            // Create an Intent to start the TrackPageActivity
            val intent = Intent(this, TrackHabithealthActivity::class.java)
            // Start the activity
            startActivity(intent)
        }
    }


    private fun clearInputFields() {
        etSleepDate.text.clear()
        etSleepTime.text.clear()
        etSleepDuration.text.clear()


    }
}