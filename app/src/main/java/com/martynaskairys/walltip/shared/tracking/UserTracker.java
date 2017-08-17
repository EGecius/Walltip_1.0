package com.martynaskairys.walltip.shared.tracking;

import com.martynaskairys.walltip.features.selectcategory.MainActivity;

/**
 * Tracks user activity
 */
public interface UserTracker {

	/** Reports to tracking system that use has entered {@link MainActivity} */
	void reportInChoosingFolderActivityOnCreate();

	void reportInChoosingFolderActivityFolderA();

	void reportInChoosingFolderActivityFolderB();

	void reportInChoosingFolderActivityFolderC();

	void reportInPictureActivityOnCreate();

	void reportInThumbnailActivityOnCreate();
}
