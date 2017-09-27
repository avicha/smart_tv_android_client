package com.sicheng.smart_tv.launcher;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.sicheng.smart_tv.R;
import com.sicheng.smart_tv.fragments.SearchTVFragment;
import com.sicheng.smart_tv.fragments.TVListFragment;
import com.sicheng.smart_tv.models.ListResponse;
import com.sicheng.smart_tv.models.TV;
import com.sicheng.smart_tv.services.TVService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TVActivity extends BaseActivity implements SearchTVFragment.OnFragmentInteractionListener, TVListFragment.OnFragmentInteractionListener {
    private SearchTVFragment searchTVFragment;
    private TVListFragment recommendedTVListFragment;
    private TVService.TVServiceInterface tvService = TVService.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tv);
        Log.i("hello", "onCreated TV");
        this.searchTVFragment = (SearchTVFragment) getFragmentManager().findFragmentById(R.id.search_tv_fragment);
        this.recommendedTVListFragment = (TVListFragment) getFragmentManager().findFragmentById(R.id.recommended_tv_list_fragment);

    }

    @Override
    protected void onStart() {
        super.onStart();
        Call<ListResponse<TV>> call = this.tvService.recommend(20);
        call.enqueue(new Callback<ListResponse<TV>>() {
            @Override
            public void onResponse(Call<ListResponse<TV>> call, Response<ListResponse<TV>> response) {
                ListResponse<TV> resp = response.body();
                ArrayList<TV> tvs = resp.getResult();
                recommendedTVListFragment.setTVs(tvs);
            }

            @Override
            public void onFailure(Call<ListResponse<TV>> call, Throwable t) {
                Log.e("RECOMMEND_TV", call.request().url() + ": failed: " + t);
            }
        });
    }

    @Override
    public void onFragmentSearch(String keywords, ArrayList<TV> tvs, int totalRows) {
        Intent intent = new Intent(this, SearchResultActivity.class);
        intent.putExtra("type", "tv");
        intent.putExtra("keywords", keywords);
        intent.putExtra("totalRows", totalRows);
        intent.putParcelableArrayListExtra("data", tvs);
        startActivity(intent);
    }
}
