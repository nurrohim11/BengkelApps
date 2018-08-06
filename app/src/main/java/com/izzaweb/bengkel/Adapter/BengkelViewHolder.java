package com.izzaweb.bengkel.Adapter;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.izzaweb.bengkel.Models.Bengkel.Bengkel;
import com.izzaweb.bengkel.Models.Bengkel.BengkelItem;
import com.izzaweb.bengkel.Models.Bengkel.BengkelList;
import com.izzaweb.bengkel.R;
import com.izzaweb.bengkel.Ui.DetailsBengkel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BengkelViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.txtBengkel)TextView txtBengkel;
    @BindView(R.id.txtAlamatBengkel) TextView txtAlamat;
    @BindView(R.id.txtNoTelpon)TextView txtNoTelp;
    @BindView(R.id.txtHariBuka)TextView txtHariBuka;
    private List<BengkelItem> list;
    private void setResult(ArrayList<BengkelItem> lists){
        list= lists;
    }
    public BengkelViewHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }
    public void bind(final BengkelItem item){
        txtBengkel.setText(item.getNamaBengkel());
        txtAlamat.setText(item.getAlamatBengkel());
        txtHariBuka.setText(item.getHariBuka());
        txtNoTelp.setText(item.getNoTelp());
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(itemView.getContext(), DetailsBengkel.class);
                intent.putExtra(DetailsBengkel.BENGKEL_ITEM, new Gson().toJson(item));
                itemView.getContext().startActivity(intent);
            }
        });
    }
}
