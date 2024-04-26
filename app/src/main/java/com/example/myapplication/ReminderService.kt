package com.example.myapplication


import android.app.IntentService
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat

class ReminderService : IntentService("ReminderService") {

    override fun onHandleIntent(intent: Intent?) {
        // Handle the reminder logic here
        showReminderNotification()
    }

    private fun showReminderNotification() {
        // Create a unique channel ID for the reminder notification
        val channelId = "reminder_channel"
        val notificationId = 1 // Unique ID for the notification

        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Create a notification channel for Android Oreo and higher
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Reminder Channel",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(channel)
        }

        // Intent to launch when the user taps the notification
        val notificationIntent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            notificationIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE // Flags for pending intent
        )

        // Build the notification
        val notification = NotificationCompat.Builder(this, channelId)
            .setContentTitle("Reminder") // Title of the notification
            .setContentText("Don't forget to track your fitness activities!") // Content text
            .setSmallIcon(R.drawable.notification_icon) // Small icon displayed in the notification bar
            .setContentIntent(pendingIntent) // Intent to launch on notification tap
            .setAutoCancel(true) // Automatically cancel the notification when tapped
            .build()

        // Show the notification
        notificationManager.notify(notificationId, notification)
    }
}

