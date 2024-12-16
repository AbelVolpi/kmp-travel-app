package com.luacheia.kmptravelapp.data.datasource.local

import com.luacheia.kmptravelapp.data.datasource.local.model.RealmInfo
import com.luacheia.kmptravelapp.data.model.Info
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query

class InfoLocalDataSource(
    private val realm: Realm
) {
    suspend fun deleteAllInfos() {
        realm.write {
            deleteAll()
        }
    }

    suspend fun saveInfo(info: Info, tag: String) {
        val realmInfo = RealmInfo(
            info.id,
            info.key,
            info.value,
            tag,
        )
        realm.write {
            copyToRealm(realmInfo)
        }
    }

    fun getInfoByTag(tag: String): Info {
        val item: RealmInfo = realm.query<RealmInfo>("tag = $0", tag).find().first()
        return Info(
            item.id,
            item.key,
            item.value
        )
    }
}