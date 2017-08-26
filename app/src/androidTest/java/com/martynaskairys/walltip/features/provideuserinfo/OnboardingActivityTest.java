package com.martynaskairys.walltip.features.provideuserinfo;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.martynaskairys.walltip.features.provideuserinfo.OnboardingActivity
		.OnboardingPagerFragment.ONBOARDING_TEXT_1;
import static com.martynaskairys.walltip.features.provideuserinfo.OnboardingActivity.OnboardingPagerFragment.ONBOARDING_TITLE_1;

/**
 * Tests for {@link OnboardingActivity}
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class OnboardingActivityTest {

	@Rule
	public ActivityTestRule<OnboardingActivity> rule = new ActivityTestRule<>(OnboardingActivity
			.class);

	@Test
	public void onboardingTitle1isVisible() {
		onView(withText(ONBOARDING_TITLE_1)).check(matches(isDisplayed()));
	}

	@Test
	public void subtitleVisibleAndCorrect() {
		onView(withText(ONBOARDING_TEXT_1)).check(matches(isDisplayed()));
	}

}