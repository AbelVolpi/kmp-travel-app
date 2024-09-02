package com.abelvolpi.kmptravelapp.android.app.utils

import android.content.Context
import android.content.pm.PackageManager

fun Context.checkAppIsInstalled(appPackage: String): Boolean {
    return try {
        packageManager.getPackageInfo(appPackage, 0)
        true
    } catch (e: PackageManager.NameNotFoundException) {
        false
    }
}