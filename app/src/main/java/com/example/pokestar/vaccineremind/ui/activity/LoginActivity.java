package com.example.pokestar.vaccineremind.ui.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
 * 登录界面
 */
public class LoginActivity extends AppCompatActivity {

    private EditText mTextId;
    private EditText mTextPassword;
    private Button mButtonLogin;

    private String id;
    private String passoword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
    }

    private void initView() {
        mTextId = findViewById(R.id.login_id);
        mTextPassword = findViewById(R.id.login_password);
        mButtonLogin = findViewById(R.id.login_button);

        mButtonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id = mTextId.getText().toString();
                passoword = mTextPassword.getText().toString();

                User user = new User();
                user.setUsername(id);
                user.setPassword(passoword);

                user.login(new SaveListener<User>() {
                    @Override
                    public void done(User user, BmobException e) {
                        if (e == null){
                            Configure.setUSERID(getApplicationContext(),id);
                            Configure.setBABYID(getApplicationContext(),user.getBabyId());
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            finish();
                        }else {
                            Toast.makeText(getApplicationContext(),"登录失败！",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            finish();
                        }
                    }
                });


            }
        });


    }
}
