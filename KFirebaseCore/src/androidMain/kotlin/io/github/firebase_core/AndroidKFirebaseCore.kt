package io.github.firebase_core

import android.app.Activity
import com.google.firebase.Firebase
import com.google.firebase.initialize

object AndroidKFirebaseCore {

    fun initialize(activity: Activity) {
        Firebase.initialize(context = activity)
    }
}