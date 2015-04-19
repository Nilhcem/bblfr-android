package com.nilhcem.bblfr.ui;

import android.os.Bundle;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import rx.Subscription;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(RobolectricTestRunner.class)
public class BaseActivityTest {

    private BaseActivity activity;

    @Spy TestSubscription subscription;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        activity = Robolectric.buildActivity(TestBaseActivity.class).create().get();
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

    static class TestBaseActivity extends BaseActivity {
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            try {
                super.onCreate(savedInstanceState);
            } catch (IllegalStateException e) {
                // You need to use a Theme.AppCompat theme (or descendant) with this activity.
            }
        }
    }

    static class TestSubscription implements Subscription {

        @Override
        public void unsubscribe() {
        }

        @Override
        public boolean isUnsubscribed() {
            return false;
        }
    }
}
