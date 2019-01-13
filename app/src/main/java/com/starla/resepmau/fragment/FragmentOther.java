package com.starla.resepmau.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.starla.resepmau.R;
import com.starla.resepmau.adapter.FavoriteAdapter;
import com.starla.resepmau.model.Favorite;

import java.util.ArrayList;

public class FragmentOther extends Fragment {

    private RecyclerView mRecyclerView;
    private FavoriteAdapter mAdapter;
    private ArrayList<Favorite> mList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_other, null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView = view.findViewById(R.id.rv_fav);
        LinearLayoutManager lm = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(lm);
        populateDummyData();
        mAdapter = new FavoriteAdapter(getActivity(), mList);
        mRecyclerView.setAdapter(mAdapter);

    }

    private void populateDummyData(){
        mList.add(new Favorite("Mendengarkan musik", "Lorem ipsum dolor sit amet"));
        mList.add(new Favorite("Makan", "Lorem ipsum dolor sit amet"));
        mList.add(new Favorite("Tidur", "Lorem ipsum dolor sit amet"));
        mList.add(new Favorite("Jalan-jalan", "Lorem ipsum dolor sit amet"));
        mList.add(new Favorite("Olahraga", "Lorem ipsum dolor sit amet"));
        mList.add(new Favorite("Mancakrida", "Lorem ipsum dolor sit amet"));
        mList.add(new Favorite("Berswa-foto", "Lorem ipsum dolor sit amet"));

    }
}
