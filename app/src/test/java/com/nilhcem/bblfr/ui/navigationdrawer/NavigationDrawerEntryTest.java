package com.nilhcem.bblfr.ui.navigationdrawer;

import org.junit.Test;

import static com.nilhcem.bblfr.ui.navigationdrawer.NavigationDrawerEntry.FIND_BAGGER;
import static com.nilhcem.bblfr.ui.navigationdrawer.NavigationDrawerEntry.HOSTS;
import static com.nilhcem.bblfr.ui.navigationdrawer.NavigationDrawerEntry.valueOf;
import static org.assertj.core.api.Assertions.assertThat;

public class NavigationDrawerEntryTest {

    @Test
    public void should_return_entry_when_giving_name() {
        // Given
        String name = HOSTS.name();

        // When
        NavigationDrawerEntry entry = valueOf(name, FIND_BAGGER);

        // Then
        assertThat(entry).isEqualTo(HOSTS);
    }

    @Test
    public void should_return_default_value_if_name_is_not_found() {
        // Given
        String name = null;

        // When
        NavigationDrawerEntry entry = valueOf(name, FIND_BAGGER);

        // Then
        assertThat(entry).isEqualTo(FIND_BAGGER);
    }
}
