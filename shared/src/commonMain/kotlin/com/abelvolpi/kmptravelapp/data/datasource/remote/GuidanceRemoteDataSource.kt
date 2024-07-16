package com.abelvolpi.kmptravelapp.data.datasource.remote

import com.abelvolpi.kmptravelapp.data.model.Guidance
import dev.gitlive.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GuidanceRemoteDataSource(
    private val firebaseFirestore: FirebaseFirestore
) {

    fun getGuidances(): Flow<List<Guidance>> = flow {
        try {
            val guidancesResponse = firebaseFirestore.collection(GUIDANCES).get()
            val guidancesResponseGuidancesIds =
                guidancesResponse.documents.map { it.id }
            val guidancesResponseData: List<Guidance> =
                guidancesResponse.documents.map { documentSnapshot ->
                    documentSnapshot.data()
                }
            guidancesResponseData.forEachIndexed { index, accommodation ->
                accommodation.id = guidancesResponseGuidancesIds[index]
            }
            emit(guidancesResponseData)
        } catch (error: Exception) {
            println(error)
            emit(emptyList())
        }
    }

    fun getGuidanceById(id: String): Flow<Guidance> = flow {
        try {
            val guidanceResponse = firebaseFirestore.collection(GUIDANCES).document(id).get()
            val guidancesResponseData: Guidance = guidanceResponse.data()
            guidancesResponseData.id = guidanceResponse.id
            emit(guidancesResponseData)
        } catch (error: Exception) {
            println(error)
            emit(Guidance())
        }
    }


    companion object {
        private const val GUIDANCES = "guidances"
    }
}