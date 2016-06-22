package com.nilhcem.bblfr.ui;

import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.nilhcem.bblfr.BBLRobolectricTestRunner;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.robolectric.RuntimeEnvironment;

import java.util.Arrays;
import java.util.Collections;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(BBLRobolectricTestRunner.class)
public class BaseHeaderAdapterTest {

    @Spy TestBaseHeaderAdapter adapter;
    @Mock TestBaseRecyclerViewHolder holder;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        adapter.updateItems(Arrays.asList(1, 2, 3, 4), 0);
    }

    @Test
    public void should_create_item_viewholder() {
        // Given
        int viewType = BaseHeaderAdapter.TYPE_ITEM;
        ViewGroup parent = new FrameLayout(RuntimeEnvironment.application);

        // When
        adapter.onCreateViewHolder(parent, viewType);

        // Then
        verify(adapter, times(1)).onCreateItemView(parent);
        verify(adapter, times(0)).onCreateHeaderView(parent);
    }

    @Test
    public void should_create_header_viewholder() {
        // Given
        int viewType = BaseHeaderAdapter.TYPE_HEADER;
        ViewGroup parent = new FrameLayout(RuntimeEnvironment.application);

        // When
        adapter.onCreateViewHolder(parent, viewType);

        // Then
        verify(adapter, times(0)).onCreateItemView(parent);
        verify(adapter, times(1)).onCreateHeaderView(parent);
    }

    @Test
    public void should_bind_item_viewholder_with_no_header() {
        // Given
        adapter.mHasHeader = false;

        // When
        adapter.onBindViewHolder(holder, 1);

        // Then
        verify(adapter, times(1)).onBindItemView(holder, 2);
        verify(adapter, times(0)).onBindHeaderView(any(), anyInt());
    }

    @Test
    public void should_bind_item_viewholder_with_header() {
        // Given
        adapter.mHasHeader = true;

        // When
        adapter.onBindViewHolder(holder, 1);

        // Then
        verify(adapter, times(1)).onBindItemView(holder, 1);
        verify(adapter, times(0)).onBindHeaderView(any(), anyInt());
    }

    @Test
    public void should_bind_header_viewholder() {
        // Given
        adapter.mHasHeader = true;

        // When
        adapter.onBindViewHolder(holder, 0);

        // Then
        verify(adapter, times(0)).onBindItemView(any(), anyInt());
        verify(adapter, times(1)).onBindHeaderView(holder, 0);
    }

    @Test
    public void should_return_items_count_0_if_no_item() {
        // Given
        adapter.mItems = Collections.emptyList();

        // When
        int itemCount = adapter.getItemCount();

        // Then
        assertThat(itemCount).isEqualTo(0);
    }

    @Test
    public void should_return_items_count_plus_one_header_if_any() {
        // Given
        adapter.mHasHeader = true;

        // When
        int itemCount = adapter.getItemCount();

        // Then
        assertThat(itemCount).isEqualTo(5);
    }

    @Test
    public void should_return_items_count_without_header_if_none() {
        // Given
        adapter.mHasHeader = false;

        // When
        int itemCount = adapter.getItemCount();

        // Then
        assertThat(itemCount).isEqualTo(4);
    }

    @Test
    public void should_return_viewtype_header_if_so() {
        // Given
        adapter.mHasHeader = true;

        // When
        int viewType = adapter.getItemViewType(0);

        // Then
        assertThat(viewType).isEqualTo(BaseHeaderAdapter.TYPE_HEADER);
    }

    @Test
    public void should_return_viewtype_item_if_so() {
        // Given
        adapter.mHasHeader = false;

        // When
        int viewType = adapter.getItemViewType(0);

        // Then
        assertThat(viewType).isEqualTo(BaseHeaderAdapter.TYPE_ITEM);
    }

    static class TestBaseHeaderAdapter extends BaseHeaderAdapter<Integer, Integer, TestBaseRecyclerViewHolder, TestBaseRecyclerViewHolder> {
        @Override
        protected TestBaseRecyclerViewHolder onCreateItemView(ViewGroup parent) {
            return null;
        }
    }

    static class TestBaseRecyclerViewHolder extends BaseRecyclerViewHolder<Integer> {

        public TestBaseRecyclerViewHolder() {
            super(null, 0, false);
        }
    }
}
