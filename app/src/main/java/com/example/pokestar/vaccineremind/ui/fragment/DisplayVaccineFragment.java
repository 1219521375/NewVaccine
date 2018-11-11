package com.example.pokestar.vaccineremind.ui.fragment;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.icu.util.Calendar;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.CollapsibleActionView;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mapsdkvi.VDeviceAPI;
import com.example.pokestar.vaccineremind.R;
import com.example.pokestar.vaccineremind.bean.Baby;
import com.example.pokestar.vaccineremind.bean.Vaccine;
import com.example.pokestar.vaccineremind.bean.VaccineDetail;
import com.example.pokestar.vaccineremind.bean.VaccineList;
import com.example.pokestar.vaccineremind.ui.activity.AddVaccineActivity;
import com.example.pokestar.vaccineremind.ui.activity.DetailActivity;
import com.example.pokestar.vaccineremind.utils.Configure;
import com.example.pokestar.vaccineremind.utils.ToastUtil;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import java.util.logging.StreamHandler;

import cn.bmob.v3.BmobACL;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.b.V;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DisplayVaccineFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DisplayVaccineFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
@RequiresApi(api = Build.VERSION_CODES.N)
public class DisplayVaccineFragment extends BaseFragment {

    private OnFragmentInteractionListener mListener;
    String vacListId;

    TextView mTextViewVacTime;
    TextView mTextViewVacName;
    TextView mTextViewleftTime;

    private String VacName = "";
    private String VacTime = "";

    Button mButtonChange;

    Baby mBaby;
    Button buttonDetail;

    Calendar mCalendar = Calendar.getInstance();

    // TODO: Rename and change types and number of parameters
    public static DisplayVaccineFragment newInstance() {
        DisplayVaccineFragment fragment = new DisplayVaccineFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_display_vaccine;
    }

    @Override
    protected int getContainerId() {
        return R.id.vaccine_fragment_container;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_display_vaccine, container, false);
        initData();
        initView(view);
        return view;
    }

    private void initData() {





    }

    private void initView(View view) {



        mTextViewVacTime = view.findViewById(R.id.display_vaccine_time);
        mTextViewVacName = view.findViewById(R.id.display_vaccine_name);
        mTextViewleftTime = view.findViewById(R.id.display_vaccine_left_time);


        BmobQuery<Baby> query = new BmobQuery<Baby>();
        query.getObject(Configure.getBABYID(getActivity()), new QueryListener<Baby>() {
            @Override
            public void done(Baby baby, BmobException e) {
                if(e == null){
                    mBaby = baby;
                    VacTime = baby.getYear() + "年" + baby.getMonth() + "月" + baby.getDay() + "日";
                    for (int i = 0; i < baby.getVaccineList().size(); i++) {
                        VacName = VacName + baby.getVaccineList().get(i).getVacName() + " ";

                    }
                    String baby_month,baby_day;
                    if(baby.getMonth()<10) {
                        baby_month = "0" + baby.getMonth();
                    }else {
                        baby_month = baby.getMonth() + "";
                    }
                    if(baby.getDay()<10){
                        baby_day = "0" + baby.getDay();
                    }else {
                        baby_day = baby.getDay() + "";
                    }
                    String nextVacTime = baby.getYear() + "-" + baby_month + "-" + baby_day;
                    String month,day;
                    if((mCalendar.get(Calendar.MONTH)+1)<10) {
                        month = "0" + (mCalendar.get(Calendar.MONTH)+1);
                    }else {
                        month = (mCalendar.get(Calendar.MONTH)+1) + "";
                    }

                    if(mCalendar.get(Calendar.DAY_OF_MONTH)<10){
                        day = "0" + mCalendar.get(Calendar.DAY_OF_MONTH);
                    }else {
                        day = mCalendar.get(Calendar.DAY_OF_MONTH) + "";
                    }

                    String dayTime = mCalendar.get(Calendar.YEAR) + "-" + month + "-" + day;
                    mTextViewVacTime.setText(VacTime);
                    mTextViewVacName.setText(VacName);
                    mTextViewleftTime.setText("距离接种日还有" + getDaySub(dayTime,nextVacTime) + "天");
                    if(baby.getYear() > mCalendar.get(Calendar.YEAR)){
                        //时间未到
                    }else if(baby.getYear() == mCalendar.get(Calendar.YEAR)) {
                        //时间未到
                        if(baby.getMonth() > mCalendar.get(Calendar.MONTH)+1){
                            //时间未到
                        }else if(baby.getMonth() == mCalendar.get(Calendar.MONTH)+1){
                            if(baby.getDay() > mCalendar.get(Calendar.DAY_OF_MONTH)){
                                //时间未到
                            }else if (baby.getDay() == mCalendar.get(Calendar.DAY_OF_MONTH)){
                                // TODO 打疫苗了
                                showNormalMoreButtonDialog();
                            }else {
                                // TODO 打疫苗了
                                showNormalMoreButtonDialog();
                            }

                        }else {
                            // TODO 打疫苗了
                            showNormalMoreButtonDialog();
                        }

                    }else {
                        // TODO 打疫苗了
                        showNormalMoreButtonDialog();
                    }


                }else {
                    ToastUtil.showShort(getActivity(),e.getMessage());
                }
            }
        });


        mButtonChange = view.findViewById(R.id.display_change_time);
        mButtonChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                显示日期选择器
                 */
                new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, final int year, final int month, final int dayOfMonth) {
                        // 此处得到选择的时间，可以进行你想要的操作

                        Baby baby = new Baby();
                        baby.setYear(year);
                        baby.setMonth(month+1);
                        baby.setDay(dayOfMonth);
                        baby.update(Configure.getBABYID(getActivity()), new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                if(e==null){
                                    ToastUtil.showShort(getActivity(),"您选择了" + year + "年" + (month + 1) + "月" + dayOfMonth + "日。");
                                }else{
                                    Log.i("bmob","更新失败："+e.getMessage()+","+e.getErrorCode());
                                }
                            }
                        });
                    }
                }
                        // 设置初始日期
                        ,mCalendar.get(Calendar.YEAR)
                        ,mCalendar.get(Calendar.MONTH)
                        ,mCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        buttonDetail = (Button)view.findViewById(R.id.details_button);
        buttonDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                BmobQuery<Baby> query = new BmobQuery<Baby>();
                query.getObject(Configure.getBABYID(getActivity()), new QueryListener<Baby>() {
                    @Override
                    public void done(final Baby baby, BmobException e) {
                        if (e == null) {
                            ArrayList<String> vaccine = new ArrayList<>();
                            for (int i = 0; i < baby.getVaccineList().size(); i++) {
                                vaccine.add(baby.getVaccineList().get(i).getVacName().substring(0,2));
                            }

                            BmobQuery<VaccineDetail> query = new BmobQuery<VaccineDetail>();
                            //执行查询方法
                            query.findObjects(new FindListener<VaccineDetail>() {
                                @Override
                                public void done(List<VaccineDetail> object, BmobException e) {
                                    if(e==null){
                                        ArrayList<String> vacNames = new ArrayList<>();
                                        ArrayList<String> vacReaction = new ArrayList<>();
                                        ArrayList<String> vacNotice = new ArrayList<>();
                                        for (int i = 0; i < baby.getVaccineList().size(); i++) {
                                            for (int j = 0; j < object.size(); j++) {
                                                if(baby.getVaccineList().get(i).getVacName().substring(0,2).equals(
                                                        object.get(i).getName().substring(0,2)
                                                )){
                                                    vacNames.add(object.get(j).getName());
                                                    vacNotice.add(object.get(j).getNotice());
                                                    vacReaction.add(object.get(j).getReaction());

                                                }
                                            }
                                        }
                                        Intent intent = new Intent(getActivity(), DetailActivity.class);
                                        intent.putStringArrayListExtra("namelist",vacNames);
                                        intent.putStringArrayListExtra("noticelist",vacNotice);
                                        intent.putStringArrayListExtra("reactionlist",vacReaction);
                                        startActivity(intent);
                                    }else{
                                        Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                                    }
                                }
                            });




                        } else {
                            ToastUtil.showShort(getActivity(),"error");
                        }
                    }


                });
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


    /*
    * 显示对话框
    * TODO 未完成
    * */
    private void showNormalMoreButtonDialog(){
        AlertDialog.Builder normalMoreButtonDialog = new AlertDialog.Builder(getActivity());
        normalMoreButtonDialog.setTitle("您在当天去打疫苗了吗？");
        normalMoreButtonDialog.setIcon(R.drawable.ic_vac);
        normalMoreButtonDialog.setMessage("您有没有按时前去打疫苗呢？");

        //设置按钮
        normalMoreButtonDialog.setPositiveButton("是的"
                , new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //TODO
                        //更新vacclinelIST 与baby绑定
                        BmobQuery<Baby> query = new BmobQuery<Baby>();
                        query.getObject(Configure.getBABYID(getActivity()), new QueryListener<Baby>() {
                            @Override
                            public void done(Baby baby, BmobException e) {
                                if(e == null){
                                    if(baby.getVacListId() == null||baby.getVacListId() == ""){
                                        VaccineList vaccineList = new VaccineList();
                                        List<Vaccine> vaccines = new ArrayList<Vaccine>();
                                        for (int i = 0; i < baby.getVaccineList().size(); i++) {
                                            Vaccine vaccine = baby.getVaccineList().get(i);
                                            vaccine.setVacTime(baby.getYear() + "年"
                                                    + baby.getMonth() + "月" + baby.getDay() + "日");
                                            vaccines.add(vaccine);
                                        }
                                        vaccineList.setVaccineList(vaccines);
                                        vaccineList.setBabyId(Configure.getBABYID(getActivity()));


                                        vaccineList.save(new SaveListener<String>() {
                                            @Override
                                            public void done(String s, BmobException e) {
                                                if(e == null){
                                                    Log.i("save","vaclist save");
                                                    vacListId = s;
                                                    List<Vaccine> list = new ArrayList<Vaccine>();
                                                    Baby baby1 = new Baby();
                                                    baby1.setVacListId(vacListId);
                                                    baby1.setVaccineList(list);//更新baby vaclist为0
                                                    baby1.update(Configure.getBABYID(getActivity()), new UpdateListener() {
                                                        @Override
                                                        public void done(BmobException e) {
                                                            if(e == null){
                                                                Log.i("save" ,"baby vac id save");
                                                            }else {
                                                                ToastUtil.showShort(getActivity(),e.getMessage() + "3");
                                                            }
                                                        }
                                                    });


                                                }else {
                                                    ToastUtil.showShort(getActivity(),e.getMessage() + "4");
                                                }
                                            }
                                        });


                                    }else {
                                        //当前baby已经有vaclist

                                        final List<Vaccine> list1 = new ArrayList<Vaccine>();
                                        list1.addAll(baby.getVaccineList());

                                        final String vacTime = baby.getYear() + "年"
                                                + baby.getMonth() + "月" + baby.getDay() + "日";
                                        final String vaclistId = baby.getVacListId();

                                        BmobQuery<VaccineList> query = new BmobQuery<VaccineList>();
                                        query.getObject(baby.getVacListId(), new QueryListener<VaccineList>() {
                                            @Override
                                            public void done(VaccineList vaccineList, BmobException e) {
                                                if(e == null){
                                                    List<Vaccine> list = new ArrayList<Vaccine>();
                                                    list.addAll(vaccineList.getVaccineList());

                                                    for (int i = 0; i < list1.size(); i++) {
                                                        Vaccine vaccine = list1.get(i);
                                                        vaccine.setVacTime(vacTime);
                                                        list.add(vaccine);
                                                    }
                                                    vaccineList.setVaccineList(list);
                                                    vaccineList.update(vaclistId, new UpdateListener() {
                                                        @Override
                                                        public void done(BmobException e) {
                                                            if(e == null){
                                                                Log.i("save" ,"baby vac id save 2");

                                                            }else {
                                                                ToastUtil.showShort(getActivity(),e.getMessage() + "2");
                                                            }
                                                        }
                                                    });

                                                    List<Vaccine> list2 = new ArrayList<Vaccine>();
                                                    Baby baby1 = new Baby();
                                                    baby1.setVaccineList(list2);//更新baby vaclist为0
                                                    baby1.update(Configure.getBABYID(getActivity()), new UpdateListener() {
                                                        @Override
                                                        public void done(BmobException e) {
                                                            if(e == null){
                                                                Log.i("save" ,"baby vac id save32");
                                                            }else {
                                                                ToastUtil.showShort(getActivity(),e.getMessage() + "32");
                                                            }
                                                        }
                                                    });


                                                }else {
                                                    ToastUtil.showShort(getActivity(),e.getMessage());
                                                }

                                            }
                                        });






                                    }


                                }else {
                                    ToastUtil.showShort(getActivity(),e.getMessage() + "1");
                                }

                            }
                        });




                        dialog.dismiss();
                    }
                });
        normalMoreButtonDialog.setNegativeButton("没有打"
                , new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 更新baby vaclist为0
                        List<Vaccine> list = new ArrayList<Vaccine>();
                        Baby mBaby = new Baby();
                        mBaby.setVaccineList(list);
                        mBaby.update(Configure.getBABYID(getActivity()), new UpdateListener() {

                            @Override
                            public void done(BmobException e) {
                                if(e==null){
                                    Log.i("bmob","更新成功,vallist = null");
                                }else{
                                    ToastUtil.showShort(getActivity(),e.getMessage());
                                }
                            }
                        });
                        dialog.dismiss();
                    }
                });
        normalMoreButtonDialog.setNeutralButton("取消了本次计划"
                , new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 更新baby vaclist为0
                        List<Vaccine> list = new ArrayList<Vaccine>();
                        Baby mBaby = new Baby();
                        mBaby.setVaccineList(list);
                        mBaby.update(Configure.getBABYID(getActivity()), new UpdateListener() {

                            @Override
                            public void done(BmobException e) {
                                if(e==null){
                                    Log.i("bmob","更新成功,vallist = null");
                                }else{
                                    ToastUtil.showShort(getActivity(),e.getMessage());
                                }
                            }
                        });
                        dialog.dismiss();
                    }
                });

        normalMoreButtonDialog.create().show();
    }


    public static long getDaySub(String beginDateStr,String endDateStr)
     {
         long day=0;
         java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd");
         java.util.Date beginDate;
         java.util.Date endDate;
         try
         {
             beginDate = format.parse(beginDateStr);
             endDate= format.parse(endDateStr);
             day=(endDate.getTime()-beginDate.getTime())/(24*60*60*1000);
             //System.out.println("相隔的天数="+day);
         } catch (ParseException e)
         {
             // TODO 自动生成 catch 块
             e.printStackTrace();
         }
         return day;
     }

}
