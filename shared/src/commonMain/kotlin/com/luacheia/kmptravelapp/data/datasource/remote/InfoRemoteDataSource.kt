package com.luacheia.kmptravelapp.data.datasource.remote

import com.luacheia.kmptravelapp.data.model.Info
import dev.gitlive.firebase.firestore.DocumentSnapshot
import dev.gitlive.firebase.firestore.FirebaseFirestore
import dev.gitlive.firebase.firestore.where
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class InfoRemoteDataSource(
    firebaseFirestore: FirebaseFirestore
) : BaseRemoteDataSource<Info>(firebaseFirestore, INFOS) {

    override fun parseDocument(document: DocumentSnapshot): Info =
        document.data(Info.serializer()).apply {
            id = document.id
        }

    fun getInfo(keyValue: String): Flow<Info?> = flow {
        try {
            val infosCollectionReference = firebaseFirestore.collection(collectionName)
            val infosResponse = infosCollectionReference.where(KEY_FIELD, keyValue).get()
            val info = infosResponse.documents.firstOrNull()?.let { parseDocument(it) }
            emit(info)
        } catch (error: Exception) {
            println(error)
            emit(null)
        }
    }

    companion object {
        private const val INFOS = "infos"
        private const val KEY_FIELD = "key"
    }
}
