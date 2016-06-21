package com.nilhcem.bblfr.model.baggers;

import ollie.Model;
import ollie.annotation.Column;
import ollie.annotation.Table;

@Table("sessions_tags")
public class SessionTag extends Model {

    @Column("session_id")
    public Session session;

    @Column("tag_id")
    public Tag tag;

    public SessionTag(Session session, Tag tag) {
        this.session = session;
        this.tag = tag;
    }
}
