package com.genry.phonegalleryandroid.DB.Dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.genry.phonegalleryandroid.DB.Models.Tag;
import com.genry.phonegalleryandroid.DB.Models.User;

import java.util.List;

@Dao
public abstract class TagDao {
    @Query("SELECT * FROM tag")
    public abstract List<Tag> getAll();

    @Query("SELECT * FROM tag WHERE id IN (:tagIds)")
    public abstract List<Tag> loadAllByIds(int[] tagIds);

    @Query("SELECT * FROM tag WHERE userId LIKE :userId")
    public abstract List<Tag> getAllByUserId(Integer userId);

    @Query("SELECT * FROM tag WHERE name LIKE :name")
    public abstract List<Tag> getTagsByName(String name);

    @Query("SELECT * FROM tag WHERE userId LIKE :userId")
    public abstract List<Tag> getTagsByUserId(Integer userId);

    @Query("SELECT * FROM tag WHERE name LIKE :name LIMIT 1")
    public abstract Tag getTagByName(String name);

    @Insert
    public abstract void insertAll(Tag... tags);

    @Insert
    public abstract long insert(Tag tag);

    @Update
    public abstract int update(Tag tag);

    @Delete
    public abstract void delete(Tag tag);

    @Query("DELETE FROM tag")
    public abstract void deleteAll();
}
