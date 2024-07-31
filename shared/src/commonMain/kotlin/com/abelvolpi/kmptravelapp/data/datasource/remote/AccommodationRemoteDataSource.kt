package com.abelvolpi.kmptravelapp.data.datasource.remote

import com.abelvolpi.kmptravelapp.data.model.Accommodation
import dev.gitlive.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class AccommodationRemoteDataSource(
    private val firebaseFirestore: FirebaseFirestore
) {

    fun getAccommodations(): Flow<List<Accommodation>> = flow {
        try {
            val accommodationsResponse = firebaseFirestore.collection(ACCOMMODATIONS).get()
            val accommodationsResponseAccommodationsIds =
                accommodationsResponse.documents.map { it.id }
            val accommodationsResponseData: List<Accommodation> =
                accommodationsResponse.documents.map { documentSnapshot ->
                    documentSnapshot.data()
                }
            accommodationsResponseData.forEachIndexed { index, accommodation ->
                accommodation.id = accommodationsResponseAccommodationsIds[index]
            }
            emit(accommodationsResponseData)
        } catch (error: Exception) {
            println(error)
            emit(emptyList())
        }
    }

    companion object {
        private const val ACCOMMODATIONS = "accommodations"
    }
}