package com.liberty.dojontkotpreceiverandnotifier.core

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context

fun createNotificationChannel(context: Context) {
    // Get the system service responsible for notifications.
    // Using the generic getSystemService with NotificationManager::class.java
    // returns a NotificationManager instance.
    val notifManager = context.getSystemService(NotificationManager::class.java)

    // Create a NotificationChannel object.
    // id: unique string ID for this channel (used later when posting notifications)
    val channel = NotificationChannel(
        NotifChannel.CHANNEL_ID,
        NotifChannel.CHANNEL_NAME,
        NotificationManager.IMPORTANCE_HIGH
    ).apply { description = NotifChannel.CHANNEL_DESC }

    // Register the channel with the system.
    // If a channel with the same ID already exists, this call is safe and ignored.
    notifManager.createNotificationChannel(channel)
}