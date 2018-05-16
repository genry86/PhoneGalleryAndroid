package com.genry.phonegalleryandroid.DB.Models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.support.annotation.NonNull;

@Entity(tableName = "tagPhotoJoin",
        primaryKeys = { "tagId", "photoId" },
        foreignKeys = {
                @ForeignKey(entity = Tag.class, parentColumns = "id", childColumns = "tagId"),
                @ForeignKey(entity = Photo.class, parentColumns = "id", childColumns = "photoId")
        })
public class TagPhotoJoin {
    @NonNull
    public Integer tagId;
    @NonNull
    public Integer photoId;

    public TagPhotoJoin(Integer tagId, Integer photoId) {
        this.tagId = tagId;
        this.photoId = photoId;
    }
}
