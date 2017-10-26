package com.sicheng.smart_tv.fragments;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sicheng.smart_tv.R;
import com.sicheng.smart_tv.models.SearchOption;

/**
 * A simple {@link Fragment} subclass.
 * to handle interaction events.
 * Use the {@link TVSearchOptionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TVSearchOptionFragment extends Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String SEARCH_OPTION = "search_option";
    private static final String VALUE = "value";

    // TODO: Rename and change types of parameters
    private SearchOption search_option;
    private String value;

    private TextView label;
    private LinearLayout options;

    public TVSearchOptionFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param searchOption Parameter 1.
     * @return A new instance of fragment TVSearchOptionFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TVSearchOptionFragment newInstance(SearchOption searchOption, String value) {
        TVSearchOptionFragment fragment = new TVSearchOptionFragment();
        Bundle args = new Bundle();
        args.putParcelable(SEARCH_OPTION, searchOption);
        args.putString(VALUE, value);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            search_option = getArguments().getParcelable(SEARCH_OPTION);
            value = getArguments().getString(VALUE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tv_search_option, container, false);
        this.label = view.findViewById(R.id.label);
        this.options = view.findViewById(R.id.options);
        this.label.setText(this.search_option.getText());
        for (SearchOption.SearchOptionValue searchOptionValue : this.search_option.getValues()) {
            TextView textView = new TextView(getContext());
            textView.setTag(searchOptionValue.getValue());
            textView.setFocusable(true);
            textView.setTextColor(Color.parseColor("#f1f1f1"));
            textView.setTextSize(14);
            textView.setHeight((int) (30 * getResources().getDisplayMetrics().density));
            textView.setGravity(Gravity.CENTER);
            textView.setPadding(20, 0, 20, 0);
            textView.setText(searchOptionValue.getText());
            textView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View view, boolean b) {
                    String val = (String) view.getTag();
                    if (b) {
                        if (!val.equals(value)) {
                            view.setBackgroundColor(Color.parseColor("#33BCAAA4"));
                        }
                    } else {
                        if (!val.equals(value)) {
                            view.setBackgroundColor(Color.parseColor("#00000000"));
                        }
                    }
                }
            });
            this.options.addView(textView);
        }
        if (this.value != null) {
            this.select(this.value);
        }
        return view;
    }

    public void select(String value) {
        for (int i = 0, len = this.search_option.getValues().size(); i < len; i++) {
            SearchOption.SearchOptionValue searchOptionValue = this.search_option.getValues().get(i);
            String val = searchOptionValue.getValue();
            if (val.equals(value)) {
                this.value = value;
                this.options.getChildAt(i).setBackgroundColor(Color.parseColor("#77BCAAA4"));
            } else {
                this.options.getChildAt(i).setBackgroundColor(Color.parseColor("#00000000"));
            }
        }
    }

    public void changeSelectedValue() {
        for (int i = 0, len = this.options.getChildCount(); i < len; i++) {
            View child = this.options.getChildAt(i);
            if (child.isFocused()) {
                String val = (String) child.getTag();
                this.select(val);
            }
        }
    }

    public String getKey() {
        return this.search_option.getKey();
    }

    public String getValue() {
        return this.value;
    }

}
