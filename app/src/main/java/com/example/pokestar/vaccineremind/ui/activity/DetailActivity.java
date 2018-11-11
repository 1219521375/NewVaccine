package com.example.pokestar.vaccineremind.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.pokestar.vaccineremind.R;

import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    ArrayList<String> vacNames = new ArrayList<>();
    ArrayList<String> vacReaction = new ArrayList<>();
    ArrayList<String> vacNotice = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        vacNames = getIntent().getStringArrayListExtra("namelist");
        vacReaction = getIntent().getStringArrayListExtra("reactionlist");
        vacNotice = getIntent().getStringArrayListExtra("noticelist");

        initView();

    }

    private void initView() {

        recyclerView = findViewById(R.id.vaccine_detail_list);
        //设置RecyclerView管理器
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        DetailVacAdapter adapter = new DetailVacAdapter();

        recyclerView.setAdapter(adapter);
        recyclerView.setNestedScrollingEnabled(false);//禁止rcyc嵌套滑动



    }

    public class DetailVacAdapter extends RecyclerView.Adapter<DetailVacAdapter.DetailViewHolder>{


        @Override
        public DetailViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.detail_card, parent, false);
            DetailViewHolder viewHolder = new DetailViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(DetailViewHolder holder, int position) {
            holder.detailTitle.setText(vacNames.get(position));
            holder.detailNotice.setText(vacNotice.get(position));
            holder.detailReaction.setText(vacReaction.get(position));

        }

        @Override
        public int getItemCount() {
            return vacNames.size();
        }

        class DetailViewHolder extends RecyclerView.ViewHolder{

            TextView detailTitle;
            TextView detailNotice;
            TextView detailReaction;

            public DetailViewHolder(View itemView) {
                super(itemView);
                detailTitle = itemView.findViewById(R.id.detail_vaccine_name);
                detailNotice = itemView.findViewById(R.id.detail_vaccine_warn);
                detailReaction = itemView.findViewById(R.id.detail_vaccine_sympt);
            }
        }

    }
}
