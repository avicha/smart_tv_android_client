package com.sicheng.smart_tv.fragments;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.sicheng.smart_tv.R;
import com.sicheng.smart_tv.adapters.TVAdapter;
import com.sicheng.smart_tv.launcher.VideoPlayerActivity;
import com.sicheng.smart_tv.models.TV;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TVListFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TVListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TVListFragment extends Fragment {
    private GridView gridView;
    private OnFragmentInteractionListener mListener;

    public TVListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment TVListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TVListFragment newInstance() {
        TVListFragment fragment = new TVListFragment();
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
        View view = inflater.inflate(R.layout.fragment_tv_list, container, false);
        this.gridView = view.findViewById(R.id.tv_list);
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

    }

    public void setTVs(final ArrayList<TV> tvs) {
        TVAdapter TVAdapter = new TVAdapter(getContext(), tvs);
        this.gridView.setAdapter(TVAdapter);
        this.gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TV tv = tvs.get(i);
                ArrayList<TV> playlist = new ArrayList<TV>();
                playlist.add(tv);
                Intent intent = new Intent(getContext(), VideoPlayerActivity.class);
                intent.putParcelableArrayListExtra("playlist", playlist);
                Log.d("PLAY_TV", "onItemClick: " + tv.getName());
                getActivity().startActivity(intent);
            }
        });
    }
}
