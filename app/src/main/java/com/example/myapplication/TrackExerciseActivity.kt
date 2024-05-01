package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class TrackExerciseActivity : AppCompatActivity() {

    private lateinit var etExerciseDate: EditText
    private lateinit var etExerciseTime: EditText
    private lateinit var etExerciseDuration: EditText
    private lateinit var etExerciseType: EditText
    private lateinit var btnSaveExercise: Button
    private lateinit var btnback: Button


    private var exerciseGoal: Int = 0
    private var totalExerciseDuration: Int = 0

    private lateinit var dbHelper: DatabaseHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_track_exercise)
       /* ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }*/
        checkExerciseGoal()
        etExerciseDate = findViewById(R.id.etExerciseDate)
        etExerciseTime = findViewById(R.id.etExerciseTime)
        etExerciseDuration = findViewById(R.id.etExerciseDuration)
        etExerciseType = findViewById(R.id.etExerciseType)
        btnSaveExercise = findViewById(R.id.btnSaveExercise)
        btnback = findViewById(R.id.btnbackt)



        dbHelper = DatabaseHelper(this)


        // Set initial exercise goal (can be retrieved from database or user input)
        exerciseGoal = 30 // For demonstration, set exercise goal to 30 minutes

        // Set click listener for save button
        btnSaveExercise.setOnClickListener {
            saveExercise()

        }
    }
    private fun saveExercise() {
        val date = etExerciseDate.text.toString()
        val time = etExerciseTime.text.toString()
        val duration = etExerciseDuration.text.toString().toIntOrNull()
        val exerciseType = etExerciseType.text.toString()

        // Check if any field is empty
        if (date.isNotEmpty() && time.isNotEmpty() && duration != null && exerciseType.isNotEmpty()) {
            // Create an ExerciseEntry object
            val exerciseEntry = ExerciseEntry(
                id = 0, // Set id to 0 since it will be auto-generated by the database
                date = date,
                time = time,
                duration = duration,
                exerciseType = exerciseType
            )

            // Insert exercise entry into the database
            val success = dbHelper.addExerciseEntry(exerciseEntry)

            if (success) {
                // If insertion is successful, display a success message
                Toast.makeText(this, "Exercise entry saved successfully!", Toast.LENGTH_SHORT).show()
                // You can also add logic to clear input fields after saving the entry
                clearInputFields()
            } else {
                // If insertion fails, display an error message
                Toast.makeText(this, "Failed to save exercise entry.", Toast.LENGTH_SHORT).show()
            }

        } else {
            // If any field is empty, display a message to fill all fields
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


    private fun checkExerciseGoal() {
        if (totalExerciseDuration >= exerciseGoal) {
            Toast.makeText(this, "Congratulations! You've reached your exercise goal.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun clearInputFields() {
        etExerciseDate.text.clear()
        etExerciseTime.text.clear()
        etExerciseDuration.text.clear()
        etExerciseType.text.clear()


    }
}