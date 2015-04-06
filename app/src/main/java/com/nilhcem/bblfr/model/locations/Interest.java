package com.nilhcem.bblfr.model.locations;

import ollie.Model;
import ollie.annotation.Column;
import ollie.annotation.Table;

@Table("interests")
public class Interest extends Model {

    @Column("name")
    public String name;

    public Interest() {
    }

    public Interest(String name) {
        this.name = name;
    }
}
