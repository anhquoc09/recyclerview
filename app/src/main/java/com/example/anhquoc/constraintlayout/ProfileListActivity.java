package com.example.anhquoc.constraintlayout;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProfileListActivity
        extends AppCompatActivity
        implements ProfileListItemTouchHelper.RecyclerItemTouchHelperListener,
        ProfileListRVAdapter.ItemClickListener,
SwipeRefreshLayout.OnRefreshListener {
    private List<Profile> mProfileList = new ArrayList<>();
    private ProfileListRVAdapter mProfileListAdapter;

    @BindView(R.id.profileRecyclerView)
    RecyclerView mProfileRecyclerView;
    @BindView(R.id.swipe_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.myToolbar)
    Toolbar toolbar;
    @BindView(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initActivity();

        initProfileRecyclerView();

        setDataRefresh();
    }

    private void initActivity() {
        setContentView(R.layout.activity_profile_list);
        ButterKnife.bind(this);
    }

    private void initProfileRecyclerView() {
        mProfileRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mProfileRecyclerView.setLayoutManager(layoutManager);

        mProfileListAdapter = new ProfileListRVAdapter(this, mProfileList);
        mProfileRecyclerView.setAdapter(mProfileListAdapter);
        mProfileListAdapter.setItemClickListener(this);

        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        mProfileRecyclerView.addItemDecoration(itemDecoration);

        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new ProfileListItemTouchHelper(0, ItemTouchHelper.RIGHT, this);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(mProfileRecyclerView);
    }

    public void setDataRefresh() {
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.black,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark);

        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                if(mSwipeRefreshLayout !=null) {
                    mSwipeRefreshLayout.setRefreshing(true);
                }

                loadProfileItem();
            }
        });
    }

    public void loadProfileItem() {
        mSwipeRefreshLayout.setRefreshing(true);

        String jsonString = readJSON();
        Gson gson = new Gson();
        Type listType = new TypeToken<List<Profile>>() {
        }.getType();
        mProfileList.clear();
        mProfileList.addAll((List<Profile>) gson.fromJson(jsonString, listType));

        mProfileListAdapter.notifyDataSetChanged();

        mSwipeRefreshLayout.setRefreshing(false);
    }

    private String readJSON() {
        InputStream inputStream = getResources().openRawResource(R.raw.data);
        StringBuilder builder = new StringBuilder();
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            String s;
            while ((s = bufferedReader.readLine()) != null) {
                builder.append(s);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new String(builder);
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        mProfileListAdapter.removeItem(viewHolder, mProfileRecyclerView);
    }

    @Override
    public void onClick(View itemView, int position) {
        Intent intent = new Intent(this, ProfileDetailActivity.class);
        intent.putExtra("id", mProfileList.get(position).getUserId());
        intent.putExtra("name", mProfileList.get(position).getDisplayName());
        intent.putExtra("avatarUrl", mProfileList.get(position).getAvatar());
        startActivity(intent);
    }

    @Override
    public void onRefresh() {
        loadProfileItem();
    }
}
