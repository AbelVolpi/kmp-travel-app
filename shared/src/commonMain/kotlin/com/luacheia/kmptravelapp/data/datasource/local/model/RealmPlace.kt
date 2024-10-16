package com.luacheia.kmptravelapp.data.datasource.local.model

import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.types.RealmList
import io.realm.kotlin.types.RealmObject

class RealmPlace(
    var id: String,
    var name: String,
    var imageUrls: RealmList<String>,
    var description: String,
    var address: String,
    var categoryId: String,
    var price: String
) : RealmObject {
    constructor() : this(
        "",
        "",
        realmListOf(""),
        "",
        "",
        "",
        ""
    )
}
