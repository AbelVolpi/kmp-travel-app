package com.luacheia.kmptravelapp.data.datasource.local.model

import io.realm.kotlin.types.RealmObject

class RealmGuidance(
    var id: String,
    var title: String,
    var subtitle: String,
    var iconUrl: String,
    var description: String
) : RealmObject {
    constructor() : this("", "", "", "", "")
}