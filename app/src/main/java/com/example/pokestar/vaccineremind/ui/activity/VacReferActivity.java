package com.example.pokestar.vaccineremind.ui.activity;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.pokestar.vaccineremind.MainActivity;
import com.example.pokestar.vaccineremind.R;
import com.example.pokestar.vaccineremind.ui.fragment.planfragment.ReferVaccineFragment;

public class VacReferActivity extends BaseActivity {


    private Toolbar mToolbar;

    @Override
    protected Fragment createFragment() {
        return ReferVaccineFragment.newInstance();
    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_vac_refer;
    }

    @Override
    public int getContainerId() {
        return R.id.vaccine_plan_refer;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vac_refer);

        mToolbar = (Toolbar)findViewById(R.id.toolbar_vaccine_refer);

        initView();

    }

    private void initView() {
        mToolbar.setTitle("接种参考");
        mToolbar.setNavigationIcon(R.drawable.back);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            }
        });




    }
}
