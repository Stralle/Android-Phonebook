package com.example.strahinja.contacts;

import android.content.Intent;
import android.database.Cursor;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class FragmentMain extends Fragment {


    private ArrayList<Contact> allContacts = new ArrayList<>();
    private View view;
    private RecyclerView myRecyclerView;
    private RecyclerMainViewAdapter recyclerViewAdapter;
    private FloatingActionButton fab;
    private boolean flag = false;

    public FragmentMain() {}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        allContacts = new ArrayList<Contact>();

        allContacts = MainActivity.getThemAll();

        if (allContacts.isEmpty()) {
            flag = true;
        }
        else {
            flag = false;
        }

    }


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_main, container, false);

        Log.d("FragmentMain:", "Content view set");
        myRecyclerView = (RecyclerView) view.findViewById(R.id.contact_recyclerview);

        recyclerViewAdapter = new RecyclerMainViewAdapter(getActivity(), MainActivity.allContacts);
        myRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        myRecyclerView.setAdapter(recyclerViewAdapter);

        if(flag) {
            showMessage("Oops nothing found :( ","No contacts in database");
        }

        fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent;
                myIntent = new Intent(getActivity(), AddNewContactActivity.class);
                startActivity(myIntent);
            }
        });

        return view;
    }


    public void showMessage(String title, String Message)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }

}
