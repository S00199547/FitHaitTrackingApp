package com.example.myapplication

import android.app.AlarmManager
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
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class TrackHabithealthActivity : AppCompatActivity() {


    private lateinit var btnSleep: Button
    private lateinit var btnHydration: Button


    private lateinit var listView2: ListView
    private lateinit var listView4: ListView


    private lateinit var tvHydrationPercentage: TextView
    private lateinit var tvSleepPercentage: TextView


    private lateinit var btnback: Button
    private lateinit var btntip2: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_track_habithealth)
        /* ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }*/

        // Initialize buttons

        btnSleep = findViewById(R.id.btnSleep)
        btnHydration = findViewById(R.id.btnHydration)


        listView2 = findViewById(R.id.listView2)
        listView4 = findViewById(R.id.listView4)



        tvHydrationPercentage = findViewById(R.id.tvHydrationPercentage)
        tvSleepPercentage = findViewById(R.id.tvSleepPercentage)


        // Set click listeners for buttons
        btnSleep.setOnClickListener {
            startActivity(Intent(this, TrackSleepActivity::class.java))
        }


        btnHydration.setOnClickListener {
            startActivity(Intent(this, TrackHydrationActivity::class.java))
        }

        // Retrieve  sleep,  hydration entries
        val dbHelper = DatabaseHelper(this)
        val sleepEntries = dbHelper.getAllSleepEntries()
        val hydrationEntries = dbHelper.getAllHydrationEntries()


        // Calculate percentage completion for sleep tracking (assuming 8 hours goal)
        val sleepGoal = 8 * 60 // 8 hours in minutes
        val sleepTotalMinutes = sleepEntries.sumBy { it.duration }
        val sleepPercentage = (sleepTotalMinutes.toDouble() / sleepGoal) * 100


        // Calculate percentage completion for hydration tracking (assuming 8 glasses goal)
        val hydrationGoal = 8 // 8 glasses goal
        val hydrationTotalGlasses = hydrationEntries.sumBy { it.amount }
        val hydrationPercentage = (hydrationTotalGlasses.toDouble() / hydrationGoal) * 100


        // Set the completion percentages in their respective TextViews

        tvSleepPercentage.text = "Sleep: ${sleepPercentage.toInt()}% completed"
        tvHydrationPercentage.text = "Hydration: ${hydrationPercentage.toInt()}% completed"

        // Display sleep, hydration entries in their respective ListViews

        val sleepAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            sleepEntries.map { it.toString() }
        )
        listView2.adapter = sleepAdapter

        val hydrationAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            hydrationEntries.map { it.toString() }
        )
        listView4.adapter = hydrationAdapter


        btnback = findViewById(R.id.btnbackt)
        btntip2 = findViewById(R.id.btntip2)

        //backbutton
        btnback.setOnClickListener {
            // Create an Intent to start the TrackPageActivity
            val intent = Intent(this, MainActivity::class.java)
            // Start the activity
            startActivity(intent)
        }

        //Tippagebutton
        btntip2.setOnClickListener {
            // Create an Intent to start the TrackPageActivity
            val intent = Intent(this, TipsActivity::class.java)
            // Start the activity
            startActivity(intent)
        }

        //Sleep
        // Set click listener for delete button
        listView2.setOnItemClickListener { parent, view, position, id ->
            val sleepEntryToDelete = sleepEntries[position] // Get sleep entry from the list

            // Delete sleep entry from the database
            val dbHelper = DatabaseHelper(this)
            val deletedRows = dbHelper.deleteSleepEntry(sleepEntryToDelete.id)

            if (deletedRows > 0) {
                // If entry is deleted from the database, remove it from the list and update the UI
                sleepEntries.removeAt(position)
                (listView2.adapter as ArrayAdapter<*>).notifyDataSetChanged()
                Toast.makeText(this, "Sleep entry deleted successfully", Toast.LENGTH_SHORT).show()
            } else {
                // If deletion fails, show an error message
                Toast.makeText(this, "Failed to delete sleep entry", Toast.LENGTH_SHORT).show()
            }
        }

        //Hydration
        // Set click listener for delete button
        listView4.setOnItemClickListener { parent, view, position, id ->
            val hydrationEntryToDelete =
                hydrationEntries[position] // Get hydration entry from the list

            // Delete hydration entry from the database
            val dbHelper = DatabaseHelper(this)
            val deletedRows = dbHelper.deleteHydrationEntry(hydrationEntryToDelete.id)

            if (deletedRows > 0) {
                // If entry is deleted from the database, remove it from the list and update the UI
                hydrationEntries.removeAt(position)
                (listView4.adapter as ArrayAdapter<*>).notifyDataSetChanged()
                Toast.makeText(this, "Hydration entry deleted successfully", Toast.LENGTH_SHORT)
                    .show()
            } else {
                // If deletion fails, show an error message
                Toast.makeText(this, "Failed to delete hydration entry", Toast.LENGTH_SHORT)
                    .show()
            }
        }

    }
}