package com.mjebooksdk.database;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class ReadLocation extends RealmObject {
    @PrimaryKey
    private String id;
    private String location;

    private String title;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
