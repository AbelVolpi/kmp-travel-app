package com.abelvolpi.kmptravelapp.data.datasource.remote

import com.abelvolpi.kmptravelapp.data.model.Accommodation
import dev.gitlive.firebase.firestore.DocumentSnapshot
import dev.gitlive.firebase.firestore.FirebaseFirestore

class AccommodationRemoteDataSource(
    firebaseFirestore: FirebaseFirestore
) : BaseRemoteDataSource<Accommodation>(firebaseFirestore, ACCOMMODATIONS) {

    override fun parseDocument(document: DocumentSnapshot): Accommodation =
        document.data(Accommodation.serializer()).apply {
            id = document.id
        }

    companion object {
        private const val ACCOMMODATIONS = "accommodations"
    }
}
