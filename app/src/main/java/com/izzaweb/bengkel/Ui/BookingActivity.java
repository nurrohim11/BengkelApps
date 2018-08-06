package com.izzaweb.bengkel.Ui;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;

import com.izzaweb.bengkel.Adapter.SpinnerAdapter;
import com.izzaweb.bengkel.Base.BaseActivity;
import com.izzaweb.bengkel.Models.Booking.UserBooking;
import com.izzaweb.bengkel.Models.Login.RequestResponse;
import com.izzaweb.bengkel.Models.Motor.ListMotor;
import com.izzaweb.bengkel.Models.Motor.Motor;
import com.izzaweb.bengkel.Networking.APIClient;
import com.izzaweb.bengkel.Networking.APIInterface;
import com.izzaweb.bengkel.R;
import com.izzaweb.bengkel.SessionManager.UserSession;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookingActivity extends BaseActivity {
    @BindView(R.id.spTipeMotor)
    Spinner spTipeMotor;

    @BindView(R.id.txtPlatNomor)
    EditText txtPlatNomor;
    @BindView(R.id.txtTanggalService)
    EditText txtTanggalService;
    @BindView(R.id.txtKeluhan)
    EditText txtKeluhan;

    List<Motor> listMotor ;
    Motor motor = new Motor();

    private TimePickerDialog timePickerDialog;
    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
    String date;
    Calendar newDate;
    private int mHour, mMinute;
    UserBooking mBooking;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bind(R.layout.activity_booking);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Booking");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(fieldCheck()){
                    bookingData();
                }
            }
        });
        setDataSpinner();
        pickDateTime();
        txtTanggalService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog.show();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void setDataSpinner(){
        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
        Call<ListMotor> call = apiInterface.getAllMotor();
        call.enqueue(new Callback<ListMotor>() {
            @Override
            public void onResponse(Call<ListMotor> call, Response<ListMotor> response) {
                listMotor = response.body().getMotorlist();
                SpinnerAdapter spinnerAdapter = new SpinnerAdapter(BookingActivity.this,android.R.layout.simple_spinner_item,
                        listMotor);
                spTipeMotor.setAdapter(spinnerAdapter);

            }

            @Override
            public void onFailure(Call<ListMotor> call, Throwable t) {
                showToast("gagal");
            }
        });
        spTipeMotor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                motor = (Motor)parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    private void bookingData(){
        mBooking = new UserBooking();
        mBooking.setIdBengkel(getIntent().getStringExtra("id_bengkel"));
        mBooking.setIdTipeMotor(motor.getIdTipeMotor());
        mBooking.setIdUser(String.valueOf(new UserSession(BookingActivity.this).getUserID()));
        mBooking.setPlatNo(txtPlatNomor.getText().toString().trim());
        mBooking.setKeluhan(txtKeluhan.getText().toString().trim());
        mBooking.setStatusChecked("false");
        mBooking.setTanggalService(txtTanggalService.getText().toString());
        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
        Call<RequestResponse> call = apiInterface.bookingInput(mBooking);
        call.enqueue(new Callback<RequestResponse>() {
            @Override
            public void onResponse(Call<RequestResponse> call, Response<RequestResponse> response) {
                if(response.body().getSuccess()){
                    showToast("Booking Success");
                }
            }

            @Override
            public void onFailure(Call<RequestResponse> call, Throwable t) {

            }
        });
    }
    private void pickDateTime(){
        Calendar newCalendar = Calendar.getInstance();
        mHour = newCalendar.get(Calendar.HOUR_OF_DAY);
        mMinute = newCalendar.get(Calendar.MINUTE);
        datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        newDate = Calendar.getInstance();
                        newDate.set(year, month, dayOfMonth);
                        date = dateFormatter.format(newDate.getTime()).toString();
                        timePickerDialog.show();
                    }
                },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        // Launch Time Picker Dialog


        timePickerDialog = new TimePickerDialog(this,
                (new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        String hourString;
                        if (hourOfDay < 10)
                            hourString = "0" + hourOfDay;
                        else
                            hourString = "" +hourOfDay;

                        String minuteSting;
                        if (minute < 10)
                            minuteSting = "0" + minute;
                        else
                            minuteSting = "" +minute;
                        date = date + (" "+ hourString + ":" + minuteSting);
                        txtTanggalService.setText(date);
                    }
                }), mHour, mMinute, true);
    }

    private Boolean fieldCheck(){
        Boolean fieldCheck = true;

        if(motor.getIdTipeMotor().isEmpty()){
            showToast("Anda Belum Memilih Tipe Kendaraan");
            fieldCheck = false;
        }
        if(TextUtils.isEmpty(txtPlatNomor.getText())){
            txtPlatNomor.setError("Insert this Field!");
            fieldCheck = false;
        }
        if(TextUtils.isEmpty(txtKeluhan.getText())){
            txtKeluhan.setError("Insert this Field!");
            fieldCheck = false;
        }
        if(TextUtils.isEmpty(txtTanggalService.getText())){
            txtTanggalService.setError("Insert this Field!");
            fieldCheck = false;
        }

        return fieldCheck;
    }
}
