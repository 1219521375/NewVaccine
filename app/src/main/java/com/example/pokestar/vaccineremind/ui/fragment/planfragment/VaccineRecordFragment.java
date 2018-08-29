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
import android.widget.Button;
import android.widget.TextView;

import com.example.pokestar.vaccineremind.R;
import com.example.pokestar.vaccineremind.bean.Vaccine;
import com.example.pokestar.vaccineremind.ui.fragment.BaseFragment;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    List<Vaccine> mVaccineList;
    VaccineRecordAdapter mAdapter;

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
        return R.id.viewpager_vaccine_plan;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_vaccine_record, container, false);
        initData();
        initView(view);

        return view;
    }

    private void initData() {
        mVaccineList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            mVaccineList.add(new Vaccine("五联疫苗",new Date(),false));
        }

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
            holder.vaccineTime.setText(mVaccineList.get(position).getVacTime().toString());

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
            Button vaccineIscalled;

            public VacViewHolder(View itemView) {
                super(itemView);
                vaccineIscalled = (Button)itemView.findViewById(R.id.vaccine_iscalled);
                vaccvineName = (TextView)itemView.findViewById(R.id.vaccine_name);
                vaccineTime = (TextView)itemView.findViewById(R.id.vaccine_time);
            }
        }
    }



}
