package com.sicheng.smart_tv.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.sicheng.smart_tv.R;
import com.sicheng.smart_tv.models.Category;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CategoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CategoryFragment extends Fragment {
    private ImageView iconImageView;
    private TextView nameTextView;
    private TextView descTextView;
    private Category category;

    public CategoryFragment() {

    }

    public Category getCategory() {
        return this.category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param category Category.
     * @return A new instance of fragment CategoryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CategoryFragment newInstance(Category category) {
        CategoryFragment fragment = new CategoryFragment();
        fragment.setCategory(category);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_category, container, false);
        this.iconImageView = view.findViewById(R.id.icon);
        this.nameTextView = view.findViewById(R.id.name);
        this.descTextView = view.findViewById(R.id.desc);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        this.render();
    }

    public void render() {
        String icon = this.category.getIcon();
        if (icon != null && icon != "") {
            ImageLoader imageLoader = ImageLoader.getInstance();
            imageLoader.displayImage(this.category.getIcon(), this.iconImageView);
        }
        this.nameTextView.setText(this.category.getName());
        this.descTextView.setText(this.category.getDesc());
    }

}
