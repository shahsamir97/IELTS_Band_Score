package com.test.ieltsmarkstoband

import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyService : FirebaseMessagingService() {
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        remoteMessage.notification
        showNotification(remoteMessage.notification!!.title, remoteMessage.notification!!.body)
    }

    fun showNotification(title: String?, message: String?) {
        val builder = NotificationCompat.Builder(this, "MyNotifications")
                .setContentTitle(title)
                .setSmallIcon(R.drawable.notification_icon)
                .setAutoCancel(true)
                .setContentText(message)
        val managerCompat = NotificationManagerCompat.from(this)
        managerCompat.notify(999, builder.build())
    }
}