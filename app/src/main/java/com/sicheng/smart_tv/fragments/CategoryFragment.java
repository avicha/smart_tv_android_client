package com.sicheng.smart_tv.fragments;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
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
    private String name;
    private String desc;
    private String icon;
    private String type;
    private ImageView iconImageView;
    private TextView nameTextView;
    private TextView descTextView;
    private OnFragmentInteractionListener mListener;

    public CategoryFragment() {

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
        Bundle args = new Bundle();
        args.putString("name", category.getName());
        args.putString("desc", category.getDesc());
        args.putString("icon", category.getIcon());
        args.putString("type", category.getType());
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            name = getArguments().getString("name");
            desc = getArguments().getString("desc");
            icon = getArguments().getString("icon");
            type = getArguments().getString("type");
        }
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
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onStart() {
        super.onStart();
        this.render();
        this.setupEvents();
    }

    public void render() {
        if (icon != null && icon != "") {
            ImageLoader imageLoader = ImageLoader.getInstance();
            imageLoader.displayImage(this.icon, this.iconImageView);
        }
        this.nameTextView.setText(this.name);
        this.descTextView.setText(this.desc);
    }

    public void setupEvents() {
        getView().setOnFocusChangeListener(new View.OnFocusChangeListener() {
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
        getView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleCategorySelected(type);
            }
        });
    }

    protected void handleCategorySelected(String type) {
        if (mListener != null) {
            mListener.onCategorySelected(type);
        }

    }

    public interface OnFragmentInteractionListener {
        void onCategorySelected(String type);
    }
}
