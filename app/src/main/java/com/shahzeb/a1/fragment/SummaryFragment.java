package com.shahzeb.a1.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shahzeb.a1.A1Constants;
import com.shahzeb.a1.BaseFragment;
import com.shahzeb.a1.R;
import com.shahzeb.a1.model.Recents;
import com.shahzeb.a1.model.Summary;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SummaryFragment extends BaseFragment {

    @BindView(R.id.tv_main_type)
    TextView mTvMainType;
    @BindView(R.id.tv_manufacturer)
    TextView mTvManufacturer;
    @BindView(R.id.tv_built_date)
    TextView mTvBuiltDate;

    private Summary mSummary;

    public SummaryFragment() {
        // Required empty public constructor
    }

    public static SummaryFragment newInstance(Summary summary) {
        SummaryFragment fragment = new SummaryFragment();
        Bundle args = new Bundle();
        args.putParcelable(A1Constants.KEY_SUMMARY, summary);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mSummary = getArguments().getParcelable(A1Constants.KEY_SUMMARY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_summary, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mTvManufacturer.setText(getManufacturerName());
        mTvMainType.setText(mSummary.mainType);
        mTvBuiltDate.setText(mSummary.builtDate);

        insertUniqueItem();
    }

    private void insertUniqueItem() {
        List<Recents> list = Recents.listAll(Recents.class);
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).manufacturer.equals(mSummary.manufacturer)
                    && list.get(i).mainType.equals(mSummary.mainType)
                    && list.get(i).builtDate.equals(mSummary.builtDate)) {
                break;
            }

        }
        Recents recent = new Recents(mSummary.manufacturer, mSummary.mainType, mSummary.builtDate);
        recent.save();
    }

    private String getManufacturerName() {
        if (mSummary != null && mSummary.manufacturer != null) {
            String[] parts = mSummary.manufacturer.split(",");
            if (parts.length > 1){
                return parts[1];
            }
        }
        return null;
    }

    private String getManufacturerId() {
        if (mSummary != null && mSummary.manufacturer != null) {
            String[] parts = mSummary.manufacturer.split(",");
            if (parts.length > 0){
                return parts[0];
            }
        }
        return null;
    }
}
