package com.abelvolpi.kmptravelapp.data.datasource.remote

import com.abelvolpi.kmptravelapp.data.model.Info
import dev.gitlive.firebase.firestore.FirebaseFirestore
import dev.gitlive.firebase.firestore.where
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class InfoRemoteDataSource(
    private val firebaseFirestore: FirebaseFirestore
) {

    fun getInfo(
        field: String,
        value: String
    ): Flow<Info> = flow {
        try {
            val infosCollectionReference = firebaseFirestore.collection(INFOS)
            val infosResponse = infosCollectionReference.where(field, value).get()
            val infosResponseData: Info = infosResponse.documents.first().data()
            emit(infosResponseData)
        } catch (error: Exception) {
            println(error)
            emit(Info())
        }
    }

    companion object {
        private const val INFOS = "infos"
    }
}