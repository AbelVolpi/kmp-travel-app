package com.abelvolpi.kmptravelapp.data.datasource.remote

import com.abelvolpi.kmptravelapp.data.model.Category
import dev.gitlive.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class CategoryRemoteDataSource(
    private val firebaseFirestore: FirebaseFirestore
) {

    fun getCategories(): Flow<List<Category>> = flow {
        try {
            val categoriesResponse = firebaseFirestore.collection(CATEGORIES).get()
            emit(categoriesResponse.documents.map { it.data() })
        } catch (error: Exception) {
            throw error
        }
    }

    companion object {
        private const val CATEGORIES = "categories"
    }
}