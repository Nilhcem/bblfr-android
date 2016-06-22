package com.nilhcem.bblfr.model.baggers;

import android.os.Parcel;

import com.nilhcem.bblfr.BBLRobolectricTestRunner;

import org.junit.Test;
import org.junit.runner.RunWith;

import static com.google.common.truth.Truth.assertThat;

@RunWith(BBLRobolectricTestRunner.class)
public class CityTest {

    @Test
    public void should_restore_from_parcelable() {
        // Given
        City city = new City();
        city.name = "Paris";
        city.lat = 48.85;
        city.lng = 2.34;
        city.picture = "img/villes/BBL.jpg";

        // When
        Parcel parcel = Parcel.obtain();
        city.writeToParcel(parcel, 0);
        parcel.setDataPosition(0);

        // Then
        City fromParcel = City.CREATOR.createFromParcel(parcel);
        assertThat(fromParcel.name).isEqualTo("Paris");
        assertThat(fromParcel.lat).isWithin(4.85);
        assertThat(fromParcel.lng).isWithin(2.34);
        assertThat(fromParcel.picture).isEqualTo("img/villes/BBL.jpg");
    }
}
