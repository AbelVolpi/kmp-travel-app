package com.abelvolpi.kmptravelapp.data.datasource.local.model

import io.realm.kotlin.types.RealmObject

class RealmCategory(
//    @PrimaryKey
//    var id: String,
    var name: String,
    var iconUrl: String
) : RealmObject {
    constructor() : this("", "")
}
