package com.example.pokestar.vaccineremind;

import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.example.pokestar.vaccineremind.ui.activity.SingleFragmentActivity;
import com.example.pokestar.vaccineremind.ui.fragment.ForumFragment;
import com.example.pokestar.vaccineremind.ui.fragment.KnowledgeFragment;
import com.example.pokestar.vaccineremind.ui.fragment.MineFragment;
import com.example.pokestar.vaccineremind.ui.fragment.VaccineFragment;
import com.example.pokestar.vaccineremind.utils.BottomNavigationViewHelper;

import java.lang.reflect.Field;

public class MainActivity extends SingleFragmentActivity {

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

    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            mMenuItem = item;
            switch (item.getItemId()){
                case R.id.navigation_vaccine:
                    switchFragment(VaccineFragment.newInstance());
                    return true;
                case R.id.navigation_knowledge:
                    switchFragment(KnowledgeFragment.newInstance());
                    return true;
                /*case R.id.navigation_forum:
                    switchFragment(ForumFragment.newInstance());
                    return true;*/
                case R.id.navigation_mine:
                    switchFragment(MineFragment.newInstance());
                    return true;
            }

            return false;
        }
    };




}
