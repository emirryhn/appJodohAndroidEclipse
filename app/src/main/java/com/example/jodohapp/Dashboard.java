package com.example.jodohapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.jodohapp.entity.UserModel;
import com.example.jodohapp.service.ApiClient;
import com.example.jodohapp.service.UserInterface;
import com.example.jodohapp.utility.JwtUtil;
import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;

public class Dashboard extends AppCompatActivity {

    TextView textName;
    Button btnPilihCalon, btnDataCalon, btnLogout;
    String token;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        textName = findViewById(R.id.text_name);
        btnDataCalon = findViewById(R.id.btn_data_calon);
        btnPilihCalon = findViewById(R.id.btn_pilih_calon);
        btnLogout = findViewById(R.id.btn_logout);
        token ="Bearer "+getIntent().getStringExtra("token");

        String nameLabel =("Welcome "+getIntent().getExtras().getString("name"));
        textName.setText(nameLabel);
        UserInterface userInterface = ApiClient.getRetrofit().create(UserInterface.class);

        dataCalon();
        pilihCalon();
        logout();
    }

    private void logout() {
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void pilihCalon() {
        btnPilihCalon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Dashboard.this, PilihCalon.class);
                i.putExtra("token", getIntent().getStringExtra("token"));
                startActivity(i);
            }
        });

    }

    private void dataCalon() {
    }
}