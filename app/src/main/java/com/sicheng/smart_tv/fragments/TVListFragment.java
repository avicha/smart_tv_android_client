package com.sicheng.smart_tv.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.sicheng.smart_tv.R;
import com.sicheng.smart_tv.adapters.TVAdapter;
import com.sicheng.smart_tv.launcher.VideoPlayerActivity;
import com.sicheng.smart_tv.models.ListResponse;
import com.sicheng.smart_tv.models.TV;
import com.sicheng.smart_tv.models.TVSearchParams;
import com.sicheng.smart_tv.services.TVService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * to handle interaction events.
 * Use the {@link TVListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TVListFragment extends Fragment {
    private TVSearchParams tvSearchParams;
    private ArrayList<TV> tvArrayList = new ArrayList<>();
    private TextView tvHeaderQueryStatusTextView;
    private GridView gridView;
    private boolean isLoading = false;
    private boolean isEnded = false;
    private TVService.TVServiceInterface tvService = TVService.getInstance();

    public TVListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment TVListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TVListFragment newInstance() {
        TVListFragment fragment = new TVListFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tv_list, container, false);
        this.gridView = view.findViewById(R.id.tv_list);
        this.tvHeaderQueryStatusTextView = view.findViewById(R.id.tv_header_query_status);
        TVAdapter TVAdapter = new TVAdapter(getContext(), tvArrayList);
        this.gridView.setAdapter(TVAdapter);
        this.gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TV tv = tvArrayList.get(i);
                ArrayList<TV> playlist = new ArrayList<>();
                playlist.add(tv);
                Intent intent = new Intent(getContext(), VideoPlayerActivity.class);
                intent.putParcelableArrayListExtra("playlist", playlist);
                getActivity().startActivity(intent);
            }
        });
        this.gridView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id) {
                //倒数第几行开始加载下一页
                int preRows = 2;
                if (pos > tvArrayList.size() - preRows * gridView.getNumColumns() && !isLoading && !isEnded) {
                    loadMore();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        return view;
    }

    public void load(TVSearchParams tvSearchParams, boolean isReset) {
        this.tvSearchParams = tvSearchParams;
        if (isReset) {
            this.isEnded = false;
            tvArrayList.clear();
        }
//        this.tvHeaderQueryStatusTextView = tvSearchParams.getQueryStatus();
        Call<ListResponse<TV>> call = this.tvService.search(this.tvSearchParams.asMap());
        this.isLoading = true;
        call.enqueue(new Callback<ListResponse<TV>>() {
            @Override
            public void onResponse(Call<ListResponse<TV>> call, Response<ListResponse<TV>> response) {
                isLoading = false;
                ListResponse<TV> resp = response.body();
                ArrayList<TV> tvs = resp.getResult();
                if (tvs.size() == 0) {
                    isEnded = true;
                }
                tvArrayList.addAll(tvs);
                ((TVAdapter) gridView.getAdapter()).notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ListResponse<TV>> call, Throwable t) {
                isLoading = false;
                Log.e("API:TV_SEARCH", call.request().url() + ": failed: " + t);
            }
        });
    }

    public void loadMore() {
        if (this.tvSearchParams != null && !this.isEnded) {
            this.tvSearchParams.setPage(String.valueOf(Integer.parseInt(this.tvSearchParams.getPage()) + 1));
            this.load(this.tvSearchParams, false);
        }
    }
}
