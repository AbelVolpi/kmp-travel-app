package com.luacheia.kmptravelapp.data.datasource.local

import com.luacheia.kmptravelapp.data.datasource.local.model.RealmCategory
import com.luacheia.kmptravelapp.data.model.Category
import io.realm.kotlin.Realm

class CategoryLocalDataSource(
    private val realm: Realm
) {

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
