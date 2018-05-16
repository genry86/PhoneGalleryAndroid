package com.genry.phonegalleryandroid.DB.Models;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.ToOne;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;

@Entity
public class TagPhotoJoin {
    @Id
    private Long id;

    private Long tagId;
    private Long photoId;

    @ToOne(joinProperty = "tagId")
    private Tag tag;

    @ToOne(joinProperty = "photoId")
    private Photo photo;

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated(hash = 2039130281)
    private transient TagPhotoJoinDao myDao;

    @Generated(hash = 78536325)
    public TagPhotoJoin(Long id, Long tagId, Long photoId) {
        this.id = id;
        this.tagId = tagId;
        this.photoId = photoId;
    }

    @Generated(hash = 160511687)
    public TagPhotoJoin() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTagId() {
        return this.tagId;
    }

    public void setTagId(Long tagId) {
        this.tagId = tagId;
    }

    public Long getPhotoId() {
        return this.photoId;
    }

    public void setPhotoId(Long photoId) {
        this.photoId = photoId;
    }

    @Generated(hash = 1006483784)
    private transient Long tag__resolvedKey;

    /** To-one relationship, resolved on first access. */
    @Generated(hash = 891466911)
    public Tag getTag() {
        Long __key = this.tagId;
        if (tag__resolvedKey == null || !tag__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            TagDao targetDao = daoSession.getTagDao();
            Tag tagNew = targetDao.load(__key);
            synchronized (this) {
                tag = tagNew;
                tag__resolvedKey = __key;
            }
        }
        return tag;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 106711820)
    public void setTag(Tag tag) {
        synchronized (this) {
            this.tag = tag;
            tagId = tag == null ? null : tag.getId();
            tag__resolvedKey = tagId;
        }
    }

    @Generated(hash = 1137958716)
    private transient Long photo__resolvedKey;

    /** To-one relationship, resolved on first access. */
    @Generated(hash = 1568905261)
    public Photo getPhoto() {
        Long __key = this.photoId;
        if (photo__resolvedKey == null || !photo__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            PhotoDao targetDao = daoSession.getPhotoDao();
            Photo photoNew = targetDao.load(__key);
            synchronized (this) {
                photo = photoNew;
                photo__resolvedKey = __key;
            }
        }
        return photo;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 2088160251)
    public void setPhoto(Photo photo) {
        synchronized (this) {
            this.photo = photo;
            photoId = photo == null ? null : photo.getId();
            photo__resolvedKey = photoId;
        }
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
    @Generated(hash = 287919554)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getTagPhotoJoinDao() : null;
    }

 

}
