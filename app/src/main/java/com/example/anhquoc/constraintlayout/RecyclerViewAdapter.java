package com.example.anhquoc.constraintlayout;

import android.content.Context;
import android.content.Intent;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import android.os.AsyncTask;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder> {

    private Context context;
    private List<Profile> profileList;
    private AsyncTask downloadTask;

    public RecyclerViewAdapter(Context context, List<Profile> plist) {
        this.context = context;
        this.profileList = plist;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.profile_list_item,viewGroup,false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerViewHolder recyclerViewHolder, int i) {
        recyclerViewHolder.txtID.setText(profileList.get(i).getProfileId());
        recyclerViewHolder.txtDisplayName.setText(profileList.get(i).getDisplayName());
        //Download image from url
        String avatarUrl = profileList.get(i).getAvatar();
        Glide
                .with(context)
                .load(avatarUrl)
                .into(recyclerViewHolder.imgAvatar);

        recyclerViewHolder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View itemView, int position) {
                Intent intent = new Intent(context, ProfileDetail.class);
                intent.putExtra("id", profileList.get(position).getProfileId());
                intent.putExtra("name", profileList.get(position).getDisplayName());
                intent.putExtra("avatarUrl", profileList.get(position).getAvatar());
                context.startActivity(intent);
            }

        });
    }

    @Override
    public int getItemCount() {
        return profileList.size();
    }

    public interface ItemClickListener{
        void onClick(View itemView,int position);
    }

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.txtDisplayName)TextView txtDisplayName;
        @BindView(R.id.txtUserID)TextView txtID;
        @BindView(R.id.imgAvatar)ImageView imgAvatar;

        private ItemClickListener itemClickListener;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            //set OnClick for Item
            itemView.setOnClickListener( this);
        }
        //Setter for ItemClick
        public void setItemClickListener(ItemClickListener itemClickListener){
            this.itemClickListener = itemClickListener;
        }
        @Override
        public void onClick(View view){
            itemClickListener.onClick(view, getAdapterPosition());
        }
    }

}
