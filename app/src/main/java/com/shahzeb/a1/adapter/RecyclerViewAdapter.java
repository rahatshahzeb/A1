package com.shahzeb.a1.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shahzeb.a1.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.JobViewHolder> {

    public final static String TAG = RecyclerViewAdapter.class.getSimpleName();

    private static Context mContext;
    private static List<String> mData;

    public void add(String item, int position) {
        position = position == -1 ? getItemCount()  : position;
        mData.add(position,item);
        notifyItemInserted(position);
    }

    public void remove(int position){
        if (position < getItemCount()  ) {
            mData.remove(position);
            notifyItemRemoved(position);
        }
    }

    public static class JobViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.tv_title)
        TextView mTvTitle;

        int position;

        public JobViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);

            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Log.v(TAG, "Element clicked: " + getAdapterPosition());
            int id = v.getId();
            switch (id){

                default:

            }
        }
    }

    public RecyclerViewAdapter(Context context, ArrayList<String> data) {
        mContext = context;
        if (data != null) {
            mData = data;
        } else {
            mData = new ArrayList<String>();
        }
    }

    public JobViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(mContext).inflate(R.layout.item_recyclerview, parent, false);
        return new JobViewHolder(view);
    }

    @Override
    public void onBindViewHolder(JobViewHolder holder, final int position) {
        holder.position = position;
        holder.mTvTitle.setText(mData.get(position));

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void notifyAdapter(ArrayList<String> data){
        if (data != null) {
            mData = data;
        } else {
            mData = new ArrayList<>();
        }
        notifyDataSetChanged();
    }
}
