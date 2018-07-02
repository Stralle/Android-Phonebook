package com.example.strahinja.contacts;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class FragmentInfo extends Fragment {

    private View view;
    private ImageView imageView, favIcon;
    private FloatingActionButton fab;
    private Button btnLocation;
    private static final int REQUEST_SMS = 0;
    private static final int REQ_PICK_CONTACT = 2 ;

    public FragmentInfo() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_info, container, false);

        imageView = view.findViewById(R.id.info_image);
        byte[] bytes = EditActivity.currentContact.getImage();
        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        imageView.setImageBitmap(bitmap);
        fab = view.findViewById(R.id.info_fab);
        btnLocation = view.findViewById(R.id.info_btn_map);
        favIcon = view.findViewById(R.id.info_heart);

        if(EditActivity.currentContact.getFavorite() == 1) {
            favIcon.setImageResource(R.drawable.ic_favorite_red);
        }
        else {
            favIcon.setImageResource(R.drawable.ic_favorite);
        }

        favIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(EditActivity.currentContact.getFavorite() == 1) {
                    EditActivity.currentContact.setFavorite(0);
                    favIcon.setImageResource(R.drawable.ic_favorite);
                }
                else {
                    EditActivity.currentContact.setFavorite(1);
                    favIcon.setImageResource(R.drawable.ic_favorite_red);
                }
                MainActivity.myDb.updateFavorite(EditActivity.currentContact.getId(), Integer.toString(EditActivity.currentContact.getFavorite()));
            }
        });

        btnLocation.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent myIntent;
                myIntent = new Intent(getActivity(), MapsActivity.class);
                startActivity(myIntent);
                getActivity().finish();
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
           public void onClick(View view) {

               // Creating alert Dialog with one Button
               AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());

               // Setting Dialog Title
               alertDialog.setTitle("Send SMS");

               Context context = getContext();
               LinearLayout layout = new LinearLayout(context);
               layout.setOrientation(LinearLayout.VERTICAL);

               // Setting Dialog Message
               alertDialog.setMessage("Enter number and text");

               final EditText phone = new EditText(getActivity());
               phone.setText(EditActivity.currentContact.getPhone());
               phone.setHint("Phone");

               final EditText message = new EditText(getActivity());
               message.setHint("Message");
               LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                       LinearLayout.LayoutParams.MATCH_PARENT,
                       LinearLayout.LayoutParams.MATCH_PARENT);
               phone.setLayoutParams(lp);
               message.setLayoutParams(lp);
               layout.addView(phone);
               layout.addView(message);

               alertDialog.setView(layout);

               alertDialog.setPositiveButton("Send", new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                       //do things
                       if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                           int hasSMSPermission = getActivity().checkSelfPermission(Manifest.permission.SEND_SMS);
                           if (hasSMSPermission != PackageManager.PERMISSION_GRANTED) {
                               if (!shouldShowRequestPermissionRationale(Manifest.permission.SEND_SMS)) {
                                   showMessageOKCancel("You need to allow access to Send SMS",
                                           new DialogInterface.OnClickListener() {
                                               @Override
                                               public void onClick(DialogInterface dialog, int which) {
                                                   if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                       requestPermissions(new String[] {Manifest.permission.SEND_SMS},
                                                               REQUEST_SMS);
                                                   }
                                               }
                                           });
                                   return;
                               }
                               requestPermissions(new String[] {Manifest.permission.SEND_SMS},
                                       REQUEST_SMS);
                               return;
                           }
                           sendMySMS(phone, message);
                       }
                   }
               });

               // Showing Alert Message
               alertDialog.show();
           }
        });
        //TODO: FAB messaging!
        initRecyclerView(view);
        return view;
    }

    private void initRecyclerView(View view){
        RecyclerView recycler = view.findViewById(R.id.fragmentinfo_recyclerview);
        HashMap<String, String> map = new HashMap<>();
        ArrayList<String> keys = new ArrayList<>();
        keys.add("Name");
        keys.add("Phone");
        keys.add("Email");

        map.put("Name", EditActivity.currentContact.getFirstName() + " " + EditActivity.currentContact.getLastName());
        map.put("Phone", EditActivity.currentContact.getPhone());
        map.put("Email", EditActivity.currentContact.getEmail());

        if(map == null) {
            Log.d("Map is NULL", ". OMG");
        }
        RecyclerCardInfoAdapter adapter = new RecyclerCardInfoAdapter(map, keys, getContext());
        recycler.setAdapter(adapter);
        recycler.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    public void sendMySMS(EditText phoneEditText, EditText messageEditText) {

        String phone = phoneEditText.getText().toString();
        String message = messageEditText.getText().toString();

        //Check if the phoneNumber is empty
        if (phone.isEmpty()) {
            Toast.makeText(getContext(), "Please Enter a Valid Phone Number", Toast.LENGTH_SHORT).show();
        } else {

            SmsManager sms = SmsManager.getDefault();
            // if message length is too long messages are divided
            List<String> messages = sms.divideMessage(message);
            for (String msg : messages) {

                PendingIntent sentIntent = PendingIntent.getBroadcast(getActivity(), 0, new Intent("SMS_SENT"), 0);
                PendingIntent deliveredIntent = PendingIntent.getBroadcast(getActivity(), 0, new Intent("SMS_DELIVERED"), 0);
                sms.sendTextMessage(phone, null, msg, sentIntent, deliveredIntent);

            }
        }
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new android.support.v7.app.AlertDialog.Builder(getActivity())
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

}
