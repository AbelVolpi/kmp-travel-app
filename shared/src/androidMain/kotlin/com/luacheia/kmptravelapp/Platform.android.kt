package com.luacheia.kmptravelapp

import android.app.Application
import android.content.Context
import android.os.Environment
import java.io.File
import java.io.FileOutputStream
import java.net.HttpURLConnection
import java.net.URL
import java.security.MessageDigest

class AndroidPlatform : Platform {
    override val name: String = "Android ${android.os.Build.VERSION.RELEASE}"
}

actual fun getPlatform(): Platform = AndroidPlatform()

actual fun getImageDownloader(): ImageDownloaderProtocol = AndroidImageDownloader(AppContext.get())

class AndroidImageDownloader(
    private val context: Context
) : ImageDownloaderProtocol {

    private fun createPath(): File? {
        val directory = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val folder = File(directory, "ImagesByKMP")
        if (!folder.exists()) {
            if (!folder.mkdirs()) {
                println("Failed to create directory: ${folder.absolutePath}")
                return null
            }
        }
        return folder
    }

    override fun downloadAndSaveImage(url: String) {
        val folder = createPath() ?: return
        val fileName = hashURL(url) + ".jpg"
        val file = File(folder, fileName)

        try {
            val connection = URL(url).openConnection() as HttpURLConnection
            connection.connect()
            if (connection.responseCode == HttpURLConnection.HTTP_OK) {
                val inputStream = connection.inputStream
                val outputStream = FileOutputStream(file)
                inputStream.copyTo(outputStream)
                inputStream.close()
                outputStream.close()
                println("Image saved at: ${file.absolutePath}")
            } else {
                println("Failed to download image: ${connection.responseCode}")
            }
            connection.disconnect()
        } catch (e: Exception) {
            println("Error downloading image: $e")
        }
    }

    private fun hashURL(urlString: String): String {
        val digest = MessageDigest.getInstance("SHA-256")
        val hash = digest.digest(urlString.toByteArray())
        return hash.joinToString("") { "%02x".format(it) }
    }
}

object AppContext {
    private lateinit var application: Application

    fun setUp(context: Context) {
        application = context as Application
    }

    fun get(): Context {
        if(::application.isInitialized.not()) throw Exception("Application context isn't initialized")
        return application.applicationContext
    }
}