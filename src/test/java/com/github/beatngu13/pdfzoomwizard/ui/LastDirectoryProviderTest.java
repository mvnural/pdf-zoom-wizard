package com.github.beatngu13.pdfzoomwizard.ui;

import static com.github.beatngu13.pdfzoomwizard.ui.LastDirectoryProvider.LAST_DIRECTORY_PREFERENCES_KEY;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.File;
import java.nio.file.Path;
import java.util.prefs.Preferences;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class LastDirectoryProviderTest {

	LastDirectoryProvider cut;

	@BeforeEach
	void setUp() throws Exception {
		cut = new LastDirectoryProvider();
	}

	@Test
	void set_should_not_accept_null() throws Exception {
		assertThatThrownBy(() -> cut.set(null)) //
				.isInstanceOf(NullPointerException.class) //
				.hasMessage("Last directory cannot be null.");
	}

	@Test
	void set_should_not_accept_non_existing_file() throws Exception {
		File nonExistingFile = new File("non-existing-file");

		assertThatThrownBy(() -> cut.set(nonExistingFile)) //
				.isInstanceOf(IllegalArgumentException.class) //
				.hasMessage("Last directory must be an existing file.");
	}

	@Test
	void set_should_not_accept_non_directory(@TempDir Path temp) throws Exception {
		File nonDirectory = temp.resolve("non-directory").toFile();
		nonDirectory.createNewFile();

		assertThatThrownBy(() -> cut.set(nonDirectory)) //
				.isInstanceOf(IllegalArgumentException.class) //
				.hasMessage("Last directory must be an actual directory.");
	}

	@Test
	void set_should_put_last_directory_in_preferences(@TempDir Path temp) throws Exception {
		Preferences prefs = mock(Preferences.class);
		File lastDir = temp.resolve("directory").toFile();
		lastDir.mkdir();

		cut.set(lastDir, prefs);

		verify(prefs).put(LAST_DIRECTORY_PREFERENCES_KEY, lastDir.getAbsolutePath());
	}

	@Test
	void get_should_yield_absent_optional_if_last_directory_is_absent() throws Exception {
		Preferences prefs = mock(Preferences.class);

		assertThat(cut.get(prefs)).isNotPresent();
	}

	@Test
	void get_should_yield_present_optional_if_last_directory_is_present() throws Exception {
		Preferences prefs = mock(Preferences.class);
		String lastDirPath = "directory";
		when(prefs.get(eq(LAST_DIRECTORY_PREFERENCES_KEY), any())).thenReturn(lastDirPath);

		assertThat(cut.get(prefs)).hasValue(new File(lastDirPath));
	}

}
