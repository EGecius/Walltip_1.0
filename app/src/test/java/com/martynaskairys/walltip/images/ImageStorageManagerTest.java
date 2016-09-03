package com.martynaskairys.walltip.images;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public final class ImageStorageManagerTest {

	public static final String IMAGE_0 = "IMAGE_0";
	public static final String IMAGE_1 = "IMAGE_1";
	public static final String IMAGE_2 = "IMAGE_2";
	public static final String IMAGE_3 = "IMAGE_3";
	@Mock ImageStorage imageStorage;

	@Captor ArgumentCaptor<ArrayList<String>> captor;

	ImageStorageManager generator;

	ArrayList<String> originalRemainingUrls = new ArrayList<>();
	ArrayList<String> remainingUrls = new ArrayList<>();
	ArrayList<String> fullListOfUrls = new ArrayList<>();
	private String takenUrl;


	@Before
	public void setup() {
		generator = new ImageStorageManager(imageStorage);

		populateUrlLists();

		when(imageStorage.getRemainingUrls()).thenReturn(remainingUrls);
		when(imageStorage.getUrls()).thenReturn(fullListOfUrls);
	}

	private void populateUrlLists() {
		originalRemainingUrls.add(IMAGE_0);
		originalRemainingUrls.add(IMAGE_1);
		originalRemainingUrls.add(IMAGE_2);

		remainingUrls.addAll(originalRemainingUrls);

		fullListOfUrls.add(IMAGE_0);
		fullListOfUrls.add(IMAGE_1);
		fullListOfUrls.add(IMAGE_2);
		fullListOfUrls.add(IMAGE_3);
	}

	@Test
	public void whenTakesRandomImage_thenRetrievesThemFromStorage_thenSavesThemToStorageWithoutTheTakenOne() {
		whenIsAskedToTakeRandomImage();
		thenRetrievesImageFromStorage();
		thenSavesThemToStorageWithoutTheTakenOne();
	}

	private void whenIsAskedToTakeRandomImage() {
		takenUrl = generator.takeRandomImage();
	}

	private void thenRetrievesImageFromStorage() {
		verify(imageStorage).getRemainingUrls();
		assertThat(originalRemainingUrls.contains(takenUrl)).isTrue();
	}

	private void thenSavesThemToStorageWithoutTheTakenOne() {
        verify(imageStorage).saveRemainingUrls(captor.capture());
		ArrayList<String> savedRemainingUrls = captor.getValue();

		assertThat(savedRemainingUrls.contains(takenUrl)).isFalse();
		assertThat(savedRemainingUrls.size()).isEqualTo(originalRemainingUrls.size() - 1);

		assertEverySavedUrlIsFromOriginalSet(savedRemainingUrls);
	}

	private void assertEverySavedUrlIsFromOriginalSet(final ArrayList<String> savedRemainingUrls) {
		for (final String savedUrl : savedRemainingUrls) {
			assertThat(originalRemainingUrls.contains(savedUrl)).isTrue();
		}
	}

	@Test
	public void whenLastImageIsTakenFromRemaining_thenFullListIsSavedToRemainingList() {
		whenLastImageIsTakenFromRemaining();
		thenFullListIsSavedToRemainingList();
	}

	private void whenLastImageIsTakenFromRemaining() {
		//take all 3 stored images
		takenUrl = generator.takeRandomImage();
		takenUrl = generator.takeRandomImage();
		takenUrl = generator.takeRandomImage();
	}

	private void thenFullListIsSavedToRemainingList() {
		verify(imageStorage).getUrls();
		verify(imageStorage).saveRemainingUrls(fullListOfUrls);
	}

}
