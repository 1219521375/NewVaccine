package com.example.pokestar.vaccineremind.ui.fragment.planfragment.refer;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bin.david.form.core.SmartTable;
import com.bin.david.form.data.style.FontStyle;
import com.example.pokestar.vaccineremind.R;
import com.example.pokestar.vaccineremind.bean.TableVaccine;
import com.example.pokestar.vaccineremind.ui.fragment.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SecVacFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SecVacFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SecVacFragment extends BaseFragment {

    SmartTable<TableVaccine> mSmartTable;

    private OnFragmentInteractionListener mListener;

    List<TableVaccine> tableVaccineList = new ArrayList<>();
    public static SecVacFragment newInstance() {
        SecVacFragment fragment = new SecVacFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_sec_vac;
    }

    @Override
    protected int getContainerId() {
        return R.id.viewpager_vaccine_refer;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sec_vac, container, false);
        initData();
        initView(view);
        return view;
    }

    private void initData() {
        tableVaccineList.add(new TableVaccine("二月龄","五联疫苗",1));
    }

    private void initView(View view) {

        mSmartTable = (SmartTable<TableVaccine>) view.findViewById(R.id.second_vaccine_table);



        mSmartTable.setData(tableVaccineList);
        mSmartTable.getConfig()
                .setShowTableTitle(false)
                .setShowXSequence(false)
                .setShowYSequence(false)
                .setContentStyle(new FontStyle(60,000000));



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
}
