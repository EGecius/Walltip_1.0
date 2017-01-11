package com.martynaskairys.walltip.images

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.martynaskairys.walltip.DataTypes.Folder
import java.util.*

/** Store image Urls */
internal class ImageStorageImpl(val context: Context) : ImageStorage {

    override fun saveAllChosenFolderUrls(imageUrls: Array<String>) {
        val imagesList = toList(imageUrls)
        saveInternal(ALL_CHOSEN_FOLDER_URLS, imagesList)
    }

    override fun saveFolderList(folderListString: String) {
        saveInternal(ALL_LIST_FOLDER, folderListString)
    }

    private fun toList(imageUrls: Array<String>): List<String> {
        val arrayList = ArrayList<String>()
        for (url in imageUrls) {
            arrayList.add(url)
        }
        return arrayList
    }

    override fun saveShownUrls(imageUrls: List<String>) {
        val imageUrlsString = Gson().toJson(imageUrls)
        saveInternal(SHOWN_URLS_NEW, imageUrlsString)
    }

    private fun saveInternal(key: String, imageUrls: List<String>) {
        val urlsSet = HashSet<String>(imageUrls)

        val preferences = context.getSharedPreferences(STANDARD, Context.MODE_PRIVATE)
        val edit = preferences.edit()
        edit.putStringSet(key, urlsSet)
        edit.apply()
    }


    private fun saveInternal(key: String, imageUrls: String) {
        val preferences = context.getSharedPreferences(STANDARD, Context.MODE_PRIVATE)
        val edit = preferences.edit()
        edit.putString(key, imageUrls)
        edit.apply()
    }

    /** Returns all urls from chosen folder */
    override fun getAllChosenFolderUrls() : List<Folder> {
        val preferences = context.getSharedPreferences(STANDARD, Context.MODE_PRIVATE)
        val stringSet = preferences.getStringSet(ALL_CHOSEN_FOLDER_URLS, Collections.emptySet())
        return ArrayList()
    }


    /** Returns all urls from chosen folder */
    override fun getAllChosenFolderString() : String {
        val preferences = context.getSharedPreferences(STANDARD, Context.MODE_PRIVATE)
        val folderListString = preferences.getString(ALL_LIST_FOLDER, "")
        return folderListString
    }


    // todo have every method return List

    /** TODO Update comment Returns urls from chosen folder that have not been shown yet */
    override fun getShownUrls() : ArrayList<String> {
        val preferences = context.getSharedPreferences(STANDARD, Context.MODE_PRIVATE)
        val stringUrls = preferences.getString(SHOWN_URLS_NEW, "")

        if (!stringUrls.isEmpty()) {
            val listType = object : TypeToken<ArrayList<String>>() {

            }.type

            return Gson().fromJson<ArrayList<String>>(stringUrls, listType)
        }

        return ArrayList<String>()
    }

    companion object {
        val ALL_CHOSEN_FOLDER_URLS = "chosen_folder_urls"
        val SHOWN_URLS = "shown_urls"
        val SHOWN_URLS_NEW = "shown_urls_new"
        val STANDARD = "standard"
        val ALL_LIST_FOLDER = "all_list_folder"
    }

}

interface ImageStorage {
    /** Saves full list image urls chose by user */
    fun saveAllChosenFolderUrls(imageUrls: Array<String>)
    fun saveFolderList(folderListString: String)
    fun saveShownUrls(imageUrls: List<String>)
    /** Returns all urls from chosen folder */
    fun getAllChosenFolderUrls() : List<Folder>
    fun getAllChosenFolderString() : String
    /** Returns urls from chosen folder that have been shown already */
    fun getShownUrls() : ArrayList<String>
}
