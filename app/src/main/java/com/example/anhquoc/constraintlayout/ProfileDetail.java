package com.example.anhquoc.constraintlayout;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ProfileDetail extends AppCompatActivity {

    @BindView(R.id.txtProfileID)
    TextView txtProfileID;
    @BindView(R.id.txtProfileName)
    TextView txtProfileName;
    @BindView(R.id.imgProfileAvatar)
    ImageView imgProfileAvatar;

    private Profile profile;

    @OnClick({R.id.imgProfileAvatar, R.id.imgBanner})
    public void onImageClicked(ImageView imageView) {
        if (imageView.getId() == R.id.imgProfileAvatar)
            Toast.makeText(this, "Clicked on avatar!", Toast.LENGTH_SHORT).show();
        if (imageView.getId() == R.id.imgBanner)
            Toast.makeText(this, "Clicked on banner!", Toast.LENGTH_SHORT).show();
    }

    @OnClick({R.id.btnBuy, R.id.btnRent, R.id.btnRating, R.id.btnFamily, R.id.btnPercent, R.id.btnSimilar, R.id.btnBookmark})
    public void onButtonClicked(Button button) {
        if (button.getId() == R.id.btnBuy)
            Toast.makeText(this, "Clicked on button BUY!", Toast.LENGTH_SHORT).show();
        if (button.getId() == R.id.btnRent)
            Toast.makeText(this, "Clicked on button RENT!", Toast.LENGTH_SHORT).show();
        if (button.getId() == R.id.btnRating)
            Toast.makeText(this, "Clicked on button Ratings!", Toast.LENGTH_SHORT).show();
        if (button.getId() == R.id.btnFamily)
            Toast.makeText(this, "Clicked on button Family!", Toast.LENGTH_SHORT).show();
        if (button.getId() == R.id.btnPercent)
            Toast.makeText(this, "Clicked on button Percent!", Toast.LENGTH_SHORT).show();
        if (button.getId() == R.id.btnSimilar)
            Toast.makeText(this, "Clicked on button Similar!", Toast.LENGTH_SHORT).show();
        if (button.getId() == R.id.btnBookmark)
            Toast.makeText(this, "Clicked on button bookmark!", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_detail);

        ButterKnife.bind(this);

        Intent intent = getIntent();
        String id, name, avatar;
        id = intent.getStringExtra("id");
        name = intent.getStringExtra("name");
        avatar = intent.getStringExtra("avatarUrl");
        profile = new Profile(id, name, avatar);

        txtProfileID.setText(profile.getProfileId());
        txtProfileName.setText(profile.getDisplayName());
        Glide
                .with(this)
                .load(avatar)
                .into(imgProfileAvatar);
    }
}
