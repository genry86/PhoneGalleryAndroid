package com.genry.phonegalleryandroid.DB.Dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.genry.phonegalleryandroid.DB.Models.Photo;

import java.util.List;

@Dao
public abstract class PhotoDao {

    @Query("SELECT * FROM photo WHERE firstName LIKE :firstName AND lastName LIKE :lastName AND imageUrl LIKE :imageUrl LIMIT 1")
    public abstract Photo getPhoto(String firstName, String lastName, String imageUrl);

    @Query("SELECT * FROM photo")
    public abstract List<Photo> getAll();

    @Query("SELECT * FROM photo WHERE id IN (:photoIds)")
    public abstract List<Photo> loadAllByIds(int[] photoIds);

    @Query("SELECT * FROM photo WHERE id IN (:photoId) LIMIT 1")
    public abstract Photo loadAllById(int photoId);

    @Query("SELECT * FROM photo WHERE firstName LIKE :firstName OR lastName LIKE :lastName")
    public abstract List<Photo> findByNames(String firstName, String lastName);

    @Insert
    public abstract void insertAll(Photo... photos);

    @Insert
    public abstract long insert(Photo photo);

    @Update
    public abstract int update(Photo photo);

    @Delete
    public abstract void delete(Photo photo);

    @Query("DELETE FROM photo")
    public abstract void deleteAll();
}
