package com.example.anhquoc.constraintlayout;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ProfileList extends AppCompatActivity {
    @BindView(R.id.profile_recyclerview) RecyclerView profileRecyclerView;
    private List<Profile> profileList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_list);

        ButterKnife.bind(this);
        profileRecyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        profileRecyclerView.setLayoutManager(layoutManager);

        addItemFromJson();

        RecyclerView.Adapter adapter = new RecyclerViewAdapter(this, profileList);
        profileRecyclerView.setAdapter(adapter);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        profileRecyclerView.addItemDecoration(itemDecoration);

    }

    public void addItemFromJson(){

        try {
            //read string from json
            String jsonString = readJSON();
            //parse data and add to list
            JSONArray jsonArray = new JSONArray(jsonString);

            JSONObject jsonObject;
            for (int i = 0; i<jsonArray.length(); i++ )
            {
                jsonObject = jsonArray.getJSONObject(i);

                String userID = jsonObject.getString("userId");
                String displayName = jsonObject.getString("displayName");
                String avatarURL = jsonObject.getString("avatar");

                Profile profile = new Profile(userID,displayName,avatarURL);

                profileList.add(profile);
            }
        } catch (IOException | JSONException e) {
            Log.e(ProfileList.class.getName(), "Unable to parse JSON file.", e);
            e.printStackTrace();

        }
    }

    private String readJSON() throws IOException {
        InputStream inputStream = getResources().openRawResource(R.raw.data);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
        StringBuilder builder = new StringBuilder();
        String s = null;
        while((s = bufferedReader.readLine()) != null) {
            builder.append(s);
        }
        return new String(builder);
    }

}
