package com.martynaskairys.walltip

import android.content.Context
import java.util.*

/** Store image Urls */
class ImageStorage(val context: Context) {

    fun saveUrls(imageUrls: Array<String>) {

        val urlsSet = HashSet<String>()
        Collections.addAll(urlsSet, *imageUrls)

        val preferences = context.getSharedPreferences(STANDARD, Context.MODE_PRIVATE)
        val edit = preferences.edit()
        edit.putStringSet(CHOSEN_FOLDER_URLS, urlsSet)
        edit.apply()
    }

    fun getUrls() : Set<String> {
        val preferences = context.getSharedPreferences(STANDARD, Context.MODE_PRIVATE)
        return preferences.getStringSet(CHOSEN_FOLDER_URLS, null)
    }

    companion object {
        val CHOSEN_FOLDER_URLS = "chosen_folder_urls"
        val STANDARD = "standard"
    }

}