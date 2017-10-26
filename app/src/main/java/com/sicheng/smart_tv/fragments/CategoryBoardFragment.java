package com.sicheng.smart_tv.fragments;


import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.sicheng.smart_tv.R;
import com.sicheng.smart_tv.models.Category;
import com.sicheng.smart_tv.models.ListResponse;
import com.sicheng.smart_tv.services.CategoryService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;

/**
 * A simple {@link Fragment} subclass.
 */
public class CategoryBoardFragment extends Fragment {
    private LinearLayout categoryListContainer;
    private ArrayList<Category> categoryListData;
    private CategoryService.CategoryServiceInterface categoryService = CategoryService.getInstance();
    private final int countPerRow = 7;
    private final int totalRows = 3;
    private final int[] firstCategoryColors = {Color.parseColor("#EF6C00"), Color.parseColor("#FBC02D"), Color.parseColor("#1976D2")};
    private ArrayList<CategoryFragment> categoryFragments = new ArrayList<>();
    private int focusIndex = 0;

    public CategoryBoardFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_category_board, container, false);
        this.categoryListContainer = view.findViewById(R.id.category_list);
        this.fetchCategoryListData();
        return view;
    }

    private void fetchCategoryListData() {
        Call<ListResponse<Category>> call = this.categoryService.list();
        call.enqueue(new Callback<ListResponse<Category>>() {
            @Override
            public void onResponse(Call<ListResponse<Category>> call, retrofit2.Response<ListResponse<Category>> response) {
                ListResponse<Category> resp = response.body();
                categoryListData = resp.getResult();
                render();
//                setFocusIndex(0);
            }

            @Override
            public void onFailure(Call<ListResponse<Category>> call, Throwable t) {
                Log.e("API:CATEGORY_LIST", call.request().url() + ": failed: " + t);
            }
        });
    }

    private void render() {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        for (int i = 0, len = categoryListData.size(); i < len; i++) {
            int rowIndex = (int) Math.floor(i / countPerRow);
            View row = this.categoryListContainer.getChildAt(rowIndex);
            if (row != null) {
                int viewId = row.getId();
                Category category = categoryListData.get(i);
                CategoryFragment categoryFragment = CategoryFragment.newInstance(category);
                categoryFragments.add(categoryFragment);
                fragmentTransaction.add(viewId, categoryFragment);
            }
        }
        fragmentTransaction.commit();
        fragmentManager.executePendingTransactions();
        for (int i = 0, rows = this.categoryListContainer.getChildCount(); i < rows; i++) {
            LinearLayout row = (LinearLayout) this.categoryListContainer.getChildAt(i);
            for (int j = 0, columns = row.getChildCount(); j < columns; j++) {
                LinearLayout ceil = (LinearLayout) row.getChildAt(j);
                if (j == 0) {
                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT);
                    lp.weight = 2;
                    ceil.getChildAt(0).setBackgroundColor(firstCategoryColors[i]);
                    ceil.setLayoutParams(lp);
                }
            }
        }
    }

//    public void setFocusIndex(int focusIndex) {
//        this.focusIndex = focusIndex;
//        this.categoryFragments.get(focusIndex).getView().requestFocus();
//    }
//
//    public void clickCategory(int focusIndex) {
//        this.categoryFragments.get(focusIndex).getView().performClick();
//    }

//    public void move(int keyCode) {
//        int row = (int) Math.floor(focusIndex / countPerRow);
//        int column = focusIndex % countPerRow;
//        switch (keyCode) {
//            case KeyEvent.KEYCODE_DPAD_LEFT:
//                if (column > 0) {
//                    column--;
//                }
//                break;
//            case KeyEvent.KEYCODE_DPAD_UP:
//                if (row > 0) {
//                    row--;
//                }
//                break;
//            case KeyEvent.KEYCODE_DPAD_RIGHT:
//                if (column < countPerRow - 1) {
//                    column++;
//                }
//                break;
//            case KeyEvent.KEYCODE_DPAD_DOWN:
//                if (row < totalRows - 1) {
//                    row++;
//                }
//                break;
//            case KeyEvent.KEYCODE_DPAD_CENTER:
//                this.clickCategory(focusIndex);
//                break;
//        }
//        int index = row * countPerRow + column;
//        if (categoryListData.get(index) != null && index != focusIndex) {
//            setFocusIndex(index);
//        }
//    }
}
