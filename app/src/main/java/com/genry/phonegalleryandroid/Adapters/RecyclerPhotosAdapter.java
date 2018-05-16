package com.genry.phonegalleryandroid.Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.genry.phonegalleryandroid._Application.AppConstants;
import com.genry.phonegalleryandroid.DB.Models.Photo;
import com.genry.phonegalleryandroid.R;
import com.genry.phonegalleryandroid.Utility.ImageUtils;

import java.util.List;

public class RecyclerPhotosAdapter extends Adapter<RecyclerPhotosAdapter.PhotoViewHolder> {

    public List<Photo> photos;

    private Context context;
    private IPhotoItemDelegate delegate;

    public RecyclerPhotosAdapter(Context context, IPhotoItemDelegate delegate, List<Photo> photos) {
        this.photos = photos;
        this.context = context;
        this.delegate = delegate;
    }

    public class PhotoViewHolder extends RecyclerView.ViewHolder {
        public TextView photoNameTextView;
        public ImageView photoImageView;

        public PhotoViewHolder(View view) {
            super(view);
            photoNameTextView = (TextView) view.findViewById(R.id.photoNameTextView);
            photoImageView = (ImageView) view.findViewById(R.id.photoImageView);

            view.setOnClickListener(photoItemClickListener);
        }
    }

    private View.OnClickListener photoItemClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View view) {
            delegate.onClick(view);
        }
    };

    @NonNull
    @Override
    public PhotoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_photo_item, parent, false);

        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        GridLayoutManager.LayoutParams lp = (GridLayoutManager.LayoutParams) itemView.getLayoutParams();

        Integer screenHeight = parent.getMeasuredHeight();
        float convertedImageHeight = AppConstants.IMAGE_SIZE * displayMetrics.density;
        float approxCount = screenHeight / convertedImageHeight;
        double floorHeight = Math.floor(approxCount);
        long itemCountHeight = Math.round(floorHeight);

        lp.height = parent.getMeasuredHeight() / (int)itemCountHeight;

        return new PhotoViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PhotoViewHolder holder, int position) {
        Photo photo = photos.get(position);
        holder.photoNameTextView.setText(photo.getFullName());

        if (photo.imageSrc != null) {
            Bitmap photoBitmap = ImageUtils.getImageFromDeviceStorage(photo.imageSrc);
            holder.photoImageView.setImageBitmap(photoBitmap);
        }

        holder.itemView.setTag(photo);
    }

    @Override
    public int getItemCount() {
        return photos != null ? photos.size() : 0;
    }
}
