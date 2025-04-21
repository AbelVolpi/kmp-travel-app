package com.luacheia.kmptravelapp

interface Platform {
    val name: String
}

interface ImageDownloaderProtocol {
    fun downloadAndSaveImage(url: String)
}

expect fun getPlatform(): Platform

expect fun getImageDownloader(): ImageDownloaderProtocol

