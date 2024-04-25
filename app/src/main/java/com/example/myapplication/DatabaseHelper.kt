package com.example.myapplication

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {


        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "HealthTrackerDB"

        // Table names
        const val TABLE_EXERCISE = "exercise"
        const val TABLE_SLEEP = "sleep"
        const val TABLE_MEDITATION = "meditation"
        const val TABLE_HYDRATION = "hydration"

        // Common column names
        const val COLUMN_ID = "id"
        const val COLUMN_DATE = "date"
        const val COLUMN_TIME = "time"
        const val COLUMN_DURATION = "duration"

        // Exercise table column names
        const val COLUMN_EXERCISE_TYPE = "exercise_type"

        // Define other column names for sleep, meditation, and hydration tables
        const val COLUMN_AMOUNT = "amount"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createExerciseTable =
            "CREATE TABLE $TABLE_EXERCISE ($COLUMN_ID INTEGER PRIMARY KEY, $COLUMN_DATE TEXT, $COLUMN_TIME TEXT, $COLUMN_DURATION INTEGER, $COLUMN_EXERCISE_TYPE TEXT)"
        val createSleepTable =
            "CREATE TABLE $TABLE_SLEEP ($COLUMN_ID INTEGER PRIMARY KEY, $COLUMN_DATE TEXT, $COLUMN_TIME TEXT, $COLUMN_DURATION INTEGER)"
        val createMeditationTable =
            "CREATE TABLE $TABLE_MEDITATION ($COLUMN_ID INTEGER PRIMARY KEY, $COLUMN_DATE TEXT, $COLUMN_TIME TEXT, $COLUMN_DURATION INTEGER)"
        val createHydrationTable =
            "CREATE TABLE $TABLE_HYDRATION ($COLUMN_ID INTEGER PRIMARY KEY, $COLUMN_DATE TEXT, $COLUMN_TIME TEXT, $COLUMN_AMOUNT INTEGER)"

        // Create tables
        db.execSQL(createExerciseTable)
        db.execSQL(createSleepTable)
        db.execSQL(createMeditationTable)
        db.execSQL(createHydrationTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Drop older tables if existed
        db.execSQL("DROP TABLE IF EXISTS $TABLE_EXERCISE")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_SLEEP")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_MEDITATION")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_HYDRATION")
        // Create tables again
        onCreate(db)
    }


    // Method to add a new exercise entry
    fun addExerciseEntry(exerciseEntry: ExerciseEntry): Boolean {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COLUMN_DATE, exerciseEntry.date)
        values.put(COLUMN_TIME, exerciseEntry.time)
        values.put(COLUMN_DURATION, exerciseEntry.duration)
        values.put(COLUMN_EXERCISE_TYPE, exerciseEntry.exerciseType)

        val success = db.insert(TABLE_EXERCISE, null, values)
        db.close()
        return success != -1L
    }



    // Method to get all exercise entries
    fun getAllExerciseEntries(): ArrayList<ExerciseEntry> {
        val exerciseList = ArrayList<ExerciseEntry>()
        val selectQuery = "SELECT * FROM $TABLE_EXERCISE"
        val db = this.readableDatabase
        var cursor: Cursor? = null

        try {
            cursor = db.rawQuery(selectQuery, null)

            // Check if cursor is not null and contains data
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    // Retrieve column indices
                    val idIndex = cursor.getColumnIndex(COLUMN_ID)
                    val dateIndex = cursor.getColumnIndex(COLUMN_DATE)
                    val timeIndex = cursor.getColumnIndex(COLUMN_TIME)
                    val durationIndex = cursor.getColumnIndex(COLUMN_DURATION)
                    val exerciseTypeIndex = cursor.getColumnIndex(COLUMN_EXERCISE_TYPE)

                    // Check if indices are valid (not negative)
                    if (idIndex >= 0 && dateIndex >= 0 && timeIndex >= 0 && durationIndex >= 0 && exerciseTypeIndex >= 0) {
                        // Retrieve data from cursor using indices
                        val id = cursor.getInt(idIndex)
                        val date = cursor.getString(dateIndex)
                        val time = cursor.getString(timeIndex)
                        val duration = cursor.getInt(durationIndex)
                        val exerciseType = cursor.getString(exerciseTypeIndex)

                        // Create ExerciseEntry object and add to list
                        val exerciseEntry = ExerciseEntry(id, date, time, duration, exerciseType)
                        exerciseList.add(exerciseEntry)
                    } else {
                        // Handle invalid indices or missing columns
                        // Log error or handle gracefully
                        Log.e("getAllExerciseEntries", "Invalid column index or missing columns")
                    }
                } while (cursor.moveToNext())
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            // Close cursor and database connection
            cursor?.close()
            db.close()
        }

        return exerciseList
    }



    // Method to delete an exercise entry
    fun deleteExerciseEntry(exerciseId: Int): Int {
        val db = this.writableDatabase
        val selection = "$COLUMN_ID = ?"
        val selectionArgs = arrayOf(exerciseId.toString())
        // Delete row
        val deletedRows = db.delete(TABLE_EXERCISE, selection, selectionArgs)
        // Close database connection
        db.close()
        return deletedRows
    }
    // Method to add a new sleep entry
    fun addSleepEntry(sleepEntry: SleepEntry): Boolean {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_DATE, sleepEntry.date)
            put(COLUMN_TIME, sleepEntry.time)
            put(COLUMN_DURATION, sleepEntry.duration)
        }
        val success = db.insert(TABLE_SLEEP, null, values)
        db.close()
        return success != -1L
    }

    // Method to get all sleep entries
    fun getAllSleepEntries(): ArrayList<SleepEntry> {
        val sleepList = ArrayList<SleepEntry>()
        val selectQuery = "SELECT * FROM $TABLE_SLEEP"
        val db = this.readableDatabase
        var cursor: Cursor? = null

        try {
            cursor = db.rawQuery(selectQuery, null)

            // Check if cursor is not null and contains data
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    // Retrieve column indices
                    val idIndex = cursor.getColumnIndex(COLUMN_ID)
                    val dateIndex = cursor.getColumnIndex(COLUMN_DATE)
                    val timeIndex = cursor.getColumnIndex(COLUMN_TIME)
                    val durationIndex = cursor.getColumnIndex(COLUMN_DURATION)

                    // Check if indices are valid (not negative)
                    if (idIndex >= 0 && dateIndex >= 0 && timeIndex >= 0 && durationIndex >= 0) {
                        // Retrieve data from cursor using indices
                        val id = cursor.getInt(idIndex)
                        val date = cursor.getString(dateIndex)
                        val time = cursor.getString(timeIndex)
                        val duration = cursor.getInt(durationIndex)

                        // Create SleepEntry object and add to list
                        val sleepEntry = SleepEntry(id, date, time, duration)
                        sleepList.add(sleepEntry)
                    } else {
                        // Handle invalid indices or missing columns
                        // Log error or handle gracefully
                        Log.e("getAllSleepEntries", "Invalid column index or missing columns")
                    }
                } while (cursor.moveToNext())
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            // Close cursor and database connection
            cursor?.close()
            db.close()
        }

        return sleepList
    }

    // Method to delete a sleep entry
    fun deleteSleepEntry(sleepId: Int): Int {
        val db = this.writableDatabase
        val selection = "$COLUMN_ID = ?"
        val selectionArgs = arrayOf(sleepId.toString())
        // Delete row
        val deletedRows = db.delete(TABLE_SLEEP, selection, selectionArgs)
        // Close database connection
        db.close()
        return deletedRows
    }

    // Method to get the total duration of sleep entries in minutes
    fun getTotalSleepMinutes(): Int {
        val db = this.readableDatabase
        var totalMinutes = 0

        // Query to calculate the total duration of sleep entries
        val query = "SELECT SUM($COLUMN_DURATION) FROM $TABLE_SLEEP"

        // Execute the query
        val cursor = db.rawQuery(query, null)
        if (cursor != null && cursor.moveToFirst()) {
            // Retrieve the total duration from the cursor
            totalMinutes = cursor.getInt(0)
        }

        // Close cursor and database connection
        cursor?.close()
        db.close()

        return totalMinutes
    }

    // Method to add a new hydration entry
    fun addHydrationEntry(hydrationEntry: HydrationEntry): Boolean {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_DATE, hydrationEntry.date)
            put(COLUMN_TIME, hydrationEntry.time)
            put(COLUMN_AMOUNT, hydrationEntry.amount)
        }
        val success = db.insert(TABLE_HYDRATION, null, values)
        db.close()
        return success != -1L
    }

    // Method to get all hydration entries
    fun getAllHydrationEntries(): ArrayList<HydrationEntry> {
        val hydrationList = ArrayList<HydrationEntry>()
        val selectQuery = "SELECT * FROM $TABLE_HYDRATION"
        val db = this.readableDatabase
        var cursor: Cursor? = null

        try {
            cursor = db.rawQuery(selectQuery, null)

            // Check if cursor is not null and contains data
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    // Retrieve column indices
                    val idIndex = cursor.getColumnIndex(COLUMN_ID)
                    val dateIndex = cursor.getColumnIndex(COLUMN_DATE)
                    val timeIndex = cursor.getColumnIndex(COLUMN_TIME)
                    val amountIndex = cursor.getColumnIndex(COLUMN_AMOUNT)

                    // Check if indices are valid (not negative)
                    if (idIndex >= 0 && dateIndex >= 0 && timeIndex >= 0 && amountIndex >= 0) {
                        // Retrieve data from cursor using indices
                        val id = cursor.getInt(idIndex)
                        val date = cursor.getString(dateIndex)
                        val time = cursor.getString(timeIndex)
                        val amount = cursor.getInt(amountIndex)

                        // Create HydrationEntry object and add to list
                        val hydrationEntry = HydrationEntry(id, date, time, amount)
                        hydrationList.add(hydrationEntry)
                    } else {
                        // Handle invalid indices or missing columns
                        // Log error or handle gracefully
                        Log.e("getAllHydrationEntries", "Invalid column index or missing columns")
                    }
                } while (cursor.moveToNext())
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            // Close cursor and database connection
            cursor?.close()
            db.close()
        }

        return hydrationList
    }

    // Method to delete a hydration entry
    fun deleteHydrationEntry(hydrationId: Int): Int {
        val db = this.writableDatabase
        val selection = "$COLUMN_ID = ?"
        val selectionArgs = arrayOf(hydrationId.toString())
        // Delete row
        val deletedRows = db.delete(TABLE_HYDRATION, selection, selectionArgs)
        // Close database connection
        db.close()
        return deletedRows
    }
    // Inside the DatabaseHelper class

    // Method to add a new meditation entry
    fun addMeditationEntry(meditationEntry: MeditationEntry): Boolean {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_DATE, meditationEntry.date)
            put(COLUMN_TIME, meditationEntry.time)
            put(COLUMN_DURATION, meditationEntry.duration)
        }
        val success = db.insert(TABLE_MEDITATION, null, values)
        db.close()
        return success != -1L
    }

    // Method to get all meditation entries
    fun getAllMeditationEntries(): ArrayList<MeditationEntry> {
        val meditationList = ArrayList<MeditationEntry>()
        val selectQuery = "SELECT * FROM $TABLE_MEDITATION"
        val db = this.readableDatabase
        var cursor: Cursor? = null

        try {
            cursor = db.rawQuery(selectQuery, null)

            // Check if cursor is not null and contains data
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    // Retrieve column indices
                    val idIndex = cursor.getColumnIndex(COLUMN_ID)
                    val dateIndex = cursor.getColumnIndex(COLUMN_DATE)
                    val timeIndex = cursor.getColumnIndex(COLUMN_TIME)
                    val durationIndex = cursor.getColumnIndex(COLUMN_DURATION)

                    // Check if indices are valid (not negative)
                    if (idIndex >= 0 && dateIndex >= 0 && timeIndex >= 0 && durationIndex >= 0) {
                        // Retrieve data from cursor using indices
                        val id = cursor.getInt(idIndex)
                        val date = cursor.getString(dateIndex)
                        val time = cursor.getString(timeIndex)
                        val duration = cursor.getInt(durationIndex)

                        // Create MeditationEntry object and add to list
                        val meditationEntry = MeditationEntry(id, date, time, duration)
                        meditationList.add(meditationEntry)
                    } else {
                        // Handle invalid indices or missing columns
                        // Log error or handle gracefully
                        Log.e("getAllMeditationEntries", "Invalid column index or missing columns")
                    }
                } while (cursor.moveToNext())
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            // Close cursor and database connection
            cursor?.close()
            db.close()
        }

        return meditationList
    }
    // Method to delete a meditation entry
    fun deleteMeditationEntry(meditationId: Int): Int {
        val db = this.writableDatabase
        val selection = "$COLUMN_ID = ?"
        val selectionArgs = arrayOf(meditationId.toString())
        // Delete row
        val deletedRows = db.delete(TABLE_MEDITATION, selection, selectionArgs)
        // Close database connection
        db.close()
        return deletedRows
    }


}



