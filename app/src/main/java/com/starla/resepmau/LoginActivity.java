package com.starla.resepmau;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.starla.resepmau.converter.WrappedResponse;
import com.starla.resepmau.model.User;
import com.starla.resepmau.service.UserService;
import com.starla.resepmau.util.ApiUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private UserService userService = ApiUtil.getUserService();
    private TextView goToRegister;
    private EditText etEmail, etPass;
    private Button btnLogin;
    private SharedPreferences settings;
    private User mUser = new User();
    private static final String TAG = "LoginActivity";
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().hide();
        initComp();
        goToRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });
        doLogin();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(!isNotLoggedIn()){
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }
    }

    private void initComp(){
        goToRegister = findViewById(R.id.goto_register);
        etEmail = findViewById(R.id.et_email);
        etPass = findViewById(R.id.et_password);
        btnLogin = findViewById(R.id.btnLogin);
        mProgressBar = findViewById(R.id.loading);
        settings = getSharedPreferences("TOKEN", MODE_PRIVATE);
    }

    private void doLogin(){
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String e = etEmail.getText().toString().trim();
                String p = etPass.getText().toString().trim();
                if (!e.isEmpty() && !p.isEmpty()) {
                    mProgressBar.setVisibility(View.VISIBLE);
                    btnLogin.setEnabled(false);
                    Call<WrappedResponse<User>> user = userService.login(e,p);
                    user.enqueue(new Callback<WrappedResponse<User>>() {
                        @Override
                        public void onResponse(Call<WrappedResponse<User>> call, Response<WrappedResponse<User>> response) {
                            if(response.isSuccessful()){
                                WrappedResponse<User> body = response.body();
                                if(body.getStatus().equals("1")){
                                    mUser = body.getData();
                                    if(mUser != null){
                                        System.out.println(mUser.getApi_token());
                                        setLoggedIn(mUser.getApi_token());
                                    }else{
                                        Toast.makeText(LoginActivity.this, "Response success with error", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }else{
                                Toast.makeText(LoginActivity.this, "Login failed", Toast.LENGTH_SHORT).show();
                            }
                            mProgressBar.setVisibility(View.INVISIBLE);
                            btnLogin.setEnabled(true);
                        }

                        @Override
                        public void onFailure(Call<WrappedResponse<User>> call, Throwable t) {
                            Toast.makeText(LoginActivity.this, "Cannot request to server. Please check your connection", Toast.LENGTH_SHORT).show();
                            System.err.println(t.getMessage());
                            mProgressBar.setVisibility(View.INVISIBLE);
                            btnLogin.setEnabled(true);
                        }
                    });
                }
            }
        });
        }

    private boolean isNotLoggedIn () {
        String token = settings.getString("TOKEN", "UNDEFINED");
        return token == null || token.equals("UNDEFINED");
    }

    private void setLoggedIn (String token){
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("TOKEN", token);
        editor.commit();
        startActivity(new Intent(LoginActivity.this, MainActivity.class));
        finish();
    }
}
