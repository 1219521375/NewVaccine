package com.example.pokestar.vaccineremind.ui.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.icu.util.Calendar;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.pokestar.vaccineremind.MainActivity;
import com.example.pokestar.vaccineremind.R;
import com.example.pokestar.vaccineremind.bean.Baby;
import com.example.pokestar.vaccineremind.bean.User;
import com.example.pokestar.vaccineremind.utils.Configure;
import com.example.pokestar.vaccineremind.utils.ToastUtil;
import com.github.florent37.materialtextfield.MaterialTextField;

import java.util.Date;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobDate;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import info.hoang8f.widget.FButton;

public class AddBabyActivity extends AppCompatActivity {

    private EditText mTextBabyName;
    private EditText mTextBabyBirth;

    String babyname;
    BmobDate babybirth;

    String sex;

    Calendar mCalendar;
    MaterialTextField mMaterialTextField;


    private FButton mButtonMan;
    private FButton mButtonWoman;

    private FButton mButtonAddOk;

    private Button mButtonBack;
    Button addImage;
    ImageView babyImage;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_baby);

        mTextBabyName = findViewById(R.id.add_baby_name);
        mTextBabyBirth = findViewById(R.id.add_baby_birth);


        mButtonMan = findViewById(R.id.sex_man);

        mButtonWoman = findViewById(R.id.sex_woman);
        mMaterialTextField = findViewById(R.id.baby_birth_front);

        addImage = findViewById(R.id.baby_image);
        babyImage = findViewById(R.id.baby_imageView);

        initView();

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void initView() {


        mCalendar = Calendar.getInstance();
        /*mMaterialTextField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });*/


        mTextBabyBirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
/*
                显示日期选择器
                 */
                new DatePickerDialog(AddBabyActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        // 此处得到选择的时间，可以进行你想要的操作  实际month = month +1
                        Date date = new Date(year-1900,month,dayOfMonth);
                        babybirth = new BmobDate(date);
                        mTextBabyBirth.setText("宝宝生日：" + year + "-" + (month+1) + "-" +dayOfMonth);
                        mTextBabyBirth.setHint("宝宝生日：" + year + "-" + (month+1) + "-" +dayOfMonth);

                    }
                }
                        // 设置初始日期
                        ,mCalendar.get(Calendar.YEAR)
                        ,mCalendar.get(Calendar.MONTH)
                        ,mCalendar.get(Calendar.DAY_OF_MONTH)).show();


            }
        });

        mButtonMan.setButtonColor(Color.parseColor("#43bf79"));
        mButtonWoman.setButtonColor(Color.parseColor("#43bf79"));
        mButtonMan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mButtonWoman.setButtonColor(R.color.fbutton_color_orange);
                mButtonMan.setButtonColor(R.color.fbutton_color_silver);
                sex = "男";
            }
        });
        mButtonAddOk = findViewById(R.id.button_baby_add_ok);

        mButtonWoman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mButtonWoman.setButtonColor(R.color.fbutton_color_silver);
                mButtonMan.setButtonColor(R.color.fbutton_color_orange);
                sex = "女";
            }
        });




        mButtonAddOk = findViewById(R.id.button_baby_add_ok);
        mButtonAddOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Baby mBaby = new Baby();
                babyname = mTextBabyName.getText().toString();
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
