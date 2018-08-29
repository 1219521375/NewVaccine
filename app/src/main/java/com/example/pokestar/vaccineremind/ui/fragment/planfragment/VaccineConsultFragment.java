package com.example.pokestar.vaccineremind.ui.fragment.planfragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pokestar.vaccineremind.R;
import com.example.pokestar.vaccineremind.ui.fragment.BaseFragment;

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
        return R.id.viewpager_vaccine_plan;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_vaccine_consult, container, false);
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
