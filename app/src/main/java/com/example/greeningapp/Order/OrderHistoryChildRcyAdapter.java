package com.example.greeningapp.Order;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.greeningapp.R;
import com.example.greeningapp.ReviewActivity;
import com.example.greeningapp.Review_write;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class OrderHistoryChildRcyAdapter extends RecyclerView.Adapter<OrderHistoryChildRcyAdapter.ChildViewHolder> {
////        holder.ordhreviewBtn.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
//////                if (!isReviewCompleted) {
//////                    isReviewCompleted = true;
////                    Intent intent = new Intent(cxt, Review_write.class);
////                    intent.putExtra("isReviewCompleted", isReviewCompleted);//"isReviewCompleted"를 Review_write에 보냄
////                    holder.ordhreviewBtn.setText("작성 완료");
//////                } else {
//////                    holder.ordhreviewBtn.setText("후기 작성 ");
//////                }
////            }
////        });
//        holder.ordhreviewBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(cxt, Review_write.class);
//                cxt.startActivity(intent);
//            }
//
//        });
//    }
////    // 후기 작성 완료 여부를 변경하는 메서드  //잠시 추가
////    public void setReviewCompleted(boolean reviewCompleted) {
////        isReviewCompleted = reviewCompleted;
////        notifyDataSetChanged();
////    }

    public ArrayList<MyOrder> childModelArrayList;
    Context cxt;
//    private String isReviewCompleted = "";
        private String isReviewCompleted ;

    public OrderHistoryChildRcyAdapter(ArrayList<MyOrder> childModelArrayList, Context mContext) {
        this.cxt = mContext;
        this.childModelArrayList = childModelArrayList;
    }

    @Override
    public ChildViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_orderhistory_child, parent, false);
        return new ChildViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChildViewHolder holder,  @SuppressLint("RecyclerView") int position) {
        Glide.with(holder.itemView)
                .load(childModelArrayList.get(position).getOrderImg())
                .into(holder.orderhistory_img);
        holder.pro_name.setText(childModelArrayList.get(position).getProductName());
        holder.pro_price.setText(childModelArrayList.get(position).getProductPrice());
        holder.ordervalue.setText(childModelArrayList.get(position).getTotalQuantity() + "개");

//        isReviewCompleted = childModelArrayList.get(position).getDoReview();
        String isReviewCompleted = childModelArrayList.get(position).getDoReview();

        if ("No".equals(isReviewCompleted)) {

        } else if ("Yes".equals(isReviewCompleted)) {
//            holder.ordhreviewBtn.setText("작성 완료");
//            holder.ordhreviewBtn.setEnabled(false);
        }

        holder.ordhreviewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ("No".equals(isReviewCompleted)) {
                    Intent intent = new Intent(cxt, Review_write.class);
                    intent.putExtra("product", childModelArrayList.get(position));
//                    Log.d("Order", String.valueOf(childModelArrayList.get(position)+"가져왔음"));
                    cxt.startActivity(intent);
                } else if ("Yes".equals(isReviewCompleted)) {
                    holder.ordhreviewBtn.setText("작성 완료");
                    holder.ordhreviewBtn.setEnabled(false);
                }
            }
        });



    }

    @Override
    public int getItemCount() {
        if (childModelArrayList != null) {
            return childModelArrayList.size();
        }
        return 0;
    }

    public class ChildViewHolder extends RecyclerView.ViewHolder {
        public ImageView orderhistory_img;
        public TextView pro_name, pro_price, ordervalue;

        AppCompatButton ordhreviewBtn;

        public ChildViewHolder(View itemView) {
            super(itemView);
            orderhistory_img = itemView.findViewById(R.id.orderhistory_img);
            pro_name = itemView.findViewById(R.id.pro_name);
            pro_price = itemView.findViewById(R.id.pro_price);
            ordervalue = itemView.findViewById(R.id.ordervalue);
            ordhreviewBtn = itemView.findViewById(R.id.ordhreviewBtn);
        }
    }
}