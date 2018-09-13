package com.example.pokestar.vaccineremind.ui.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;

import com.example.pokestar.vaccineremind.MainActivity;
import com.example.pokestar.vaccineremind.R;
import com.example.pokestar.vaccineremind.bean.Baby;
import com.example.pokestar.vaccineremind.bean.User;
import com.example.pokestar.vaccineremind.bean.Vaccine;
import com.example.pokestar.vaccineremind.bean.VaccineList;
import com.example.pokestar.vaccineremind.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobDate;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

public class AddVaccineActivity extends AppCompatActivity {

    private Button mButtonAddTime;
    private Button mButtonAddVacOk;

    Calendar mCalendar;

    int Vacyear,Vacmonth,Vacday;

    private RecyclerView mRecyclerViewVacList;
    List<String> VacList;

    List<Vaccine> BabyVacList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_vaccine);

        mButtonAddTime = findViewById(R.id.add_vaccine_time);
        mButtonAddVacOk = findViewById(R.id.add_vaccine_ok);
        mRecyclerViewVacList = findViewById(R.id.add_vaccine_list);

        initData();

        initView();
    }

    private void initData() {

        BabyVacList = new ArrayList<Vaccine>();
        VacList = new ArrayList<String>();
        for (int i = 0; i < 20; i++) {
            VacList.add("乙肝疫苗" + i);

        }

    }

    @TargetApi(Build.VERSION_CODES.N)
    private void initView() {


        mCalendar = Calendar.getInstance();
        mButtonAddTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                显示日期选择器
                 */
                new DatePickerDialog(AddVaccineActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        // 此处得到选择的时间，可以进行你想要的操作
                        Vacyear =  year;
                        Vacmonth = month+1;
                        Vacday = dayOfMonth;
                    }
                }
                        // 设置初始日期
                        ,mCalendar.get(Calendar.YEAR)
                        ,mCalendar.get(Calendar.MONTH)
                        ,mCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        mRecyclerViewVacList.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        mRecyclerViewVacList.setAdapter(new VaccineAddAdapter());

        mButtonAddVacOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //当前用户已登录且有baby
                final Baby mBaby = new Baby();
                mBaby.setYear(Vacyear);
                mBaby.setMonth(Vacmonth);
                mBaby.setDay(Vacday);
                mBaby.setVaccineList(BabyVacList);

                final BmobUser user1 = BmobUser.getCurrentUser();
                BmobQuery<User> query = new BmobQuery<User>();
                query.getObject(user1.getObjectId(), new QueryListener<User>() {
                    @Override
                    public void done(User user, BmobException e) {
                        if (e == null){
                            mBaby.update(user.getBabyId(), new UpdateListener() {
                                @Override
                                public void done(BmobException e) {
                                    if (e == null){
                                        ToastUtil.showShort(getApplicationContext(),"成功设置！");
                                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                        finish();
                                    }else {
                                        ToastUtil.showShort(getApplicationContext(),e.getMessage());
                                    }
                                }
                            });
                        }else {
                            ToastUtil.showShort(getApplicationContext(),e.getMessage());
                        }

                    }
                });
            }
        });


    }

    public class VaccineAddAdapter extends RecyclerView.Adapter<VaccineAddAdapter.ViewHolder>{


        /**
         * Called when RecyclerView needs a new {@link ViewHolder} of the given type to represent
         * an item.
         * <p>
         * This new ViewHolder should be constructed with a new View that can represent the items
         * of the given type. You can either create a new View manually or inflate it from an XML
         * layout file.
         * <p>
         * The new ViewHolder will be used to display items of the adapter using
         *
         * @param parent   The ViewGroup into which the new View will be added after it is bound to
         *                 an adapter position.
         * @param viewType The view type of the new View.
         * @return A new ViewHolder that holds a View of the given view type.
         * @see #getItemViewType(int)
         * @see #onBindViewHolder(ViewHolder, int)
         */
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            ViewHolder holder = new ViewHolder(
                    LayoutInflater.from(getApplicationContext()).inflate(R.layout.vaccine_add,parent,false));
            return holder;
        }

        /**
         * Returns the total number of items in the data set held by the adapter.
         *
         * @return The total number of items in this adapter.
         */
        @Override
        public int getItemCount() {
            return VacList.size();
        }

        /**
         * Called by RecyclerView to display the data at the specified position. This method should
         * update the contents of the {@link ViewHolder#itemView} to reflect the item at the given
         * position.
         * <p>
         * Note that unlike {@link ListView}, RecyclerView will not call this method
         * again if the position of the item changes in the data set unless the item itself is
         * invalidated or the new position cannot be determined. For this reason, you should only
         * use the <code>position</code> parameter while acquiring the related data item inside
         * this method and should not keep a copy of it. If you need the position of an item later
         * on (e.g. in a click listener), use {@link ViewHolder#getAdapterPosition()} which will
         * have the updated adapter position.
         *
         * @param holder   The ViewHolder which should be updated to represent the contents of the
         *                 item at the given position in the data set.
         * @param position The position of the item within the adapter's data set.
         */
        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {
            holder.mCheckBox.setText(VacList.get(position));

            holder.mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked){
                        //已选择
                        Vaccine vaccine = new Vaccine();
                        vaccine.setVacName(VacList.get(position));
                        BabyVacList.add(vaccine);

                    }else {
                        //取消选择
                        Vaccine vaccine = new Vaccine();
                        vaccine.setVacName(VacList.get(position) + "canceled");
                        removeVaccine(BabyVacList,vaccine);

                    }
                }
            });

        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            CheckBox mCheckBox;
            public ViewHolder(View itemView) {
                super(itemView);
                mCheckBox = itemView.findViewById(R.id.box);
            }
        }

    }

    public void removeVaccine(List<Vaccine> list ,Vaccine vaccine){
        //理论上应该删除的vaccine在list中有且只有一个
        //只需要保证应该删除的不存在就行，是否找到无所谓（找不到就是没有）

        for (int i = 0; i < list.size(); i++) {

            if(list.get(i).getVacName().equals(vaccine.getVacName())){
                //找到相同vaccine
                list.remove(i);
                break;
            }
        }

    }




}
