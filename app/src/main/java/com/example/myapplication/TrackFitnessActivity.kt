package com.example.myapplication


import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class TrackFitnessActivity : AppCompatActivity() {

    private lateinit var btnExercise: Button
    private lateinit var btnMeditation: Button


    private lateinit var listView1: ListView
    private lateinit var listView3: ListView


    private lateinit var tvExercisePercentage: TextView
    private lateinit var tvMeditationPercentage: TextView

    private lateinit var btnback: Button
    private lateinit var btntip1: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_track_fitness)
        /*  ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }*/

       // scheduleReminder()
        // Initialize buttons
        btnExercise = findViewById(R.id.btnExercise)
        btnMeditation = findViewById(R.id.btnMeditation)

        listView1 = findViewById(R.id.listView1)
        listView3 = findViewById(R.id.listView3)

        btnback = findViewById(R.id.btnbackt)
        btntip1 = findViewById(R.id.btntip1)



        tvExercisePercentage = findViewById(R.id.tvExercisePercentage)
        tvMeditationPercentage = findViewById(R.id.tvMeditationPercentage)


        // Set click listeners for buttons
        btnExercise.setOnClickListener {
            startActivity(Intent(this, TrackExerciseActivity::class.java))
        }

        btnMeditation.setOnClickListener {
            startActivity(Intent(this, TrackMeditationActivity::class.java))
        }

        // Retrieve exercise, meditation,entries
        val dbHelper = DatabaseHelper(this)
        val exerciseEntries = dbHelper.getAllExerciseEntries()
        val meditationEntries = dbHelper.getAllMeditationEntries()


        // Calculate percentage completion for exercise tracking (assuming 30 minutes goal)
        val exerciseGoal = 30 // 30 minutes goal
        val exerciseTotalMinutes = exerciseEntries.sumOf { it.duration }
        val exercisePercentage = (exerciseTotalMinutes.toDouble() / exerciseGoal) * 100


        // Calculate percentage completion for meditation tracking (assuming 20 minutes goal)
        val meditationGoal = 20 // 20 minutes goal
        val meditationTotalMinutes = meditationEntries.sumOf { it.duration }
        val meditationPercentage = (meditationTotalMinutes.toDouble() / meditationGoal) * 100


        // Set the completion percentages in their respective TextViews
        tvExercisePercentage.text = "Exercise: ${exercisePercentage.toInt()}% completed"
        tvMeditationPercentage.text = "Meditation: ${meditationPercentage.toInt()}% completed"


        // Display exercise, meditation entries in their respective ListViews
        val exerciseAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            exerciseEntries.map { it.toString() }
        )
        listView1.adapter = exerciseAdapter


        val meditationAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            meditationEntries.map { it.toString() }
        )
        listView3.adapter = meditationAdapter


        //backbutton
        btnback.setOnClickListener {
            // Create an Intent to start the TrackPageActivity
            val intent = Intent(this, MainActivity::class.java)
            // Start the activity
            startActivity(intent)
        }

        //Tippagebutton
        btntip1.setOnClickListener {
            // Create an Intent to start the TrackPageActivity
            val intent = Intent(this, TipsActivity::class.java)
            // Start the activity
            startActivity(intent)
        }


        //Exercise
        listView1.setOnItemClickListener { parent, view, position, id ->
            val exerciseEntryToDelete =
                exerciseEntries[position] // Get exercise entry from the list

            // Delete exercise entry from the database
            val deletedRows = dbHelper.deleteExerciseEntry(exerciseEntryToDelete.id)

            if (deletedRows > 0) {
                // If entry is deleted from the database, remove it from the list and update the UI
                exerciseEntries.removeAt(position)
                (listView1.adapter as ArrayAdapter<*>).notifyDataSetChanged()
                Toast.makeText(this, "Exercise entry deleted successfully", Toast.LENGTH_SHORT)
                    .show()
            } else {
                // If deletion fails, show an error message
                Toast.makeText(this, "Failed to delete exercise entry", Toast.LENGTH_SHORT).show()
            }
        }

        //Meditation
        // Set click listener for delete button
        listView3.setOnItemClickListener { parent, view, position, id ->
            val meditationEntryToDelete =
                meditationEntries[position] // Get meditation entry from the list

            // Delete meditation entry from the database
            val deletedRows = dbHelper.deleteMeditationEntry(meditationEntryToDelete.id)

            if (deletedRows > 0) {
                // If entry is deleted from the database, remove it from the list and update the UI
                meditationEntries.removeAt(position)
                (listView3.adapter as ArrayAdapter<*>).notifyDataSetChanged()
                Toast.makeText(this, "Meditation entry deleted successfully", Toast.LENGTH_SHORT)
                    .show()
            } else {
                // If deletion fails, show an error message
                Toast.makeText(this, "Failed to delete meditation entry", Toast.LENGTH_SHORT).show()
            }
        }


    }
}
