package com.example.pokestar.vaccineremind.ui.activity;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.pokestar.vaccineremind.MainActivity;
import com.example.pokestar.vaccineremind.R;
import com.example.pokestar.vaccineremind.ui.fragment.KnowledgeFragment;
import com.example.pokestar.vaccineremind.ui.fragment.planfragment.ReferVaccineFragment;
import com.example.pokestar.vaccineremind.ui.fragment.planfragment.VaccineRecordFragment;
import com.example.pokestar.vaccineremind.utils.FragmentAdapter;

import java.util.ArrayList;
import java.util.List;

public class VaccinePlanActivity extends BaseActivity {

    private Toolbar mToolbar;
    private FragmentAdapter mFragmentAdapter;
    private TabLayout mTabLayout;

    @Override
    protected Fragment createFragment() {
        return KnowledgeFragment.newInstance();
    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_vaccine_plan;
    }

    @Override
    public int getContainerId() {
        return R.id.viewpager_vaccine_plan;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vaccine_plan);
        mToolbar = (Toolbar)findViewById(R.id.toolbar_vaccine_plan);
        mTabLayout = (TabLayout)findViewById(R.id.tabs_vaccine_plan);

        initView();



    }

    private void initView() {
        mToolbar.setTitle("疫苗计划");
        mToolbar.setNavigationIcon(R.drawable.back);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            }
        });

        List<Fragment> fragmentList = new ArrayList<Fragment>();
        if(fragmentList!=null){
            ArrayList<String> nameList=new ArrayList<>();

            VaccineRecordFragment ff=VaccineRecordFragment.newInstance();
            fragmentList.add(ff);
            ReferVaccineFragment f1 = ReferVaccineFragment.newInstance();
            fragmentList.add(f1);
        }
        mFragmentAdapter=new FragmentAdapter(getSupportFragmentManager(),fragmentList);



        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager_vaccine_plan);
        viewPager.setAdapter(mFragmentAdapter);
        mTabLayout.setupWithViewPager(viewPager);

        mTabLayout.getTabAt(0).setText("接种记录");
        mTabLayout.getTabAt(1).setText("接种参考");




    }


}
