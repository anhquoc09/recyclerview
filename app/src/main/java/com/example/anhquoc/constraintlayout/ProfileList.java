package com.example.anhquoc.constraintlayout;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

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

import static android.widget.RelativeLayout.LayoutParams.*;

public class ProfileList extends AppCompatActivity implements RecyclerItemTouchHelper.RecyclerItemTouchHelperListener {
    ActionBar actionBar;
    private List<Profile> profileList = new ArrayList<>();
    RecyclerViewAdapter recyclerViewAdapter;

    @BindView(R.id.profileRecyclerView)
    RecyclerView profileRecyclerView;
    @BindView(R.id.myToolbar)
    Toolbar toolbar;
    @BindView(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_list);
        ButterKnife.bind(this);
        profileRecyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        profileRecyclerView.setLayoutManager(layoutManager);

        recyclerViewAdapter = new RecyclerViewAdapter(this, profileList);
        profileRecyclerView.setAdapter(recyclerViewAdapter);

        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        profileRecyclerView.addItemDecoration(itemDecoration);

        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new RecyclerItemTouchHelper(0, ItemTouchHelper.RIGHT, this);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(profileRecyclerView);

        //Support Actionbar bằng Toolbar
        customActionBar();
        //Parse JSON vào List
        addItemFromJson();
    }

    private void customActionBar() {
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        ActionBar.LayoutParams layoutParams = new ActionBar.LayoutParams(WRAP_CONTENT, MATCH_PARENT, Gravity.CENTER);

        TextView tv = new TextView(getApplicationContext());
        tv.setText("MESSAGE");
        tv.setTextColor(Color.BLACK);
        tv.setGravity(Gravity.CENTER);

        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setCustomView(tv, layoutParams);
    }

    public void addItemFromJson() {
        try {
            String jsonString = readJSON();

            JSONArray jsonArray = new JSONArray(jsonString);
            JSONObject jsonObject;
            for (int i = 0; i < jsonArray.length(); i++) {
                jsonObject = jsonArray.getJSONObject(i);
                String userID = jsonObject.getString("userId");
                String displayName = jsonObject.getString("displayName");
                String avatarURL = jsonObject.getString("avatar");

                Profile profile = new Profile(userID, displayName, avatarURL);
                profileList.add(profile);
            }
        } catch (IOException e) {
            Log.e(ProfileList.class.getName(), "Unable to parse JSON file.", e);
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private String readJSON() throws IOException {
        InputStream inputStream = getResources().openRawResource(R.raw.data);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
        StringBuilder builder = new StringBuilder();
        String s;
        while ((s = bufferedReader.readLine()) != null) {
            builder.append(s);
        }
        return new String(builder);
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        String name = profileList.get(viewHolder.getAdapterPosition()).getDisplayName();
        final int deletedIndex = viewHolder.getAdapterPosition();
        final Profile deletedItem = profileList.get(deletedIndex);

        recyclerViewAdapter.removeItem(deletedIndex);

        Snackbar snackbar = Snackbar.make(coordinatorLayout, name + " has removed from list!", Snackbar.LENGTH_LONG);
        snackbar.setAction("UNDO", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recyclerViewAdapter.restoreItem(deletedItem, deletedIndex);
            }
        });
        snackbar.setActionTextColor(Color.CYAN);
        snackbar.show();
    }
}
