package com.genry.phonegalleryandroid.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.genry.phonegalleryandroid._Application.AppState;
import com.genry.phonegalleryandroid.Fragments.IZoomPhotoDelegate;
import com.genry.phonegalleryandroid.Fragments.PhotoPagerFragment;

import java.lang.ref.WeakReference;

public class PhotoPagerAdapter extends FragmentStatePagerAdapter {
    private WeakReference<IZoomPhotoDelegate> owner;

    public PhotoPagerAdapter(FragmentManager fm, IZoomPhotoDelegate owner) {
        super(fm);
        this.owner = new WeakReference<>(owner);
    }

    @Override
    public Fragment getItem(int position) {
        return PhotoPagerFragment.newInstance(position, owner);
    }

    @Override
    public int getCount() {
        return AppState.getInstance().photos.size();
    }
}
