package com.abelvolpi.kmptravelapp.data.datasource.remote

import com.abelvolpi.kmptravelapp.data.model.Place
import dev.gitlive.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class PlaceRemoteDataSource(
    private val firebaseFirestore: FirebaseFirestore
) {
    fun getPlaces(): Flow<List<Place>> = flow {
        try {
            val placesResponse = firebaseFirestore.collection(PLACES).get()
            val placesResponseDocumentsIds = placesResponse.documents.map { it.id }
            val placesResponseData: List<Place> = placesResponse.documents.map { documentSnapshot ->
                documentSnapshot.data()
            }
            placesResponseData.forEachIndexed { index, place ->
                place.id = placesResponseDocumentsIds[index]
            }
            emit(placesResponseData)
        } catch (error: Exception) {
            println(error)
        }
    }

    companion object {
        private const val PLACES = "places"
    }
}