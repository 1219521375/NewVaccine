package com.example.pokestar.vaccineremind.ui.fragment.planfragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pokestar.vaccineremind.R;
import com.example.pokestar.vaccineremind.ui.fragment.BaseFragment;
import com.example.pokestar.vaccineremind.ui.fragment.planfragment.refer.FirstVacFragment;
import com.example.pokestar.vaccineremind.ui.fragment.planfragment.refer.SecVacFragment;
import com.example.pokestar.vaccineremind.utils.FragmentAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ReferVaccineFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ReferVaccineFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ReferVaccineFragment extends BaseFragment {

    private OnFragmentInteractionListener mListener;

    private FragmentAdapter mFragmentAdapter;
    private TabLayout mTabLayout;


    public static ReferVaccineFragment newInstance() {
        ReferVaccineFragment fragment = new ReferVaccineFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_refer_vaccine;
    }

    @Override
    protected int getContainerId() {
        return R.id.viewpager_vaccine_refer;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_refer_vaccine, container, false);

        mTabLayout = view.findViewById(R.id.tabs_vaccine_refer);

        initView(view);
        return view;
    }

    private void initView(View view) {

        List<Fragment> fragmentList = new ArrayList<Fragment>();
        if(fragmentList!=null){
            ArrayList<String> nameList=new ArrayList<>();

            FirstVacFragment fragment1 = FirstVacFragment.newInstance();
            SecVacFragment fragment2 = SecVacFragment.newInstance();
            fragmentList.add(fragment1);
            fragmentList.add(fragment2);
        }
        mFragmentAdapter=new FragmentAdapter(getActivity().getSupportFragmentManager(),fragmentList);



        ViewPager viewPager = (ViewPager)view.findViewById(R.id.viewpager_vaccine_refer);
        viewPager.setAdapter(mFragmentAdapter);
        mTabLayout.setupWithViewPager(viewPager);

        mTabLayout.getTabAt(0).setText("第一类疫苗");
        mTabLayout.getTabAt(1).setText("第二类疫苗");


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
