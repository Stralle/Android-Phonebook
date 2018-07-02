package com.example.strahinja.contacts;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.IOException;

public class EditActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPagerAdapter adapter;
    private ViewPager viewpager;

    public static Contact currentContact;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        String id = getIntent().getStringExtra("id");

        for(Contact c: MainActivity.allContacts) {
            if (c.getId().equals(id)) {
                currentContact = c;
            }
        }

        Log.d("Current contact: ", currentContact.toString());

        tabLayout = (TabLayout) findViewById(R.id.tablayout_id);
        viewpager = (ViewPager) findViewById(R.id.viewpager_id);

        adapter = new ViewPagerAdapter(getSupportFragmentManager());

        //Adding fragments
        adapter.addFragment(new FragmentInfo(), "");
        adapter.addFragment(new FragmentEdit(), "");
        adapter.addFragment(new FragmentDelete(), "");
        adapter.addFragment(new FragmentCamera2(), "");
//        adapter.addFragment(new FragmentCamera(), "");

        viewpager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewpager);

        tabLayout.getTabAt(0).setIcon(R.drawable.ic_info);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_edit);
        tabLayout.getTabAt(2).setIcon(R.drawable.ic_delete);
        tabLayout.getTabAt(3).setIcon(R.drawable.ic_camera);



    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent myIntent;
        myIntent = new Intent(EditActivity.this, MainActivity.class);
        startActivity(myIntent);
        finish();
    }
}
