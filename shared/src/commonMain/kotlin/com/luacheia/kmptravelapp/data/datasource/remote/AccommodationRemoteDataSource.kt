package com.luacheia.kmptravelapp.data.datasource.remote

import com.luacheia.kmptravelapp.data.model.Accommodation
import dev.gitlive.firebase.firestore.DocumentSnapshot
import dev.gitlive.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class AccommodationRemoteDataSource(
    firebaseFirestore: FirebaseFirestore
) : BaseRemoteDataSource<Accommodation>(firebaseFirestore, ACCOMMODATIONS) {

    override fun parseDocument(document: DocumentSnapshot): Accommodation =
        document.data(Accommodation.serializer()).apply {
            id = document.id
        }

    fun createAccommodation(accommodation: Accommodation): Flow<Boolean> = flow {
        try {
            val documentReference = firebaseFirestore.collection(ACCOMMODATIONS).add(accommodation)
            accommodation.id = documentReference.id
            documentReference.set(accommodation)
            emit(true)
        } catch (error: Exception) {
            println(error)
            emit(false)
        }
    }

    fun updateAccommodation(accommodation: Accommodation): Flow<Boolean> = flow {
        try {
            firebaseFirestore.collection(ACCOMMODATIONS).document(accommodation.id).set(accommodation)
            emit(true)
        } catch (error: Exception) {
            println(error)
            emit(false)
        }
    }

    fun deleteAccommodation(accommodationId: String): Flow<Boolean> = flow {
        try {
            firebaseFirestore.collection(ACCOMMODATIONS).document(accommodationId).delete()
            emit(true)
        } catch (error: Exception) {
            println(error)
            emit(false)
        }
    }

//    fun getItemById(id: String): Flow<Accommodation?> = flow {
//        try {
//            val document = firebaseFirestore.collection(ACCOMMODATIONS).document(id).get()
//            if (document.exists) {
//                val accommodation = document.data(Accommodation.serializer())
//                accommodation.id = document.id
//                emit(accommodation)
//            } else {
//                emit(null)
//            }
//        } catch (error: Exception) {
//            println(error)
//            emit(null)
//        }
//    }

    companion object {
        private const val ACCOMMODATIONS = "accommodations"
    }
}
