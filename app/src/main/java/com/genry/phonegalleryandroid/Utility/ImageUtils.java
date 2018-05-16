package com.genry.phonegalleryandroid.Utility;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

import com.genry.phonegalleryandroid._Application.App;
import com.genry.phonegalleryandroid._Application.AppConstants;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class ImageUtils {

    private static File storageFolder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
    private static String pathToFolder = storageFolder + File.separator + AppConstants.IMAGE_FOLDER_NAME;

    public static Bitmap getBitmapFromInternalStorage(Context context, String fileName) {
        File imgFile = context.getFileStreamPath(fileName);
        Bitmap bitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
        return bitmap;
    }

    public static Boolean saveImageToInternalStorage(Context context, Bitmap bitmap, String filename) {
        Boolean isSaved = false;
        FileOutputStream fileOutputStream;

        try {
            fileOutputStream = context.openFileOutput(filename, Context.MODE_PRIVATE);
            isSaved = bitmap.compress(Bitmap.CompressFormat.PNG, 90, fileOutputStream);

            fileOutputStream.flush();
            fileOutputStream.close();

            context.getFileStreamPath(filename);
            File imgFile = new File("/"+filename);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return isSaved;
    }

    public static Boolean createFolderIfNotExists() {
        final File folder = new File(pathToFolder);
        Boolean isFolderCreated = true;

        if (!folder.exists()) {
            isFolderCreated = folder.mkdir();
        }

        return isFolderCreated;
    }

    public static Boolean saveImageToDeviceStorage(Bitmap bitmap, String fileName) {
        Boolean isSaved = false;

        if (createFolderIfNotExists()) {
            File fullPath = new File(pathToFolder, fileName);

            if (!fullPath.exists()) {
                try {
                    OutputStream fOut = new FileOutputStream(fullPath);
                    isSaved = bitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);

                    fOut.flush();
                    fOut.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                isSaved = true;
            }
        }

        return isSaved;
    }

    public static Bitmap getImageFromDeviceStorage(String fileName) {
        File fullPath = new File(pathToFolder, fileName);

        if (fullPath.exists()) {
            Bitmap bitmap = BitmapFactory.decodeFile(fullPath.getAbsolutePath());
            return bitmap;
        }

        return null;
    }

    public static Boolean isImageFileExists(String fileName) {
        if (fileName == null) return false;

        File fullPath = new File(pathToFolder, fileName);
        return fullPath.exists();
    }

    public static Uri getMediaStoreImageUri( String fileName) {
        File fullPath = new File(pathToFolder, fileName);

        if (fullPath.exists()) {
            Bitmap bitmap = getImageFromDeviceStorage(fileName);

            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, bytes);

            String path = MediaStore.Images.Media.insertImage(App.MainContext.getContentResolver(), bitmap, fileName, fileName);
            return Uri.parse(path);
        }

        return null;
    }

    public static Boolean removeMediaStoreFile(String contentUri) {
        Uri uri = Uri.parse(contentUri);
        Integer result = App.MainContext.getContentResolver()
                            .delete(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, uri.getLastPathSegment(), null);
        return  (result >= 1);
    }

    public static void shareImage(Activity activity, Uri photoUri, String title, Integer requestCode) {
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_STREAM, photoUri);
        shareIntent.setType("image/*");
        activity.setResult(activity.RESULT_OK, shareIntent);
        activity.startActivityForResult(Intent.createChooser(shareIntent, title), requestCode);
    }
}
