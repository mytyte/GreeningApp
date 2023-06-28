package com.example.greeningapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class LogoutActivity extends AppCompatActivity{
    private FirebaseAuth mFirebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logout);

        mFirebaseAuth = FirebaseAuth.getInstance();

        Button btn_logout = findViewById(R.id.btn_logout);
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                // 로그아웃 하기
                mFirebaseAuth.signOut();

                Intent intent = new Intent(LogoutActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
        //탈퇴 처리
        mFirebaseAuth.getCurrentUser().delete();
    }

}
