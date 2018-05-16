package com.genry.phonegalleryandroid.Utility;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import com.genry.phonegalleryandroid.DB.Models.Photo;
import com.genry.phonegalleryandroid.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class JsonDataLoader extends AsyncTaskLoader<List<Photo>> {

    private List<Photo> cachedList;

    private Integer page = 1;
    private Integer per_page = 10;

    public JsonDataLoader(@NonNull Context context, Bundle args) {
        super(context);

        this.page = Integer.parseInt(args.getString("page"));
    }

    @Nullable
    @Override
    public ArrayList<Photo> loadInBackground() {

        ArrayList<Photo> list = new ArrayList<>();

        URL url = null;
        HttpURLConnection connection = null;
        JSONObject json;

        try {

            String apiUrl = String.format(getContext().getString(R.string.web_service_url), page, per_page);
            url = new URL(apiUrl);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        try {
            connection = (HttpURLConnection)url.openConnection();
            int response = connection.getResponseCode();

            if (response == HttpURLConnection.HTTP_OK) {
                StringBuilder builder = new StringBuilder();

                try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {

                    String line;

                    while ((line = reader.readLine()) != null) {
                        builder.append(line);
                    }
                }
                catch (IOException e) {
                    e.printStackTrace();
                }

                json = new JSONObject(builder.toString());
                JSONArray items = json.getJSONArray("data");

                for (int i = 0; i < items.length(); i++) {
                    JSONObject item = items.getJSONObject(i);

                    Long id = item.getLong("id");
                    String firstName = item.optString("first_name");
                    String lastName = item.optString("last_name");
                    String imageUrl = item.optString("avatar");

                    Photo photoItem = new Photo(id, firstName, lastName, imageUrl);
                    list.add(photoItem);
                }
            }
            else {
                Log.e("loader", "Incorrect Response");
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            connection.disconnect(); // close the HttpURLConnection
        }

        return list;
    }

    @Override
    public void deliverResult(@Nullable List<Photo> data) {
        super.deliverResult(data);
        cachedList = data;
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();

        if (cachedList != null) {
            deliverResult(cachedList);
        } else  {
          forceLoad();
        }
    }
}
