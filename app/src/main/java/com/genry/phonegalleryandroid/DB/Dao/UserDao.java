package com.genry.phonegalleryandroid.DB.Dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.genry.phonegalleryandroid._Application.App;
import com.genry.phonegalleryandroid.DB.Models.User;

import java.util.List;

@Dao
public abstract class UserDao {

    @Query("SELECT * FROM user WHERE user.id = :userId LIMIT 1")
    public abstract User getUser(Integer userId);

    @Query("SELECT * FROM user ORDER BY id DESC LIMIT 1")
    public abstract User getLast();

    @Query("SELECT * FROM user")
    public abstract List<User> getAll();

    @Query("SELECT * FROM user WHERE id IN (:userIds)")
    public abstract List<User> loadAllByIds(int[] userIds);

    @Query("SELECT * FROM user WHERE name LIKE :name LIMIT 1")
    public abstract User findByName(String name);

    @Query("SELECT * FROM user LEFT JOIN tag ON user.id = tag.userId")
    public abstract List<User> getUsersWithTags();

    @Ignore
    public void addUser(User user) {
        User storedUser = findByName(user.name);
        user.id = storedUser == null ? Integer.valueOf((int) insert(user)) : storedUser.id;
    }

    @Insert
    public abstract void insertAll(User... users);

    @Insert
    public abstract long insert(User user);

    @Update
    public abstract int update(User user);

    @Delete
    public abstract void delete(User user);

    @Query("DELETE FROM user")
    public abstract void deleteAll();
}
