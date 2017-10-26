package com.sicheng.smart_tv.launcher;

import android.os.Bundle;
import android.view.KeyEvent;

import com.sicheng.smart_tv.R;
import com.sicheng.smart_tv.fragments.TVListFragment;
import com.sicheng.smart_tv.fragments.TVSearchOptionBoardFragment;
import com.sicheng.smart_tv.models.TVSearchParams;

public class TVActivity extends BaseActivity {
    private TVListFragment tvListFragment;
    private TVSearchOptionBoardFragment tvSearchOptionBoardFragment;
    private TVSearchParams tvSearchParams = new TVSearchParams();
    private boolean isSearching = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tv);
        this.tvSearchOptionBoardFragment = (TVSearchOptionBoardFragment) getFragmentManager().findFragmentById(R.id.tv_search_option_board_fragment);
        this.tvListFragment = (TVListFragment) getFragmentManager().findFragmentById(R.id.tv_list_fragment);
    }

    @Override
    protected void onStart() {
        super.onStart();
        tvSearchParams.setKeywords("");
        tvSearchParams.setType("-1");
        tvSearchParams.setYears("");
        tvSearchParams.setRegion("-1");
        tvSearchParams.setPage("1");
        tvSearchParams.setRows("18");
        this.tvSearchOptionBoardFragment.setSearchParams(tvSearchParams);
        this.tvListFragment.load(tvSearchParams, true);
    }

    @Override
    public boolean onKeycodeMenuKeyUp(KeyEvent event) {
        if (!this.isSearching) {
            this.tvSearchOptionBoardFragment.show();
            this.tvListFragment.getView().setFocusable(false);
            this.isSearching = true;
        } else {
            this.tvSearchOptionBoardFragment.hide();
            this.tvListFragment.getView().setFocusable(true);
            this.isSearching = false;
            tvSearchParams = this.tvSearchOptionBoardFragment.getSearchParams();
            tvSearchParams.setPage("1");
            this.tvListFragment.load(tvSearchParams, true);
        }
        return false;
    }

    @Override
    public boolean onKeycodeEnterKeyUp(KeyEvent event) {
        if (this.isSearching) {
            this.tvSearchOptionBoardFragment.changeSearchOption();
            return false;
        }
        return super.onKeycodeEnterKeyUp(event);
    }

    @Override
    public boolean onKeycodeBackKeyUp(KeyEvent event) {
        if (this.isSearching) {
            this.tvSearchOptionBoardFragment.hide();
            this.isSearching = false;
            return false;
        }
        return super.onKeycodeBackKeyUp(event);
    }
}
