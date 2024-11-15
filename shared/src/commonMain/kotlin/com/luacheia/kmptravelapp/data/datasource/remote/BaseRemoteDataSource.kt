package com.luacheia.kmptravelapp.data.datasource.remote

import dev.gitlive.firebase.firestore.DocumentSnapshot
import dev.gitlive.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

abstract class BaseRemoteDataSource<T>(
    protected val firebaseFirestore: FirebaseFirestore,
    protected val collectionName: String
) {

    protected abstract fun parseDocument(document: DocumentSnapshot): T

    fun getItems(): Flow<List<T>> = flow {
        try {
            val response = firebaseFirestore.collection(collectionName).get()
            val items = response.documents.map { parseDocument(it) }
            emit(items)
        } catch (error: Exception) {
            println(error)
            emit(emptyList())
        }
    }

    fun getItemById(id: String): Flow<T?> = flow {
        try {
            val document = firebaseFirestore.collection(collectionName).document(id).get()
            emit(parseDocument(document))
        } catch (error: Exception) {
            println(error)
            emit(null)
        }
    }
}
