package com.example.pokestar.vaccineremind.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.allen.library.SuperTextView;
import com.example.pokestar.vaccineremind.R;
import com.example.pokestar.vaccineremind.bean.VaccineSql;
import com.example.pokestar.vaccineremind.ui.activity.DetailMapActivity;
import com.example.pokestar.vaccineremind.ui.activity.VaccineMapCCActivity;
import com.example.pokestar.vaccineremind.ui.activity.VaccineMapWHActivity;
import com.example.pokestar.vaccineremind.utils.ToastUtil;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link VaccineConsultFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link VaccineConsultFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class VaccineConsultFragment extends BaseFragment {

    private OnFragmentInteractionListener mListener;

    Button mapCCButton;
    Button mapWHButton;

    MaterialEditText input;
    SuperTextView output_name;
    SuperTextView output_company;
    SuperTextView output_time;

    Button consultButton;
    Button openMapButton;


    public static VaccineConsultFragment newInstance() {
        VaccineConsultFragment fragment = new VaccineConsultFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_vaccine_consult;
    }

    @Override
    protected int getContainerId() {
        return R.id.vaccine_plan_list;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_vaccine_consult, container, false);

        initConsult(view);
        initView(view);

        return view;
    }

    private void initConsult(View view) {

        input = view.findViewById(R.id.consult_edit_in);
        output_name = view.findViewById(R.id.consult_text_out1);
        output_company = view.findViewById(R.id.consult_text_out2);
        output_time = view.findViewById(R.id.consult_text_out3);



        consultButton = view.findViewById(R.id.vac_consult);
        consultButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String consult_in = "'" + input.getText().toString() + "'";
                BmobQuery<VaccineSql> query = new BmobQuery<VaccineSql>();
                //查询BatchNumber叫consult_in的数据
                query.addWhereEqualTo("BatchNumber", consult_in);
                //返回50条数据，如果不加上这条语句，默认返回10条数据
                query.setLimit(5);
                //执行查询方法
                query.findObjects(new FindListener<VaccineSql>() {
                    @Override
                    public void done(List<VaccineSql> object, BmobException e) {
                        if(e==null){
                            String cccs = "长春长生生物科技";
                            String whsw = "武汉生物制品";
                            output_name.setLeftString("疫苗名：");
                            output_name.setRightString(object.get(0).getVaccineName().replace("'",""));
                            output_company.setLeftString("生产公司：");
                            output_company.setRightString(object.get(0).getCompany().replace("'",""));
                            if(object.get(0).getCompany().replace("'","").substring(0,4).equals(cccs.substring(0,4))){
                                ToastUtil.showShort(getActivity(),"该公司曾经生产过问题疫苗！");
                            }
                            if(object.get(0).getCompany().replace("'","").substring(0,4).equals(whsw.substring(0,4))){
                                ToastUtil.showShort(getActivity(),"该公司曾经生产过问题疫苗！");

                            }
                            output_time.setLeftString("有效期限：");
                            output_time.setRightString(object.get(0).getValidityPeriod().replace("'",""));
                        }else{
                            Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                            output_name.setRightString("未查询到相应的疫苗！");
                            output_company.setRightString(" ");
                            output_time.setRightString("");
                        }
                    }
                });

            }
        });






    }

    private void initView(View view) {

        mapCCButton = view.findViewById(R.id.vac_map_cc);
        mapCCButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), VaccineMapCCActivity.class));
            }
        });

        mapWHButton = view.findViewById(R.id.vac_map_wh);
        mapWHButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), VaccineMapWHActivity.class));
            }
        });

        openMapButton = view.findViewById(R.id.consult_map_open);
        openMapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), DetailMapActivity.class);
                intent.putExtra("company",output_company.getRightString());
                startActivity(intent);
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
