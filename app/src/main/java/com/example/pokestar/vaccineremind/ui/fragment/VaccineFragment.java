package com.example.pokestar.vaccineremind.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pokestar.vaccineremind.MainActivity;
import com.example.pokestar.vaccineremind.R;
import com.example.pokestar.vaccineremind.adapter.VaccineKnowAdapter;
import com.example.pokestar.vaccineremind.application.MyApplication;
import com.example.pokestar.vaccineremind.bean.Baby;
import com.example.pokestar.vaccineremind.bean.User;
import com.example.pokestar.vaccineremind.bean.VaccineNews;
import com.example.pokestar.vaccineremind.database.VNDao;
import com.example.pokestar.vaccineremind.ui.activity.AddBabyActivity;
import com.example.pokestar.vaccineremind.ui.activity.MyBabyActivity;
import com.example.pokestar.vaccineremind.ui.activity.VaccinePlanActivity;
import com.example.pokestar.vaccineremind.ui.activity.WebNewsActivity;
import com.example.pokestar.vaccineremind.utils.Configure;
import com.example.pokestar.vaccineremind.utils.ToastUtil;
import com.example.pokestar.vaccineremind.widget.NetImageView;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.UpdateListener;
import info.hoang8f.widget.FButton;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link VaccineFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link VaccineFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class VaccineFragment extends BaseFragment {

    private TextView mTextView_baby_data;
    private ImageView mImageView_baby;

    private OnFragmentInteractionListener mListener;
    private FButton button_vacplan;
    private FButton button_calldoc;

    RecyclerView mRecyclerView;
    List<String> titles;
    List<VaccineNews> mNews = new ArrayList<VaccineNews>();


    final List<Baby> mBabyList = new ArrayList<>();

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
        button_vacplan.setButtonColor(Color.parseColor("#43bf79"));
        button_vacplan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), VaccinePlanActivity.class);
                startActivity(intent);
            }
        });
        button_calldoc = view.findViewById(R.id.button_call_doc);
        button_calldoc.setButtonColor(Color.parseColor("#43bf79"));
        button_calldoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO 联系门诊
                replaceFragment(AddVaccineFragment.newInstance());
            }
        });



        FloatingActionButton actionC = new FloatingActionButton(getActivity());
        actionC.setTitle("test-add baby");
        actionC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),AddBabyActivity.class));
            }
        });

        final FloatingActionsMenu menuMultipleActions = (FloatingActionsMenu)view.findViewById(R.id.multiple_actions);
        menuMultipleActions.addButton(actionC);




        final FloatingActionButton actionA = (FloatingActionButton)view.findViewById(R.id.action_a);
        actionA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO 添加宝宝
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

        //floatingButton
        final View actionB =view.findViewById(R.id.action_b);
        actionB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), MyBabyActivity.class));
            }
        });



        initToolbarView(view);

        initData();

        initRecyclerView(view);

        return view;

    }

    private void initData() {
        titles = new ArrayList<String>();
        titles.add("this is tile.");

//        BmobQuery<VaccineNews> query = new BmobQuery<VaccineNews>();
//        //返回50条数据，如果不加上这条语句，默认返回10条数据
//        query.setLimit(4);
//        //执行查询方法
//        query.findObjects(new FindListener<VaccineNews>() {
//            @Override
//            public void done(List<VaccineNews> object, BmobException e) {
//                if(e==null){
//                    //获得SharedPreferences的实例 sp_name是文件名
//                    SharedPreferences sp = getActivity().getSharedPreferences("vaccine_news", Context.MODE_PRIVATE);
//                    //获得Editor 实例
//                    SharedPreferences.Editor editor = sp.edit();
//                    //以key-value形式保存数据
//                    editor.putString("1_title", "data");
//                    //apply()是异步写入数据
//                    editor.apply();
//
//
//                    ToastUtil.showShort(getActivity(), "");
//
//                }else{
//                    Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
//                }
//            }
//
//        });
        mNews.add(new VaccineNews("关于刷屏的“假疫苗”真相，你知道多少","https://baijiahao.baidu.com/s?id=1606664160402430025&wfr=spider&for=pc","https://ss2.baidu.com/6ONYsjip0QIZ8tyhnq/it/u=3971911016,3067881133&fm=173&app=25&f=JPEG?w=640&h=265&s=3C24C21512717621165814710300C0F0"));
        mNews.add(new VaccineNews("宝宝必打疫苗有哪些?","http://www.mama.cn/z/art/58093/","http://pics.mama.cn/attachment/mamacn/images/201805/20180528/103347_80284.jpg"));
        mNews.add(new VaccineNews("开启每日补充“新”计划，让宝宝身高增!","http://zt.mama.cn/subject/dymwdkt4-pc/","http://pics.mama.cn/attachment/mamacn/images/201803/20180313/094637_77585_w280_h140.jpg"));
        mNews.add(new VaccineNews("如何预防秋燥上火，让宝宝少生病？","http://www.mama.cn/special/sangongzi/","http://pics.mama.cn/attachment/mamacn/images/201709/20170928/144008_15350_w280_h140.jpg"));
        mNews.add(new VaccineNews("全城热议二胎开放 生or不生？","http://www.mama.cn/special/ertai/","http://pics.mama.cn/attachment/mamacn/images/201511/20151106/100616_68112_w280_h140.jpg"));

    }

    private void initRecyclerView(View view) {




        mRecyclerView = view.findViewById(R.id.recycler_view_vaccine_know);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        final VaccineKnowAdapter adapter = new VaccineKnowAdapter(getActivity(),mNews);
        adapter.setItemClickListener(new VaccineKnowAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                //TODO 跳转webview
                Intent intent = new Intent(getActivity(), WebNewsActivity.class);
                intent.putExtra("url",mNews.get(position).getUrl());
                startActivity(intent);
            }
        });
        mRecyclerView.setAdapter(adapter);



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
                            Baby baby1 = baby;
                            mBabyList.add(baby1);


                        }else {
                            addFragment(AddVaccineFragment.newInstance(),"setVaccine");
                            ToastUtil.showShort(getActivity(),e.getMessage());
                        }

                    }
                });
            }

        }


       // ToastUtil.showShort(getActivity(),"1 " + mBabyList.size());


    }

    private void initToolbarView(View view) {

        mImageView_baby = view.findViewById(R.id.image_baby);
        mTextView_baby_data = view.findViewById(R.id.text_baby_data);

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
                                        mTextView_baby_data.setText(baby.getName() + " | " + baby.getBirth().getDate().subSequence(0,10));

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
