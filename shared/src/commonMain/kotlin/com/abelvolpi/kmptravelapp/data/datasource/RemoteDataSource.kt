package com.abelvolpi.kmptravelapp.data.datasource

import com.abelvolpi.kmptravelapp.data.model.Category
import com.abelvolpi.kmptravelapp.data.model.Place
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.firestore.FirebaseFirestore
import dev.gitlive.firebase.firestore.firestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class RemoteDataSource(
    private val firebaseFirestore: FirebaseFirestore
) {

//    private val firebaseFirestore = Firebase.firestore

    fun getCategories(): Flow<List<Category>> = flow {
        try {
            val categoriesResponse = firebaseFirestore.collection(CATEGORIES).get()
            emit(categoriesResponse.documents.map { it.data() })
        } catch (error: Exception) {
            throw error
        }
    }

    fun getPlaces(): Flow<List<Place>> = flow {
        try {
            val categoriesResponse = firebaseFirestore.collection(PLACES).get()
            emit(categoriesResponse.documents.map { it.data() })
        } catch (error: Exception) {
            throw error
        }
    }

    companion object {
        private const val CATEGORIES = "categories"
        private const val PLACES = "places"
    }
}