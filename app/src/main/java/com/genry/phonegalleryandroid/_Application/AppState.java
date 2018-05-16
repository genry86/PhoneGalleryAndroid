package com.genry.phonegalleryandroid._Application;

import android.os.Build;
import android.support.annotation.RequiresApi;

import com.genry.phonegalleryandroid.DB.Models.Photo;
import com.genry.phonegalleryandroid.DB.Models.Tag;
import com.genry.phonegalleryandroid.DB.Models.User;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class AppState {
    private static final AppState appState = new AppState();

    private AppState() {}
    public static AppState getInstance() { return  appState; }

    public User currentUser = null;
    public Tag currentTag = null;
    public final List<Photo> photos = new ArrayList<>();

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void addPhotos(List<Photo> newPhotos) {
        if (newPhotos == null) return;

        newPhotos.forEach(photoItem -> {
            if (!photos.contains(photoItem)) {
                currentTag.addPhoto(photoItem);
                photos.add(photoItem);
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public Photo getPhotoById(Long id) {
        AtomicReference<Photo> foundPhotoItem = new AtomicReference<>();

        photos.forEach((photoItem) -> {
            if (photoItem.getId().equals(id)) {
                foundPhotoItem.set(photoItem);
            }
        });
        
        return foundPhotoItem.get();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public Integer getIndexById(Long id){
        Photo photo = getPhotoById(id);
        return  photos.indexOf(photo);
    }
}
