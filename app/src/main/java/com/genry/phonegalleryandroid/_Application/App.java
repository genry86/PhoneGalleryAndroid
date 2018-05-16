package com.genry.phonegalleryandroid._Application;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;

import com.genry.phonegalleryandroid.DB.Models.Photo;
import com.genry.phonegalleryandroid.DB.Models.Tag;
import com.genry.phonegalleryandroid.DB.Models.User;
import com.genry.phonegalleryandroid.Utility.IAppStateSetupDelegate;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class App extends Application {

    public static class Constants extends AppConstants {}

    public static Context MainContext = null;
    public static AppPreferences Preferences = null;
    public static AppState State = null;
    public static AppDatabase DB = null;

    @Override
    public void onCreate() {
        super.onCreate();
        MainContext = getApplicationContext();
        Preferences = AppPreferences.getInstance();
        State = AppState.getInstance();
        DB = AppDatabase.getInstance(MainContext);

        appStateSetupTask.execute();
    }

    static AsyncTask<Void, Void, Void> appStateSetupTask = new AsyncTask<Void, Void, Void>() {
        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        protected Void doInBackground(Void... voids) {

            // ****  ROOM ORM
            User user = DB.users().findByName(Constants.DEFAULT_USER_NAME);
            if (user == null) {
                user = new User(Constants.DEFAULT_USER_NAME);
                DB.users().addUser(user);
            }

            Tag tag = DB.tags().getTagByName(Constants.DEFAULT_TAG_NAME);
            if (tag == null) {
                tag = new Tag(Constants.DEFAULT_TAG_NAME);
                user.addTag(tag);
            }

            State.currentUser = user;
            State.currentTag = tag;
            State.addPhotos((ArrayList<Photo>) tag.getPhotos());

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
