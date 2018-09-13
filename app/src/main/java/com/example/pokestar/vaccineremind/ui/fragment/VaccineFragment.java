package com.example.pokestar.vaccineremind.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pokestar.vaccineremind.MainActivity;
import com.example.pokestar.vaccineremind.R;
import com.example.pokestar.vaccineremind.application.MyApplication;
import com.example.pokestar.vaccineremind.bean.Baby;
import com.example.pokestar.vaccineremind.bean.User;
import com.example.pokestar.vaccineremind.ui.activity.AddBabyActivity;
import com.example.pokestar.vaccineremind.ui.activity.VaccinePlanActivity;
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
 * {@link VaccineFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link VaccineFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class VaccineFragment extends BaseFragment {

    private Button button_add_baby;
    private TextView mTextView_baby_data;
    private ImageView mImageView_baby;

    private OnFragmentInteractionListener mListener;
    private Button button_vacplan;
    private Button button_calldoc;


    public static VaccineFragment newInstance() {
        VaccineFragment fragment = new VaccineFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_vaccine;
    }

    @Override
    protected int getContainerId() {
        return R.id.vaccine_fragment_container;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_vaccine, container, false);

        //根据情况更改Fragment
        initFragmentView();

        button_vacplan = view.findViewById(R.id.button_vaccine_plan);
        button_vacplan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), VaccinePlanActivity.class);
                startActivity(intent);
            }
        });
        button_calldoc = view.findViewById(R.id.button_call_doc);
        button_calldoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO 联系门诊
                replaceFragment(AddVaccineFragment.newInstance());
            }
        });

        initToolbarView(view);

        return view;

    }

    private void initFragmentView() {
        if(Configure.getUSERID(getActivity()).equals("")){
            //未登录
            addFragment(AddVaccineFragment.newInstance(),"setVaccine");

        }else {
            // 已登录


            //判断有无baby
            if(Configure.getBABYID(getActivity()).equals("")){
                //未创建baby
                addFragment(AddVaccineFragment.newInstance(),"setVaccine");

            }else {
                //用户当前有baby
                BmobQuery<Baby> query1 = new BmobQuery<Baby>();
                query1.getObject(Configure.getBABYID(getActivity()), new QueryListener<Baby>() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void done(Baby baby, BmobException e) {
                        if(e == null){
                            if(baby.getVaccineList().size() == 0){
                                //当前无vaccine添加
                                addFragment(AddVaccineFragment.newInstance(),"setVaccine");
                            }else {
                                //当前有需要打的疫苗
                                addFragment(DisplayVaccineFragment.newInstance(),"DisplayVaccine");

                            }


                        }else {
                            addFragment(AddVaccineFragment.newInstance(),"setVaccine");
                            ToastUtil.showShort(getActivity(),e.getMessage());
                        }

                    }
                });
            }

        }


    }

    private void initToolbarView(View view) {

        button_add_baby = view.findViewById(R.id.add_baby);
        mTextView_baby_data = view.findViewById(R.id.text_baby_data);
        mImageView_baby = view.findViewById(R.id.image_baby);

        if(Configure.getUSERID(getActivity()).equals("")){
            //未登录
            mImageView_baby.setImageResource(R.drawable.baby);
            mTextView_baby_data.setText("请登录后添加宝宝！");

        }else {
            // 已登录
            User user = new User();
            final BmobUser user1 = BmobUser.getCurrentUser();

            //判断有无baby
            BmobQuery<User> query = new BmobQuery<User>();
            query.getObject(user1.getObjectId(), new QueryListener<User>() {
                @Override
                public void done(User user, BmobException e) {
                    if(e == null){
                        if(user.getBabyId() == null){
                            //未创建baby
                            mImageView_baby.setImageResource(R.drawable.baby);
                            mTextView_baby_data.setText("请先添加宝宝！");
                        }else {
                            //用户当前有baby
                            BmobQuery<Baby> query1 = new BmobQuery<Baby>();
                            query1.getObject(user.getBabyId(), new QueryListener<Baby>() {
                                @Override
                                public void done(Baby baby, BmobException e) {
                                    if(e == null){
                                        mImageView_baby.setImageResource(R.drawable.baby);
                                        mTextView_baby_data.setText(baby.getName() + " | " + "birth");


                                    }else {
                                        mImageView_baby.setImageResource(R.drawable.baby);
                                        mTextView_baby_data.setText("请先添加宝宝！!");
                                        ToastUtil.showShort(getActivity(),e.getMessage());
                                    }

                                }
                            });
                        }

                    }else {
                        mImageView_baby.setImageResource(R.drawable.baby);
                        mTextView_baby_data.setText("请先添加宝宝");
                        ToastUtil.showShort(getActivity(),e.getMessage());
                    }
                }
            });

        }


        button_add_baby.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Configure.getUSERID(getActivity()).equals("")){
                    //未登录
                    mImageView_baby.setImageResource(R.drawable.baby);
                    mTextView_baby_data.setText("请登录后添加宝宝！");

                }else {

                    // 已登录
                    User user = new User();
                    final BmobUser user1 = BmobUser.getCurrentUser();

                    //判断有无baby
                    BmobQuery<User> query = new BmobQuery<User>();
                    query.getObject(user1.getObjectId(), new QueryListener<User>() {
                        @Override
                        public void done(User user, BmobException e) {
                            if(e == null){
                                if(user.getBabyId() == null){
                                    //未创建baby 跳转添加宝宝页面
                                    startActivity(new Intent(getActivity(), AddBabyActivity.class));

                                }else {
                                    //用户当前有baby
                                    ToastUtil.showShort(getActivity(),"当前支持一用户一个baby");
                                }

                            }else {
                                mImageView_baby.setImageResource(R.drawable.baby);
                                mTextView_baby_data.setText("请先添加宝宝！");
                                ToastUtil.showShort(getActivity(),e.getMessage());
                            }
                        }
                    });




                }
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
