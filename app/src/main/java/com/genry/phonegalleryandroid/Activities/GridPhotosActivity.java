package com.genry.phonegalleryandroid.Activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.genry.phonegalleryandroid.Adapters.IPhotoItemDelegate;
import com.genry.phonegalleryandroid.Adapters.RecyclerPhotosAdapter;
import com.genry.phonegalleryandroid.Utility.IAppStateSetupDelegate;
import com.genry.phonegalleryandroid._Application.App;
import com.genry.phonegalleryandroid._Application.AppConstants;
import com.genry.phonegalleryandroid._Application.AppState;
import com.genry.phonegalleryandroid.DB.Models.Photo;
import com.genry.phonegalleryandroid.R;
import com.genry.phonegalleryandroid.Utility.IImageLoadDelegate;
import com.genry.phonegalleryandroid.Utility.ImageLoadTask;
import com.genry.phonegalleryandroid.Utility.JsonDataLoader;

import java.util.ArrayList;
import java.util.List;

public class GridPhotosActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Photo>>,
                                                                    IImageLoadDelegate, IPhotoItemDelegate, IAppStateSetupDelegate{

    public final String TAG = getClass().getSimpleName();

    private static final int LOADER_ID = 1;
    private Loader<List<Photo>> loader;

    private Integer page = 1;

    private RecyclerView photosRecyclerView;
    private RecyclerPhotosAdapter recyclerPhotosAdapter;
    private RecyclerView.LayoutManager photosLayoutManager;
    private ProgressBar centralProgressBar;

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photos);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        centralProgressBar = findViewById(R.id.centralProgressBar);
        centralProgressBar.setActivated(true);
        centralProgressBar.setVisibility(View.VISIBLE);

        photosRecyclerView = findViewById(R.id.photosRecyclerView);
        photosRecyclerView.setHasFixedSize(true);

        photosLayoutManager = new GridLayoutManager(this, getColumnCount());
        photosRecyclerView.setLayoutManager(photosLayoutManager);

        recyclerPhotosAdapter = new RecyclerPhotosAdapter(getBaseContext(), this, App.State.photos);
        photosRecyclerView.setAdapter(recyclerPhotosAdapter);

        App.addStartActivity(this);
    }

    @Override
    public void appStateInitialized() {
        Bundle bundle = new Bundle();
        bundle.putString("page", page.toString());
        loader = getSupportLoaderManager().initLoader(LOADER_ID, bundle, this);
    }

    int getColumnCount() {
        DisplayMetrics displayMetrics = this.getResources().getDisplayMetrics();

        Integer screenWidth = this.getResources().getDisplayMetrics().widthPixels;

        float convertedImageWidth = AppConstants.IMAGE_SIZE * displayMetrics.density;
        float approxCount = screenWidth / convertedImageWidth;
        double floorColumnCount = Math.floor(approxCount);
        long columnCount = Math.round(floorColumnCount);

        return (int)columnCount;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        photosLayoutManager = new GridLayoutManager(this, getColumnCount());
        photosRecyclerView.setLayoutManager(photosLayoutManager);
        recyclerPhotosAdapter.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public Loader<List<Photo>> onCreateLoader(int id, @Nullable Bundle args) {

        switch (id) {
            case LOADER_ID:
                loader = new JsonDataLoader(this, args);
                break;
        }

        return loader;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onLoadFinished(@NonNull Loader<List<Photo>> loader, List<Photo> items) {
        switch (loader.getId()) {
            case LOADER_ID: {

                App.State.addPhotos(items);
                recyclerPhotosAdapter.notifyDataSetChanged();

                centralProgressBar.setActivated(false);
                centralProgressBar.setVisibility(View.INVISIBLE);

                items.forEach(photo -> {
                    if (!photo.checkPhotoIsDownloaded()) {
                        new ImageLoadTask( this).execute(photo);
                    }
                });
            }
                break;
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<Photo>> loader) {
        recyclerPhotosAdapter.photos = null;
        Log.d(TAG, "onLoaderReset");
    }

    @Override
    public void photoDownloaded() {
        recyclerPhotosAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View view) {
        Photo photo = (Photo)view.getTag();

        Bundle bundle = new Bundle();
        bundle.putLong("id", photo.id);

        Intent detailedPhotoIntent = new Intent(this, SinglePhotoActivity.class);
        detailedPhotoIntent.putExtra("id" , photo.id);

        detailedPhotoIntent.putExtras(bundle);

        startActivity(detailedPhotoIntent);
    }
}
