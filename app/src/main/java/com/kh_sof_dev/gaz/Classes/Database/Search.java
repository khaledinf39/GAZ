package com.kh_sof_dev.gaz.Classes.Database;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Search extends RealmObject {

    private long id;
    @PrimaryKey
    private String name;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
