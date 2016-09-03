package com.martynaskairys.walltip

import java.util.*

/**
 * Takes random images, also ensuring they won't repeat until all images have been taken from a collection
 */
internal class RandomGenerator(private val imageStorage: ImageStorage) {

    /** Takes random image from the list of not shown remaining images. Once this list is empty, it is restored to its
     * original full state */
    fun takeRandomImage(): String {

        val remainingUrls = imageStorage.getRemainingUrls()
        val randomImage = getRandomImage(remainingUrls)
        remainingUrls.remove(randomImage)

        imageStorage.saveRemainingUrls(remainingUrls)

        restoreListToOriginalIfNeeded(remainingUrls)

        return randomImage
    }

    private fun restoreListToOriginalIfNeeded(remainingUrls: ArrayList<String>) {
        if (remainingUrls.size == 0) {
            val allUrls = imageStorage.getUrls()
            imageStorage.saveRemainingUrls(allUrls)
        }
    }

    private fun getRandomImage(urls: List<String>): String {
        val randomNumber = Random().nextInt(urls.size)
        return urls[randomNumber]
    }
}
