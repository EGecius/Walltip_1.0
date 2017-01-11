package com.martynaskairys.walltip.images


import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.martynaskairys.walltip.DataTypes.Folder
import java.util.*

/**
 * Takes random images, also ensuring they won't repeat until all images have been taken from a collection
 */
class ImageStorageManager(private val imageStorage: ImageStorage) {


    /** Takes random image from the list of not shown remaining images. Once this list is empty, it is restored to its
     * original full state */
    fun takeRandomImage(): String {

        val folderListString = imageStorage.getAllChosenFolderString()
        val listType = object : TypeToken<ArrayList<Folder>>() {

        }.type

        val folderList = Gson().fromJson<List<Folder>>(folderListString, listType)

        val showUrls = imageStorage.getShownUrls()

        val availableUrls = ArrayList<String>()

        for (folder in folderList) {
            availableUrls.addAll(folder.urls)
        }

        availableUrls.removeAll(showUrls)

        if (availableUrls.isEmpty()) {
            for (folder in folderList) {
                availableUrls.addAll(folder.urls)
            }

            showUrls.clear()
        }

        val randomImage = getRandomImage(availableUrls)

        showUrls.add(randomImage)

        imageStorage.saveShownUrls(showUrls)

        return randomImage
    }

    private fun getRandomImage(urls: List<String>): String {


        val randomNumber = Random().nextInt(urls.size)
        return urls[randomNumber]


    }

    /** Saves urls of images chosen by user */
    fun saveUserChosenUrls(imageUrls: Array<String>) {
        imageStorage.saveAllChosenFolderUrls(imageUrls)
    }

    fun saveUserChosenFolders(folderListString: String) {
        imageStorage.saveFolderList(folderListString)
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
