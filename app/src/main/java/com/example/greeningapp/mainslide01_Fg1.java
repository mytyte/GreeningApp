package com.example.greeningapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.greeningapp.shop.ShoppingMainActivity;

public class mainslide01_Fg1 extends Fragment {

    private TextView slide01_main1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.slide01_main1, container, false);

        slide01_main1 = rootView.findViewById(R.id.slide01_main1); // 레이아웃 파일에서 TextView의 ID를 사용하여 뷰를 찾는다.

        slide01_main1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Intent intent = new Intent(getActivity(), ShoppingMainActivity.class);
//                startActivity(intent);
            }
        });

        return rootView;

    }

}
