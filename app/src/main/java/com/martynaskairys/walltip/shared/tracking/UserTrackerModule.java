package com.martynaskairys.walltip.shared.tracking;

import dagger.Module;
import dagger.Provides;

/**
 * Instantiates UserTracker objects using Dagger
 */
@Module
public class UserTrackerModule {

	@Provides
	UserTracker provideUserTracker() {
		return new UserTrackerImpl();
	}
}
