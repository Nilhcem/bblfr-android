package com.nilhcem.bblfr.model.baggers;

import ollie.Model;
import ollie.annotation.Column;
import ollie.annotation.Table;

@Table("tags")
public class Tag extends Model {

    @Column("name")
    public String name;

    public Tag() {
    }

    public Tag(String name) {
        this.name = name;
    }
}
