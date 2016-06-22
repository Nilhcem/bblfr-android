package com.nilhcem.bblfr.core.utils;

import android.content.Context;
import android.net.ConnectivityManager;

import com.nilhcem.bblfr.BBLRobolectricTestRunner;
import com.nilhcem.bblfr.BuildConfig;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Locale;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.Mockito.when;

@RunWith(BBLRobolectricTestRunner.class)
public class NetworkUtilsTest {

    @Mock Context context;
    @Mock(answer = Answers.RETURNS_DEEP_STUBS) ConnectivityManager manager;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        when(context.getSystemService(Context.CONNECTIVITY_SERVICE)).thenReturn(manager);
    }

    @Test
    public void should_return_true_if_network_is_available() {
        // Given
        when(manager.getActiveNetworkInfo().isConnected()).thenReturn(true);

        // When
        boolean networkAvailable = NetworkUtils.isNetworkAvailable(context);

        // Then
        assertThat(networkAvailable).isTrue();
    }

    @Test
    public void should_return_false_when_network_is_null_or_not_connected() {
        // Given When
        when(manager.getActiveNetworkInfo().isConnected()).thenReturn(false);
        boolean res1 = NetworkUtils.isNetworkAvailable(context);
        when(manager.getActiveNetworkInfo()).thenReturn(null);
        boolean res2 = NetworkUtils.isNetworkAvailable(context);

        // Then
        assertThat(res1).isEqualTo(res2);
        assertThat(res1).isFalse();
    }

    @Test
    public void should_return_same_url_if_absolute() {
        // Given
        String url = "http://www.nilhcem.com";

        // When
        String result = NetworkUtils.getAbsoluteUrl(url);

        // Then
        assertThat(result).isEqualTo(url);
    }

    @Test
    public void should_transform_relative_to_absolute_url() {
        // Given
        String url = "/imgs/nilhcem.jpg";

        // When
        String result = NetworkUtils.getAbsoluteUrl(url);

        // Then
        assertThat(result).isEqualTo(BuildConfig.WS_ENDPOINT + url);
    }

    @Test
    public void should_return_twitter_url_from_username() {
        // Given
        String username = "Nilhcem";

        // When
        String twitterUrl = NetworkUtils.getTwitterUrl(username);

        // Then
        assertThat(twitterUrl.toLowerCase(Locale.US)).isEqualTo("http://www.twitter.com/#!/nilhcem");
    }
}
