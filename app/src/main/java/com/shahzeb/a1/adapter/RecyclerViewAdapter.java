package com.shahzeb.a1.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shahzeb.a1.BaseFragment;
import com.shahzeb.a1.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.JobViewHolder> {

    public final static String TAG = RecyclerViewAdapter.class.getSimpleName();

    private static Context mContext;
    private static List<String> mData;
    private static RecyclerViewAdapterCallback mCallback;

    public interface RecyclerViewAdapterCallback {
        void onItemClick(int position);
    }

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
        TextView tvTitle;
        @BindView(R.id.ly_parent)
        RelativeLayout parentView;

        int position;

        public JobViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);

            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Log.v(TAG, "Element clicked: " + getAdapterPosition());
            mCallback.onItemClick(getAdapterPosition());
        }
    }

    public RecyclerViewAdapter(Context context, ArrayList<String> data, BaseFragment fragment) {
        mContext = context;
        if (data != null) {
            mData = data;
        } else {
            mData = new ArrayList<>();
        }
        mCallback = fragment;
    }

    public JobViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(mContext).inflate(R.layout.item_recyclerview, parent, false);
        return new JobViewHolder(view);
    }

    @Override
    public void onBindViewHolder(JobViewHolder holder, final int position) {
        holder.position = position;
        if (position % 2 == 0) {
            holder.parentView.setBackgroundColor(ContextCompat.getColor(mContext, R.color.grey_100));
        } else {
            holder.parentView.setBackgroundColor(ContextCompat.getColor(mContext, R.color.grey_0));
        }
        holder.tvTitle.setText(mData.get(position));

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
