package com.nilhcem.bblfr.ui.about;

import com.nilhcem.bblfr.R;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.truth.Truth.assertThat;

public class AboutPagerStepEntryTest {

    @Test
    public void should_return_titles() {
        // When
        int[] titles = AboutPagerStepEntry.getTitles();

        // Then
        assertThat(intArrayToList(titles)).containsExactly(R.string.about_step1_title, R.string.about_step2_title, R.string.about_step3_title, 0);
    }

    @Test
    public void should_return_contents() {
        // When
        int[] titles = AboutPagerStepEntry.getContents();

        // Then
        assertThat(intArrayToList(titles)).containsExactly(R.string.about_step1_content, R.string.about_step2_content, R.string.about_step3_content, 0);
    }

    private List<Integer> intArrayToList(int[] from) {
        List<Integer> result = new ArrayList<>();
        for (int cur : from) {
            result.add(cur);
        }
        return result;
    }
}
