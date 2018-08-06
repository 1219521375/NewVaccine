package com.example.pokestar.vaccineremind.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.example.pokestar.vaccineremind.R;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initview();
    }

    private void initview() {
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar_login_activity);
        setSupportActionBar(toolbar);
    }


}
