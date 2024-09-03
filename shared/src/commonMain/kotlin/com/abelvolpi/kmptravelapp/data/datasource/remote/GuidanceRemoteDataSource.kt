package com.abelvolpi.kmptravelapp.data.datasource.remote

import com.abelvolpi.kmptravelapp.data.model.Guidance
import dev.gitlive.firebase.firestore.DocumentSnapshot
import dev.gitlive.firebase.firestore.FirebaseFirestore

class GuidanceRemoteDataSource(
    firebaseFirestore: FirebaseFirestore
) : BaseRemoteDataSource<Guidance>(firebaseFirestore, GUIDANCES) {

    override fun parseDocument(document: DocumentSnapshot): Guidance =
        document.data(Guidance.serializer()).apply {
            id = document.id
        }

    companion object {
        private const val GUIDANCES = "guidances"
    }
}
