package com.luacheia.kmptravelapp.data.datasource.local

import com.luacheia.kmptravelapp.data.datasource.local.model.RealmPlace
import com.luacheia.kmptravelapp.data.model.Place
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import io.realm.kotlin.ext.toRealmList
import io.realm.kotlin.query.RealmResults

class PlaceLocalDataSource(
    private val realm: Realm
) {
    suspend fun deleteAllPlaces() {
        realm.write {
            deleteAll()
        }
    }

    suspend fun savePlaces(places: List<Place>) {
        places.forEach {
            val realmPlace = RealmPlace(
                it.id,
                it.name,
                it.imageUrls.toRealmList(),
                it.description,
                it.address,
                it.city,
                it.categoryId,
                it.price
            )
            realm.write {
                copyToRealm(realmPlace)
            }
        }
    }

    fun getAllPlaces(): List<Place> {
        val items: RealmResults<RealmPlace> = realm.query<RealmPlace>().find()
        return items.map {
            Place(
                it.id,
                it.name,
                it.imageUrls,
                it.description,
                it.address,
                it.categoryId,
                it.price
            )
        }
    }

    fun getPlaceById(placeId: String): Place {
        val items: RealmResults<RealmPlace> = realm.query<RealmPlace>("id==$placeId").find()
        return items.map {
            Place(
                it.id,
                it.name,
                it.imageUrls,
                it.description,
                it.address,
                it.city,
                it.categoryId,
                it.price
            )
        }.first()
    }

    fun getPlacesByCategory(categoryId: String): List<Place> {
        val items: RealmResults<RealmPlace> = realm.query<RealmPlace>("id==$categoryId").find()
        return items.map {
            Place(
                it.id,
                it.name,
                it.imageUrls,
                it.description,
                it.address,
                it.city,
                it.categoryId,
                it.price
            )
        }
    }
}
