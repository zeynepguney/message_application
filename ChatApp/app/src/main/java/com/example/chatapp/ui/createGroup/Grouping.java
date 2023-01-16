package com.example.chatapp.ui.createGroup;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatapp.GroupModels;
import com.example.chatapp.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class Grouping extends RecyclerView.Adapter<Grouping.GroupViewHolder>{
    List<GroupModels> groupModelsList;
    public Grouping(List<GroupModels> groupModelsList){
        this.groupModelsList = groupModelsList;
    }
    @NonNull
    @Override
    public GroupViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        GroupViewHolder groupViewHolder = new GroupViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.create_group, parent, false));
        return groupViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull GroupViewHolder holder, int position) {
        GroupModels groupModels = groupModelsList.get(position);
        holder.setData(groupModels);
    }

    @Override
    public int getItemCount() {
        return groupModelsList.size();
    }

    public class GroupViewHolder extends RecyclerView.ViewHolder {
        ImageView groupImage;
        TextView groupName, groupExplanation;
        public GroupViewHolder(View itemView){
            super(itemView);
            groupImage = itemView.findViewById(R.id.list_groupImage);
            groupName = itemView.findViewById(R.id.list_groupName);
            groupExplanation = itemView.findViewById(R.id.list_groupExplain);
        }
        public void setData(GroupModels groupModels){
            groupName.setText(groupModels.getGroupName());
            groupExplanation.setText(groupModels.getGroupExplanation());

            if (groupModels.getGroupImage() != null) {
                Picasso.get().load(groupModels.getGroupImage()).into(groupImage);
            }
        }

    }
}
