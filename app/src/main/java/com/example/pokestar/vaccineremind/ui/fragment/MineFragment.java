package com.example.pokestar.vaccineremind.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pokestar.vaccineremind.MainActivity;
import com.example.pokestar.vaccineremind.R;
import com.example.pokestar.vaccineremind.ui.activity.ChangePasswordActivity;
import com.example.pokestar.vaccineremind.ui.activity.LoginActivity;
import com.example.pokestar.vaccineremind.ui.activity.RegisterActivity;
import com.example.pokestar.vaccineremind.utils.Configure;
import com.leon.lib.settingview.LSettingItem;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MineFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MineFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MineFragment extends BaseFragment {


    private OnFragmentInteractionListener mListener;

    private TextView mTextViewUser;

    LSettingItem mItemBaby;
    LSettingItem mItemUs;
    LSettingItem mItemLogout;


    public static MineFragment newInstance() {
        MineFragment fragment = new MineFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mine;
    }

    @Override
    protected int getContainerId() {
        return 0;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view;
        if(Configure.getUSERID(getActivity()).equals("")){
            //未登录
            view = inflater.inflate(R.layout.fragment_mine_unregist, container, false);
            initLogin(view);
        }else{
            //已登录
            view = inflater.inflate(R.layout.fragment_mine, container, false);
            initView(view);
        }

        return view;
    }

    private void initLogin(View view) {
        Button loginButton = (Button) view.findViewById(R.id.mine_login_button);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),LoginActivity.class);
                startActivity(intent);
            }
        });

        Button registerButton = (Button)view.findViewById(R.id.mine_register_button);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),RegisterActivity.class);
                startActivity(intent);
            }
        });


    }

    private void initView(View view) {

        //已登录
        mTextViewUser = view.findViewById(R.id.user);
        mTextViewUser.setText(Configure.getUSERID(getActivity()) + "");

        mItemBaby = view.findViewById(R.id.item_my_baby);
        mItemUs = view.findViewById(R.id.item_us);
        mItemLogout = view.findViewById(R.id.item_log_out);

        mItemLogout.setmOnLSettingItemClick(new LSettingItem.OnLSettingItemClick() {
            @Override
            public void click(boolean isChecked) {
                Configure.setBABYID(getActivity(),"");
                Configure.setUSERID(getActivity(),"");
                getActivity().recreate();
            }
        });



    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
