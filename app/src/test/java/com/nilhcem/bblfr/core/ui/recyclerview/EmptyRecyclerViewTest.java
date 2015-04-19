package com.nilhcem.bblfr.core.ui.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

@RunWith(RobolectricTestRunner.class)
public class EmptyRecyclerViewTest {

    private EmptyRecyclerView recyclerView;
    private View emptyView;

    @Before
    public void setup() {
        emptyView = new View(Robolectric.application);
        recyclerView = new EmptyRecyclerView(Robolectric.application);
        recyclerView.setEmptyView(emptyView);
    }

    @Test
    public void should_show_empty_view_if_adapter_is_empty() {
        // Given
        RecyclerView.Adapter adapter = new RecyclerView.Adapter() {
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                return null;
            }

            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            }

            @Override
            public int getItemCount() {
                return 0;
            }
        };

        // When
        recyclerView.setAdapter(adapter);

        // Then
        Assertions.assertThat(emptyView.getVisibility()).isEqualTo(View.VISIBLE);
    }

    @Test
    public void should_hide_empty_view_if_adapter_has_data() {
        // Given
        RecyclerView.Adapter adapter = new RecyclerView.Adapter() {
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                return null;
            }

            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            }

            @Override
            public int getItemCount() {
                return 2;
            }
        };

        // When
        recyclerView.setAdapter(adapter);

        // Then
        Assertions.assertThat(emptyView.getVisibility()).isEqualTo(View.GONE);
    }
}
