package com.abelvolpi.kmptravelapp.android.app.utils

import android.content.Context
import android.content.pm.PackageManager
import android.widget.Toast

fun Context.checkAppIsInstalled(appPackage: String): Boolean = try {
    packageManager.getPackageInfo(appPackage, 0)
    true
} catch (e: PackageManager.NameNotFoundException) {
    false
}

fun Context.showToast(message: String) {
    Toast.makeText(
        this,
        message,
        Toast.LENGTH_LONG
    ).show()
}
