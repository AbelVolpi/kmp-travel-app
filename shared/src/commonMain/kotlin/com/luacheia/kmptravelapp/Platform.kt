package com.luacheia.kmptravelapp

interface Platform {
    val name: String
}

interface ImageDownloaderProtocol {
    fun downloadAndSaveImage(url: String): String
}

expect fun getPlatform(): Platform

// TODO move to a class
expect fun getImageDownloader(): ImageDownloaderProtocol

