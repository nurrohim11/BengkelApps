package com.izzaweb.bengkel.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.izzaweb.bengkel.Models.Bengkel.Bengkel;
import com.izzaweb.bengkel.Models.Bengkel.BengkelItem;
import com.izzaweb.bengkel.Models.Bengkel.BengkelList;
import com.izzaweb.bengkel.R;

import java.util.ArrayList;
import java.util.List;

public class BengkelAdapter extends RecyclerView.Adapter<BengkelViewHolder> {
    private List<BengkelItem> list = new ArrayList<>();
    public  BengkelAdapter(){

    }
    public void clearAll(){
        list.clear();
        notifyDataSetChanged();
    }
    public void replaceAll(List<BengkelItem> items){
        list.clear();
        list=items;
    }
    public void updateData(List<BengkelItem> items){
        list.addAll(items);
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public BengkelViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new BengkelViewHolder(
                LayoutInflater.from(viewGroup.getContext()).inflate(
                        R.layout.list_bengkel, viewGroup, false
                )
        );
    }

    @Override
    public void onBindViewHolder(@NonNull BengkelViewHolder bengkelViewHolder, int i) {
        bengkelViewHolder.bind(list.get(i));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
