package com.abelvolpi.kmptravelapp.data.datasource.remote

import com.abelvolpi.kmptravelapp.data.model.Info
import dev.gitlive.firebase.firestore.DocumentSnapshot
import dev.gitlive.firebase.firestore.FirebaseFirestore
import dev.gitlive.firebase.firestore.where
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class InfoRemoteDataSource(
    firebaseFirestore: FirebaseFirestore
) : BaseRemoteDataSource<Info>(firebaseFirestore, INFOS) {

    override fun parseDocument(document: DocumentSnapshot): Info {
        return document.data(Info.serializer()).apply {
            id = document.id
        }
    }

    fun getInfo(field: String, value: String): Flow<Info?> = flow {
        try {
            val infosCollectionReference = firebaseFirestore.collection(collectionName)
            val infosResponse = infosCollectionReference.where(field, value).get()
            val info = infosResponse.documents.firstOrNull()?.let { parseDocument(it) }
            emit(info)
        } catch (error: Exception) {
            println(error)
            emit(null)
        }
    }

    companion object {
        private const val INFOS = "infos"
    }
}
