package com.luacheia.kmptravelapp.data.datasource.remote

import com.luacheia.kmptravelapp.data.model.Category
import dev.gitlive.firebase.firestore.DocumentSnapshot
import dev.gitlive.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class CategoryRemoteDataSource(
    firebaseFirestore: FirebaseFirestore
) : BaseRemoteDataSource<Category>(firebaseFirestore, CATEGORIES) {

    override fun parseDocument(document: DocumentSnapshot): Category =
        document.data(Category.serializer()).apply {
            id = document.id
        }

    fun createCategory(category: Category): Flow<Boolean> = flow {
        try {
            firebaseFirestore.collection(CATEGORIES).add(category)
            emit(true)
        } catch (error: Exception) {
            println(error)
            emit(false)
        }
    }

    fun updateCategory(category: Category): Flow<Boolean> = flow {
        try {
            firebaseFirestore.collection(CATEGORIES).document(category.id).set(category)
            emit(true)
        } catch (error: Exception) {
            println(error)
            emit(false)
        }
    }

    fun deleteCategory(categoryId: String): Flow<Boolean> = flow {
        try {
            firebaseFirestore.collection(CATEGORIES).document(categoryId).delete()
            emit(true)
        } catch (error: Exception) {
            println(error)
            emit(false)
        }
    }


    companion object {
        private const val CATEGORIES = "categories"
    }
}
