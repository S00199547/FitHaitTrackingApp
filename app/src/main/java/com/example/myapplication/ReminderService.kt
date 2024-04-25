package com.example.myapplication

import android.app.IntentService
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.example.myapplication.R // Change "R" to your actual package name

class ReminderService : IntentService("ReminderService") {

    override fun onHandleIntent(intent: Intent?) {
        // Handle the reminder logic here
        showReminderNotification()
    }

    private fun showReminderNotification() {
        // Create and show a notification for the reminder
        val channelId = "reminder_channel"
        val notificationId = 1

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Create a notification channel for Android Oreo and higher
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Reminder Channel",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(channel)
        }

        val notificationIntent = Intent(this, TrackFitnessActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            notificationIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE // Add the FLAG_IMMUTABLE flag
        )

        val notification = NotificationCompat.Builder(this, channelId)
            .setContentTitle("Reminder")
            .setContentText("Don't forget to track your fitness activities!")
            .setSmallIcon(R.drawable.notification_icon)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(notificationId, notification)
    }
}