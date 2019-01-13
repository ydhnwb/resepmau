package com.starla.resepmau;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.starla.resepmau.converter.WrappedResponse;
import com.starla.resepmau.model.User;
import com.starla.resepmau.service.UserService;
import com.starla.resepmau.util.ApiUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    private EditText etEmail, etName, etPass;
    private Button btnRegister;
    private UserService userService = ApiUtil.getUserService();
    private SharedPreferences settings;
    private User mUser = new User();
    private static final String TAG = "RegisterActivity";
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().hide();
        initComp();
        doRegister();
    }

    private void initComp(){
        etEmail = findViewById(R.id.et_email);
        etPass = findViewById(R.id.et_password);
        etName = findViewById(R.id.et_name);
        btnRegister = findViewById(R.id.btnRegister);
        mProgressBar = findViewById(R.id.loading);
        settings = getSharedPreferences("TOKEN", MODE_PRIVATE);
    }

    private void doRegister(){
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = etName.getText().toString().trim();
                String email = etEmail.getText().toString().trim();
                String pass = etPass.getText().toString().trim();
                if(!name.isEmpty() && !email.isEmpty() && !pass.isEmpty() && pass.length() > 8 && name.length() > 8){
                    mProgressBar.setVisibility(View.VISIBLE);
                    btnRegister.setEnabled(false);
                    Call<WrappedResponse<User>> user_ = userService.register(name, email, pass);
                    user_.enqueue(new Callback<WrappedResponse<User>>() {
                        @Override
                        public void onResponse(Call<WrappedResponse<User>> call, Response<WrappedResponse<User>> response) {
                            if(response.isSuccessful()){
                                WrappedResponse<User> body = response.body();
                                if(body.getStatus().equals("1")){
                                    mUser = body.getData();
                                    if(mUser != null){
                                        Log.d(TAG, mUser.getApi_token());
                                        setLoggedIn(mUser.getApi_token());
                                    }else{
                                        Toast.makeText(RegisterActivity.this, "Response success with error", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }else{
                                Toast.makeText(RegisterActivity.this, "Register failed", Toast.LENGTH_SHORT).show();
                            }
                            mProgressBar.setVisibility(View.INVISIBLE);
                            btnRegister.setEnabled(true);
                        }
                        @Override
                        public void onFailure(Call<WrappedResponse<User>> call, Throwable t) {
                            Log.d(TAG, "ydhnwb "+t.getMessage());
                            mProgressBar.setVisibility(View.INVISIBLE);
                            btnRegister.setEnabled(true);
                            Toast.makeText(RegisterActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                        }
                    });
                }else{
                    Toast.makeText(RegisterActivity.this, "Please fill all forms. Minimum character is 8 for all form", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void setLoggedIn (String token){
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("TOKEN", token);
        editor.commit();
        finish();
    }
}
