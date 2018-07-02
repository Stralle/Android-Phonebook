package com.example.strahinja.contacts;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

public class FragmentFavorite extends Fragment {

    View view;

    public FragmentFavorite() {

    }

    AdapterFavorite adapter;
    GridView gv;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);

        gv = (GridView) view.findViewById(R.id.gv);

        adapter = new AdapterFavorite(getActivity(), MainActivity.allContacts);
        gv.setAdapter(adapter);

        return view;
    }

    /*@Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);
        initRecyclerView(view);
        return view;
    }

    private void initRecyclerView(View view){
        RecyclerView recycler = view.findViewById(R.id.recycler_view2);
        RecyclerCardFavAdapter adapter = new RecyclerCardFavAdapter(MainActivity.allContacts, getContext());
        recycler.setAdapter(adapter);
        recycler.setLayoutManager(new LinearLayoutManager(getActivity()));
    }
*/
}
