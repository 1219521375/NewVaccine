package com.example.pokestar.vaccineremind.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.pokestar.vaccineremind.R;
import com.example.pokestar.vaccineremind.bean.Baby;
import com.example.pokestar.vaccineremind.bean.User;
import com.example.pokestar.vaccineremind.ui.activity.AddBabyActivity;
import com.example.pokestar.vaccineremind.ui.activity.AddVaccineActivity;
import com.example.pokestar.vaccineremind.utils.Configure;
import com.example.pokestar.vaccineremind.utils.ToastUtil;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AddVaccineFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AddVaccineFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddVaccineFragment extends BaseFragment {


    private OnFragmentInteractionListener mListener;

    private Button buttonSetVaccine;



    public static AddVaccineFragment newInstance() {
        AddVaccineFragment fragment = new AddVaccineFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_add_vaccine;
    }

    @Override
    protected int getContainerId() {
        return R.id.vaccine_fragment_container;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_vaccine, container, false);
        buttonSetVaccine = view.findViewById(R.id.button_set_vaccine);
        buttonSetVaccine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Configure.getUSERID(getActivity()).equals("")){
                    Toast.makeText(getActivity(),"请先登录！",Toast.LENGTH_SHORT).show();
                }else {

                    final BmobUser user1 = BmobUser.getCurrentUser();

                    //判断用户有无baby
                    BmobQuery<User> query = new BmobQuery<User>();
                    query.getObject(user1.getObjectId(), new QueryListener<User>() {
                        @Override
                        public void done(User user, BmobException e) {
                            if(e == null){
                                if(user.getBabyId() == null){
                                    //未创建baby 弹出toast
                                    ToastUtil.showShort(getActivity(),"请先添加baby！");

                                }else {
                                    //用户当前有baby  跳转添加疫苗页面
                                    startActivity(new Intent(getActivity(), AddVaccineActivity.class));
                                    getActivity().finish();
                                }

                            }else {
                                ToastUtil.showShort(getActivity(),e.getMessage());
                            }
                        }
                    });



                }
            }
        });
        return view;
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


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


}
