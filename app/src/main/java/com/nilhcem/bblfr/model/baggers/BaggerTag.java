package com.nilhcem.bblfr.model.baggers;

import ollie.Model;
import ollie.annotation.Column;
import ollie.annotation.Table;

@Table("baggers_tags")
public class BaggerTag extends Model {

    @Column("bagger_id")
    public Bagger bagger;

    @Column("tag_id")
    public Tag tag;

    public BaggerTag(Bagger bagger, Tag tag) {
        this.bagger = bagger;
        this.tag = tag;
    }
}
