package com.sicheng.smart_tv.launcher;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;

import com.sicheng.smart_tv.R;
import com.sicheng.smart_tv.fragments.CategoryBoardFragment;
import com.sicheng.smart_tv.fragments.CategoryFragment;

public class CategoryActivity extends BaseActivity implements CategoryFragment.OnFragmentInteractionListener {
    private CategoryBoardFragment categoryBoardFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        this.categoryBoardFragment = (CategoryBoardFragment) getFragmentManager().findFragmentById(R.id.category_board_fragment);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                return true;
            case KeyEvent.KEYCODE_DPAD_LEFT:
            case KeyEvent.KEYCODE_DPAD_UP:
            case KeyEvent.KEYCODE_DPAD_RIGHT:
            case KeyEvent.KEYCODE_DPAD_DOWN:
            case KeyEvent.KEYCODE_DPAD_CENTER:
                this.categoryBoardFragment.move(keyCode);
                break;
        }
        return false;
    }

    @Override
    public void onCategorySelected(String type) {
        switch (type) {
            case "tv":
                Intent intent = new Intent(getApplicationContext(), TVActivity.class);
                startActivity(intent);
                break;
        }
    }
}
