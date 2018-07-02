package com.example.strahinja.contacts;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class FragmentDelete extends Fragment {


    private TextView deleteName, deletePhone;
    private Button btnDelete;

    public FragmentDelete() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_delete, container, false);

        deleteName =(TextView) view.findViewById(R.id.delete_name);
        deletePhone = (TextView) view.findViewById(R.id.delete_phone);
        btnDelete = (Button) view.findViewById(R.id.delete_btn);

        deleteName.setText(EditActivity.currentContact.getFirstName());
        deletePhone.setText(EditActivity.currentContact.getPhone());

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                builder.setTitle("Are you sure?");

                builder.setMessage("Are you sure you want to delete this contact?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        MainActivity.myDb.deleteData(EditActivity.currentContact.getId());
                        MainActivity.getThemAll();
                        Intent i = new Intent(getActivity(), MainActivity.class);
                        startActivity(i);
                        getActivity().finish();
                    }
                });
                builder.setNegativeButton("No", null);
                builder.show();
            }
        });

        return view;
    }

}
