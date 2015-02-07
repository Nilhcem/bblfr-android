package com.nilhcem.bblfr.model.locations;

import ollie.Model;
import ollie.annotation.Column;
import ollie.annotation.Table;

@Table("locations_interests")
public class LocationInterest extends Model {

    @Column("location_id")
    public Location location;

    @Column("interest_id")
    public Interest interest;

    public LocationInterest(Location location, Interest interest) {
        this.location = location;
        this.interest = interest;
    }
}
