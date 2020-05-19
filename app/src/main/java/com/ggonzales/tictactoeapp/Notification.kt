package com.ggonzales.tictactoeapp

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class Notification(){
    val NOTIFICATION_TAG = "New request"
    fun notifyRequests(context: Context, message : String, number : Int){
        val intent = Intent(context, LoginActivity::class.java)
        val pendingIntent: PendingIntent = PendingIntent.getActivity(context, 0, intent, 0)

        var builder = NotificationCompat.Builder(context, "CHANNEL_ID")
            .setDefaults(Notification.DEFAULT_ALL)
            .setSmallIcon(R.drawable.tictactoe_icon2)
            .setContentTitle("Game invitation")
            .setContentText(message)
            .setNumber(number)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        val notManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notManager.notify(NOTIFICATION_TAG, 0, builder.build())

        Log.d("Notification sent", "Notification was sent")


    }
}
