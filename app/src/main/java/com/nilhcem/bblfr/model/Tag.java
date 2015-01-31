package com.nilhcem.bblfr.model;

import ollie.Model;
import ollie.annotation.Column;
import ollie.annotation.Table;

@Table("tags")
public class Tag extends Model {

    @Column("name")
    public String mName;

    public Tag(String name) {
        mName = name;
    }
}
