package com.sicheng.smart_tv.launcher;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.sicheng.smart_tv.R;
import com.sicheng.smart_tv.fragments.SearchTVFragment;
import com.sicheng.smart_tv.models.TV;

import java.util.ArrayList;

public class TVActivity extends Activity implements SearchTVFragment.OnFragmentInteractionListener {
    private SearchTVFragment searchTVFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tv);
        this.searchTVFragment = (SearchTVFragment) getFragmentManager().findFragmentById(R.id.search_tv_fragment);
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
