package io.github.firebase_core.models

data class KFirebaseAppDetails(
    val name: String?,
    val options: KFirebaseOptions
)

data class KFirebaseOptions(
    val appIdentifier: String?,
    val apiKey: String?,
    val projectId: String?,
    val databaseUrl: String?,
    val gcmSenderId: String?,
    val storageBucket: String?,


    )