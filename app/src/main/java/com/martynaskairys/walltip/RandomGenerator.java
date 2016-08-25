package com.martynaskairys.walltip;

import java.util.Set;

/**
 * Takes random images, also ensuring they won't repeat until all images have been taken from a collection
 */
final class RandomGenerator {

	private final ImageStorage imageStorage;

	public RandomGenerator(final ImageStorage imageStorage) {
		this.imageStorage = imageStorage;
	}


	public String takeRandomImage() {

		Set<String> urls = imageStorage.getUrls();
		return null;
	}
}
