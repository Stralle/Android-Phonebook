package com.example.strahinja.contacts;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class RecyclerCardInfoAdapter extends RecyclerView.Adapter<RecyclerCardInfoAdapter.ViewHolder> {

    public static HashMap<String, String> mData = new HashMap<>();
    public static ArrayList<String> keys = new ArrayList<>();
    private Context context;

    public RecyclerCardInfoAdapter(HashMap<String, String> maData, List<String> keys, Context context){
        if(maData == null) {
            Log.d("MDATA ", " IS NULL!!!");
        }
        else {
            Log.d("MDATA ", "NOT NULL");
        }
        this.mData.putAll(maData);
        this.keys.addAll(keys);
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_info_item, parent, false);
        ViewHolder vHolder = new ViewHolder(v);
        return vHolder;
    }

    public static HashMap<String, String> getmData() {
        return mData;
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerCardInfoAdapter.ViewHolder holder, int position) {
        final int pos = position;
        //Glide.with(context).asBitmap().load(mData.get(position).getImgUrl()).into(holder.getImage());
        String key = keys.get(position);
        String value = mData.get(key);
        holder.getItemName().setText(key);
        holder.getItemContact().setText(value);

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView itemName;
        private TextView itemContact;
        private RelativeLayout layout;

        public ViewHolder(View itemView) {
            super(itemView);
            itemName = itemView.findViewById(R.id.cardInfoName);
            itemContact = itemView.findViewById(R.id.cardInfoDetail);
            layout = itemView.findViewById(R.id.cardInfoRelativeLayout);
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

    }
}
