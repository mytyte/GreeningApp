package com.example.greeningapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.CustomViewHolder>{
    private ArrayList<ReviewMain> arrayList;
    private Context context;

    public ReviewAdapter(ArrayList<ReviewMain> arrayList, ReviewActivity reviewActivity) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.review_item, parent, false);
        CustomViewHolder holder = new CustomViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        Glide.with(holder.itemView)
                .load(arrayList.get(position).getReview_image())
                .into(holder.inputimg);
        holder.reviewdes.setText(String.valueOf(arrayList.get(position).getWrite_review()));
        holder.userrating.setRating(arrayList.get(position).getRating());



    }

    @Override
    public int getItemCount() {
        //삼합연산자
        return (arrayList !=null ? arrayList.size() :0);
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        ImageView inputimg;
        RatingBar userrating;
        TextView reviewdes;


        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            this.inputimg = itemView.findViewById(R.id.inputimg);
            this.reviewdes = itemView.findViewById(R.id.reviewdes);
            this.userrating = itemView.findViewById(R.id.userrating);


        }
    }


}
