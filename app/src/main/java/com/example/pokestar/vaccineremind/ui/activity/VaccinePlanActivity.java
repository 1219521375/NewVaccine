package com.example.pokestar.vaccineremind.ui.activity;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.pokestar.vaccineremind.MainActivity;
import com.example.pokestar.vaccineremind.R;
import com.example.pokestar.vaccineremind.ui.fragment.planfragment.VaccineRecordFragment;
import com.example.pokestar.vaccineremind.utils.FragmentAdapter;

public class VaccinePlanActivity extends BaseActivity {

    private Toolbar mToolbar;
    private FragmentAdapter mFragmentAdapter;

    @Override
    protected Fragment createFragment() {
        return VaccineRecordFragment.newInstance();
    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_vaccine_plan;
    }

    @Override
    public int getContainerId() {
        return R.id.vaccine_plan_list;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vaccine_plan);
        mToolbar = (Toolbar)findViewById(R.id.toolbar_vaccine_plan);

        initView();
        addFragment(VaccineRecordFragment.newInstance(),"00");


    }

    private void initView() {
        mToolbar.setTitle("接种记录");
        mToolbar.setNavigationIcon(R.drawable.back);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            }
        });

//        List<Fragment> fragmentList = new ArrayList<Fragment>();
//        if(fragmentList!=null){
//            ArrayList<String> nameList=new ArrayList<>();
//
//            VaccineRecordFragment ff=VaccineRecordFragment.newInstance();
//            fragmentList.add(ff);
//            ReferVaccineFragment f1 = ReferVaccineFragment.newInstance();
//            fragmentList.add(f1);
//        }
//        mFragmentAdapter=new FragmentAdapter(getSupportFragmentManager(),fragmentList);
//
//
//
//        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager_vaccine_plan);
//        viewPager.setAdapter(mFragmentAdapter);
//        mTabLayout.setupWithViewPager(viewPager);
//
//        mTabLayout.getTabAt(0).setText("接种记录");
//        mTabLayout.getTabAt(1).setText("接种参考");




    }


}
