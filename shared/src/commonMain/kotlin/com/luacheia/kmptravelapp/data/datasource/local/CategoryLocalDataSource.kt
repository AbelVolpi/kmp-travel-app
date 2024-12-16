package com.luacheia.kmptravelapp.data.datasource.local

import com.luacheia.kmptravelapp.data.datasource.local.model.RealmCategory
import com.luacheia.kmptravelapp.data.model.Category
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import io.realm.kotlin.query.RealmResults

class CategoryLocalDataSource(
    private val realm: Realm
) {

    suspend fun saveCategories(categories: List<Category>) {
        categories.forEach {
            val realmCategory = RealmCategory(it.id, it.name, it.iconUrl)
            realm.write {
                copyToRealm(realmCategory)
            }
        }
    }

    fun getCategories(): List<Category> {
        val items: RealmResults<RealmCategory> = realm.query<RealmCategory>().find()
        return items.map {
            Category(
                it.id,
                it.name,
                it.iconUrl
            )
        }
    }

    suspend fun deleteAllCategories() {
        realm.write {
            deleteAll()
        }
    }
}
