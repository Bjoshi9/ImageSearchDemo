package com.example.imagesearchdemo.ui;

import android.content.res.Configuration;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.example.imagesearchdemo.R;

public class DetailActivity extends AppCompatActivity {

    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        //First Let's get the intent extra to set the title and show image

        String imageTitle = getIntent().getStringExtra("Title");
        String imageLink = getIntent().getStringExtra("ImageLink");
        imageView = findViewById(R.id.ivDetailImage);

        //Get the toolbar and set the title and after that assign that to show as the action bar
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(imageTitle);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        int span = getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT ? 3 : 6;

        // Load the image using Glide
        Glide
                .with(imageView)
                .setDefaultRequestOptions(
                        new RequestOptions()
                                .placeholder(R.drawable.ic_launcher_foreground)
                                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                                .error(R.drawable.ic_launcher_foreground)
                )

                .load(imageLink)
                .into(imageView);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home) finish();
        return super.onOptionsItemSelected(item);
    }
}