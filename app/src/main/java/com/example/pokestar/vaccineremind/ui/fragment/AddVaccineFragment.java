package com.example.pokestar.vaccineremind.ui.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.pokestar.vaccineremind.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AddVaccineFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AddVaccineFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddVaccineFragment extends BaseFragment {


    private OnFragmentInteractionListener mListener;

    private Button buttonSetVaccine;



    public static AddVaccineFragment newInstance() {
        AddVaccineFragment fragment = new AddVaccineFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_add_vaccine;
    }

    @Override
    protected int getContainerId() {
        return R.id.vaccine_fragment_container;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_vaccine, container, false);
        buttonSetVaccine = view.findViewById(R.id.button_set_vaccine);
        buttonSetVaccine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(DisplayVaccineFragment.newInstance());
            }
        });
        return view;
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


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


}
