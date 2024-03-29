package com.abelvolpi.kmptravelapp

import com.abelvolpi.kmptravelapp.data.model.Category
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.firestore.firestore
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlin.random.Random
import kotlin.time.Duration.Companion.seconds

class Greeting {
    private val platform: Platform = getPlatform()

    fun greet(): Flow<String> = flow {
        emit(if (Random.nextBoolean()) "Hi!" else "Hello!")
        delay(1.seconds)
        emit("Guess what this is! > ${platform.name.reversed()}")
        delay(1.seconds)
    }

    fun getCategories(): Flow<List<Category>> = flow {
        val firebaseFirestore = Firebase.firestore
        try {
            val categoriesResponse = firebaseFirestore.collection("categories").get()
            emit(categoriesResponse.documents.map { it.data() })
        } catch (e: Exception) {
            throw e
        }
    }
}
