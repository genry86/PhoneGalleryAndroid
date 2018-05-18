package com.genry.phonegalleryandroid.Activities;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.eftimoff.viewpagertransformers.CubeOutTransformer;
import com.genry.phonegalleryandroid.Adapters.PhotoPagerAdapter;
import com.genry.phonegalleryandroid._Application.App;
import com.genry.phonegalleryandroid._Application.AppConstants;
import com.genry.phonegalleryandroid._Application.AppState;
import com.genry.phonegalleryandroid.Controls.PhotoViewPager;
import com.genry.phonegalleryandroid.Fragments.IZoomPhotoDelegate;
import com.genry.phonegalleryandroid.DB.Models.Photo;
import com.genry.phonegalleryandroid.R;
import com.genry.phonegalleryandroid.Utility.ImageUtils;
import com.genry.phonegalleryandroid.databinding.ActivityPhotoBinding;

public class SinglePhotoActivity extends AppCompatActivity implements IZoomPhotoDelegate {

    private PhotoViewPager photoPager;
    Photo photoSharing = null;
    private String mediaStoreSrc = null;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case AppConstants.REQUEST_CODE_PHOTO_SHARED: {
                if (resultCode == RESULT_OK) {
                    Toast.makeText(getBaseContext(), "Photo Shared!", Toast.LENGTH_LONG).show();
                }
                if (ImageUtils.removeMediaStoreFile(mediaStoreSrc)) {
                    mediaStoreSrc = null;
                }
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_photo);
        ActivityPhotoBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_photo);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

//        FloatingActionButton floatingActionButton = findViewById(R.id.floatingActionButton);
//        floatingActionButton.setOnClickListener(onClickListener);

        photoPager = (PhotoViewPager) findViewById(R.id.photo_pager);
        photoPager.setAdapter(new PhotoPagerAdapter(getSupportFragmentManager(), this));
        photoPager.setPageTransformer(true, new CubeOutTransformer());

        Long photoId = getIntent().getExtras().getLong("id");
        Photo photo = App.State.getPhotoById(photoId);
        Integer index = App.State.getIndexById(photoId);
        photoPager.setCurrentItem(index);

        binding.setPhoto(photo);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void zoom(float scaleFactor) {
        photoPager.setPagingEnabled(scaleFactor >= 1 && scaleFactor <= 1.001);
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Integer currentPhotoIndex = photoPager.getCurrentItem();
            Photo photo = App.State.photos.get(currentPhotoIndex);

            Uri imageUri = ImageUtils.getMediaStoreImageUri(photo.imageSrc);
            mediaStoreSrc = imageUri.getPath();

            ImageUtils.shareImage(SinglePhotoActivity.this, imageUri, photo.getFullName(), AppConstants.REQUEST_CODE_PHOTO_SHARED);
        }
    };

    public void shareClickEvent(View view) {
        Integer currentPhotoIndex = photoPager.getCurrentItem();
        Photo photo = App.State.photos.get(currentPhotoIndex);

        Uri imageUri = ImageUtils.getMediaStoreImageUri(photo.imageSrc);
        mediaStoreSrc = imageUri.getPath();

        photo.setLastName(photo.lastName + ".");

        ImageUtils.shareImage(SinglePhotoActivity.this, imageUri, photo.getFullName(), AppConstants.REQUEST_CODE_PHOTO_SHARED);
    }
}
