package com.martynaskairys.walltip.shared.tracking

import com.crashlytics.android.answers.Answers
import com.crashlytics.android.answers.ContentViewEvent

/**
 * Tracks user activity using Fabric's 'Answers'
 */
class UserTrackerImpl : UserTracker {

    private val answers: Answers = Answers.getInstance()

    override fun reportInChoosingFolderActivityOnCreate() {
        // TODO: Use your own attributes to track content views in your app
        answers.logContentView(ContentViewEvent()
                .putContentName("ChoosingActivity window")
                .putContentType("Video")
                .putContentId("1234")
                .putCustomAttribute("Favorites Count", 15)
                .putCustomAttribute("Screen Orientation", "Portrait"))
    }

    override fun reportInChoosingFolderActivityFolderB() {
        answers.logContentView(ContentViewEvent()
                .putContentName("FolderB window"))
    }

    override fun reportInChoosingFolderActivityFolderC() {
        answers.logContentView(ContentViewEvent()
                .putContentName("FolderC window"))
    }

    override fun reportInChoosingFolderActivityFolderA() {
        answers.logContentView(ContentViewEvent()
                .putContentName("FolderA window"))
    }

    override fun reportInPictureActivityOnCreate() {
        // TODO: Use your own attributes to track content views in your app
        answers.logContentView(ContentViewEvent()
                .putContentName("SingleImageActivity")
                .putContentType("Video")
                .putContentId("1234")
                .putCustomAttribute("Favorites Count", 15)
                .putCustomAttribute("Screen Orientation", "Portrait"))
    }

    override fun reportInThumbnailActivityOnCreate() {
        // TODO: Use your own attributes to track content views in your app
        answers.logContentView(ContentViewEvent()
                .putContentName("CategoryActivity window")
                .putContentType("Video")
                .putContentId("1234")
                .putCustomAttribute("Favorites Count", 15)
                .putCustomAttribute("Screen Orientation", "Portrait"))
    }

}
