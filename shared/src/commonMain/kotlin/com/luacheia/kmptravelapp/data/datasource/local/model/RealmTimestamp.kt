package com.luacheia.kmptravelapp.data.datasource.local.model

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

class RealmTimestamp(
    @PrimaryKey
    var id: String = "",
    var time: Int = 0
) : RealmObject {
    constructor() : this(
        "",
        0
    )
}