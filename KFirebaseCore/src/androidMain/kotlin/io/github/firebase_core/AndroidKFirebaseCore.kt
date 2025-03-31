package io.github.firebase_core

import android.app.Activity
import com.google.firebase.Firebase
import com.google.firebase.initialize
import java.lang.ref.WeakReference


object AndroidKFirebaseCore {
       private var activity: WeakReference<Activity?> = WeakReference(null)



       fun getActivity(): Activity {
            return activity.get()!!
        }

        fun initialization(activity: Activity) {
            Firebase.initialize(activity)
            this.activity = WeakReference(activity)
        }

    }