package com.galvezssr.pizarra.kernel

import com.google.firebase.messaging.FirebaseMessagingService

// This class receives all notifications coming from Firebase
class MessagingService: FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        super.onNewToken(token)
    }

}