package com.genry.phonegalleryandroid.Utility;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import com.genry.phonegalleryandroid.DB.Models.Photo;

import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;


public class ImageLoadTask extends AsyncTask<Photo, Void, Boolean> {

    private WeakReference<IImageLoadDelegate> delegate;

    private Photo photo;
    private String imageSrc;

    public ImageLoadTask(IImageLoadDelegate delegate) {
        this.delegate = new WeakReference<>(delegate);
    }

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
            if (delegate.get() != null) {
                delegate.get().photoDownloaded();
            }
        }
    }
}
