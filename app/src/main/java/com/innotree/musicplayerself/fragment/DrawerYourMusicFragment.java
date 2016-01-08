package com.innotree.musicplayerself.fragment;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.innotree.musicplayerself.R;
import com.innotree.musicplayerself.adapter.YourMusicRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DrawerYourMusicFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DrawerYourMusicFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DrawerYourMusicFragment extends android.support.v4.app.Fragment {

    private static List<String> options = new ArrayList<>();
    private static List<String> optionImages = new ArrayList<>();

    private OnFragmentInteractionListener mListener;

    private YourMusicRecyclerViewAdapter mRecyclerViewAdapter;

    public DrawerYourMusicFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View fragmentView = inflater.inflate(R.layout.fragment_drawer_your_music, container, false);

        RecyclerView mRecyclerView = (RecyclerView) fragmentView.findViewById(R.id.rv_list);
        mRecyclerViewAdapter = new YourMusicRecyclerViewAdapter(options, optionImages);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mRecyclerViewAdapter);

        addAdapterData();

        return fragmentView;
    }

    public void addAdapterData() {

        options.clear();
        optionImages.clear();

        options.add("Playlists");
        options.add("Songs");
        options.add("Albums");
        options.add("Artists");

        optionImages.add("drawable://" + R.drawable.playlist);
        optionImages.add("drawable://" + R.drawable.songs);
        optionImages.add("drawable://" + R.drawable.albums);
        optionImages.add("drawable://" + R.drawable.artists);

        mRecyclerViewAdapter.notifyDataSetChanged();
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (OnFragmentInteractionListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
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
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }
}