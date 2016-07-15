package com.devintensive.devintensive.ui.adapters;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.devintensive.devintensive.R;
import com.devintensive.devintensive.data.network.res.UserListRes;
import com.devintensive.devintensive.ui.views.AspectRatioImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.UsersViewHolder>{

    List<UserListRes.UserData> mUsers;
    Context mContext;

    public UsersAdapter(List<UserListRes.UserData> users){
        mUsers = users;
    }

    @Override
    public UsersAdapter.UsersViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user_list,parent,false);
        return new UsersViewHolder(convertView);
    }

    @Override
    public void onBindViewHolder(UsersAdapter.UsersViewHolder holder, int position) {
        //TODO: realize bind
        UserListRes.UserData user = mUsers.get(position);

        Picasso.with(mContext)
                .load(user.getPublicInfo().getPhoto())
                .placeholder(mContext.getResources().getDrawable(R.drawable.user_bg))
                .error(mContext.getResources().getDrawable(R.drawable.user_bg))
                .into(holder.userPhoto);

        holder.mFullName.setText(user.getFullName());
        holder.mRating.setText(String.valueOf(user.getProfileValues().getRating()));
        holder.mCodeLines.setText(String.valueOf(user.getProfileValues().getLinesCode()));
        holder.mProjects.setText(String.valueOf(user.getProfileValues().getProjects()));

        if (user.getPublicInfo().getBio() == null || user.getPublicInfo().getBio().isEmpty()) {
            holder.mBio.setVisibility(View.GONE);

        }else {
            holder.mBio.setVisibility(View.VISIBLE);
            holder.mBio.setText(user.getPublicInfo().getBio());
        }

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class UsersViewHolder extends RecyclerView.ViewHolder {

        protected AspectRatioImageView userPhoto;
        protected TextView mFullName,mRating,mCodeLines,mProjects,mBio;
        public UsersViewHolder(View itemView) {
            super(itemView);

            userPhoto = (AspectRatioImageView) itemView.findViewById(R.id.user_photo_img);
            mFullName = (TextView) itemView.findViewById(R.id.user_full_name_txt);
            mRating = (TextView) itemView.findViewById(R.id.rating_txt);
            mCodeLines = (TextView) itemView.findViewById(R.id.code_lines_txt);
            mProjects = (TextView) itemView.findViewById(R.id.projects_txt);
            mBio = (TextView) itemView.findViewById(R.id.bio_txt);
        }
    }
}