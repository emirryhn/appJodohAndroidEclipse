package com.example.jodohapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jodohapp.entity.UserModel;
import com.example.jodohapp.service.ApiClient;
import com.example.jodohapp.service.UserInterface;
import com.example.jodohapp.utility.JwtUtil;
import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    EditText editUsername, editPassword;
    Button btnLogin;
    TextView textToRegis;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editUsername = findViewById(R.id.edit_username);
        editPassword = findViewById(R.id.edit_password);
        btnLogin = findViewById(R.id.btn_login);
        textToRegis = findViewById(R.id.text_to_regsiter);

        textToRegis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), Register.class);
                startActivity(i);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                UserModel user = new UserModel();
                user.setUsername(editUsername.getText().toString());
                user.setPassword(editPassword.getText().toString());
                UserInterface userInterface = ApiClient.getRetrofit().create(UserInterface.class);
                Call<String> call = userInterface.userLogin(user);
                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        System.out.println("suuuuuuuuuuuuuuuuuuuuk");
                        Intent i = new Intent(getApplicationContext(), Dashboard.class);
                        i.putExtra("token", response.body());
                        i.putExtra("name", editUsername.getText().toString());
                        Toast.makeText(MainActivity.this,"Login Success", Toast.LENGTH_SHORT).show();
                        startActivity(i);
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Toast.makeText(MainActivity.this, "Login problem!", Toast.LENGTH_SHORT).show();
                        System.out.println("========================");
                    }
                });
            }
        });
    }
}