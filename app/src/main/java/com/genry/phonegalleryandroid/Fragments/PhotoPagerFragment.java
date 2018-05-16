package com.genry.phonegalleryandroid.Fragments;


import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.genry.phonegalleryandroid._Application.App;
import com.genry.phonegalleryandroid._Application.AppState;
import com.genry.phonegalleryandroid.DB.Models.Photo;
import com.genry.phonegalleryandroid.R;
import com.genry.phonegalleryandroid.Utility.ImageUtils;
import com.github.chrisbanes.photoview.OnScaleChangedListener;
import com.github.chrisbanes.photoview.PhotoView;

import java.lang.ref.WeakReference;

public class PhotoPagerFragment extends Fragment {

    private WeakReference<IZoomPhotoDelegate> owner;
    private Integer position = 0;

    public static PhotoPagerFragment newInstance(int photoIndex, WeakReference<IZoomPhotoDelegate> owner) {
        PhotoPagerFragment newPhotoFragment = new PhotoPagerFragment();
        newPhotoFragment.owner = owner;

        Bundle args = new Bundle();
        args.putInt("photoIndex", photoIndex);
        newPhotoFragment.setArguments(args);

        return newPhotoFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        position = getArguments().getInt("photoIndex", 0);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.single_photo_fragment, container, false);

        Photo photo = App.State.photos.get(position);

        TextView photoNameTextView = rootView.findViewById(R.id.photoNameTextView);
        PhotoView photoImageView = rootView.findViewById(R.id.photoImageView);

        photoImageView.setOnScaleChangeListener(onScaleChangedListener);

        if (photoNameTextView != null && photoImageView != null) {
            photoNameTextView.setText(photo.getFullName());

            if (photo.imageSrc != null) {
                Bitmap photoBitmap = ImageUtils.getImageFromDeviceStorage(photo.imageSrc);
                photoImageView.setImageBitmap(photoBitmap);
            }
        }

        return rootView;
    }

    private OnScaleChangedListener onScaleChangedListener = new OnScaleChangedListener() {
        @Override
        public void onScaleChange(float scaleFactor, float focusX, float focusY) {
            if (owner.get() != null ) {
                owner.get().zoom(scaleFactor);
            }
        }
    };

}
