package com.nilhcem.bblfr.ui.baggers.list;

import android.os.Parcel;
import android.text.Html;
import android.text.Spanned;

import com.nilhcem.bblfr.BBLRobolectricTestRunner;
import com.nilhcem.bblfr.R;
import com.nilhcem.bblfr.model.baggers.Bagger;
import com.nilhcem.bblfr.model.baggers.Session;
import com.nilhcem.bblfr.model.baggers.Website;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RuntimeEnvironment;

import java.util.Locale;

import edu.emory.mathcs.backport.java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(BBLRobolectricTestRunner.class)
public class BaggersListEntryTest {

    BaggersListEntry entry;

    @Before
    public void setup() {
        Website website = new Website();
        website.url = "http://www.nilhcem.com";

        Session session = new Session();
        session.title = "Work hard,";
        session.summary = "Have fun!";

        Bagger bagger = new Bagger();
        bagger.name = "Gautier";
        bagger.picture = "http://www.nilhcem.com/img.jpg";
        bagger.websites = Arrays.asList(new Website[]{website});
        bagger.sessions = Arrays.asList(new Session[]{session});
        bagger.bio = "Software Craftsman";
        bagger.location = "Franconville";
        bagger.contacts.twitter = "Nilhcem";
        bagger.contacts.mail = "me@example.com";

        entry = new BaggersListEntry(RuntimeEnvironment.application, bagger);
    }

    @Test
    public void should_restore_from_parcelable() {
        // Given
        String name = entry.name.toString();
        String links = entry.links.toString();
        String sessions = entry.sessions.toString();
        String bio = entry.bio.toString();
        String locations = entry.locations.toString();
        String pictureUrl = entry.pictureUrl;
        String email = entry.email;

        // When
        Parcel parcel = Parcel.obtain();
        entry.writeToParcel(parcel, 0);
        parcel.setDataPosition(0);

        // Then
        BaggersListEntry fromParcel = BaggersListEntry.CREATOR.createFromParcel(parcel);
        Spanned htmlLocation = Html.fromHtml(String.format(Locale.US, RuntimeEnvironment.application.getString(R.string.baggers_list_location), "Franconville"));
        assertThat(fromParcel.name.toString()).isEqualTo(name).isEqualTo("Gautier");
        assertThat(fromParcel.links.toString()).isEqualTo(links);
        assertThat(fromParcel.sessions.toString()).isEqualTo(sessions);
        assertThat(fromParcel.bio.toString()).isEqualTo(bio).isEqualTo("Software Craftsman");
        assertThat(fromParcel.locations.toString()).isEqualTo(locations).isEqualTo(htmlLocation.toString());
        assertThat(fromParcel.pictureUrl).isEqualTo(pictureUrl).isEqualTo("http://www.nilhcem.com/img.jpg");
        assertThat(fromParcel.email).isEqualTo(email).isEqualTo("me@example.com");
    }
}
