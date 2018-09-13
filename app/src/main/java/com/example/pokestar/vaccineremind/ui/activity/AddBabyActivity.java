package com.example.pokestar.vaccineremind.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.pokestar.vaccineremind.MainActivity;
import com.example.pokestar.vaccineremind.R;
import com.example.pokestar.vaccineremind.bean.Baby;
import com.example.pokestar.vaccineremind.bean.User;
import com.example.pokestar.vaccineremind.utils.Configure;
import com.example.pokestar.vaccineremind.utils.ToastUtil;

import java.util.Date;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobDate;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

public class AddBabyActivity extends AppCompatActivity {

    private EditText mTextBabyName;
    private EditText mTextBabyBirth;

    String babyname;
    BmobDate babybirth;

    String sex;



    private Button mButtonMan;
    private Button mButtonWoman;

    private Button mButtonAddOk;

    private Button mButtonBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_baby);

        initView();

    }

    private void initView() {

        mTextBabyName = findViewById(R.id.add_baby_name);
        mTextBabyBirth = findViewById(R.id.add_baby_birth);



        mButtonMan = findViewById(R.id.sex_man);

        mButtonWoman = findViewById(R.id.sex_woman);

        mButtonMan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mButtonWoman.setBackgroundColor((Color.parseColor("#d5d6d6")));
                mButtonMan.setBackgroundColor((Color.parseColor("#FFFFFB25")));
                sex = "男";
            }
        });
        mButtonAddOk = findViewById(R.id.button_baby_add_ok);

        mButtonWoman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mButtonWoman.setBackgroundColor((Color.parseColor("#FFFFFB25")));
                mButtonMan.setBackgroundColor((Color.parseColor("#d5d6d6")));
                sex = "女";
            }
        });

        mButtonAddOk = findViewById(R.id.button_baby_add_ok);
        mButtonAddOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Baby mBaby = new Baby();
                babyname = mTextBabyName.getText().toString();
                babybirth = new BmobDate(new Date());
                mBaby.setName(babyname);
                mBaby.setBirth(babybirth);
                mBaby.setSex(sex);

                final BmobUser user1 = BmobUser.getCurrentUser();
                mBaby.setUserId(user1.getObjectId());
                mBaby.save(new SaveListener<String>() {
                    @Override
                    public void done(String s, BmobException e) {
                        if(e == null){
                            final User user = new User();
                            user.setBabyId(mBaby.getObjectId());
                            user.update(user1.getObjectId(), new UpdateListener() {
                                @Override
                                public void done(BmobException e) {
                                    if(e == null){
                                        //存储成功
                                        Configure.setBABYID(getApplicationContext(),mBaby.getObjectId());
                                        ToastUtil.showShort(getApplicationContext(),"success " + mBaby.getObjectId());
                                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                        finish();
                                    }else {
                                        ToastUtil.showShort(getApplicationContext(),e.getMessage());
                                    }
                                }
                            });
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            finish();
                        }else {
                            ToastUtil.showShort(getApplicationContext(),e.getMessage());
                        }

                    }
                });

            }
        });

        mButtonBack = findViewById(R.id.add_baby_back);
        mButtonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
