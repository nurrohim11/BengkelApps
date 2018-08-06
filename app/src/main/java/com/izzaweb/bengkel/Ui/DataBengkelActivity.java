package com.izzaweb.bengkel.Ui;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.izzaweb.bengkel.Adapter.BengkelAdapter;
import com.izzaweb.bengkel.Base.BaseActivity;
import com.izzaweb.bengkel.MainActivity;
import com.izzaweb.bengkel.Models.Bengkel.Bengkel;
import com.izzaweb.bengkel.Models.Bengkel.BengkelItem;
import com.izzaweb.bengkel.Models.Bengkel.BengkelList;
import com.izzaweb.bengkel.Models.Bengkel.ResponseBengkel;
import com.izzaweb.bengkel.Networking.APIClient;
import com.izzaweb.bengkel.Networking.APIInterface;
import com.izzaweb.bengkel.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DataBengkelActivity extends BaseActivity {
    @BindView(R.id.rvDataBengkel)RecyclerView rvBengkel;
    private BengkelAdapter adapter;
    List<Bengkel> bengkelList ;

    private Call<ResponseBengkel>  apiCall;
    private APIClient apiClient = new APIClient();
    final String TAG= "BENGKEL";
    List<BengkelItem> resultsItems = new ArrayList<>();

    private int currentPage =1;
    private int totalPages=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bind(R.layout.activity_data_bengkel);
        setTitle("Data Bengkel");
        setupList();
        loadData();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (apiCall != null) apiCall.cancel();
    }
    public void setupList(){
        adapter= new BengkelAdapter();
        rvBengkel.setLayoutManager(new LinearLayoutManager(this));
        rvBengkel.setAdapter(adapter);
    }
    public void loadData(){
        APIInterface apiInterface = apiClient.getClient().create(APIInterface.class);
        apiCall = apiInterface.getBengkelAll();
        Log.d(TAG, "getData: " + apiCall.request().url());
        apiCall.enqueue(new Callback<ResponseBengkel>() {
            @Override
            public void onResponse(Call<ResponseBengkel> call, Response<ResponseBengkel> response) {
                //Log.d(TAG,"ResponseBengkel" + response);
                if (response.isSuccessful()){
                    //adapter.replaceAll(response.body().getBengkel());
                   // Log.d(TAG,"ResponseBengkel data" + response.body());
                    resultsItems= response.body().getBengkel();
                  //  Log.d(TAG,"Data" +call);
                    Log.d(TAG,"Berhasil : " +response.body());
                }else {
                    Log.d(TAG,"Gagal : " +response.body());
                }
                Toast.makeText(getApplicationContext(),"Getting Workshop Location Success", Toast.LENGTH_SHORT);

            }

            @Override
            public void onFailure(Call<ResponseBengkel> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Getting Workshop Location Failed", Toast.LENGTH_SHORT);
            }
        });
    }
}
