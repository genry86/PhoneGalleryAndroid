package com.genry.phonegalleryandroid.DB.Models;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Objects;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import com.genry.phonegalleryandroid.Utility.ImageUtils;
import com.genry.phonegalleryandroid._Application.App;

@Entity
public class Photo {

    @PrimaryKey
    public Integer id;

    @ColumnInfo(name = "firstName")
    public String firstName;

    @ColumnInfo(name = "lastName")
    public String lastName;

    @ColumnInfo(name = "imageUrl")
    public String imageUrl;

    @ColumnInfo(name = "imageSrc")
    public String imageSrc;

    @Ignore
    public Photo(Integer id, String firstName, String lastName, String imageUrl) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.imageUrl = imageUrl;
    }

    public Photo(Integer id, String firstName, String lastName, String imageUrl, String imageSrc) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.imageUrl = imageUrl;
        this.imageSrc = imageSrc;
    }

    @Ignore
    public void addTag(Tag tag) {
        if (App.DB.tags().getTagsByName(tag.name) == null) {
            tag.id = (int)App.DB.tags().insert(tag);
        }

        TagPhotoJoin storedTagPhotoJoin = App.DB.tagPhotoJoinDao().getTagPhoto(tag.id, id);

        if (storedTagPhotoJoin == null) {
            TagPhotoJoin tagPhotoJoin = new TagPhotoJoin(tag.id, id);
            App.DB.tagPhotoJoinDao().insert(tagPhotoJoin);
        }
    }

    @Ignore
    public Boolean checkPhotoIsDownloaded() {
        String tmpImageSrc = generateSrcName();
        imageSrc = ImageUtils.isImageFileExists(imageSrc) ? tmpImageSrc : null;

        return (imageSrc != null);
    }

    public String getFullName() {
         return firstName + " " + lastName;
    }

    public String generateSrcName() {
        String imageFileName = null;
        try {
            URL url = new URL(this.imageUrl);
            String photoFilename  = new File(url.getPath()).getName();

            imageFileName = this.id + "_"+ photoFilename;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return imageFileName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Photo photo = (Photo) o;
        return Objects.equals(id, photo.id) &&
                Objects.equals(firstName, photo.firstName) &&
                Objects.equals(lastName, photo.lastName) &&
                Objects.equals(this.imageUrl, photo.imageUrl);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, firstName, lastName, imageUrl);
    }

    @Override
    public String toString() {
        return "Photo{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", imageSrc='" + imageSrc + '\'' +
                '}';
    }
}
