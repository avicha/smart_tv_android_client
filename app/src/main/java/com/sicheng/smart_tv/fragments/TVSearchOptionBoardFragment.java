package com.sicheng.smart_tv.fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;

import com.sicheng.smart_tv.R;
import com.sicheng.smart_tv.models.ListResponse;
import com.sicheng.smart_tv.models.SearchOption;
import com.sicheng.smart_tv.models.TVSearchParams;
import com.sicheng.smart_tv.services.TVService;

import java.lang.reflect.Field;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TVSearchOptionBoardFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TVSearchOptionBoardFragment extends Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String DEFAULT_SEARCH_PARAMS = "default_search_params";

    private TVSearchParams search_params;

    private TableLayout tvSearchOptionsContainer;

    private TVService.TVServiceInterface tvService = TVService.getInstance();


    private ArrayList<TVSearchOptionFragment> tvSearchOptionFragmentArrayList = new ArrayList<>();

    public TVSearchOptionBoardFragment() {
        // Required empty public constructor
    }

    public void setSearchParams(TVSearchParams tvSearchParams) {
        this.search_params = tvSearchParams;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param searchParams Parameter 1.
     * @return A new instance of fragment TVSearchOptionBoardFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TVSearchOptionBoardFragment newInstance(TVSearchParams searchParams) {
        TVSearchOptionBoardFragment fragment = new TVSearchOptionBoardFragment();
        Bundle args = new Bundle();
        args.putParcelable(DEFAULT_SEARCH_PARAMS, searchParams);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            search_params = getArguments().getParcelable(DEFAULT_SEARCH_PARAMS);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tv_search_option_board, container, false);
        this.tvSearchOptionsContainer = view.findViewById(R.id.tv_search_options_container);
        this.loadTVSearchOptions();
        return view;
    }

    public void loadTVSearchOptions() {
        Call<ListResponse<SearchOption>> call = tvService.getSearchOptions();
        call.enqueue(new Callback<ListResponse<SearchOption>>() {
            @Override
            public void onResponse(Call<ListResponse<SearchOption>> call, Response<ListResponse<SearchOption>> response) {
                ListResponse<SearchOption> searchOptionListResponse = response.body();
                ArrayList<SearchOption> searchOptionArrayList = searchOptionListResponse.getResult();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                for (SearchOption searchOption : searchOptionArrayList) {
                    String key = searchOption.getKey();
                    if (search_params != null) {
                        try {
                            Field field = search_params.getClass().getDeclaredField(key);
                            field.setAccessible(true);
                            String value = (String) field.get(search_params);
                            TVSearchOptionFragment tvSearchOptionFragment = TVSearchOptionFragment.newInstance(searchOption, value);
                            tvSearchOptionFragmentArrayList.add(tvSearchOptionFragment);
                            fragmentTransaction.add(R.id.tv_search_options_container, tvSearchOptionFragment);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        TVSearchOptionFragment tvSearchOptionFragment = TVSearchOptionFragment.newInstance(searchOption, null);
                        tvSearchOptionFragmentArrayList.add(tvSearchOptionFragment);
                        fragmentTransaction.add(R.id.tv_search_options_container, tvSearchOptionFragment);
                    }
                }
                fragmentTransaction.commit();
                fragmentManager.executePendingTransactions();
            }

            @Override
            public void onFailure(Call<ListResponse<SearchOption>> call, Throwable t) {
                Log.e("TV_GET_SEARCH_OPTIONS", call.request().url() + ": failed: " + t);
            }
        });
    }

    public void show() {
        this.getView().setVisibility(View.VISIBLE);
    }

    public void hide() {
        this.getView().setVisibility(View.GONE);
    }

    public TVSearchParams getSearchParams() {
        return this.search_params;
    }

    public void changeSearchOption() {
        for (int i = 0, len = this.tvSearchOptionFragmentArrayList.size(); i < len; i++) {
            TVSearchOptionFragment child = this.tvSearchOptionFragmentArrayList.get(i);
            child.changeSelectedValue();
            String key = child.getKey();
            String value = child.getValue();
            try {
                Field field = search_params.getClass().getDeclaredField(key);
                field.setAccessible(true);
                field.set(search_params, value);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
