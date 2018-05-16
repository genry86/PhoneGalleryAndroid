package com.genry.phonegalleryandroid.DB.Dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.genry.phonegalleryandroid.DB.Models.Tag;
import com.genry.phonegalleryandroid.DB.Models.TagPhotoJoin;

import java.util.List;

@Dao
public abstract class TagPhotoJoinDao {
    @Query("SELECT * FROM tagPhotoJoin")
    public abstract List<TagPhotoJoin> getAll();

    @Query("SELECT * FROM tagPhotoJoin WHERE tagPhotoJoin.tagId = :tagId and tagPhotoJoin.photoId = :photoId LIMIT 1")
    public abstract TagPhotoJoin getTagPhoto(Integer tagId, Integer photoId);

    @Query("SELECT * FROM tagPhotoJoin WHERE tagPhotoJoin.tagId = :tagId OR tagPhotoJoin.photoId = :photoId")
    public abstract List<TagPhotoJoin> getTagPhotos(Integer tagId, Integer photoId);

    @Insert
    public abstract void insertAll(TagPhotoJoin... tagPhotoJoins);

    @Insert
    public abstract long insert(TagPhotoJoin tagPhotoJoin);

    @Update
    public abstract int update(TagPhotoJoin tagPhotoJoin);

    @Delete
    public abstract void delete(TagPhotoJoin tagPhotoJoin);

    @Query("DELETE FROM tagPhotoJoin")
    public abstract void deleteAll();
}
