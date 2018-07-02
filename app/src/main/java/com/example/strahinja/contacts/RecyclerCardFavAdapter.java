package com.example.strahinja.contacts;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class RecyclerCardFavAdapter extends RecyclerView.Adapter<RecyclerCardFavAdapter.ViewHolder> {

    private static ArrayList<Contact> mData;
    private Context context;

    public RecyclerCardFavAdapter(ArrayList<Contact> mData, Context context){
        this.mData = new ArrayList<>();
        for(Contact c: mData){
            if(c.getFavorite() == 1){
                this.mData.add(c);
            }
        }
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_fav_item, parent, false);
        ViewHolder vHolder = new ViewHolder(v);
        return vHolder;
    }

    public static ArrayList<Contact> getmData() {
        return mData;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final int pos = position;
        //Glide.with(context).asBitmap().load(mData.get(position).getImgUrl()).into(holder.getImage());
        holder.getItemName().setText(mData.get(position).getFirstName() + " " + mData.get(position).getLastName());
        holder.getItemContact().setText(mData.get(position).getPhone());
        byte[] bytes = mData.get(position).getImage();
//        Log.d("FRAGMENT-EDIT: Bytes: ", ""+bytes);
        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        holder.getItemImage().setImageBitmap(bitmap);

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView itemName;
        private TextView itemContact;
        private ImageView itemImage;
        private RelativeLayout layout;

        public ViewHolder(View itemView) {
            super(itemView);
            itemName = itemView.findViewById(R.id.cardFavName);
            itemContact = itemView.findViewById(R.id.cardFavContact);
            itemImage = itemView.findViewById(R.id.cardFavImage);
//            layout = itemView.findViewById(R.id.cardFavRelativeLayout);
        }

        public RelativeLayout getLayout() {
            return layout;
        }

        public TextView getItemContact() {
            return itemContact;
        }

        public TextView getItemName() {
            return itemName;
        }

        public void setItemContact(TextView itemContact) {
            this.itemContact = itemContact;
        }

        public void setItemName(TextView itemName) {
            this.itemName = itemName;
        }

        public void setLayout(RelativeLayout layout) {
            this.layout = layout;
        }

        public ImageView getItemImage() {
            return itemImage;
        }

        public void setItemImage(ImageView itemImage) {
            this.itemImage = itemImage;
        }
    }
}
