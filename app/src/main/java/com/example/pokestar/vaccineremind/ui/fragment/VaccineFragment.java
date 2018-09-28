package com.example.pokestar.vaccineremind.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pokestar.vaccineremind.MainActivity;
import com.example.pokestar.vaccineremind.R;
import com.example.pokestar.vaccineremind.application.MyApplication;
import com.example.pokestar.vaccineremind.bean.Baby;
import com.example.pokestar.vaccineremind.bean.User;
import com.example.pokestar.vaccineremind.ui.activity.AddBabyActivity;
import com.example.pokestar.vaccineremind.ui.activity.VaccinePlanActivity;
import com.example.pokestar.vaccineremind.utils.Configure;
import com.example.pokestar.vaccineremind.utils.ToastUtil;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link VaccineFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link VaccineFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class VaccineFragment extends BaseFragment {

    private TextView mTextView_baby_data;
    private ImageView mImageView_baby;

    private OnFragmentInteractionListener mListener;
    private Button button_vacplan;
    private Button button_calldoc;

    RecyclerView mRecyclerView;
    List<String> titles;


    final List<Baby> mBabyList = new ArrayList<>();

    public static VaccineFragment newInstance() {
        VaccineFragment fragment = new VaccineFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_vaccine;
    }

    @Override
    protected int getContainerId() {
        return R.id.vaccine_fragment_container;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_vaccine, container, false);

        //根据情况更改Fragment
        initFragmentView();

        button_vacplan = view.findViewById(R.id.button_vaccine_plan);
        button_vacplan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), VaccinePlanActivity.class);
                startActivity(intent);
            }
        });
        button_calldoc = view.findViewById(R.id.button_call_doc);
        button_calldoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO 联系门诊
                replaceFragment(AddVaccineFragment.newInstance());
            }
        });

        //floatingButton
        final View actionB =view.findViewById(R.id.action_b);
        actionB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO button2切换宝宝
            }
        });

        FloatingActionButton actionC = new FloatingActionButton(getActivity());
        actionC.setTitle("Hide/Show Action above");
        actionC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO BUTTON3
                ToastUtil.showShort(getActivity(),"ac");
                //actionB.setVisibility(actionB.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
            }
        });

        final FloatingActionsMenu menuMultipleActions = (FloatingActionsMenu)view.findViewById(R.id.multiple_actions);
        menuMultipleActions.addButton(actionC);




        final FloatingActionButton actionA = (FloatingActionButton)view.findViewById(R.id.action_a);
        actionA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO 添加宝宝
                if(Configure.getUSERID(getActivity()).equals("")){
                    //未登录
                    mImageView_baby.setImageResource(R.drawable.baby);
                    mTextView_baby_data.setText("请登录后添加宝宝！");

                }else {

                    // 已登录
                    User user = new User();
                    final BmobUser user1 = BmobUser.getCurrentUser();

                    //判断有无baby
                    BmobQuery<User> query = new BmobQuery<User>();
                    query.getObject(user1.getObjectId(), new QueryListener<User>() {
                        @Override
                        public void done(User user, BmobException e) {
                            if(e == null){
                                if(user.getBabyId() == null){
                                    //未创建baby 跳转添加宝宝页面
                                    startActivity(new Intent(getActivity(), AddBabyActivity.class));

                                }else {
                                    //用户当前有baby
                                    ToastUtil.showShort(getActivity(),"当前支持一用户一个baby");
                                }

                            }else {
                                mImageView_baby.setImageResource(R.drawable.baby);
                                mTextView_baby_data.setText("请先添加宝宝！");
                                ToastUtil.showShort(getActivity(),e.getMessage());
                            }
                        }
                    });




                }
            }
        });





        initToolbarView(view);

        initRecyclerView(view);

        return view;

    }

    private void initRecyclerView(View view) {

        titles = new ArrayList<String>();
        titles.add("this is tile.");


        mRecyclerView = view.findViewById(R.id.recycler_view_vaccine_know);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(new VaccineKnowAdapter());



    }

    private void initFragmentView() {
        if(Configure.getUSERID(getActivity()).equals("")){
            //未登录
            addFragment(AddVaccineFragment.newInstance(),"setVaccine");

        }else {
            // 已登录


            //判断有无baby
            if(Configure.getBABYID(getActivity()).equals("")){
                //未创建baby
                addFragment(AddVaccineFragment.newInstance(),"setVaccine");

            }else {
                //用户当前有baby
                BmobQuery<Baby> query1 = new BmobQuery<Baby>();
                query1.getObject(Configure.getBABYID(getActivity()), new QueryListener<Baby>() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void done(Baby baby, BmobException e) {
                        if(e == null){
                            if(baby.getVaccineList().size() == 0){
                                //当前无vaccine添加
                                addFragment(AddVaccineFragment.newInstance(),"setVaccine");
                            }else {
                                //当前有需要打的疫苗
                                addFragment(DisplayVaccineFragment.newInstance(),"DisplayVaccine");

                            }
                            Baby baby1 = baby;
                            mBabyList.add(baby1);


                        }else {
                            addFragment(AddVaccineFragment.newInstance(),"setVaccine");
                            ToastUtil.showShort(getActivity(),e.getMessage());
                        }

                    }
                });
            }

        }


        ToastUtil.showShort(getActivity(),"1 " + mBabyList.size());


    }

    private void initToolbarView(View view) {

        mImageView_baby = view.findViewById(R.id.image_baby);
        mTextView_baby_data = view.findViewById(R.id.text_baby_data);

        if(Configure.getUSERID(getActivity()).equals("")){
            //未登录
            mImageView_baby.setImageResource(R.drawable.baby);
            mTextView_baby_data.setText("请登录后添加宝宝！");

        }else {
            // 已登录
            User user = new User();
            final BmobUser user1 = BmobUser.getCurrentUser();

            //判断有无baby
            BmobQuery<User> query = new BmobQuery<User>();
            query.getObject(user1.getObjectId(), new QueryListener<User>() {
                @Override
                public void done(User user, BmobException e) {
                    if(e == null){
                        if(user.getBabyId() == null){
                            //未创建baby
                            mImageView_baby.setImageResource(R.drawable.baby);
                            mTextView_baby_data.setText("请先添加宝宝！");
                        }else {
                            //用户当前有baby
                            BmobQuery<Baby> query1 = new BmobQuery<Baby>();
                            query1.getObject(user.getBabyId(), new QueryListener<Baby>() {
                                @Override
                                public void done(Baby baby, BmobException e) {
                                    if(e == null){
                                        mImageView_baby.setImageResource(R.drawable.baby);
                                        mTextView_baby_data.setText(baby.getName() + " | " + "birth");


                                    }else {
                                        mImageView_baby.setImageResource(R.drawable.baby);
                                        mTextView_baby_data.setText("请先添加宝宝！!");
                                        ToastUtil.showShort(getActivity(),e.getMessage());
                                    }

                                }
                            });
                        }

                    }else {
                        mImageView_baby.setImageResource(R.drawable.baby);
                        mTextView_baby_data.setText("请先添加宝宝");
                        ToastUtil.showShort(getActivity(),e.getMessage());
                    }
                }
            });

        }


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


    public class VaccineKnowAdapter extends RecyclerView.Adapter<VaccineKnowAdapter.ViewHolder>{

        /**
         * Called when RecyclerView needs a new {@link ViewHolder} of the given type to represent
         * an item.
         * <p>
         * This new ViewHolder should be constructed with a new View that can represent the items
         * of the given type. You can either create a new View manually or inflate it from an XML
         * layout file.
         * <p>
         * The new ViewHolder will be used to display items of the adapter using
         * Since it will be re-used to display
         * different items in the data set, it is a good idea to cache references to sub views of
         * the View to avoid unnecessary {@link View#findViewById(int)} calls.
         *
         * @param parent   The ViewGroup into which the new View will be added after it is bound to
         *                 an adapter position.
         * @param viewType The view type of the new View.
         * @return A new ViewHolder that holds a View of the given view type.
         * @see #getItemViewType(int)
         * @see #onBindViewHolder(ViewHolder, int)
         */
        @Override
        public VaccineKnowAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            VaccineKnowAdapter.ViewHolder holder = new VaccineKnowAdapter.ViewHolder(
                    LayoutInflater.from(getActivity()).inflate(R.layout.know_vaccine,parent,false));
            return holder;
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
         * <p>
         *
         * @param holder   The ViewHolder which should be updated to represent the contents of the
         *                 item at the given position in the data set.
         * @param position The position of the item within the adapter's data set.
         */
        @Override
        public void onBindViewHolder(VaccineKnowAdapter.ViewHolder holder, int position) {
            holder.textTitle.setText("This is title.");
            holder.imageKnow.setImageResource(R.drawable.baby);

        }

        /**
         * Returns the total number of items in the data set held by the adapter.
         *
         * @return The total number of items in this adapter.
         */
        @Override
        public int getItemCount() {
            return 5;
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            TextView textTitle;
            ImageView imageKnow;

            public ViewHolder(View itemView) {
                super(itemView);
                textTitle =(TextView)itemView.findViewById(R.id.know_title);
                imageKnow = (ImageView)itemView.findViewById(R.id.know_image);
            }
        }
    }

}
