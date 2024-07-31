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
            val categoriesResponseCategoriesIds = categoriesResponse.documents.map { it.id }
            val categoriesResponseData: List<Category> =
                categoriesResponse.documents.map { documentSnapshot ->
                    documentSnapshot.data()
                }
            categoriesResponseData.forEachIndexed { index, category ->
                category.id = categoriesResponseCategoriesIds[index]
            }
            emit(categoriesResponseData)
        } catch (error: Exception) {
            println(error)
            emit(emptyList())
        }
    }

    companion object {
        private const val CATEGORIES = "categories"
    }
}