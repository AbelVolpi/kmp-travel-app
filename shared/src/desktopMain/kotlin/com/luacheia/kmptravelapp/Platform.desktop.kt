package com.luacheia.kmptravelapp

class DesktopPlatform : Platform {
    override val name: String = "Desktop"
}

actual fun getPlatform(): Platform = DesktopPlatform()

actual fun getImageDownloader(): ImageDownloaderProtocol {
    // TODO: Implement a desktop-specific image downloader
    return object : ImageDownloaderProtocol {
        override fun downloadAndSaveImage(url: String): String {
            // TODO: Add logic to download and save an image on the desktop
            println("Downloading image from: $url")
            return "path/to/downloaded/image.jpg"
        }
    }
}