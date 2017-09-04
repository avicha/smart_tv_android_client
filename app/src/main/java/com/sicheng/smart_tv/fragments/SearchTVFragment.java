package com.sicheng.smart_tv.fragments;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.sicheng.smart_tv.R;
import com.sicheng.smart_tv.models.ListResponse;
import com.sicheng.smart_tv.models.TV;
import com.sicheng.smart_tv.services.TVService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SearchTVFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SearchTVFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchTVFragment extends Fragment {

    private SearchView searchView;
    private final String API_URL = "http://192.168.23.183:8888";
    private TVService tvService;

    private OnFragmentInteractionListener mListener;

    public SearchTVFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment SearchTVFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SearchTVFragment newInstance() {
        SearchTVFragment fragment = new SearchTVFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search_tv, container, false);
        this.searchView = view.findViewById(R.id.keywords);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(this.API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        this.tvService = retrofit.create(TVService.class);
        initEvent();
        return view;
    }

    private void initEvent() {
        this.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(final String s) {
                Log.d("SEARCH_TV", "onQueryTextSubmit: " + s);
                Call<ListResponse<TV>> call = tvService.search(s);
                call.enqueue(new Callback<ListResponse<TV>>() {
                    @Override
                    public void onResponse(Call<ListResponse<TV>> call, Response<ListResponse<TV>> response) {
                        ListResponse<TV> resp = response.body();
                        ArrayList<TV> tvs = resp.getResult();
                        int totalRows = resp.getTotalRows();
                        if (mListener != null) {
                            mListener.onFragmentSearch(s, tvs, totalRows);
                        }

                    }

                    @Override
                    public void onFailure(Call<ListResponse<TV>> call, Throwable t) {
                        Log.e("SEARCH_TV", call.request().url() + ": failed: " + t);
                    }
                });
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                Log.d("SEARCH_TV", "onQueryTextChange: " + s);
                return false;
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        void onFragmentSearch(String keywords, ArrayList<TV> resources, int totalRows);
    }

    public void setQuery(String keywords, Boolean submit) {
        this.searchView.setQuery(keywords, submit);
    }
}
