package com.genry.phonegalleryandroid.DB.Models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import com.genry.phonegalleryandroid._Application.App;

import java.util.List;
import java.util.Objects;

@Entity
public class User {
    @PrimaryKey(autoGenerate = true)
    public Integer id;

    @ColumnInfo(name = "name")
    public String name;

    @Ignore
    public User(String name) {
        this.name = name;
    }

    public User(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    @Ignore
    public void addTag(Tag tag) {
        Tag storedTag = App.DB.tags().getTagByName(tag.name);
        tag.id = storedTag == null ? Integer.valueOf((int) App.DB.tags().insert(tag)) : storedTag.id;
        tag.userId = id;
        App.DB.tags().update(tag);
    }

    @Ignore
    public List<Tag> getTags() {
        return App.DB.tags().getTagsByUserId(id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(name, user.name);
    }

    @Override
    public int hashCode() {

        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
