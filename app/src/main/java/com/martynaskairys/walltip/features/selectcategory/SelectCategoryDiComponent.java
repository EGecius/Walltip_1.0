package com.martynaskairys.walltip.features.selectcategory;

import com.martynaskairys.walltip.shared.tracking.UserTrackerModule;

import dagger.Component;

/** Dependency injection module for 'select categories' functionality */
@Component (modules = UserTrackerModule.class)
public interface SelectCategoryDiComponent {

	void inject(CategoryActivity activity);
}
