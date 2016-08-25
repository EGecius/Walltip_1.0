package com.martynaskairys.walltip;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

/**
 * Tests for {@link RandomGenerator}
 */
@RunWith (MockitoJUnitRunner.class)
public class RandomGeneratorTest {

	@Mock ImageStorage imageStorage;
	@Captor ArgumentCaptor<List<String>> argumentCaptor;

	RandomGenerator generator;

	Set<String> remainingUrls;
	String takenUrl;


	@Before
	public void setup() {
		generator = new RandomGenerator(imageStorage);
	}

	@Test
	public void whenTakesRandomImage_thenRetrievesThemFromStorage_thenSavesThemToStorageWithoutTheTakenOne() {
		whenIsAskedToTakeRandomImage();
		thenRetrievesThemFromStorage();
		thenSavesThemToStorageWithoutTheTakenOne();
	}

	private void whenIsAskedToTakeRandomImage() {
		takenUrl = generator.takeRandomImage();
	}

	private void thenRetrievesThemFromStorage() {
		verify(imageStorage).getRemainingUrls();
	}

	private void thenSavesThemToStorageWithoutTheTakenOne() {
		verify(imageStorage).saveRemainingUrls(argumentCaptor.capture());
		List<String> remainingUrls = argumentCaptor.getValue();

		assertThat(remainingUrls.contains(takenUrl)).isFalse();
	}

}