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
// TODO REVIEW DUPLICATION WITH ID
    suspend fun saveInfo(info: Info) {
        val realmInfo = RealmInfo(
            info.id,
            info.key,
            info.value,
        )
        realm.write {
            copyToRealm(realmInfo)
        }
    }

    fun getInfoByKey(key: String): Info {
        val item: RealmInfo = realm.query<RealmInfo>("key = $0", key).find().first()
        return Info(
            item.id,
            item.key,
            item.value
        )
    }
}