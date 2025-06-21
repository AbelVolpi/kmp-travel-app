package com.luacheia.kmptravelapp.data.datasource.remote

import com.luacheia.kmptravelapp.data.model.Place
import dev.gitlive.firebase.firestore.DocumentSnapshot
import dev.gitlive.firebase.firestore.FirebaseFirestore
import dev.gitlive.firebase.firestore.where
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class PlaceRemoteDataSource(
    firebaseFirestore: FirebaseFirestore
) : BaseRemoteDataSource<Place>(firebaseFirestore, PLACES) {

    override fun parseDocument(document: DocumentSnapshot): Place = document.data(Place.serializer()).apply {
        id = document.id
    }

    fun createPlace(place: Place): Flow<Boolean> = flow {
        try {
            val documentReference = firebaseFirestore.collection(PLACES).add(place)
            place.id = documentReference.id
            documentReference.set(place)
            emit(true)
        } catch (error: Exception) {
            println(error)
            emit(false)
        }
    }

    fun updatePlace(place: Place): Flow<Boolean> = flow {
        try {
            firebaseFirestore.collection(PLACES).document(place.id).set(place)
            emit(true)
        } catch (error: Exception) {
            println(error)
            emit(false)
        }
    }

    fun deletePlace(placeId: String): Flow<Boolean> = flow {
        try {
            firebaseFirestore.collection(PLACES).document(placeId).delete()
            emit(true)
        } catch (error: Exception) {
            println(error)
            emit(false)
        }
    }

//    fun getItemById(id: String): Flow<Place?> = flow {
//        try {
//            val document = firebaseFirestore.collection(PLACES).document(id).get()
//            if (document.exists) {
//                val place = document.data(Place.serializer())
//                place.id = document.id
//                emit(place)
//            } else {
//                emit(null)
//            }
//        } catch (error: Exception) {
//            println(error)
//            emit(null)
//        }
//    }

    fun getPlacesByCategory(categoryId: String): Flow<List<Place>> = flow {
        try {
            val placesCollectionReference = firebaseFirestore.collection(collectionName)
            val placesResponse = placesCollectionReference.where(CATEGORY_ID_FIELD, categoryId).get()
            val placesResponseDocumentsIds = placesResponse.documents.map { it.id }
            val placesResponseData: List<Place> = placesResponse.documents.map { it.data() }
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
