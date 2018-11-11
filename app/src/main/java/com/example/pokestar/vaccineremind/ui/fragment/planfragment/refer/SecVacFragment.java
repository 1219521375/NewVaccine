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

   // SmartTable<TableVaccine> mSmartTable;

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
        /**
         * A+C群流脑疫苗：3周岁注射1针次，6、9周岁各加强一针。
         无细胞百白破疫苗：可替代全细胞百白破疫苗，接种程序同全细胞百白破疫苗。
         麻腮风疫苗：1.5-2周岁注射一针，基础免疫后4年加强1针。
         甲肝减毒活疫苗或甲肝灭活疫苗：甲肝减毒活疫苗接种时间是2岁时注射1针，4年后加强1针。灭活疫苗1-16岁接种2针，间隔6个月，16岁以上接种1针。
         水痘疫苗：1-12岁接种1针次。
         B型流感嗜血杆菌苗：2、4、6月龄各注射一次，12月龄以上接种一针即可。
         流行性感冒疫苗：1-3周岁每年注射2针，间隔1个月。3周岁以上每年接种1次即可。

         */
        tableVaccineList.add(new TableVaccine("三周岁"," A+C群流脑疫苗",1));
        tableVaccineList.add(new TableVaccine("六周岁"," A+C群流脑疫苗",2));
        tableVaccineList.add(new TableVaccine("九周岁"," A+C群流脑疫苗",3));
        tableVaccineList.add(new TableVaccine("二月龄","五联疫苗",1));
        tableVaccineList.add(new TableVaccine("二月龄","五联疫苗",1));
        tableVaccineList.add(new TableVaccine("二月龄","五联疫苗",1));
        tableVaccineList.add(new TableVaccine("二月龄","五联疫苗",1));
        tableVaccineList.add(new TableVaccine("二月龄","五联疫苗",1));
    }

    private void initView(View view) {

//        mSmartTable = (SmartTable<TableVaccine>) view.findViewById(R.id.second_vaccine_table);
//
//
//
//        mSmartTable.setData(tableVaccineList);
//        mSmartTable.getConfig()
//                .setShowTableTitle(false)
//                .setShowXSequence(false)
//                .setShowYSequence(false)
//                .setContentStyle(new FontStyle(60,000000));



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
