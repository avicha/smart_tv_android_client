package com.sicheng.smart_tv.launcher;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.sicheng.smart_tv.R;
import com.sicheng.smart_tv.fragments.CategoryFragment;
import com.sicheng.smart_tv.fragments.StatusBarFragment;
import com.sicheng.smart_tv.models.Category;
import com.sicheng.smart_tv.models.ListResponse;
import com.sicheng.smart_tv.services.CategoryService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;

public class CategoryActivity extends BaseActivity implements StatusBarFragment.OnFragmentInteractionListener, CategoryFragment.OnFragmentInteractionListener {
    private LinearLayout categoryListContainer;
    private ArrayList<Category> categoryListData;
    private CategoryService.CategoryServiceInterface categoryService = CategoryService.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        this.categoryListContainer = findViewById(R.id.category_list);
        this.initCategoryListData();
    }

    private void initCategoryListData() {
        Call<ListResponse<Category>> call = this.categoryService.list();
        call.enqueue(new Callback<ListResponse<Category>>() {
            @Override
            public void onResponse(Call<ListResponse<Category>> call, retrofit2.Response<ListResponse<Category>> response) {
                ListResponse<Category> resp = response.body();
                categoryListData = resp.getResult();
                initCategoryListView();
            }

            @Override
            public void onFailure(Call<ListResponse<Category>> call, Throwable t) {
                Log.e("API:CATEGORY_LIST", call.request().url() + ": failed: " + t);
            }
        });
    }

    private void initCategoryListView() {
        int countPerRow = 7;
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        for (int i = 0, len = categoryListData.size(); i < len; i++) {
            int rowIndex = (int) Math.floor(i / countPerRow);
            View row = this.categoryListContainer.getChildAt(rowIndex);
            if (row != null) {
                int viewId = row.getId();
                Category category = categoryListData.get(i);
                CategoryFragment categoryFragment = CategoryFragment.newInstance(category);
                fragmentTransaction.add(viewId, categoryFragment);
            }
        }
        fragmentTransaction.commit();
        fragmentManager.executePendingTransactions();
        for (int i = 0, rows = this.categoryListContainer.getChildCount(); i < rows; i++) {
            LinearLayout row = (LinearLayout) this.categoryListContainer.getChildAt(i);
            LinearLayout ceil = (LinearLayout) row.getChildAt(0);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT);
            lp.weight = 2;
            ceil.setLayoutParams(lp);
        }

    }

    @Override
    public void onCategorySelected(String type) {

    }
}
