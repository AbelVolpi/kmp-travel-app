package com.luacheia.kmptravelapp.data.datasource.local.model

import io.realm.kotlin.types.RealmObject

class RealmInfo(
    var id: String,
    var key: String,
    var value: String,
) : RealmObject {
    constructor() : this("", "", "")
}