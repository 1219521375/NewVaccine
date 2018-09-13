package com.example.pokestar.vaccineremind.ui.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.pokestar.vaccineremind.MainActivity;
import com.example.pokestar.vaccineremind.R;
import com.example.pokestar.vaccineremind.bean.User;
import com.example.pokestar.vaccineremind.utils.Configure;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
注册页面
 */
public class RegisterActivity extends AppCompatActivity {

    private EditText phoneText;
    private EditText passwordText;
    private Button loginButton;

    private String phone_num;
    private String passowrd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initView();

    }

    private void initView() {

        phoneText = findViewById(R.id.phone_number_register);
        passwordText = findViewById(R.id.password_register);
        loginButton = findViewById(R.id.register_ok);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phone_num = phoneText.getText().toString();
                passowrd = passwordText.getText().toString();
                User user1 = new User();
                user1.setUsername(phone_num);
                user1.setPassword(passowrd);

                user1.signUp(new SaveListener<User>() {
                    @Override
                    public void done(User user, BmobException e) {
                        if (e == null){
                            Configure.setUSERID(getApplicationContext(),phone_num);
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            finish();
                        }else {
                            Toast.makeText(getApplicationContext(),"注册失败！",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            finish();
                        }

                    }
                });

            }
        });


    }
}
