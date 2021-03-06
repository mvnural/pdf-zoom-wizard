package com.github.beatngu13.pdfzoomwizard.ui;

import java.io.File;
import java.util.Objects;
import java.util.Optional;
import java.util.prefs.Preferences;

import com.google.common.annotations.VisibleForTesting;

import lombok.extern.slf4j.Slf4j;

@Slf4j
class LastDirectoryProvider {

	/**
	 * Preferences key for the absolute path of the last directory.
	 */
	static final String LAST_DIRECTORY_PREFERENCES_KEY = "last_directory";

	public Optional<File> get() {
		Preferences prefs = Preferences.userNodeForPackage(LastDirectoryProvider.class);
		return get(prefs);
	}

	@VisibleForTesting
	Optional<File> get(Preferences prefs) {
		String lastDirPath = prefs.get(LAST_DIRECTORY_PREFERENCES_KEY, null);
		log.debug("Get last directory: {}", lastDirPath);
		return Optional.ofNullable(lastDirPath).map(File::new);
	}

	public void set(File lastDir) {
		Preferences prefs = Preferences.userNodeForPackage(LastDirectoryProvider.class);
		set(lastDir, prefs);
	}

	@VisibleForTesting
	void set(File lastDir, Preferences prefs) {
		validate(lastDir);
		String lastDirPath = lastDir.getAbsolutePath();
		log.debug("Set last directory: {}", lastDirPath);
		prefs.put(LAST_DIRECTORY_PREFERENCES_KEY, lastDirPath);
	}

	private static void validate(File lastDir) {
		Objects.requireNonNull(lastDir, "Last directory cannot be null.");
		if (!lastDir.exists()) {
			throw new IllegalArgumentException("Last directory must be an existing file.");
		}
		if (!lastDir.isDirectory()) {
			throw new IllegalArgumentException("Last directory must be an actual directory.");
		}
	}

}
