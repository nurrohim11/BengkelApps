package com.izzaweb.bengkel.Ui;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.izzaweb.bengkel.Base.BaseActivity;
import com.izzaweb.bengkel.Models.Login.RequestResponse;
import com.izzaweb.bengkel.Models.User.User;
import com.izzaweb.bengkel.Networking.APIClient;
import com.izzaweb.bengkel.Networking.APIInterface;
import com.izzaweb.bengkel.R;
import com.izzaweb.bengkel.SessionManager.UserSession;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends BaseActivity {
    @BindView(R.id.txtNama)TextInputEditText txtNama;
    @BindView(R.id.txtAlamat)TextInputEditText txtAlamat;
    @BindView(R.id.txtNoTelp)TextInputEditText txtNoTelp;
    @BindView(R.id.txtEmail)TextInputEditText txtEmail;
    @BindView(R.id.txtpass)TextInputEditText txtpass;
    private ProgressDialog mRegProgres;
    UserSession session;
    Call<RequestResponse> call;

    private final String TAG = "ProfilActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bind(R.layout.activity_profile);
        session = new UserSession(ProfileActivity.this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("My Profile");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        mRegProgres = new ProgressDialog(this);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(fieldCheck() == true){
                    mRegProgres.setTitle("Getting Data");
                    mRegProgres.setMessage("Please Wait...");
                    mRegProgres.setCanceledOnTouchOutside(false);
                    mRegProgres.show();
                    String id_user = String.valueOf(session.getUserID());
                    String nama = txtNama.getText().toString().trim();
                    String alamat = txtAlamat.getText().toString().trim();
                    String telp = txtNoTelp.getText().toString().trim();
                    String email = txtEmail.getText().toString().trim();
                    String password = txtpass.getText().toString().trim();
                    APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
                    call = apiInterface.editUser(new User(id_user,nama,email,alamat,telp,password));
                    call.enqueue(new Callback<RequestResponse>() {
                        @Override
                        public void onResponse(Call<RequestResponse> call, Response<RequestResponse> response) {
                            Log.d(TAG, "onResponse: " + response.body().toString());
                            if(response.body().getSuccess() == true){
                                Toast.makeText(ProfileActivity.this, response.body().getInfo(), Toast.LENGTH_SHORT).show();
                                mRegProgres.dismiss();
                                finish();
                            }else{
                                mRegProgres.dismiss();
                                Toast.makeText(ProfileActivity.this, response.body().getInfo(), Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<RequestResponse> call, Throwable t) {
                            mRegProgres.dismiss();
                        }
                    });
                }
            }
        });

        getInitData();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (call != null) call.cancel();
    }
    private Boolean fieldCheck(){
        Boolean fieldCheck = true;

        if(TextUtils.isEmpty(txtNama.getText())){
            txtNama.setError("Insert this Field!");
            fieldCheck = false;
        }
        if(TextUtils.isEmpty(txtAlamat.getText())){
            txtAlamat.setError("Insert this Field!");
            fieldCheck = false;
        }
        if(TextUtils.isEmpty(txtNoTelp.getText())){
            txtNoTelp.setError("Insert this Field!");
            fieldCheck = false;
        }
        if(TextUtils.isEmpty(txtEmail.getText())){
            txtEmail.setError("Insert this Field!");
            fieldCheck = false;
        }
        if(TextUtils.isEmpty(txtpass.getText())){
            txtpass.setError("Insert this Field!");
            fieldCheck = false;
        }

        return fieldCheck;
    }
    private void getInitData(){
        mRegProgres.setTitle("Getting Data");
        mRegProgres.setMessage("Please Wait...");
        mRegProgres.setCanceledOnTouchOutside(false);
        mRegProgres.show();

        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
        Call<User> call = apiInterface.getUserData(session.getUserID());
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                txtNama.setText(response.body().getNamaUser());
                txtAlamat.setText(response.body().getAlamat());
                txtNoTelp.setText(response.body().getNoTelp());
                txtEmail.setText(response.body().getEmail());
                mRegProgres.dismiss();
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });

    }

}
