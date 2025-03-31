package io.github.firebase_core

import io.github.firebase_core.models.KFirebaseAppDetails
import io.github.firebase_core.models.KFirebaseOptions
import io.github.native.kfirebasecore.FIRApp
import kotlinx.cinterop.ExperimentalForeignApi

object IOSKFirebaseCore {
    @OptIn(ExperimentalForeignApi::class)
    fun initialization() {
        FIRApp.configure()
    }
}

actual object KFirebaseCore {
    @OptIn(ExperimentalForeignApi::class)
    actual fun app(): KFirebaseAppDetails {
        val appDetails = FIRApp.defaultApp()

        return KFirebaseAppDetails(
            name = appDetails?.name,
            options = KFirebaseOptions(
                apiKey = appDetails?.options?.APIKey,
                projectId = appDetails?.options?.projectID,
                databaseUrl = appDetails?.options?.databaseURL,
                gcmSenderId = appDetails?.options?.GCMSenderID,
                storageBucket = appDetails?.options?.storageBucket,
                trackingId = appDetails?.options?.trackingID
            )
        )
    }
}