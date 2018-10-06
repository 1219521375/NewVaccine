package com.example.pokestar.vaccineremind.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.pokestar.vaccineremind.R;
import com.example.pokestar.vaccineremind.bean.VaccineNews;
import com.example.pokestar.vaccineremind.widget.NetImageView;

import java.util.List;

/**
 * Created by PokeStar on 2018/10/6.
 */

public class VaccineKnowAdapter extends RecyclerView.Adapter<VaccineKnowAdapter.ViewHolder>{

    private List<VaccineNews> mNews;
    private Context mContext;

    private  OnItemClickListener mOnItemClickListener;


    public VaccineKnowAdapter(Activity activity, List<VaccineNews> list) {
        mContext = activity;
        mNews = list;
    }

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
        View view = View.inflate(mContext,R.layout.know_vaccine,null);
        VaccineKnowAdapter.ViewHolder holder = new VaccineKnowAdapter.ViewHolder(view,mOnItemClickListener);

        return holder;
    }

    @Override
    public void onBindViewHolder(VaccineKnowAdapter.ViewHolder holder, int position) {
        holder.textTitle.setText(mNews.get(position).getTitle());
        holder.imageKnow.setImageURL(mNews.get(position).getImageUrl());

    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        return mNews.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView textTitle;
        NetImageView imageKnow;
        private  OnItemClickListener mOnItemClickListener;

        public ViewHolder(View itemView, OnItemClickListener listener) {
            super(itemView);
            textTitle =(TextView)itemView.findViewById(R.id.know_title);
            imageKnow = (NetImageView) itemView.findViewById(R.id.know_image);
            mOnItemClickListener = listener;
            itemView.setOnClickListener(this);
        }


        /**
         * Called when a view has been clicked.
         *
         * @param v The view that was clicked.
         */
        @Override
        public void onClick(View v) {
            mOnItemClickListener.onItemClick(v,getPosition());
        }
    }

    /**
     * 回调接口
     */
    public interface OnItemClickListener {
        void onItemClick(View view,int position);
    }
    /**
     * 在activity里面adapter就是调用的这个方法,将点击事件监听传递过来,并赋值给全局的监听
     *
     * @param myItemClickListener
     */
    public void setItemClickListener(OnItemClickListener myItemClickListener) {
        this.mOnItemClickListener = myItemClickListener;
    }


}
