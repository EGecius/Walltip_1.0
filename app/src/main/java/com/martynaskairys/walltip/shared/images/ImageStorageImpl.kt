package com.martynaskairys.walltip.shared.images

import android.content.Context
import java.util.*

/** Stores image Urls */
internal class ImageStorageImpl(private val context: Context) : ImageStorage {

    override fun getUserChosenFolderIndex(): Int {
        val preferences = context.getSharedPreferences(STANDARD, Context.MODE_PRIVATE)
        val index = preferences.getInt(KEY_CHOSEN_FOLDER_INDEX, -1)
        if (index == -1) throw IllegalArgumentException("user did not selected folder yet")
        return index
    }

    override fun saveUserChosenFolderIndex(folderIndex: Int) {
        saveToSharedPreferences(KEY_CHOSEN_FOLDER_INDEX, folderIndex)
    }

    override fun saveAllChosenFolderUrls(imageUrls: Array<String>) {
        val imagesList = toList(imageUrls)
        saveToSharedPreferences(KEY_ALL_CHOSEN_FOLDER_URLS, imagesList)
    }

    private fun toList(imageUrls: Array<String>): List<String> {
        val arrayList = ArrayList<String>()
        for (url in imageUrls) {
            arrayList.add(url)
        }
        return arrayList
    }

    override fun saveShownUrls(imageUrls: List<String>) {
        saveToSharedPreferences(SHOWN_URLS, imageUrls)
    }

    private fun saveToSharedPreferences(key: String, stringList: List<String>) {
        val urlsSet = HashSet<String>(stringList)

        val preferences = context.getSharedPreferences(STANDARD, Context.MODE_PRIVATE)
        val edit = preferences.edit()
        edit.putStringSet(key, urlsSet)
        edit.apply()
    }

    private fun saveToSharedPreferences(key: String, intValue: Int) {
        val preferences = context.getSharedPreferences(STANDARD, Context.MODE_PRIVATE)
        val edit = preferences.edit()
        edit.putInt(key, intValue)
        edit.apply()
    }

    /** Returns all urls from chosen folder */
    override fun getAllChosenFolderUrls(): ArrayList<String> {
        val preferences = context.getSharedPreferences(STANDARD, Context.MODE_PRIVATE)
        val stringSet = preferences.getStringSet(KEY_ALL_CHOSEN_FOLDER_URLS, Collections.emptySet())
        return ArrayList(stringSet)
    }

    // todo have every method return List


    /** TODO Update comment Returns urls from chosen folder that have not been shown yet */
    override fun getShownUrls(): ArrayList<String> {
        val preferences = context.getSharedPreferences(STANDARD, Context.MODE_PRIVATE)
        val stringSet = preferences.getStringSet(SHOWN_URLS, Collections.emptySet())
        return ArrayList(stringSet)
    }

    companion object {
        val KEY_ALL_CHOSEN_FOLDER_URLS = "chosen_folder_urls"
        val KEY_CHOSEN_FOLDER_INDEX = "chosen_folder_index"
        val SHOWN_URLS = "shown_urls"
        val STANDARD = "standard"
    }

}

interface ImageStorage {
    /** Saves full list image urls chose by user */
    fun saveAllChosenFolderUrls(imageUrls: Array<String>)

    fun saveShownUrls(imageUrls: List<String>)
    /** Returns all urls from chosen folder */
    fun getAllChosenFolderUrls(): ArrayList<String>

    /** Returns urls from chosen folder that have been shown already */
    fun getShownUrls(): ArrayList<String>

    fun saveUserChosenFolderIndex(folderIndex: Int)
    fun getUserChosenFolderIndex(): Int
}
