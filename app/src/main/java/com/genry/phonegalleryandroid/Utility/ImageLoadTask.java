package com.genry.phonegalleryandroid.Utility;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import com.genry.phonegalleryandroid.DB.Models.Photo;
import com.genry.phonegalleryandroid._Application.App;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class ImageLoadTask extends AsyncTask<Photo, Void, Boolean> {

    private Photo photo;
    private String imageSrc;

    @Override
    protected Boolean doInBackground(Photo... photos) {

        photo = photos[0];
        HttpURLConnection connection = null;
        Boolean isSaved = false;

        try {
            URL url = new URL(photo.imageUrl);
            connection = (HttpURLConnection)url.openConnection();
            imageSrc = photo.generateSrcName();

            try (InputStream inputStream = connection.getInputStream()) {
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                isSaved = ImageUtils.saveImageToDeviceStorage(bitmap, imageSrc);

                if (isSaved) {
                    photo.imageSrc = imageSrc;
                    photo.update();
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            connection.disconnect();
        }

        return isSaved;
    }

    @Override
    protected void onPostExecute(Boolean isSaved) {
        super.onPostExecute(isSaved);

        if (isSaved) {
            Intent intent = new Intent();
            intent.setAction(App.Constants.PHOTO_LIST_REFRESHED_ACTION);
            App.MainContext.sendBroadcast(intent);
        }
    }
}
