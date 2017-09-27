package com.sicheng.smart_tv.launcher;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.LinearLayout;

import com.sicheng.smart_tv.R;
import com.sicheng.smart_tv.fragments.CategoryFragment;
import com.sicheng.smart_tv.models.Category;
import com.sicheng.smart_tv.models.ListResponse;
import com.sicheng.smart_tv.services.CategoryService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;

public class CategoryActivity extends BaseActivity {
    private LinearLayout categoryListContainer;
    private ArrayList<Category> categoryListData;
    private CategoryService.CategoryServiceInterface categoryService = CategoryService.getInstance();
    private final int countPerRow = 7;
    private final int totalRows = 3;
    private final int[] firstCategoryColors = {Color.parseColor("#EF6C00"), Color.parseColor("#FBC02D"), Color.parseColor("#1976D2")};
    private int focusIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        this.categoryListContainer = findViewById(R.id.category_list);
        this.fetchCategoryListData();
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        int row = (int) Math.floor(focusIndex / countPerRow);
        int column = focusIndex % countPerRow;
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                return true;
            case KeyEvent.KEYCODE_DPAD_LEFT:
                if (column > 0) {
                    column--;
                }
                break;
            case KeyEvent.KEYCODE_DPAD_UP:
                if (row > 0) {
                    row--;
                }
                break;
            case KeyEvent.KEYCODE_DPAD_RIGHT:
                if (column < countPerRow - 1) {
                    column++;
                }
                break;
            case KeyEvent.KEYCODE_DPAD_DOWN:
                if (row < totalRows - 1) {
                    row++;
                }
                break;
            case KeyEvent.KEYCODE_DPAD_CENTER:
                LinearLayout rowDom = (LinearLayout) this.categoryListContainer.getChildAt(row);
                LinearLayout ceil = (LinearLayout) rowDom.getChildAt(column);
                ceil.performClick();
                return false;
        }
        int index = row * countPerRow + column;
        if (categoryListData.get(index) != null && index != focusIndex) {
            focusIndex = index;
            LinearLayout rowDom = (LinearLayout) this.categoryListContainer.getChildAt(row);
            LinearLayout ceil = (LinearLayout) rowDom.getChildAt(column);
            ceil.requestFocus();
        }
        return false;
    }

    private void fetchCategoryListData() {
        Call<ListResponse<Category>> call = this.categoryService.list();
        call.enqueue(new Callback<ListResponse<Category>>() {
            @Override
            public void onResponse(Call<ListResponse<Category>> call, retrofit2.Response<ListResponse<Category>> response) {
                ListResponse<Category> resp = response.body();
                categoryListData = resp.getResult();
                renderCategoryListView();
            }

            @Override
            public void onFailure(Call<ListResponse<Category>> call, Throwable t) {
                Log.e("API:CATEGORY_LIST", call.request().url() + ": failed: " + t);
            }
        });
    }

    private void renderCategoryListView() {

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
            for (int j = 0, columns = row.getChildCount(); j < columns; j++) {
                LinearLayout ceil = (LinearLayout) row.getChildAt(j);
                int index = i * countPerRow + j;
                ceil.setTag(categoryListData.get(index).getType());
                if (j == 0) {
                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT);
                    lp.weight = 2;
                    ceil.getChildAt(0).setBackgroundColor(firstCategoryColors[i]);
                    ceil.setLayoutParams(lp);
                }
                ceil.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String type = (String) view.getTag();
                        handleCategorySelected(type);
                    }
                });
                ceil.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View view, boolean b) {
                        AnimationSet animationSet = new AnimationSet(true);
                        animationSet.setFillAfter(true);
                        if (b) {
                            ScaleAnimation scaleAnimation = new ScaleAnimation(1, 1.1f, 1, 1.1f,
                                    Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                            scaleAnimation.setDuration(400);
                            animationSet.addAnimation(scaleAnimation);
                            view.startAnimation(animationSet);
                        } else {
                            ScaleAnimation scaleAnimation = new ScaleAnimation(1.1f, 1, 1.1f, 1,
                                    Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                            scaleAnimation.setDuration(400);
                            animationSet.addAnimation(scaleAnimation);
                            view.startAnimation(animationSet);
                        }
                    }
                });
                if (index == focusIndex) {
                    ceil.requestFocus();
                }
            }
        }
    }

    public void handleCategorySelected(String type) {
        Log.i("CategorySelected", type);
        switch (type) {
            case "tv":
                Intent intent = new Intent(getApplicationContext(), TVActivity.class);
                startActivity(intent);
                break;
        }
    }
}
