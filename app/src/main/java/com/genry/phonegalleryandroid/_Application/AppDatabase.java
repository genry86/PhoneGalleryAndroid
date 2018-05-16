package com.genry.phonegalleryandroid._Application;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.genry.phonegalleryandroid.DB.Dao.PhotoDao;
import com.genry.phonegalleryandroid.DB.Dao.TagDao;
import com.genry.phonegalleryandroid.DB.Dao.TagPhotoJoinDao;
import com.genry.phonegalleryandroid.DB.Dao.UserDao;
import com.genry.phonegalleryandroid.DB.Models.Photo;
import com.genry.phonegalleryandroid.DB.Models.Tag;
import com.genry.phonegalleryandroid.DB.Models.TagPhotoJoin;
import com.genry.phonegalleryandroid.DB.Models.User;

@Database(entities = {User.class, Tag.class, TagPhotoJoin.class, Photo.class}, version = 2)
public abstract class AppDatabase extends RoomDatabase {

    public abstract UserDao users();
    public abstract TagPhotoJoinDao tagPhotoJoinDao();
    public abstract TagDao tags();
    public abstract PhotoDao photos();

    private static AppDatabase appDatabase;

    public static AppDatabase getInstance(Context context) {
        if (null == appDatabase) {
            appDatabase = buildDatabaseInstance(context);
        }
        return appDatabase;
    }

    private static AppDatabase buildDatabaseInstance(Context context) {
        return Room.databaseBuilder(context, AppDatabase.class, AppConstants.DB_NAME)
                .allowMainThreadQueries().build();
    }

    public void cleanUp(){
        appDatabase = null;
    }
}
