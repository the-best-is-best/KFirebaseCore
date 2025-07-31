package io.github.firebase_core

import com.google.firebase.Firebase
import com.google.firebase.app
import io.github.firebase_core.models.KFirebaseAppDetails
import io.github.firebase_core.models.KFirebaseOptions

actual object KFirebaseCore {
    actual fun app(): KFirebaseAppDetails {
        val appDetails = Firebase.app
        appDetails.options.gaTrackingId
        return KFirebaseAppDetails(
            name = appDetails.name,
            options = KFirebaseOptions(
                appIdentifier = appDetails.options.applicationId,
                apiKey = appDetails.options.apiKey,
                projectId = appDetails.options.projectId,
                databaseUrl = appDetails.options.databaseUrl,
                gcmSenderId = appDetails.options.gcmSenderId,
                storageBucket = appDetails.options.storageBucket,
            )
        )


    }
}