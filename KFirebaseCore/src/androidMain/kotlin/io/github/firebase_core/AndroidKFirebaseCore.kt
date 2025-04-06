package io.github.firebase_core

import android.content.Context
import com.google.firebase.FirebaseApp


object AndroidKFirebaseCore {
    fun initialization(context: Context) {
        FirebaseApp.initializeApp(context)
        }

    }