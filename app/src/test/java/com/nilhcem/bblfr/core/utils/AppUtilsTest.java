package com.nilhcem.bblfr.core.utils;

import android.content.Context;

import com.nilhcem.bblfr.BuildConfig;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AppUtilsTest {

    @Mock(answer = Answers.RETURNS_DEEP_STUBS) Context context;

    @Test
    public void should_format_into_readable_version_name_and_code() {
        // Given
        String expected = BuildConfig.VERSION_NAME + " (#" + BuildConfig.VERSION_CODE + ")";

        // When
        String version = AppUtils.getVersion();

        // Then
        assertThat(version).isEqualTo(expected);
    }

    @Test
    public void should_return_true_when_app_was_installed_from_google_play() {
        // Given
        String playStorePackageName = "com.android.vending";
        when(context.getPackageManager().getInstallerPackageName(anyString())).thenReturn(playStorePackageName);

        // When
        boolean result = AppUtils.wasInstalledFromGooglePlay(context);

        // Then
        assertThat(result).isTrue();
    }

    @Test
    public void should_return_false_when_app_was_not_installed_from_google_play() {
        // Given
        String playStorePackageName = "com.amazon.venezia";
        when(context.getPackageManager().getInstallerPackageName(anyString())).thenReturn(playStorePackageName);

        // When
        boolean result = AppUtils.wasInstalledFromGooglePlay(context);

        // Then
        assertThat(result).isFalse();
    }
}
