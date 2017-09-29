package com.sicheng.smart_tv.launcher;

import android.os.Bundle;

import com.sicheng.smart_tv.R;
import com.sicheng.smart_tv.fragments.TVListFragment;
import com.sicheng.smart_tv.models.TVQueryParams;

public class TVActivity extends BaseActivity implements TVListFragment.OnFragmentInteractionListener {
    private TVListFragment tvListFragment;
    private TVQueryParams tvQueryParams = new TVQueryParams();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tv);
        this.tvListFragment = (TVListFragment) getFragmentManager().findFragmentById(R.id.tv_list_fragment);
    }

    @Override
    protected void onStart() {
        super.onStart();
        tvQueryParams.setKeywords("");
        tvQueryParams.setType(-1);
        tvQueryParams.setYears("");
        tvQueryParams.setRegion(-1);
        tvQueryParams.setPage(1);
        tvQueryParams.setRows(18);
        this.tvListFragment.load(tvQueryParams);
    }
}
