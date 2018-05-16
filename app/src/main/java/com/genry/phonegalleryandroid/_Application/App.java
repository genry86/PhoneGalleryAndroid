package com.genry.phonegalleryandroid._Application;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;

import com.genry.phonegalleryandroid.DB.Models.DaoMaster;
import com.genry.phonegalleryandroid.DB.Models.DaoSession;
import com.genry.phonegalleryandroid.DB.Models.Tag;
import com.genry.phonegalleryandroid.DB.Models.TagDao;
import com.genry.phonegalleryandroid.DB.Models.User;
import com.genry.phonegalleryandroid.DB.Models.UserDao;

import org.greenrobot.greendao.database.Database;

public class App extends Application {

    public static class Constants extends AppConstants {}

    public static Context MainContext = null;
    public static AppPreferences Preferences = null;
    public static AppState State = null;

    public static DaoSession DB = null;

    @Override
    public void onCreate() {
        super.onCreate();
        MainContext = getApplicationContext();
        Preferences = AppPreferences.getInstance();
        State = AppState.getInstance();

        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, Constants.DB_NAME);
        Database db = helper.getWritableDb();
        DB = new DaoMaster(db).newSession();

        appStateSetupTask.execute();
    }

    static AsyncTask<Void, Void, Void> appStateSetupTask = new AsyncTask<Void, Void, Void>() {
        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        protected Void doInBackground(Void... voids) {

            // ****  GreenDao ORM
//            DB.getUserDao().deleteAll();
//            DB.getTagDao().deleteAll();
//            DB.getPhotoDao().deleteAll();
//            DB.getTagPhotoJoinDao().deleteAll();

            User user = DB.getUserDao().queryBuilder().where(UserDao.Properties.Name.eq(Constants.DEFAULT_USER_NAME)).unique();
            if (user == null) {
                user = new User(Constants.DEFAULT_USER_NAME);
                DB.insertOrReplace(user);
            }

            Tag tag = DB.getTagDao().queryBuilder().where(TagDao.Properties.Name.eq(Constants.DEFAULT_TAG_NAME)).unique();
            if (tag == null) {
                tag = new Tag(Constants.DEFAULT_TAG_NAME);
                tag.setUser(user);
                DB.insertOrReplace(tag);
            }

            State.currentUser = user;
            State.currentTag = tag;
            State.addPhotos(tag.getPhotos());

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            Intent intent = new Intent();
            intent.setAction(Constants.APP_STATE_INITIALIZED);
            MainContext.sendBroadcast(intent);
        }
    };
}
