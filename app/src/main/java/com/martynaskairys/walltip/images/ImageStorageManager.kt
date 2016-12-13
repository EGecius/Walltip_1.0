package com.martynaskairys.walltip.images

import java.util.*

/**
 * Takes random images, also ensuring they won't repeat until all images have been taken from a collection
 */
class ImageStorageManager(private val imageStorage: ImageStorage) {

    /** Takes random image from the list of not shown remaining images. Once this list is empty, it is restored to its
     * original full state */
    fun takeRandomImage(): String {

        val allUrls = imageStorage.getAllUrls()
        val shownUrls = imageStorage.getShownUrls()

        val availableUrls = ArrayList(allUrls)  // copy allUrls list, so original arrayList would not be modified
        for (shownUrl in shownUrls){
            availableUrls.remove(shownUrl)
        }

        if(allUrls.size > 0 && availableUrls.size == 0)
        {
            // shown urls list needs to be reset
            imageStorage.saveShownUrls(ArrayList<String>())
        }

        val randomImage = getRandomImage(availableUrls)

        shownUrls.add(randomImage)
        imageStorage.saveShownUrls(shownUrls)

        return randomImage
    }

    private fun getRandomImage(urls: List<String>): String {
        val randomNumber = Random().nextInt(urls.size)
        return urls[randomNumber]
    }

    /** Saves urls of images chosen by user */
    fun saveUserChosenUrls(imageUrls: Array<String>) {
        imageStorage.saveAllUrls(imageUrls)
    }
    
    //todo  same method exists in other classes - remove duplication

    private fun toList(imageUrls: Array<String>): List<String> {
        val arrayList = ArrayList<String>()
        for (url in imageUrls) {
            arrayList.add(url)
        }
        return arrayList
    }
}
