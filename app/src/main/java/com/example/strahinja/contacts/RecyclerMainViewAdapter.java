package com.example.strahinja.contacts;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

public class RecyclerMainViewAdapter extends RecyclerView.Adapter<RecyclerMainViewAdapter.MyViewHolder> {

    Context mContext;
    List<Contact> mData;

    public RecyclerMainViewAdapter(Context mContext, List<Contact> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.item_contact, parent, false);
        final MyViewHolder vHolder = new MyViewHolder(view);

        vHolder.item.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mContext, EditActivity.class);
                intent.putExtra("id", String.valueOf(mData.get(vHolder.getAdapterPosition()).getId()));
                mContext.startActivity(intent);

            }
        });

        return vHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        holder.textViewName.setText(mData.get(position).getFirstName() + " " + mData.get(position).getLastName());
        holder.textViewPhone.setText(mData.get(position).getPhone());

        byte[] bytes = mData.get(position).getImage();
        if (bytes != null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            holder.img.setImageBitmap(bitmap);
        }
        else {
            holder.img.setImageResource(R.drawable.ic_person);
        }

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends ViewHolder {

        private LinearLayout item;
        private TextView textViewName;
        private TextView textViewPhone;
        private ImageView img;

        public MyViewHolder(final View itemView) {
            super(itemView);

            item = (LinearLayout) itemView.findViewById(R.id.contact_item);
            textViewName = (TextView) itemView.findViewById(R.id.contact_name);
            textViewPhone = (TextView) itemView.findViewById(R.id.contact_phone);
            img = (ImageView) itemView.findViewById(R.id.contact_img);

        }
    }
}
