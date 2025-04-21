package com.luacheia.kmptravelapp.data.datasource.local

import com.luacheia.kmptravelapp.data.datasource.local.model.RealmGuidance
import com.luacheia.kmptravelapp.data.model.Guidance
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import io.realm.kotlin.query.RealmResults

class GuidanceLocalDataSource(
    private val realm: Realm
) {
    suspend fun deleteAllGuidelines() {
        realm.write {
            deleteAll()
        }
    }

    suspend fun saveGuidelines(guidelines: List<Guidance>) {
        guidelines.forEach {
            val realmGuidance = RealmGuidance(
                it.id,
                it.title,
                it.subtitle,
                it.iconUrl,
                it.description
            )
            realm.write {
                copyToRealm(realmGuidance)
            }
        }
    }

    fun getGuidanceById(guidanceId: String): Guidance {
        val items: RealmResults<RealmGuidance> = realm.query<RealmGuidance>("id==$guidanceId").find()
        return items.map {
            Guidance(
                it.id,
                it.title,
                it.subtitle,
                it.iconUrl,
                it.description
            )
        }.first()
    }

    fun getAllGuidelines(): List<Guidance> {
        val items: RealmResults<RealmGuidance> = realm.query<RealmGuidance>().find()
        return items.map {
            Guidance(
                it.id,
                it.title,
                it.subtitle,
                it.iconUrl,
                it.description
            )
        }
    }
}