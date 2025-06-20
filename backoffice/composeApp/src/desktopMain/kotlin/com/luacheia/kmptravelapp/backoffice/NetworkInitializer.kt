package com.luacheia.kmptravelapp.backoffice

import android.app.Application
import com.google.firebase.FirebaseOptions
import com.google.firebase.FirebasePlatform
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.initialize
import java.io.File
import java.util.Properties

object NetworkInitializer {

    fun initFirebase() {
        println("Initializing Firebase")
        FirebasePlatform.initializeFirebasePlatform(object : FirebasePlatform() {
            val storage = mutableMapOf<String, String>()
            override fun store(key: String, value: String) = storage.set(key, value)
            override fun retrieve(key: String) = storage[key]
            override fun clear(key: String) {
                storage.remove(key)
            }

            override fun log(msg: String) = println("FIREBASE SDK: $msg")
            override fun getDatabasePath(name: String): File =
                File("${System.getProperty("java.io.tmpdir")}${File.separatorChar}$name")
        })

        val properties = loadFirebaseKeys()
        val options = FirebaseOptions.Builder()
            .setProjectId(properties["projectId"] as String)
            .setApplicationId(properties["applicationId"] as String)
            .setApiKey(properties["apiKey"] as String)
            .build()

        Firebase.initialize(Application(), options)
    }

    private fun loadFirebaseKeys(): Properties {
        val properties = Properties()
        val file = File("keys.properties")
        if (file.exists()) {
            file.inputStream().use { properties.load(it) }
        } else {
            throw IllegalStateException("Firebase keys file not found: ${file.absolutePath}")
        }
        return properties
    }
}