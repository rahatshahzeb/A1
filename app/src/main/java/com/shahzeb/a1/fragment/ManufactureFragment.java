package com.shahzeb.a1.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.shahzeb.a1.BaseFragment;
import com.shahzeb.a1.R;
import com.shahzeb.a1.adapter.RecyclerViewAdapter;
import com.shahzeb.a1.adapter.SectionRecyclerViewAdapter;
import com.shahzeb.a1.model.A1Response;
import com.shahzeb.a1.util.NetworkManager;
import com.shahzeb.a1.view.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Response;

public class ManufactureFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener{

    private final static String TAG = ManufactureFragment.class.getSimpleName();

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.progress)
    ProgressBar mProgressBar;
    @BindView(R.id.empty_view)
    TextView mEmptyView;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    private ArrayList<String> mDataset = new ArrayList<>();
    private List<SectionRecyclerViewAdapter.Section> mSectionsDataset = new ArrayList<>();
    private LinearLayoutManager mLayoutManager;
    private RecyclerViewAdapter mAdapter;

    @Override
    public void onRefresh() {
        NetworkManager.getInstance().fetchManufacturers(0, 10, this);
    }

    public ManufactureFragment() {
        // Required empty public constructor
    }

    public static ManufactureFragment newInstance() {
        ManufactureFragment fragment = new ManufactureFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_manufacture, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);

        mRecyclerView.setHasFixedSize(true);
        setRecyclerViewLayoutManager();
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity()));

        setAdapter();

        mSwipeRefreshLayout.setOnRefreshListener(this);
    }

    public void addSections() {
        mSectionsDataset.clear();
        mSectionsDataset.add(new SectionRecyclerViewAdapter.Section(0,"Recent"));
        mSectionsDataset.add(new SectionRecyclerViewAdapter.Section(4,"Recent"));
    }

    public void setRecyclerViewLayoutManager() {
        int scrollPosition = 0;

        // If a layout manager has already been set, get current scroll position.
        if (mRecyclerView.getLayoutManager() != null) {
            scrollPosition = ((LinearLayoutManager) mRecyclerView.getLayoutManager())
                    .findFirstCompletelyVisibleItemPosition();
        }

        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.scrollToPosition(scrollPosition);
    }

    public void setAdapter() {
        if (mAdapter == null) {
            mAdapter = new RecyclerViewAdapter(getActivity(), mDataset);

            SectionRecyclerViewAdapter.Section[] dummy = new SectionRecyclerViewAdapter.Section[mSectionsDataset.size()];
            SectionRecyclerViewAdapter mSectionedAdapter = new
                    SectionRecyclerViewAdapter(getActivity(),R.layout.item_section_recyclerview ,mAdapter);
            mSectionedAdapter.setSections(mSectionsDataset.toArray(dummy));

            mRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.notifyAdapter(mDataset);
        }
    }

    public boolean isDataSetEmpty() {
        if (mDataset != null && mDataset.size() != 0) {
            return false;
        }
        return true;
    }

    public void setProgressVisibility(boolean visibile) {
        if (visibile) {
            mProgressBar.setVisibility(View.VISIBLE);
        } else {
            mProgressBar.setVisibility(View.GONE);
        }
    }

    public void setEmptyViewVisibility(boolean visibile) {
        if (visibile) {
            mEmptyView.setVisibility(View.VISIBLE);
        } else {
            mEmptyView.setVisibility(View.GONE);
        }
    }

    @Override
    public void onResponse(Call call, Response response) {
        mAdapter.notifyAdapter(((A1Response)response.body()).wkda.);
    }

    @Override
    public void onFailure(Call call, Throwable t) {

    }
}
