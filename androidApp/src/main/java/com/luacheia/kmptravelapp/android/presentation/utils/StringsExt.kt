package com.luacheia.kmptravelapp.android.presentation.utils

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.buildAnnotatedString
import androidx.core.text.HtmlCompat

fun String.formatBreakLines() = this.replace("\\n", "\n")

fun String.toAnnotatedString(): AnnotatedString {
    return buildAnnotatedString {
        val html = HtmlCompat.fromHtml(this@toAnnotatedString, HtmlCompat.FROM_HTML_MODE_LEGACY)
        append(html.toString())
    }
}