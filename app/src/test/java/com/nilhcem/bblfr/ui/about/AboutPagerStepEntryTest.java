package com.nilhcem.bblfr.ui.about;

import com.nilhcem.bblfr.R;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class AboutPagerStepEntryTest {

    @Test
    public void should_return_titles() {
        // When
        int[] titles = AboutPagerStepEntry.getTitles();

        // Then
        assertThat(titles).containsExactly(R.string.about_step1_title, R.string.about_step2_title, R.string.about_step3_title, 0);
    }

    @Test
    public void should_return_contents() {
        // When
        int[] titles = AboutPagerStepEntry.getContents();

        // Then
        assertThat(titles).containsExactly(R.string.about_step1_content, R.string.about_step2_content, R.string.about_step3_content, 0);
    }
}
