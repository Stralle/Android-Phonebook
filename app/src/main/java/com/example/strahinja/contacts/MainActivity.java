package com.example.strahinja.contacts;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static DBHelper myDb;
    public static ArrayList<Contact> allContacts = new ArrayList<>();

    private TabLayout tabLayout;
    private ViewPagerAdapter adapter;
    private ViewPager viewpager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDb = new DBHelper(this);

        allContacts = new ArrayList<Contact>();
        getThemAll(); //allContacts = getThemAll();

        tabLayout = (TabLayout) findViewById(R.id.tablayout_id);
        viewpager = (ViewPager) findViewById(R.id.viewpager_id);

        adapter = new ViewPagerAdapter(getSupportFragmentManager());

        //Adding fragments
        adapter.addFragment(new FragmentMain(), "");
        adapter.addFragment(new FragmentFavorite(), "");
//        adapter.addFragment(new FragmentCamera(), "");
//        adapter.addFragment(new FragmentDelete(), "");

        viewpager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewpager);

//        tabLayout.getTabAt(0).setIcon(R.drawable.ic_call);
        tabLayout.getTabAt(0).setIcon(R.drawable.ic_person1);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_star);

    }


    public static ArrayList<Contact> getThemAll() {

        Cursor res = myDb.getAllData();

        allContacts.removeAll(allContacts);

        while (res.moveToNext()) {

            Contact c = new Contact(
                    res.getString(0),
                    res.getString(1),
                    res.getString(2),
                    res.getString(3),
                    res.getInt(4),
                    res.getString(5),
                    res.getBlob(6)
            );
            c.setLocation(res.getString(7));

            Log.d("TAG", "getThemAll: " + c.toString() + c.getFavorite());
            Log.d("GTA", " " + c.getLocation());
            if (c.getLastName() != null || c.getFirstName() != null)
                allContacts.add(c);
        }

        return allContacts;

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("Resuming", "back to my first activity");
        allContacts = getThemAll();

        for (Contact c: allContacts) {
            Log.d("First and Last name: ", c.getFirstName() + " " + c.getLastName() + " L: " + c.getLocation());
        }

    }

    @Override
    public void onBackPressed() {
        Log.d("Back", " is pressed!!!");
        allContacts = getThemAll();
    }
}
