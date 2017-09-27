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
import android.widget.TextView;

import com.sicheng.smart_tv.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NavFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NavFragment extends Fragment {
    private String menu;
    private String page;
    private TextView navTextView;
    private boolean isActive;
    private OnFragmentInteractionListener mListener;

    public NavFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param menu 导航.
     * @return A new instance of fragment NavFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NavFragment newInstance(String menu, String page) {
        NavFragment fragment = new NavFragment();
        Bundle args = new Bundle();
        args.putString("menu", menu);
        args.putString("page", page);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            menu = getArguments().getString("menu");
            page = getArguments().getString("page");
        }
        this.isActive = false;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_nav, container, false);
        navTextView = view.findViewById(R.id.nav);
        navTextView.setText(menu);
        navTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleNavClick();
            }
        });
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

    public void focus() {
        if (!this.isActive) {
            AnimationSet animationSet = new AnimationSet(true);
            animationSet.setFillAfter(true);
            ScaleAnimation scaleAnimation = new ScaleAnimation(1, 1.2f, 1, 1.2f,
                    Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            scaleAnimation.setDuration(400);
            animationSet.addAnimation(scaleAnimation);
            getView().startAnimation(animationSet);
            this.isActive = true;
        }
    }

    public void blur() {
        if (this.isActive) {
            AnimationSet animationSet = new AnimationSet(true);
            animationSet.setFillAfter(true);
            ScaleAnimation scaleAnimation = new ScaleAnimation(1.2f, 1, 1.2f, 1,
                    Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            scaleAnimation.setDuration(400);
            animationSet.addAnimation(scaleAnimation);
            getView().startAnimation(animationSet);
            this.isActive = false;
        }
    }

    protected void handleNavClick() {
        mListener.onNavClick(this.page);
    }

    public interface OnFragmentInteractionListener {
        void onNavClick(String page);
    }

}
