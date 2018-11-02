package com.example.anhquoc.constraintlayout;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileListRVAdapter extends RecyclerView.Adapter<ProfileListRVAdapter.RecyclerViewHolder> {

    private Context context;
    private List<Profile> mProfileList;
    private ItemClickListener mItemClickListener;

    ProfileListRVAdapter(Context context, List<Profile> plist) {
        this.context = context;
        this.mProfileList = plist;
    }
    void setItemClickListener(ItemClickListener mItemClickListener){
        this.mItemClickListener = mItemClickListener;
    }
    public class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{
        //Khai báo view cho mỗi item
        @BindView(R.id.itemDisplayName)
        TextView itemDisplayName;
        @BindView(R.id.itemUserID)
        TextView itemUserID;
        @BindView(R.id.itemAvatar)
        CircleImageView itemAvatar;
        @BindView(R.id.profileListItem)
        RelativeLayout profileListItem;

        RecyclerViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View view){
            if(mItemClickListener!=null)
                mItemClickListener.onClick(view, getAdapterPosition());
        }

        @Override
        public boolean onLongClick(View view) {
            mItemClickListener.onClick(view, getAdapterPosition());
            return true;
        }
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.profile_list_item,viewGroup,false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerViewHolder recyclerViewHolder, int i) {
        //Gán dữ liệu và thuộc tính cho mỗi item
        recyclerViewHolder.itemUserID.setText(mProfileList.get(i).getUserId());
        recyclerViewHolder.itemDisplayName.setText(mProfileList.get(i).getDisplayName());

        String avatarUrl = mProfileList.get(i).getAvatar();
        RequestOptions requestOptions = new RequestOptions().override(recyclerViewHolder.itemAvatar.getWidth());
        Glide.with(context)
                .load(avatarUrl)
                .apply(requestOptions)
                .into(recyclerViewHolder.itemAvatar);
    }

    @Override
    public int getItemCount() {
        return mProfileList.size();
    }

    public void removeItem(final RecyclerView.ViewHolder viewHolder, RecyclerView recyclerView) {
        String name = mProfileList.get(viewHolder.getAdapterPosition()).getDisplayName();
        final int deletedIndex = viewHolder.getAdapterPosition();
        final Profile deletedItem = mProfileList.get(deletedIndex);
        mProfileList.remove(deletedIndex);
        notifyItemRemoved(deletedIndex);

        Snackbar snackbar = Snackbar
                .make(recyclerView,"Removed "+name, Snackbar.LENGTH_LONG)
                .setAction("UNDO", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        restoreItem(deletedItem, deletedIndex);
                    }
                });
        snackbar.setActionTextColor(Color.CYAN);
        snackbar.show();
    }

    public void restoreItem(Profile item, int position) {
        mProfileList.add(position, item);
        notifyItemInserted(position);
    }

    public interface ItemClickListener{
        void onClick(View itemView,int position);
    }
}
