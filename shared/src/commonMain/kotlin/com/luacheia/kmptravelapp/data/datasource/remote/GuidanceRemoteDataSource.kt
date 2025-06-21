package com.luacheia.kmptravelapp.data.datasource.remote

import com.luacheia.kmptravelapp.data.model.Guidance
import dev.gitlive.firebase.firestore.DocumentSnapshot
import dev.gitlive.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GuidanceRemoteDataSource(
    firebaseFirestore: FirebaseFirestore
) : BaseRemoteDataSource<Guidance>(firebaseFirestore, GUIDANCES) {

    override fun parseDocument(document: DocumentSnapshot): Guidance =
        document.data(Guidance.serializer()).apply {
            id = document.id
        }

    fun createGuidance(guidance: Guidance): Flow<Boolean> = flow {
        try {
            val documentReference = firebaseFirestore.collection(GUIDANCES).add(guidance)
            guidance.id = documentReference.id
            documentReference.set(guidance)
            emit(true)
        } catch (error: Exception) {
            println(error)
            emit(false)
        }
    }

    fun updateGuidance(guidance: Guidance): Flow<Boolean> = flow {
        try {
            firebaseFirestore.collection(GUIDANCES).document(guidance.id).set(guidance)
            emit(true)
        } catch (error: Exception) {
            println(error)
            emit(false)
        }
    }

    fun deleteGuidance(guidanceId: String): Flow<Boolean> = flow {
        try {
            firebaseFirestore.collection(GUIDANCES).document(guidanceId).delete()
            emit(true)
        } catch (error: Exception) {
            println(error)
            emit(false)
        }
    }

//    fun getItemById(id: String): Flow<Guidance?> = flow {
//        try {
//            val document = firebaseFirestore.collection(GUIDANCES).document(id).get()
//            if (document.exists) {
//                val guidance = document.data(Guidance.serializer())
//                guidance.id = document.id
//                emit(guidance)
//            } else {
//                emit(null)
//            }
//        } catch (error: Exception) {
//            println(error)
//            emit(null)
//        }
//    }

    companion object {
        private const val GUIDANCES = "guidances"
    }
}
