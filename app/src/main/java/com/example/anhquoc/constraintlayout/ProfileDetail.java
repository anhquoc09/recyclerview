package com.example.anhquoc.constraintlayout;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProfileDetail extends AppCompatActivity {

    @BindView(R.id.txtProfileID)TextView txtProfileID;
    @BindView(R.id.txtProfileName)TextView txtProfileName;
    @BindView(R.id.imgProfileAvatar)ImageView imgProfileAvatar;

    private Profile profile;
    private AsyncTask downloadTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_detail);

        ButterKnife.bind(this);

        Intent intent = getIntent();
        //Get Profile detail from intent
        String id, name, avatar;
        id = intent.getStringExtra("id");
        name = intent.getStringExtra("name");
        avatar = intent.getStringExtra("avatarUrl");
        profile = new Profile(id,name,avatar);

        txtProfileID.setText(profile.getProfileId());
        txtProfileName.setText(profile.getDisplayName());
        Glide
                .with(this)
                .load(avatar)
                .into(imgProfileAvatar);
    }
}
