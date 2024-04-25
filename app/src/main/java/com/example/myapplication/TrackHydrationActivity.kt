package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class TrackHydrationActivity : AppCompatActivity() {
    private lateinit var etHydrationDate: EditText
    private lateinit var etHydrationTime: EditText
    private lateinit var etHydrationAmount: EditText
    private lateinit var btnSaveHydration: Button
    private lateinit var btnback: Button
    private lateinit var dbHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_track_hydration)
      /*  ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }*/
        etHydrationDate = findViewById(R.id.etHydrationDate)
        etHydrationTime = findViewById(R.id.etHydrationTime)
        etHydrationAmount = findViewById(R.id.etHydrationAmount)
        btnSaveHydration = findViewById(R.id.btnSaveHydration)
        btnback = findViewById(R.id.btnbackt)

        dbHelper = DatabaseHelper(this)

        btnSaveHydration.setOnClickListener {
            saveHydration()
        }
    }

    private fun saveHydration() {
        val date = etHydrationDate.text.toString()
        val time = etHydrationTime.text.toString()
        val amount = etHydrationAmount.text.toString().toIntOrNull()

        if (date.isNotEmpty() && time.isNotEmpty() && amount != null) {
            val hydrationEntry = HydrationEntry(
                id = 0,
                date = date,
                time = time,
                amount = amount
            )

            val success = dbHelper.addHydrationEntry(hydrationEntry)

            if (success) {
                Toast.makeText(this, "Hydration entry saved successfully!", Toast.LENGTH_SHORT).show()
                clearInputFields()
            } else {
                Toast.makeText(this, "Failed to save hydration entry.", Toast.LENGTH_SHORT).show()
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
        etHydrationDate.text.clear()
        etHydrationTime.text.clear()
        etHydrationAmount.text.clear()



    }
}