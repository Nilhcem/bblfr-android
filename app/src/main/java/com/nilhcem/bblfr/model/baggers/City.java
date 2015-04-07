package com.nilhcem.bblfr.model.baggers;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonProperty;

import ollie.Model;
import ollie.annotation.Column;
import ollie.annotation.Table;

@Table("cities")
public class City extends Model implements Parcelable {

    @JsonProperty
    @Column("name")
    public String name;

    @JsonProperty(value = "ville_img")
    @Column("picture")
    public String picture;

    @JsonProperty
    @Column("lat")
    public Double lat;

    @JsonProperty
    @Column("lng")
    public Double lng;

    public City() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeString(name);
        dest.writeString(picture);
        dest.writeValue(lat);
        dest.writeValue(lng);
    }

    private City(Parcel in) {
        id = (Long) in.readValue(Long.class.getClassLoader());
        name = in.readString();
        picture = in.readString();
        lat = (Double) in.readValue(Double.class.getClassLoader());
        lng = (Double) in.readValue(Double.class.getClassLoader());
    }

    public static final Parcelable.Creator<City> CREATOR = new Parcelable.Creator<City>() {
        public City createFromParcel(Parcel source) {
            return new City(source);
        }

        public City[] newArray(int size) {
            return new City[size];
        }
    };
}
