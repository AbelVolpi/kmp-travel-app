package com.abelvolpi.kmptravelapp

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform