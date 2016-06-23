package com.nilhcem.bblfr.ui;

import android.os.Build;

import com.nilhcem.bblfr.BuildConfig;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import rx.Subscription;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = Build.VERSION_CODES.M)
public class BaseActivityTest {

    private TestBaseActivity activity;

    @Spy TestSubscription subscription;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        activity = Robolectric.setupActivity(TestBaseActivity.class);
    }

    @Test
    public void should_not_unsubscribe_if_subscription_is_unsubscribed() {
        // Given
        when(subscription.isUnsubscribed()).thenReturn(true);
        activity.mSubscription = subscription;

        // When
        activity.onStop();

        // Then
        verify(subscription, times(0)).unsubscribe();
    }

    @Test
    public void should_unsubscribe_if_subscription_is_subscribed() {
        // Given
        when(subscription.isUnsubscribed()).thenReturn(false);
        activity.mSubscription = subscription;

        // When
        activity.onStop();

        // Then
        verify(subscription, times(1)).unsubscribe();
    }

    public static class TestBaseActivity extends BaseActivity {
    }

    public static class TestSubscription implements Subscription {

        @Override
        public void unsubscribe() {
        }

        @Override
        public boolean isUnsubscribed() {
            return false;
        }
    }
}
