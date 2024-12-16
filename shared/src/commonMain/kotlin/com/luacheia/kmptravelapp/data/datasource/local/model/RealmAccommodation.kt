package com.luacheia.kmptravelapp.data.datasource.local.model

import io.realm.kotlin.types.RealmObject

class RealmAccommodation(
    var id: String,
    var title: String,
    var iconUrl: String,
    var link: String
) : RealmObject {
    constructor() : this("", "", "", "")
}