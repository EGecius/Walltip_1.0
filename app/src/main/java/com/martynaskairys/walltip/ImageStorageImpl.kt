package com.martynaskairys.walltip

import android.content.Context
import java.util.*

/** Store image Urls */
open class ImageStorageImpl(val context: Context) : ImageStorage {

    override fun saveUrls(imageUrls: Array<String>) {
        val imagesList = toList(imageUrls)
        saveInternal(ALL_CHOSEN_FOLDER_URLS, imagesList)
    }

    private fun toList(imageUrls: Array<String>): List<String> {
        val arrayList = ArrayList<String>()
        for (url in imageUrls) {
            arrayList.add(url)
        }
        return arrayList
    }

    override fun saveRemainingUrls(imageUrls: ArrayList<String>) {
        saveInternal(REMAINING_URLS, imageUrls)
    }

    private fun saveInternal(key: String, imageUrls: List<String>) {
        val urlsSet = HashSet<String>(imageUrls)

        val preferences = context.getSharedPreferences(STANDARD, Context.MODE_PRIVATE)
        val edit = preferences.edit()
        edit.putStringSet(key, urlsSet)
        edit.apply()
    }

    /** Returns all urls from chosen folder */
    override fun getUrls() : ArrayList<String> {
        val preferences = context.getSharedPreferences(STANDARD, Context.MODE_PRIVATE)
        val stringSet = preferences.getStringSet(ALL_CHOSEN_FOLDER_URLS, null)
        return ArrayList(stringSet)
    }

    // todo have every method return List

    /** Returns urls from chosen folder that have not been shown yet */
    override fun getRemainingUrls() : ArrayList<String> {
        val preferences = context.getSharedPreferences(STANDARD, Context.MODE_PRIVATE)
        val stringSet = preferences.getStringSet(REMAINING_CHOSEN_FOLDER_URLS, null)
        return ArrayList(stringSet)
    }

    companion object {
        val REMAINING_URLS = "chosen_folder_urls"
        val ALL_CHOSEN_FOLDER_URLS = "chosen_folder_urls"
        val REMAINING_CHOSEN_FOLDER_URLS = "remaining_folder_urls"
        val STANDARD = "standard"
    }

}

interface ImageStorage {
    fun saveUrls(imageUrls: Array<String>)
    fun saveRemainingUrls(imageUrls: ArrayList<String>)
    /** Returns all urls from chosen folder */
    fun getUrls() : ArrayList<String>

    /** Returns urls from chosen folder that have not been shown yet */
    fun getRemainingUrls() : ArrayList<String>

}
