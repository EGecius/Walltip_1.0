package com.martynaskairys.walltip.images

import java.util.*

/**
 * Takes random images, also ensuring they won't repeat until all images have been taken from a collection
 */
class ImageStorageManager(private val imageStorage: ImageStorage) {

    /** Takes random image from the list of not shown remaining images. Once this list is empty, it is restored to its
     * original full state */
    fun takeRandomImage(): String {

        val remainingUrls = imageStorage.getRemainingUrls()

        val workingUrlSet : ArrayList<String>
        if (remainingUrls.isEmpty()) {
            workingUrlSet = imageStorage.getAllUrls()
        } else {
            workingUrlSet = remainingUrls
        }

        val randomImage = getRandomImage(workingUrlSet)
        workingUrlSet.remove(randomImage)

        updateRemainingList(workingUrlSet)

        return randomImage
    }

    private fun updateRemainingList(remainingUrls: ArrayList<String>) {
        if (remainingUrls.size == 0) {
            val allUrls = imageStorage.getAllUrls()
            imageStorage.saveRemainingUrls(allUrls)
        } else {
            imageStorage.saveRemainingUrls(remainingUrls);
        }
    }

    private fun getRandomImage(urls: List<String>): String {
        val randomNumber = Random().nextInt(urls.size)
        return urls[randomNumber]
    }

    /** Saves urls of images chosen by user */
    fun saveUserChosenUrls(imageUrls: Array<String>) {
        imageStorage.saveAllUrls(imageUrls)
        imageStorage.saveRemainingUrls(toList(imageUrls))
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
