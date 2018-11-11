package com.example.pokestar.vaccineremind.ui.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pokestar.vaccineremind.R;
import com.example.pokestar.vaccineremind.adapter.VaccineKnowAdapter;
import com.example.pokestar.vaccineremind.bean.Baby;
import com.example.pokestar.vaccineremind.bean.User;
import com.example.pokestar.vaccineremind.bean.VaccineNews;
import com.example.pokestar.vaccineremind.ui.activity.AddBabyActivity;
import com.example.pokestar.vaccineremind.ui.activity.MyBabyActivity;
import com.example.pokestar.vaccineremind.ui.activity.VacReferActivity;
import com.example.pokestar.vaccineremind.ui.activity.VaccinePlanActivity;
import com.example.pokestar.vaccineremind.ui.activity.WebNewsActivity;
import com.example.pokestar.vaccineremind.utils.Configure;
import com.example.pokestar.vaccineremind.utils.LogUtil;
import com.example.pokestar.vaccineremind.utils.ToastUtil;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;
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

    private static final String TAG = "VaccineFragment";
    private OnFragmentInteractionListener mListener;
    private FButton button_vacplan;
    private FButton button_calldoc;

    RecyclerView mRecyclerView;
    List<String> titles;
    List<VaccineNews> mNews = new ArrayList<VaccineNews>();
    String phoneNum;



    final List<Baby> mBabyList = new ArrayList<>();

    @SuppressLint("HandlerLeak")
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0:
                    mNews = (List<VaccineNews>) msg.obj;
                    break;
            }

        }
    };

    public static VaccineFragment newInstance() {
        VaccineFragment fragment = new VaccineFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "run: 开启线程");
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

        phoneNum = Configure.getUSERID(getActivity());
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
                startActivity(new Intent(getActivity(), VacReferActivity.class));
            }
        });


        LogUtil util = new LogUtil(VaccineFragment.class,true);

        FloatingActionButton actionC = new FloatingActionButton(getActivity());
        actionC.setTitle("发送短信");
        actionC.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                Log.e(TAG, "run: 开启线程222");
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            String res = getReultForHttpPost("18505857152 ","6a68440d0b73db44a36690073","18368520120");
                            Log.e(TAG, "run:短信 " + res);
                        } catch (IOException e) {
                            e.printStackTrace();
                            Log.d(TAG, "run: error" + e.getMessage());
                        } catch (ParseException e) {
                            e.printStackTrace();
                            Log.d(TAG, "run: error" + e.getMessage());
                        }
                    }
                }).start();
                ToastUtil.showShort(getActivity(),"成功发送");
            }
        });

        final FloatingActionsMenu menuMultipleActions = (FloatingActionsMenu)view.findViewById(R.id.multiple_actions);
        menuMultipleActions.addButton(actionC);




        final FloatingActionButton actionA = (FloatingActionButton)view.findViewById(R.id.action_a);
        actionA.setIcon(R.drawable.ic_add_green);
        actionA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
        final FloatingActionButton actionB =view.findViewById(R.id.action_b);
        actionB.setIcon(R.drawable.baby_icon);
        actionB.setOnClickListener(new View.OnClickListener() {
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
                                    //未创建baby 跳转宝宝信息页面
                                    ToastUtil.showShort(getActivity(),"请先创建baby");

                                }else {
                                    //用户当前有baby
                                    startActivity(new Intent(getActivity(), MyBabyActivity.class));
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
//
//                    Message message = handler.obtainMessage();
//                    message.what = 0;
//                    //以消息为载体
//                    message.obj = object;//这里的list就是查询出list
//                    //向handler发送消息
//                    handler.sendMessage(message);
//
//                }else{
//                    Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
//                }
//            }
//
//        });
        mNews.add(new VaccineNews("关于刷屏的“假疫苗”真相，你知道多少",
                "https://baijiahao.baidu.com/s?id=1606664160402430025&wfr=spider&for=pc",
                "https://ss2.baidu.com/6ONYsjip0QIZ8tyhnq/it/u=3971911016,3067881133&fm=173&app=25&f=JPEG?w=640&h=265&s=3C24C21512717621165814710300C0F0"));
        mNews.add(new VaccineNews("宝宝必打疫苗有哪些?",
                "http://www.mama.cn/z/art/58093/",
                "http://pics.mama.cn/attachment/mamacn/images/201805/20180528/103347_80284.jpg"));
        mNews.add(new VaccineNews("疫苗知识干货集合，爸爸妈妈必看！",
                "https://www.sohu.com/a/242668019_100227154",
                "http://5b0988e595225.cdn.sohucs.com/images/20180722/52c9f691b7a243e9883d997288e26e77.jpeg"));
        mNews.add(new VaccineNews("崔玉涛：接种疫苗后不适怎么办",
                "https://yuer.pcbaby.com.cn/120/1204793.html",
                "https://img0.pcbaby.com.cn/pcbaby/1307/05/1204793_cuiyutaoyimiao1.jpg"));
        mNews.add(new VaccineNews("入秋接种7类疫苗 宝宝少生病！",
                "https://yuer.pcbaby.com.cn/121/1216424.html",
                "http://5b0988e595225.cdn.sohucs.com/images/20181017/cfd00e09899544ffbd78782f8b232c41.jpeg"));

    }

    private void initRecyclerView(View view) {




        mRecyclerView = view.findViewById(R.id.recycler_view_vaccine_know);

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false){
            @Override
            public boolean canScrollVertically(){
                return false;
              }
            };

        mRecyclerView.setLayoutManager(linearLayoutManager);
        final VaccineKnowAdapter adapter = new VaccineKnowAdapter(getActivity(),mNews);
        adapter.setItemClickListener(new VaccineKnowAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {


//                //TODO 跳转webview
                ToastUtil.showShort(getActivity(),mNews.get(position).getUrl());
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
                            //ToastUtil.showShort(getActivity(),e.getMessage());
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
                                        mImageView_baby.setImageURI(Uri.fromFile(new File(baby.getImagePath())));
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

    public String getReultForHttpPost(String name,String pwd,String phone) throws ClientProtocolException, IOException,ParseException {
        //服务器  ：服务器项目  ：servlet名称
        //文字定时短信
        String path="http://api.feige.ee/SmsService/Send";
        String time = dateToStamp("2018-10-31 10:50:00");
        HttpPost httpPost=new HttpPost(path);
        List<NameValuePair>list=new ArrayList<NameValuePair>();
        list.add(new BasicNameValuePair("Account", name));
        list.add(new BasicNameValuePair("Pwd", pwd));
        list.add(new BasicNameValuePair("Content", "尊敬的:" + phone + "，您预约在今天为您的宝宝接种疫苗，请及时前往哦！详细信息请进入禾苗APP查询。"));
        //list.add(new BasicNameValuePair("SendTime", time));//日期格式的需要转化成时间戳
        list.add(new BasicNameValuePair("Mobile", phone));
        list.add(new BasicNameValuePair("SignId", "61242"));
        httpPost.setEntity(new UrlEncodedFormEntity(list, HTTP.UTF_8));//与HttpGet区别所在，这里是将参数用List传递

        String result="";

        Log.d(TAG, "getReultForHttpPost: 发送");
        HttpResponse response=new DefaultHttpClient().execute(httpPost);
        if(response.getStatusLine().getStatusCode()==200){
            HttpEntity entity=response.getEntity();
            result= EntityUtils.toString(entity, HTTP.UTF_8);
        }
        Log.d(TAG, "getReultForHttpPost: 接收");
        return result;
    }

    /*
     * 将时间转换为时间戳
     */
    public static String dateToStamp(String s) throws ParseException {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = simpleDateFormat.parse(s);
        long ts = date.getTime();
        res = String.valueOf(ts);
        return res;
    }


}
