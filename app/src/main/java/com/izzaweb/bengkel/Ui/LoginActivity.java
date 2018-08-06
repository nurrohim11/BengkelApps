package com.izzaweb.bengkel.Ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.izzaweb.bengkel.Base.BaseActivity;
import com.izzaweb.bengkel.MainActivity;
import com.izzaweb.bengkel.Models.Login.LoginRequest;
import com.izzaweb.bengkel.Models.Login.RequestResponse;
import com.izzaweb.bengkel.Networking.APIClient;
import com.izzaweb.bengkel.Networking.APIInterface;
import com.izzaweb.bengkel.R;
import com.izzaweb.bengkel.SessionManager.UserSession;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends BaseActivity {
    @BindView(R.id.txtLoginEmail)
    TextInputEditText txtLoginEmail;
    @BindView(R.id.txtLoginPassword)
    TextInputEditText txtLoginPassword;
    @BindView(R.id.txtCreateAcc)
    TextView txtCreateAcc;
    @BindView(R.id.btnLogin)
    Button btnLogin;
    private ProgressDialog mRegProgres;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bind(R.layout.activity_login);
        mRegProgres = new ProgressDialog(this);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRegProgres.setTitle("Getting Data");
                mRegProgres.setMessage("Please Wait...");
                mRegProgres.setCanceledOnTouchOutside(false);
                mRegProgres.show();
                String email = txtLoginEmail.getText().toString().trim();
                String pass = txtLoginPassword.getText().toString().trim();
                APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
                Call<RequestResponse> call = apiInterface.getLoginStatus(new LoginRequest(email,pass));
                call.enqueue(new Callback<RequestResponse>() {
                    @Override
                    public void onResponse(Call<RequestResponse> call, Response<RequestResponse> response) {
                        if(response.body().getSuccess() == true){
                            mRegProgres.dismiss();
                            UserSession userSession = new UserSession(LoginActivity.this);
                            userSession.setLoginSession(Integer.parseInt(response.body().getIdUser()));
                            Intent i = new Intent(LoginActivity.this, MainActivity.class);
                            // Closing all the Activities
                            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            // Add new Flag to start new Activity
                            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            // Staring Login Activity
                            startActivity(i);
                            finish();
                        }else{
                            mRegProgres.dismiss();
                            Toast.makeText(LoginActivity.this, response.body().getInfo(), Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onFailure(Call<RequestResponse> call, Throwable t) {
                        mRegProgres.dismiss();
                        Toast.makeText(LoginActivity.this, "Login Gagal : "+ t, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        txtCreateAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
            }
        });
    }
}
