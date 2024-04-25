package com.abelvolpi.kmptravelapp.data.datasource.local

import com.abelvolpi.kmptravelapp.data.datasource.local.model.RealmCategory
import com.abelvolpi.kmptravelapp.data.model.Category
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import io.realm.kotlin.query.RealmResults

class CategoryLocalDataSource(
    private val realm: Realm,
) {

    suspend fun getAllCategories(): List<Category> {
        val items: RealmResults<RealmCategory> = realm.query<RealmCategory>().find()
        return items.map { Category(it.name, it.iconUrl) }
    }

    suspend fun saveCategories(categories: List<Category>) {
        categories.forEach {
            val realmCategory = RealmCategory(it.name, it.iconUrl)
            realm.write {
                copyToRealm(realmCategory)
            }
        }
    }

    suspend fun deleteAllCategories() {
        realm.write {
            deleteAll()
        }
    }
}