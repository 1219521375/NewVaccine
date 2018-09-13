package com.example.pokestar.vaccineremind.ui.fragment.planfragment.refer;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
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
 * {@link FirstVacFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FirstVacFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FirstVacFragment extends BaseFragment {

    private OnFragmentInteractionListener mListener;

    SmartTable<TableVaccine> mSmartTable;
    List<TableVaccine> tableVaccineList = new ArrayList<>();

    public static FirstVacFragment newInstance() {
        FirstVacFragment fragment = new FirstVacFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_first_vac;
    }

    @Override
    protected int getContainerId() {
        return R.id.viewpager_vaccine_refer;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_first_vac, container, false);
        initData();
        initView(view);
        return view;
    }

    private void initData() {

        tableVaccineList.add(new TableVaccine("出生24小时内","乙肝疫苗第一针",1));
        tableVaccineList.add(new TableVaccine("","卡介苗疫苗",1));
        tableVaccineList.add(new TableVaccine("一月龄","乙肝疫苗第二针",2));
        tableVaccineList.add(new TableVaccine("二月龄","脊髓灰质炎糖丸第一针",1));
        tableVaccineList.add(new TableVaccine("三月龄","脊髓灰质炎糖丸第二针",2));
        tableVaccineList.add(new TableVaccine("","百白破疫苗初种",1));
        tableVaccineList.add(new TableVaccine("四月龄","脊髓灰质炎糖丸第三针",3));
        tableVaccineList.add(new TableVaccine("","百白破疫苗第二针",2));
        tableVaccineList.add(new TableVaccine("六月龄","百白破疫苗第三针",3));
        tableVaccineList.add(new TableVaccine("","乙肝疫苗第三针",3));
        tableVaccineList.add(new TableVaccine("","A群流脑疫苗第一针",1));
        tableVaccineList.add(new TableVaccine("八月龄","麻疹疫苗第一针",1));
        tableVaccineList.add(new TableVaccine("九月龄","A群流脑疫苗第二针",2));
        tableVaccineList.add(new TableVaccine("一岁","乙脑疫苗—初种",1));
        tableVaccineList.add(new TableVaccine("1.5-2岁","百白破疫苗—加强",2));
        tableVaccineList.add(new TableVaccine("","脊髓灰质炎糖丸—部分加强",2));
        tableVaccineList.add(new TableVaccine("","乙脑疫苗—加强",2));
        tableVaccineList.add(new TableVaccine("","甲肝疫苗",1));
        tableVaccineList.add(new TableVaccine("三岁","A群流脑疫苗—第三针",3));
        tableVaccineList.add(new TableVaccine("四岁","脊髓灰质炎疫苗—加强针",3));
        tableVaccineList.add(new TableVaccine("六岁","麻疹疫苗—加强针",2));
        tableVaccineList.add(new TableVaccine("","白破二联疫苗—加强针",2));
        tableVaccineList.add(new TableVaccine("","乙脑疫苗—第三针",3));
        tableVaccineList.add(new TableVaccine("","A群流脑疫苗—第四针",4));
        tableVaccineList.add(new TableVaccine("十二岁","卡介苗—加强针",2));


    }

    private void initView(View view) {

        mSmartTable = (SmartTable<TableVaccine>) view.findViewById(R.id.first_vaccine_table);





        mSmartTable.setData(tableVaccineList);
        mSmartTable.getConfig()
                .setShowTableTitle(false)
                .setShowXSequence(false)
                .setShowYSequence(false)
                .setContentStyle(new FontStyle(43,000000));


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
