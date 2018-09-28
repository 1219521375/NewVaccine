package com.example.pokestar.vaccineremind;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.pokestar.vaccineremind.ui.activity.BaseActivity;
import com.example.pokestar.vaccineremind.ui.fragment.MineFragment;
import com.example.pokestar.vaccineremind.ui.fragment.VaccineFragment;
import com.example.pokestar.vaccineremind.ui.fragment.VaccineConsultFragment;
import com.example.pokestar.vaccineremind.utils.BottomNavigationViewHelper;

import cn.bmob.v3.Bmob;

public class MainActivity extends BaseActivity {

    private BottomNavigationView mBottomNavigationView;
    private MenuItem mMenuItem;

    @Override
    protected Fragment createFragment() {
        return VaccineFragment.newInstance();
    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_main;
    }

    @Override
    public int getContainerId() {
        return R.id.fragment_container;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBottomNavigationView = (BottomNavigationView)findViewById(R.id.main_bottomNavigationView);
        mBottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        BottomNavigationViewHelper.disableShiftMode(mBottomNavigationView);

        initBmob();

    }

    private void initBmob() {

        //提供以下两种方式进行初始化操作：

        //第一：默认初始化
        Bmob.initialize(this, "18b0ea9598bfe19a53a8a25e02f41354");
        // 注:自v3.5.2开始，数据sdk内部缝合了统计sdk，开发者无需额外集成，传渠道参数即可，不传默认没开启数据统计功能
        //Bmob.initialize(this, "Your Application ID","bmob");

        //第二：自v3.4.7版本开始,设置BmobConfig,允许设置请求超时时间、文件分片上传时每片的大小、文件的过期时间(单位为秒)，
        //BmobConfig config =new BmobConfig.Builder(this)
        ////设置appkey
        //.setApplicationId("Your Application ID")
        ////请求超时时间（单位为秒）：默认15s
        //.setConnectTimeout(30)
        ////文件分片上传时每片的大小（单位字节），默认512*1024
        //.setUploadBlockSize(1024*1024)
        ////文件的过期时间(单位为秒)：默认1800s
        //.setFileExpiration(2500)
        //.build();
        //Bmob.initialize(config);

    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            mMenuItem = item;
            switch (item.getItemId()){
                case R.id.navigation_vaccine:
                    replaceFragment(VaccineFragment.newInstance());
                    return true;
                case R.id.navigation_knowledge:
                    replaceFragment(VaccineConsultFragment.newInstance());
                    return true;
                /*case R.id.navigation_forum:
                    switchFragment(ForumFragment.newInstance());
                    return true;*/
                case R.id.navigation_mine:
                    replaceFragment(MineFragment.newInstance());
                    return true;
            }

            return false;
        }
    };




}
