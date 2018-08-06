package com.izzaweb.bengkel.Ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.izzaweb.bengkel.Base.BaseActivity;
import com.izzaweb.bengkel.Models.Bengkel.Bengkel;
import com.izzaweb.bengkel.R;

import butterknife.BindView;

public class DetailsBengkel extends BaseActivity {
    public static  final String BENGKEL_ITEM= "bengkel_item";
    @BindView(R.id.txtAlamatBengkel)TextView txtAlamatBengkel ;
    @BindView(R.id.txtJadwal)TextView txtJadwal ;
    @BindView(R.id.txtTelp)
    TextView txtTelp ;
    @BindView(R.id.txtNamaBengkel)TextView txtNamaBengkel ;
    @BindView(R.id.imgHeader)
    ImageView imgHeader;
    @BindView(R.id.btnBooking)
    Button btnBooking;
    private String id_bengkel;
    private Bengkel bengkel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bind(R.layout.activity_details_bengkel);
        setTitle("Detail Bengkel");
        Bundle data = getIntent().getExtras();
        bengkel = data.getParcelable(BENGKEL_ITEM);
        if(bengkel != null){
            id_bengkel = bengkel.getIdBengkel();
            txtAlamatBengkel.setText(bengkel.getAlamatBengkel());
            txtJadwal.setText(bengkel.getHariBuka());
            txtTelp.setText(bengkel.getNoTelp());
            txtNamaBengkel.setText(bengkel.getNamaBengkel());
//            if(!bengkel.getImgBengkel().equals(null)){
//                Glide.with(DetailsBengkel.this)
//                        .load(bengkel.getImgBengkel())
//                        .into(imgHeader);
//            }
        }
        btnBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DetailsBengkel.this,BookingActivity.class);
                i.putExtra("id_bengkel",id_bengkel);
                startActivity(i);
            }
        });
    }
}
