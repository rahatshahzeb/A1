package com.shahzeb.a1.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.shahzeb.a1.A1Constants;
import com.shahzeb.a1.BaseFragment;
import com.shahzeb.a1.R;
import com.shahzeb.a1.adapter.RecyclerViewAdapter;
import com.shahzeb.a1.adapter.SectionRecyclerViewAdapter;
import com.shahzeb.a1.model.A1Response;
import com.shahzeb.a1.model.BuiltDates;
import com.shahzeb.a1.model.MainType;
import com.shahzeb.a1.model.Manufacturer;
import com.shahzeb.a1.util.NetworkManager;
import com.shahzeb.a1.view.DividerItemDecoration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Response;

public class UniversalFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener{

    private final static String TAG = UniversalFragment.class.getSimpleName();

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.loadprogress)
    ProgressBar mProgressBar;
    @BindView(R.id.empty_view)
    TextView mEmptyView;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    private A1Constants.FragmentType mFragmentType;
    private String mManufacturer;
    private String mMainType;
    private String mBuiltDate;

    private ArrayList<String> mDataset = new ArrayList<>();
    private LinkedHashMap<String, String> mMap = new LinkedHashMap<>();
    private List<SectionRecyclerViewAdapter.Section> mSectionsDataset = new ArrayList<>();
    private LinearLayoutManager mLayoutManager;
    private RecyclerViewAdapter mAdapter;
    private SectionRecyclerViewAdapter mSectionedAdapter;

    private int mTotalPageCount = 0;
    private int mPage = 0;

    private boolean mLoading = true;
    private int mPastVisiblesItems;
    private int mVisibleItemCount;
    private int mTotalItemCount;
    private RecyclerView.OnScrollListener mRecyclerViewScrollListener;

    private OnFragmentInteractionListener mListener;

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(A1Constants.FragmentType fragmentType, String manufacturer, String mainType, String builtDate);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public UniversalFragment() {
        // Required empty public constructor
    }

    public static UniversalFragment newInstance(A1Constants.FragmentType fragmentType, String manufacturer, String mainType) {
        UniversalFragment fragment = new UniversalFragment();
        Bundle args = new Bundle();
        args.putSerializable(A1Constants.KEY_LIST_TYPE, fragmentType);
        args.putString(A1Constants.KEY_MANUFACTURER, manufacturer);
        args.putString(A1Constants.KEY_MAIN_TYPE, mainType);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mFragmentType = (A1Constants.FragmentType) getArguments().getSerializable(A1Constants.KEY_LIST_TYPE);
            mManufacturer = getArguments().getString(A1Constants.KEY_MANUFACTURER);
            mMainType = getArguments().getString(A1Constants.KEY_MAIN_TYPE);
        }
        fetchData();
        setProgressVisibility(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_universal, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);

        mRecyclerView.setHasFixedSize(true);
        setRecyclerViewLayoutManager();
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity()));

        setAdapter();
        setScrollListener();

        mRecyclerView.addOnScrollListener(mRecyclerViewScrollListener);
        mSwipeRefreshLayout.setOnRefreshListener(this);
    }

    private void addSections() {
        mSectionsDataset.clear();
        mSectionsDataset.add(new SectionRecyclerViewAdapter.Section(0,"Recent"));
        mSectionsDataset.add(new SectionRecyclerViewAdapter.Section(4,"Recent"));
    }

    public void setScrollListener() {
        mRecyclerViewScrollListener = new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if(dy > 0) {
                    mVisibleItemCount = mLayoutManager.getChildCount();
                    mTotalItemCount = mLayoutManager.getItemCount();
                    mPastVisiblesItems = mLayoutManager.findFirstVisibleItemPosition();
                    if (mLoading) {
                        if ( (mVisibleItemCount + mPastVisiblesItems) >= mTotalItemCount) {
                            mLoading = false;
                            Log.d(TAG, "Cursor call");
                            fetchData();
                        }
                    }
                }
            }
        };
    }

    private void setRecyclerViewLayoutManager() {
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

    private void fetchData() {
        if (mPage <= mTotalPageCount) {
            if (mFragmentType == A1Constants.FragmentType.MANUFACTURER) {
                NetworkManager.getInstance().fetchManufacturers(mPage, this);
            } else if (mFragmentType == A1Constants.FragmentType.MAIN_TYPE) {
                NetworkManager.getInstance().fetchMainTypes(mManufacturer.split(",")[0], mPage, this);
            } else if (mFragmentType == A1Constants.FragmentType.BUILT_DATES) {
                NetworkManager.getInstance().fetchBuiltDates(mManufacturer.split(",")[0], mMainType, this);
            }
        }
    }

    public void setAdapter() {
        if (mAdapter == null) {
            mAdapter = new RecyclerViewAdapter(getActivity(), mDataset, this);
        }
        if (mSectionedAdapter == null) {
            mSectionedAdapter = new SectionRecyclerViewAdapter(getActivity(),R.layout.item_section_recyclerview ,mAdapter);
            mRecyclerView.setAdapter(mSectionedAdapter);
        } else {
            SectionRecyclerViewAdapter.Section[] dummy = new SectionRecyclerViewAdapter.Section[mSectionsDataset.size()];
            mSectionedAdapter.setSections(mSectionsDataset.toArray(dummy));
            mAdapter.notifyAdapter(mDataset);
        }
    }

    private boolean isDataSetEmpty() {
        if (mDataset != null && mDataset.size() != 0) {
            return false;
        }
        return true;
    }

    private void setProgressVisibility(boolean visibile) {
        if (mProgressBar != null) {
            if (visibile) {
                mProgressBar.setVisibility(View.VISIBLE);
            } else {
                mProgressBar.setVisibility(View.GONE);
            }
        }
    }

    private void setEmptyViewVisibility(boolean visibile) {
        if (mEmptyView != null) {
            if (visibile) {
                mEmptyView.setVisibility(View.VISIBLE);
            } else {
                mEmptyView.setVisibility(View.GONE);
            }
        }
    }

    public String getKey(HashMap<String, String> map, String value) {
        String key = null;
        for(Map.Entry<String, String> entry : map.entrySet()) {
            if((value == null && entry.getValue() == null) || (value != null && value.equals(entry.getValue()))) {
                key = entry.getKey();
                break;
            }
        }
        return key;
    }


    @Override
    public void onRefresh() {
        setProgressVisibility(true);
        mPage = 0;
        mDataset.clear();
        mMap.clear();
        fetchData();
    }

    @Override
    public void onResponse(Call call, Response response) {
        if (response.code() == A1Constants.HTTP_SUCCESS && response.body() != null) {
            mPage++;
            mLoading = true;
            A1Response a1Response = (A1Response) response.body();
            if (mFragmentType != A1Constants.FragmentType.BUILT_DATES) {
                mTotalPageCount = a1Response.totalPageCount;
            }
            mMap.putAll(a1Response.wkda);
            mDataset.addAll(new ArrayList<>(a1Response.wkda.values()));
            setAdapter();

            if (mTotalPageCount > 1 && mPage == 1) {
                fetchData();
            }
        }
        mSwipeRefreshLayout.setRefreshing(false);
        setEmptyViewVisibility(isDataSetEmpty());
        setProgressVisibility(false);
    }

    @Override
    public void onFailure(Call call, Throwable t) {
        mSwipeRefreshLayout.setRefreshing(false);
        setEmptyViewVisibility(isDataSetEmpty());
        setProgressVisibility(false);
    }

    @Override
    public void onItemClick(int position) {
        Log.v(TAG, "Map size: " + mMap.size());
        List<String> keys = new ArrayList<>(mMap.keySet());

        mAdapter = null;
        mSectionedAdapter = null;

        if (mFragmentType == A1Constants.FragmentType.MANUFACTURER) {
            Manufacturer manufacturer = new Manufacturer(keys.get(position), mDataset.get(position));
            mManufacturer = keys.get(position) + "," + mDataset.get(position);
            mListener.onFragmentInteraction(A1Constants.FragmentType.MAIN_TYPE, mManufacturer, mMainType, mBuiltDate);
        } else if (mFragmentType == A1Constants.FragmentType.MAIN_TYPE) {
            MainType mainType = new MainType(keys.get(position), mDataset.get(position));
            mMainType = keys.get(position);
            mListener.onFragmentInteraction(A1Constants.FragmentType.BUILT_DATES, mManufacturer, mMainType, mBuiltDate);
        } else if (mFragmentType == A1Constants.FragmentType.BUILT_DATES) {
            BuiltDates builtDates = new BuiltDates(keys.get(position), mDataset.get(position));
            mBuiltDate = keys.get(position);
            mListener.onFragmentInteraction(A1Constants.FragmentType.SUMMARY, mManufacturer, mMainType, mBuiltDate);
        }
    }
}
