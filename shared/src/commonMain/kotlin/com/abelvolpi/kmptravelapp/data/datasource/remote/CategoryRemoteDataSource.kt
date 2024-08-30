package com.abelvolpi.kmptravelapp.data.datasource.remote

import com.abelvolpi.kmptravelapp.data.model.Category
import dev.gitlive.firebase.firestore.DocumentSnapshot
import dev.gitlive.firebase.firestore.FirebaseFirestore

class CategoryRemoteDataSource(
    firebaseFirestore: FirebaseFirestore
) : BaseRemoteDataSource<Category>(firebaseFirestore, CATEGORIES) {

    override fun parseDocument(document: DocumentSnapshot): Category =
        document.data(Category.serializer()).apply {
            id = document.id
        }

    companion object {
        private const val CATEGORIES = "categories"
    }
}
