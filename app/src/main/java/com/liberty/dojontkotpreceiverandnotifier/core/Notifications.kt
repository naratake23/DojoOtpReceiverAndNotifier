package com.liberty.dojontkotpreceiverandnotifier.core

import android.Manifest
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.annotation.RequiresPermission
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.liberty.dojontkotpreceiverandnotifier.R
import com.liberty.dojontkotpreceiverandnotifier.ui.MainActivity

@RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
fun createOtpNotification(context: Context, code: String){
    Log.d("xxt", "createOtpNotification- 1")

    // Create an Intent to open MainActivity when user taps the notification.
    val intent = Intent(context, MainActivity::class.java).apply {
        // FLAG_ACTIVITY_NEW_TASK: start a new task if needed.
        // FLAG_ACTIVITY_CLEAR_TASK: clear any existing task so back-stack is clean.
        // Together: tapping notif always gives a fresh MainActivity.
        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
    }

    // Wrap the Intent in a PendingIntent: a token the system can use later
    // (when the user taps the notification) to launch the activity on your behalf.
    val pendingIntent = PendingIntent.getActivity(
        context,
        0, // requestCode: identifier (0 since it doesn't need multiple variations)
        intent,
        PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        // FLAG_IMMUTABLE: required on Android 12+ for security; intent cannot be changed.
        // FLAG_UPDATE_CURRENT: if a PendingIntent with same requestCode exists, update it.
    )

    // Build the notification itself using NotificationCompat (backwards-compatible).
    val notif = NotificationCompat.Builder(context, NotifChannel.CHANNEL_ID)
        .setSmallIcon(R.drawable.ic_launcher_foreground) // small icon shown in status bar
        .setContentTitle("New OTP Code")                 // bold title line
        .setContentText("You have a new code available: $code") // main text
        .setPriority(NotificationCompat.PRIORITY_HIGH)   // heads-up style notification
        .setAutoCancel(true) // notification disappears when tapped
        .setContentIntent(pendingIntent) // action: tap launches MainActivity
        .build()

    // Fire the notification through NotificationManagerCompat.
    // notify(id, notification):
    // id = 0: fixed identifier, replaces existing notif with same ID if re-used.
    NotificationManagerCompat.from(context).notify(0, notif)
}