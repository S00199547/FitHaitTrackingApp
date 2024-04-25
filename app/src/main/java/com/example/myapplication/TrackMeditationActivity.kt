package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class TrackMeditationActivity : AppCompatActivity() {

    private lateinit var etMeditationDate: EditText
    private lateinit var etMeditationTime: EditText
    private lateinit var etMeditationDuration: EditText
    private lateinit var btnSaveMeditation: Button
    private lateinit var btnback: Button

    private lateinit var dbHelper: DatabaseHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_track_meditation)
       /* ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }*/
        etMeditationDate = findViewById(R.id.etMeditationDate)
        etMeditationTime = findViewById(R.id.etMeditationTime)
        etMeditationDuration = findViewById(R.id.etMeditationDuration)
        btnSaveMeditation = findViewById(R.id.btnSaveMeditation)
        btnback = findViewById(R.id.btnbackt)

        dbHelper = DatabaseHelper(this)

        btnSaveMeditation.setOnClickListener {
            saveMeditation()
        }
    }

    private fun saveMeditation() {
        val date = etMeditationDate.text.toString()
        val time = etMeditationTime.text.toString()
        val duration = etMeditationDuration.text.toString().toIntOrNull()

        if (date.isNotEmpty() && time.isNotEmpty() && duration != null) {
            val meditationEntry = MeditationEntry(0, date, time, duration)
            val success = dbHelper.addMeditationEntry(meditationEntry)

            if (success) {
                Toast.makeText(this, "Meditation entry saved successfully!", Toast.LENGTH_SHORT).show()
                clearInputFields()
            } else {
                Toast.makeText(this, "Failed to save meditation entry.", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
        }
        //backbutton
        btnback.setOnClickListener {
            // Create an Intent to start the TrackPageActivity
            val intent = Intent(this, TrackFitnessActivity::class.java)
            // Start the activity
            startActivity(intent)
        }
    }

    private fun clearInputFields() {
        etMeditationDate.text.clear()
        etMeditationTime.text.clear()
        etMeditationDuration.text.clear()



    }
}