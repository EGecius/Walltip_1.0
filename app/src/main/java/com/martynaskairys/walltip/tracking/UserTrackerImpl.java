package com.martynaskairys.walltip.tracking;

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
	public void reportInChoosingFolderActivity() {
		// TODO: Use your own attributes to track content views in your app
		answers.logContentView(new ContentViewEvent()
				.putContentName("ChoosingActivity window")
				.putContentType("Video")
				.putContentId("1234")
				.putCustomAttribute("Favorites Count", 15)
				.putCustomAttribute("Screen Orientation", "Portrait"));
	}
}
