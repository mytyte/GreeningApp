package com.example.greeningapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import android.widget.Button;
import android.widget.EditText;

import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.greeningapp.Order.MyOrder;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class Review_write extends AppCompatActivity {

    private static final int Gallery_Code=1;

    FirebaseDatabase mDatabase;
    DatabaseReference mRef;

    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;

    FirebaseStorage mStorage;

    ImageView uploadImage;
    Button uploadBtn;
    RatingBar RatingBarEt;
    Uri imageUri=null;
    Button cancelBtn;
    EditText reviewEt;

    MyOrder product = null;

    TextView Pname;
    ImageView Pimg;


    TextView mDate;  //날짜

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.review_write);

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

        databaseReference = FirebaseDatabase.getInstance().getReference("CurrentUser").child(firebaseUser.getUid()).child("MyOrder");


        uploadBtn = findViewById(R.id.writeUploadBtn);
        uploadImage = findViewById(R.id.writeUploadImage);
        reviewEt = findViewById(R.id.writeReviewEt);
        cancelBtn = findViewById(R.id.writeCancelBtn);
        RatingBarEt = findViewById(R.id.writeRatingBar);

        mDatabase=FirebaseDatabase.getInstance();
        mRef=mDatabase.getReference().child("Review");
        mStorage=FirebaseStorage.getInstance();
        //날짜 표시
        mDate = findViewById(R.id.reviewDate);

        String dateTimeFormat = "yyyy.MM.dd";
        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateTimeFormat);
        String formattedDate = simpleDateFormat.format(date);
        mDate.setText(formattedDate);

//        Pname = findViewById(R.id.writePname);
//        Pimg = (ImageView) findViewById(R.id.writePImg);

//        final Object object = getIntent().getSerializableExtra("product");
//
//        if(object instanceof MyOrder){
//            product = (MyOrder) object;
//            Log.d("Review_write", product+"");
//        }
//
//        if (product != null) {
//            Pname.setText(product.getProductName());
//            Glide.with(getApplicationContext()).load(product.getOrderImg()).into(Pimg);
//
//        }

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Review_write.this, ReviewActivity.class);
                startActivity(intent);
            }
        });


        uploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/");
                startActivityForResult(intent,Gallery_Code);
            }
        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @NonNull Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==Gallery_Code && resultCode == RESULT_OK)
        {
            imageUri =data.getData();
            uploadImage.setImageURI(imageUri);
        }

        uploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fn = reviewEt.getText().toString().trim();
                String reviewImage = imageUri.toString();

                if (!(fn.isEmpty() && imageUri != null))   //이미지와 후기작성이 비어있지않으면 업로드 진행
                {
                    float rating = RatingBarEt.getRating();
                    String reviewDate = mDate.getText().toString();

                    // Create a HashMap to store the review data
                    HashMap<String, Object> reviewwriteMap = new HashMap<>();
                    reviewwriteMap.put("Review_image", reviewImage);
                    reviewwriteMap.put("Write_review", fn);
                    reviewwriteMap.put("Rating", rating);
                    reviewwriteMap.put("Review_date", reviewDate);



                    mRef.push().setValue(reviewwriteMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                // Firebase 데이터 쓰기가 성공한 경우
//                                Intent intent = getIntent();
//                                MyOrder product = (MyOrder) intent.getSerializableExtra("product");
                                product.setDoReview("Yes");

                            } else {
                                // Firebase 데이터 쓰기가 실패한 경우
                                Log.e("Firebase", "Data write failed: " + task.getException().getMessage());
                            }
                        }
                    });

//                    mRef.push().setValue(reviewwriteMap).addOnCompleteListener(new OnCompleteListener<Void>() {
//                        @Override
//                        public void onComplete(@NonNull Task<Void> task) {
//                            if (task.isSuccessful()) {
//                                // 데이터가 성공적으로 추가되었을 때
////                                Query query = databaseReference.orderByChild("doReview").equalTo("false");
//
//                                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
//                                    @Override
//                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                                            // 각 데이터의 경로 가져오기
////                                            DatabaseReference dataToUpdateRef = snapshot.getRef();
//                                            String doReview = ""+snapshot.child("doReview").getValue();
//                                            if (doReview.equals("false")){
//                                                snapshot.child("doReview").getRef().setValue("true");
//                                            }
//
////                                            // "doReview" 값을 "true"로 업데이트
////                                            Intent intent = getIntent();
////                                            MyOrder product = (MyOrder) intent.getSerializableExtra("product");
////                                            product.setDoReview("true");
////                                            dataToUpdateRef.child("doReview").setValue("true");
//                                        }
//                                    }
//                                    @Override
//                                    public void onCancelled(@NonNull DatabaseError databaseError) {
//                                        // 쿼리 취소 또는 실패 시 처리
//                                    }
//                                });
//                            } else {
//                                // 데이터 추가가 실패한 경우
////                                Log.e("Firebase", "Data write failed: " + task.getException().getMessage());
//                            }
//                        }
//                    });

//                    mRef.push().setValue(reviewwriteMap).addOnCompleteListener(new OnCompleteListener<Void>() {
//                        @Override
//                        public void onComplete(@NonNull Task<Void> task) {
//                            if (task.isSuccessful()) {
//                             databaseReference.getKey().child("doReview").setValue("true").addOnCompleteListener(new OnCompleteListener<Void>() {
//                                                @Override
//                                                public void onComplete(@NonNull Task<Void> task) {
//                                                    if (task.isSuccessful()) {
//                                                        Log.d("FragmentQList", "doReview 키값 변경 완료");
//                                                    } else {
//                                                        Log.e("FragmentQList", "doReview 키값 변경 실패", task.getException());
//                                                    }
//                                                }
//                                            });
//                                        }
//                                    }
//
//                                    @Override
//                                    public void onCancelled(@NonNull DatabaseError error) {
//                                        Log.e("FragmentQList", "데이터 가져오기 실패", error.toException());
//                                    }
//                                });
//                            } else {
//                                Log.e("FragmentQList", "데이터 추가 실패", task.getException());
//                            }
//                        }
//                    });





                    AlertDialog.Builder builder = new AlertDialog.Builder(Review_write.this);

                    builder.setTitle("작성 완료").setMessage("감사합니다!");


                    builder.setPositiveButton("홈 이동", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            Intent intent = new Intent(Review_write.this, MainActivity.class);
                            startActivity(intent);
                        }
                    });

                    builder.setNegativeButton("시드 확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
//                            Intent intent = new Intent(Review_write.this, ReviewHistoryActivity.class);
//                            startActivity(intent);
                        }
                    });

                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();

                }else {
                }
            }

        });
    }
}