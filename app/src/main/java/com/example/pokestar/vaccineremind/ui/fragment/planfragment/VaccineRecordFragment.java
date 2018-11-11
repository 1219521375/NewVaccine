package com.example.pokestar.vaccineremind.ui.fragment.planfragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.pokestar.vaccineremind.R;
import com.example.pokestar.vaccineremind.bean.Baby;
import com.example.pokestar.vaccineremind.bean.Vaccine;
import com.example.pokestar.vaccineremind.bean.VaccineList;
import com.example.pokestar.vaccineremind.ui.fragment.BaseFragment;
import com.example.pokestar.vaccineremind.utils.Configure;
import com.example.pokestar.vaccineremind.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link VaccineRecordFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link VaccineRecordFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class VaccineRecordFragment extends BaseFragment {
    RecyclerView mRecyclerView;



    List<Vaccine> mVaccineList = new ArrayList<Vaccine>();
    VaccineRecordAdapter mAdapter;

    final int[] name = new int[2];

    private OnFragmentInteractionListener mListener;



    public static VaccineRecordFragment newInstance() {
        VaccineRecordFragment fragment = new VaccineRecordFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_vaccine_record;
    }

    @Override
    protected int getContainerId() {
        return R.id.vaccine_plan_list;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view;
        final TextView textViewDisplay;
        if(Configure.getUSERID(getActivity()) == ""){
            //未登录
            view = inflater.inflate(R.layout.fragment_vac_record_unlist, container, false);
            textViewDisplay = view.findViewById(R.id.record_display);
            textViewDisplay.setText("请先登录！");
        }
        else{
            //已登录
            if(Configure.getBABYID(getActivity()) == ""){
              //无baby
                view = inflater.inflate(R.layout.fragment_vac_record_unlist, container, false);
                textViewDisplay = view.findViewById(R.id.record_display);
                textViewDisplay.setText("请先添加baby！");

            }else{
                //有baby
                view = inflater.inflate(R.layout.fragment_vaccine_record, container, false);
                BmobQuery<Baby> query = new BmobQuery<Baby>();
                query.getObject(Configure.getBABYID(getActivity()), new QueryListener<Baby>() {
                    @Override
                    public void done(Baby baby, BmobException e) {
                        if(e == null){
                            if(baby.getVacListId()!=null && baby.getVacListId()!= ""){
                                BmobQuery<VaccineList> query = new BmobQuery<VaccineList>();
                                query.getObject(baby.getVacListId(), new QueryListener<VaccineList>() {
                                    @Override
                                    public void done(VaccineList vaccineList, BmobException e) {
                                        mVaccineList = vaccineList.getVaccineList();
                                        if(mVaccineList.size() == 0){

                                            ToastUtil.showShort(getActivity(),"当前没有已打疫苗！");
                                        }else {
                                            ToastUtil.showShort(getActivity(), "11");
                                            initView(view);
                                        }
                                    }
                                });
                            }
                            else{
                                replaceFragment(ReferVaccineFragment.newInstance());
                            }


                        }else {
                            //TODO 在已登录有baby但没有vac记录的情况下会报错 9015 objectid orlistener must not be null（其他错误）
                            //暂时无法解决

                            ToastUtil.showShort(getActivity(),e.getErrorCode() + "  22");
                        }
                    }
                });


            }

        }

        return view;
    }


    private void initView(View view) {
        mRecyclerView = (RecyclerView)view.findViewById(R.id.recyclerview_vac_record);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        mAdapter = new VaccineRecordAdapter();
        mRecyclerView.setAdapter(mAdapter);

    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
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

    public class VaccineRecordAdapter extends RecyclerView.Adapter<VaccineRecordAdapter.VacViewHolder> {


        public VacViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            VacViewHolder holder = new VacViewHolder(LayoutInflater.from(
                    getActivity()).inflate(R.layout.vaccine_record,parent,false));

            return holder;
        }

        @Override
        public void onBindViewHolder(VaccineRecordAdapter.VacViewHolder holder, int position) {
            holder.vaccvineName.setText(mVaccineList.get(position).getVacName());
            holder.vaccineTime.setText(mVaccineList.get(position).getVacTime());

        }

        /**
         * Returns the total number of items in the data set held by the adapter.
         *
         * @return The total number of items in this adapter.
         */
        @Override
        public int getItemCount() {
            return mVaccineList.size();
        }

        public class VacViewHolder extends RecyclerView.ViewHolder {

            TextView vaccvineName;
            TextView vaccineTime;

            public VacViewHolder(View itemView) {
                super(itemView);
                vaccvineName = (TextView)itemView.findViewById(R.id.vaccine_name);
                vaccineTime = (TextView)itemView.findViewById(R.id.vaccine_time);
            }
        }
    }



}
