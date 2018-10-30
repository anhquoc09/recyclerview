package com.example.anhquoc.constraintlayout;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import com.bumptech.glide.Glide;
import de.hdodenhof.circleimageview.CircleImageView;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder> {

    private Context context;
    private List<Profile> profileList;

    RecyclerViewAdapter(Context context, List<Profile> plist) {
        this.context = context;
        this.profileList = plist;
    }
    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.profile_list_item,viewGroup,false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerViewHolder recyclerViewHolder, int i) {
        //Gán dữ liệu và thuộc tính cho mỗi item
        recyclerViewHolder.txtID.setText(profileList.get(i).getProfileId());
        recyclerViewHolder.txtDisplayName.setText(profileList.get(i).getDisplayName());

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

    public class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        //Khai báo view cho mỗi item
        @BindView(R.id.txtDisplayName)TextView txtDisplayName;
        @BindView(R.id.txtUserID)TextView txtID;
        @BindView(R.id.imgAvatar)CircleImageView imgAvatar;

        ItemClickListener itemClickListener;

        RecyclerViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            itemView.setOnClickListener(this);
        }

        void setItemClickListener(ItemClickListener itemClickListener){
            this.itemClickListener = itemClickListener;
        }
        @Override
        public void onClick(View view){
            itemClickListener.onClick(view, getAdapterPosition());
        }
    }
}
