package com.luacheia.kmptravelapp

import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.addressOf
import kotlinx.cinterop.convert
import kotlinx.cinterop.internal.ConstantValue
import kotlinx.cinterop.refTo
import kotlinx.cinterop.usePinned
import platform.CoreCrypto.CC_SHA256
import platform.CoreCrypto.CC_SHA256_DIGEST_LENGTH
import platform.Foundation.NSFileManager
import platform.Foundation.NSSearchPathForDirectoriesInDomains
import platform.Foundation.NSUserDomainMask
import platform.UIKit.UIDevice
import platform.Foundation.NSApplicationSupportDirectory
import platform.Foundation.NSURL
import platform.Foundation.NSURLSession
import platform.Foundation.dataTaskWithURL
import platform.Foundation.writeToURL

class IOSPlatform : Platform {
    override val name: String =
        UIDevice.currentDevice.systemName() + " " + UIDevice.currentDevice.systemVersion
}

actual fun getPlatform(): Platform = IOSPlatform()

actual fun getImageDownloader(): ImageDownloaderProtocol = IOSImageDownloader()

class IOSImageDownloader: ImageDownloaderProtocol {

    private val fileManager = NSFileManager.defaultManager()

    private fun createPath(): String? {
        val fileManager = NSFileManager.defaultManager
        val directory = NSSearchPathForDirectoriesInDomains(
            NSApplicationSupportDirectory,
            NSUserDomainMask,
            true
        ).firstOrNull()

        val folderPath = "$directory/Imagens pelo KMP"

        try {
            fileManager.createDirectoryAtPath(folderPath, mapOf<Any?, Unit>())
            return folderPath
        } catch (e: Exception) {
            println(e)
            return null
        }
    }

    override fun downloadAndSaveImage(url: String) {
        val folderPath = createPath() ?: return
        val urlSession = NSURLSession.sharedSession
        val imageUrl = NSURL(string = url)
        val task = urlSession.dataTaskWithURL(imageUrl) { data, _, error ->
            if (data != null && error == null) {
                val documentsDirectory = NSSearchPathForDirectoriesInDomains(
                    NSApplicationSupportDirectory,
                    NSUserDomainMask,
                    true
                ).first()

                val fileName = hashURL(url)
                val filePath = "$folderPath/$fileName.jpg"
                val fileURL = NSURL.fileURLWithPath(filePath)
                val wasSuccessful = data.writeToURL(fileURL, true)

                if (!wasSuccessful) {
                    println("Erro ao salvar imagem no caminho: $fileURL")
                }
            } else {
                println(error)
            }
        }
        task.resume()
    }

    @OptIn(ExperimentalForeignApi::class)
    fun hashURL(urlString: String): String {
        val data = urlString.encodeToByteArray()
        val hash = UByteArray(CC_SHA256_DIGEST_LENGTH)
        data.usePinned { CC_SHA256(it.addressOf(0), data.size.convert(), hash.refTo(0)) }
        return hash.joinToString("") { it.toString(16).padStart(2, '0') }
    }
}
