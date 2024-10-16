package com.luacheia.kmptravelapp

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform
