package com.izzaweb.bengkel.Ui;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.izzaweb.bengkel.Adapter.RVAdapterHistory;
import com.izzaweb.bengkel.Base.BaseActivity;
import com.izzaweb.bengkel.Models.Booking.Booking;
import com.izzaweb.bengkel.Models.Booking.BookingList;
import com.izzaweb.bengkel.Networking.APIClient;
import com.izzaweb.bengkel.Networking.APIInterface;
import com.izzaweb.bengkel.R;
import com.izzaweb.bengkel.SessionManager.UserSession;

import java.util.List;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HistoryActivity extends BaseActivity {
    @BindView(R.id.rvHistory)
    RecyclerView rvHistory;
    List<Booking> bookings ;
    Call<BookingList> call;
    private static String TAG = "HistoryActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Booking History");
        bind(R.layout.activity_history);
        rvHistory.setLayoutManager(new LinearLayoutManager(this));
        getData();
    }

        private void getData(){
            int id_user = new UserSession(HistoryActivity.this).getUserID();
            APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
            call = apiInterface.getBookingData(id_user);
            Log.d(TAG, "getData: " + call.request().url());
            call.enqueue(new Callback<BookingList>() {
                @Override
                public void onResponse(Call<BookingList> call, Response<BookingList> response) {
                    bookings = response.body().getUserBooking();
                    RVAdapterHistory adapterHistory = new RVAdapterHistory(HistoryActivity.this,bookings);
                    rvHistory.setAdapter(adapterHistory);
                }

                @Override
                public void onFailure(Call<BookingList> call, Throwable t) {
                    showToast("Gagal");
                }
            });
        }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (call != null) call.cancel();
    }

}
