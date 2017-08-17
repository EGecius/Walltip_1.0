package com.martynaskairys.walltip.shared.tracking;

import com.crashlytics.android.answers.Answers;
import com.crashlytics.android.answers.ContentViewEvent;

/**
 * Tracks user activity using Fabric's 'Answers'
 */
public class UserTrackerImpl implements UserTracker {

	private final Answers answers;

	public UserTrackerImpl() {
		answers = Answers.getInstance();
	}

	@Override
	public void reportInChoosingFolderActivityOnCreate() {
		// TODO: Use your own attributes to track content views in your app
		answers.logContentView(new ContentViewEvent()
				.putContentName("ChoosingActivity window")
				.putContentType("Video")
				.putContentId("1234")
				.putCustomAttribute("Favorites Count", 15)
				.putCustomAttribute("Screen Orientation", "Portrait"));
	}

	@Override
	public void reportInChoosingFolderActivityFolderB() {
		answers.logContentView(new ContentViewEvent()
				.putContentName("FolderB window"));
	}

	@Override
	public void reportInChoosingFolderActivityFolderC() {
		answers.logContentView(new ContentViewEvent()
				.putContentName("FolderC window"));
	}

	@Override
	public void reportInChoosingFolderActivityFolderA() {
		answers.logContentView(new ContentViewEvent()
				.putContentName("FolderA window"));
	}

	@Override
	public void reportInPictureActivityOnCreate() {
		// TODO: Use your own attributes to track content views in your app
		answers.logContentView(new ContentViewEvent()
				.putContentName("PictureActivity")
				.putContentType("Video")
				.putContentId("1234")
				.putCustomAttribute("Favorites Count", 15)
				.putCustomAttribute("Screen Orientation", "Portrait"));
	}

	@Override
	public void reportInThumbnailActivityOnCreate() {
		// TODO: Use your own attributes to track content views in your app
		answers.logContentView(new ContentViewEvent()
				.putContentName("ThumbnailActivity window")
				.putContentType("Video")
				.putContentId("1234")
				.putCustomAttribute("Favorites Count", 15)
				.putCustomAttribute("Screen Orientation", "Portrait"));
	}

}
