package com.sicheng.smart_tv.fragments;


import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
        this.navTextView = view.findViewById(R.id.nav);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        this.render();
        this.setupEvents();
    }

    public void render() {
        navTextView.setText(menu);
    }

    public void setupEvents() {
        getView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleNavClick();
            }
        });
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
            TextView nav = getView().findViewById(R.id.nav);
            nav.setTextSize(14);
            getView().setBackgroundColor(Color.parseColor("#77BCAAA4"));
            this.isActive = true;
        }
    }

    public void blur() {
        if (this.isActive) {
            TextView nav = getView().findViewById(R.id.nav);
            nav.setTextSize(12);
            getView().setBackgroundColor(Color.TRANSPARENT);
            this.isActive = false;
        }
    }

    protected void handleNavClick() {
        if (mListener != null) {
            mListener.onNavClick(this.page);
        }
    }

    public interface OnFragmentInteractionListener {
        void onNavClick(String page);
    }

}
