package com.luacheia.kmptravelapp.data.repository

import com.luacheia.kmptravelapp.data.model.Accommodation
import com.luacheia.kmptravelapp.data.model.Category
import com.luacheia.kmptravelapp.data.model.Guidance
import com.luacheia.kmptravelapp.data.model.Place
import com.luacheia.kmptravelapp.getImageDownloader

class ImageManager {

    private val imageManager = getImageDownloader()

     fun savePlacesImages(places: List<Place>) {
         places.forEach { place ->
             place.imageUrls.forEach { imageUrl ->
                 imageManager.downloadAndSaveImage(imageUrl)
             }
         }
    }

    fun saveCategoriesImages(categories: List<Category>) {
        categories.forEach {
            imageManager.downloadAndSaveImage(it.iconUrl)
        }
    }

    fun saveAccommodationsImages(accommodations: List<Accommodation>) {
        accommodations.forEach {
            imageManager.downloadAndSaveImage(it.iconUrl)
        }
    }

    fun saveGuidelinesImages(guidelines: List<Guidance>) {
        guidelines.forEach {
            imageManager.downloadAndSaveImage(it.iconUrl)
        }
    }
}