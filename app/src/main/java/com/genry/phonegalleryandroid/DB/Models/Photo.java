package com.genry.phonegalleryandroid.DB.Models;

import com.genry.phonegalleryandroid.Utility.ImageUtils;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.JoinEntity;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.ToMany;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;

@Entity
public class Photo {
    @Id
    private Long id;

    @Property
    public String firstName;

    @Property
    public String lastName;

    @Property
    public String imageUrl;

    @Property
    public String imageSrc;

    @ToMany
    @JoinEntity(
            entity = TagPhotoJoin.class,
            sourceProperty = "photoId",
            targetProperty = "tagId"
    )
    private List<Tag> tags;

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated(hash = 566311508)
    private transient PhotoDao myDao;

    public Photo(Long id, String firstName, String lastName, String imageUrl) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.imageUrl = imageUrl;
    }



    @Generated(hash = 1513668273)
    public Photo(Long id, String firstName, String lastName, String imageUrl,
            String imageSrc) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.imageUrl = imageUrl;
        this.imageSrc = imageSrc;
    }



    @Generated(hash = 1043664727)
    public Photo() {
    }



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



    public Long getId() {
        return this.id;
    }



    public void setId(Long id) {
        this.id = id;
    }



    public String getFirstName() {
        return this.firstName;
    }



    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }



    public String getLastName() {
        return this.lastName;
    }



    public void setLastName(String lastName) {
        this.lastName = lastName;
    }



    public String getImageUrl() {
        return this.imageUrl;
    }



    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }



    public String getImageSrc() {
        return this.imageSrc;
    }



    public void setImageSrc(String imageSrc) {
        this.imageSrc = imageSrc;
    }



    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 1360898091)
    public List<Tag> getTags() {
        if (tags == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            TagDao targetDao = daoSession.getTagDao();
            List<Tag> tagsNew = targetDao._queryPhoto_Tags(id);
            synchronized (this) {
                if (tags == null) {
                    tags = tagsNew;
                }
            }
        }
        return tags;
    }



    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 404234)
    public synchronized void resetTags() {
        tags = null;
    }



    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }



    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }



    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }



    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 442052972)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getPhotoDao() : null;
    }



}
