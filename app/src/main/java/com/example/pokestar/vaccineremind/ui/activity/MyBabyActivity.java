package com.example.pokestar.vaccineremind.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.pokestar.vaccineremind.R;
import com.example.pokestar.vaccineremind.bean.Baby;
import com.example.pokestar.vaccineremind.bean.User;
import com.example.pokestar.vaccineremind.ui.fragment.AddVaccineFragment;
import com.example.pokestar.vaccineremind.ui.fragment.DisplayVaccineFragment;
import com.example.pokestar.vaccineremind.utils.Configure;
import com.example.pokestar.vaccineremind.utils.ToastUtil;
import com.example.pokestar.vaccineremind.widget.CircleImageView;

import java.io.File;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;


public class MyBabyActivity extends AppCompatActivity {

    CircleImageView babyImage;
    TextView babyName;
    TextView babySex;
    TextView babyBirth;
    Button babyBack;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_baby);

        babyImage = findViewById(R.id.my_baby_image);
        babyName = findViewById(R.id.my_baby_name);
        babySex = findViewById(R.id.my_baby_sex);
        babyBirth = findViewById(R.id.my_baby_birth);
        babyBack = findViewById(R.id.my_baby_back);


        //用户当前有baby
        BmobQuery<Baby> query1 = new BmobQuery<Baby>();
        query1.getObject(Configure.getBABYID(getApplicationContext()), new QueryListener<Baby>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void done(Baby baby, BmobException e) {
                if(e == null){
                    babyName.setText(baby.getName());
                    babySex.setText(baby.getSex());
                    babyBirth.setText(baby.getBirth().getDate().replace("00:00:00",""));
                    babyImage.setImageURI(Uri.fromFile(new File(baby.getImagePath())));

                }else {
                    ToastUtil.showShort(getApplicationContext(),e.getMessage());
                }

            }
        });


    }
}
