package com.example.allseven64.kamus;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import javax.xml.transform.Templates;

public class KamusAdapter extends RecyclerView.Adapter<KamusAdapter.KamusHolder> {
    private ArrayList<KamusModel> mData = new ArrayList<>();
    private Context context;
    private LayoutInflater mInflater;

    public KamusAdapter(Context context) {
        this.context = context;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public KamusHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_kamus, parent, false);
        return new KamusHolder(view);
    }

    public void replaceAll (ArrayList<KamusModel> mData){
        this.mData = mData;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(KamusHolder holder, int position) {
        holder.textViewKata.setText(mData.get(position).getKata_asal());
        holder.textViewArti.setText(mData.get(position).getArti());
    }

    @Override
    public int getItemViewType(int position){
        return 0;
    }

    @Override
    public long getItemId(int position){
        return position;
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class KamusHolder extends RecyclerView.ViewHolder {
        private TextView textViewKata;
        private TextView textViewArti;

        public KamusHolder (View itemView){
            super(itemView);
            textViewKata = (TextView)itemView.findViewById(R.id.tv_kata);
            textViewArti = (TextView)itemView.findViewById(R.id.tv_arti);
        }
    }

}
