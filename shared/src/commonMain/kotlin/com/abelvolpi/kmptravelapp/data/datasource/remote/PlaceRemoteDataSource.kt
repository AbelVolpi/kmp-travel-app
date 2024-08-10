package com.abelvolpi.kmptravelapp.data.datasource.remote

import com.abelvolpi.kmptravelapp.data.model.Place
import dev.gitlive.firebase.firestore.DocumentSnapshot
import dev.gitlive.firebase.firestore.FirebaseFirestore
import dev.gitlive.firebase.firestore.where
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class PlaceRemoteDataSource(
    firebaseFirestore: FirebaseFirestore
) : BaseRemoteDataSource<Place>(firebaseFirestore, PLACES) {

    override fun parseDocument(document: DocumentSnapshot): Place {
        return document.data(Place.serializer()).apply {
            id = document.id
        }
    }

    fun getPlacesByCategory(categoryId: String): Flow<List<Place>> = flow {
        try {
            val placesCollectionReference = firebaseFirestore.collection(collectionName)
            val placesResponse =
                placesCollectionReference.where(CATEGORY_ID_FIELD, categoryId).get()
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
            emit(emptyList())
        }
    }

    companion object {
        private const val PLACES = "places"
        private const val CATEGORY_ID_FIELD = "categoryId"
    }
}