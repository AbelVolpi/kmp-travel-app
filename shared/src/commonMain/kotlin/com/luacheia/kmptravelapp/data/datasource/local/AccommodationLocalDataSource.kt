package com.luacheia.kmptravelapp.data.datasource.local

import com.luacheia.kmptravelapp.data.datasource.local.model.RealmAccommodation
import com.luacheia.kmptravelapp.data.model.Accommodation
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import io.realm.kotlin.query.RealmResults

class AccommodationLocalDataSource(
    private val realm: Realm
) {
    suspend fun deleteAllAccommodations() {
        realm.write {
            deleteAll()
        }
    }

    suspend fun saveAccommodations(accommodations: List<Accommodation>) {
        accommodations.forEach {
            val realmAccommodation = RealmAccommodation(
                it.id,
                it.title,
                it.iconUrl,
                it.link,
            )
            realm.write {
                copyToRealm(realmAccommodation)
            }
        }
    }

    fun getAllAccommodations(): List<Accommodation> {
        val items: RealmResults<RealmAccommodation> = realm.query<RealmAccommodation>().find()
        print("\n\n")
        print(items)
        print("\n\n")
        return items.map {
            Accommodation(
                it.id,
                it.title,
                it.iconUrl,
                it.link
            )
        }
    }
}