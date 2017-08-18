package com.martynaskairys.walltip.shared.tracking

import com.martynaskairys.walltip.features.selectcategory.MainActivity

/**
 * Tracks user activity
 */
interface UserTracker {

    /** Reports to tracking system that use has entered [MainActivity]  */
    fun reportInChoosingFolderActivityOnCreate()

    fun reportInChoosingFolderActivityFolderA()

    fun reportInChoosingFolderActivityFolderB()

    fun reportInChoosingFolderActivityFolderC()

    fun reportInPictureActivityOnCreate()

    fun reportInThumbnailActivityOnCreate()
}
