package com.genry.phonegalleryandroid.DB.Models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.Relation;
import android.os.Build;
import android.support.annotation.RequiresApi;

import com.genry.phonegalleryandroid._Application.App;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity(foreignKeys = @ForeignKey(entity = User.class, parentColumns = "id", childColumns = "userId"))
public class Tag {

    @PrimaryKey(autoGenerate = true)
    public Integer id;

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "userId")
    public Integer userId;

    @Ignore
    public Tag(String name) {
        this.name = name;
    }

    @Ignore
    public void setUser(User user) {
        userId = user.id;
        App.DB.tags().update(this);
    }

    @Ignore
    public void addPhoto(Photo photo) {
        Photo storedPhoto = App.DB.photos().getPhoto(photo.firstName, photo.lastName, photo.imageUrl);
        photo.id = storedPhoto == null ? Integer.valueOf((int) App.DB.photos().insert(photo)) : storedPhoto.id;

        TagPhotoJoin storedTagPhotoJoin = App.DB.tagPhotoJoinDao().getTagPhoto(id, photo.id);

        if (storedTagPhotoJoin == null) {
            TagPhotoJoin tagPhotoJoin = new TagPhotoJoin(id, photo.id);
            App.DB.tagPhotoJoinDao().insert(tagPhotoJoin);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Ignore
    public void addPhotos(List<Photo> photos) {
        photos.forEach(photo -> addPhoto(photo));
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Ignore
    public List<Photo> getPhotos() {

        List<TagPhotoJoin> tagPhotoJoins = App.DB.tagPhotoJoinDao().getTagPhotos(id, 0);
        int[] photoIds = new int[tagPhotoJoins.size()];

        tagPhotoJoins.forEach(tagPhotoJoin -> {
            int index = tagPhotoJoins.indexOf(tagPhotoJoin);
            photoIds[index] = tagPhotoJoin.photoId;
        });
        return App.DB.photos().loadAllByIds(photoIds);
    }

    public Tag(Integer id, String name, Integer userId) {
        this.id = id;
        this.name = name;
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tag tag = (Tag) o;
        return Objects.equals(name, tag.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "Tag{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}

