package ru.netology.nmedia.fcm

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseInstanceIDService : FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        println(token)
    }

    override fun onMessageReceived(message: RemoteMessage) {
        println(message)
    }
}