package com.luacheia.kmptravelapp.data.datasource.local

import com.luacheia.kmptravelapp.data.datasource.local.model.RealmTimestamp
import io.realm.kotlin.Realm
import io.realm.kotlin.UpdatePolicy
import io.realm.kotlin.ext.query

// TODO SWITCH TO DATASTORE
class TimeLocalDataSource(
    private val realm: Realm
) {

    suspend fun saveLastUpdatedTimeStamp(time: Int) {
        val realmTimeStamp = RealmTimestamp(
            id = "lastUpdated",
            time = time
        )
        realm.write {
            copyToRealm(realmTimeStamp, updatePolicy = UpdatePolicy.ALL)
        }
    }

    fun getLastUpdatedTimeStamp(): Int {
        val item: RealmTimestamp? = realm.query<RealmTimestamp>("id = $0", "lastUpdated").find().firstOrNull()
        return item?.time ?: 0
    }

    suspend fun deleteLastUpdatedTimeStamp() {
        realm.write {
            val item: RealmTimestamp? = query<RealmTimestamp>("id = $0", "lastUpdated").find().firstOrNull()
            if (item != null) {
                delete(item)
            }
        }
    }
}