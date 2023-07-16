package com.example.greeningapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ReviewActivity extends AppCompatActivity {
    //총 평점
    private RatingBar ratingBar;
    private TextView value;

    //전체리뷰

    private RecyclerView fullreviewrecyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<ReviewMain> arrayList;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);


        SetupRatingBar();
        //버튼클릭
        Button button = findViewById(R.id.button);    //터치x

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ReviewActivity.this, Review_write.class);         //터치 x
                startActivity(intent);
            }
        });

        //전체리뷰
        fullreviewrecyclerView = findViewById(R.id.fullrecyclerView); //어디연결
        fullreviewrecyclerView.setHasFixedSize(true); //리사이클뷰 성능강화
        layoutManager = new LinearLayoutManager(this);
        fullreviewrecyclerView.setLayoutManager(layoutManager);
        arrayList = new ArrayList<>(); //Product객체를 담을 ArrayList(어댑터쪽으로)

        database = FirebaseDatabase.getInstance(); //파이어베이스 연동

        databaseReference = database.getReference("Review");//db데이터연결

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //파이어베이스 데이터베이스의 데이터를 받아오는곳
                arrayList.clear(); //기준 배열리스트가 존재하지않게 초기화(데이터가 쌓이기때문)
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) { //반복문으로 데이터리스트 추출
                    ReviewMain review = snapshot.getValue(ReviewMain.class);  //만들어뒀던 review객체에 데이터를 담는다( 리뷰작성시 )
                    arrayList.add(review); //담은 데이터들을 배열리스트에 넣고 리사이클뷰로 보낼준비
                }
                adapter.notifyDataSetChanged(); //리스트저장 및 새로고침
                //db가져오던중 에러발생시
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("ReviewActivity", String.valueOf(databaseError.toException())); //에러문출력
            }
        });
        adapter = new ReviewAdapter(arrayList, this);
        fullreviewrecyclerView.setAdapter(adapter);  //리사이클뷰에 어댑터연결

    }

    //포토리뷰 //레이팅바(리뷰수)
    public void SetupRatingBar(){
        ratingBar = findViewById(R.id.reviewRating);
        value =  findViewById(R.id.value);

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                value.setText(String.valueOf(rating));
            }
        });
    }


}