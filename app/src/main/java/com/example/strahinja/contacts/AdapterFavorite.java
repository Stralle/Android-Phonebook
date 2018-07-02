package com.example.strahinja.contacts;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class AdapterFavorite extends BaseAdapter{
    Context mContext;
    ArrayList<Contact> allContacts = new ArrayList<Contact>();

    public AdapterFavorite(Context mContext, ArrayList<Contact> data) {
        this.mContext = mContext;
        for (Contact c: data) {
            if (c.getFavorite() == 1)
                allContacts.add(c);
        }
    }

    @Override
    public int getCount() {
        return allContacts.size();
    }

    @Override
    public Object getItem(int i) {
        return allContacts.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view==null)
        {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_fav_item, viewGroup, false);
        }

        final Contact c = (Contact) this.getItem(i);

        ImageView img= (ImageView) view.findViewById(R.id.cardFavImage);
        TextView nameTxt= (TextView) view.findViewById(R.id.cardFavName);
        TextView propTxt= (TextView) view.findViewById(R.id.cardFavContact);

        //BIND
        nameTxt.setText(c.getFirstName() + " " + c.getLastName());
        propTxt.setText(c.getPhone());
        byte[] bytes = c.getImage();
//        Log.d("FRAGMENT-EDIT: Bytes: ", ""+bytes);
        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        img.setImageBitmap(bitmap);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, c.getFirstName(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(mContext, EditActivity.class);
                intent.putExtra("id", c.getId());
                mContext.startActivity(intent);

            }
        });

        return view;
    }
}
