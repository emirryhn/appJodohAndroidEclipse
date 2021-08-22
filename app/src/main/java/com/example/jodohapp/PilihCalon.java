package com.example.jodohapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.daprlabs.cardstack.SwipeDeck;
import com.example.jodohapp.adapter.JodohPilihAdapter;
import com.example.jodohapp.entity.UserModel;
import com.example.jodohapp.service.ApiClient;
import com.example.jodohapp.service.UserInterface;
import com.example.jodohapp.utility.JwtUtil;
import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PilihCalon extends AppCompatActivity {

    private SwipeDeck swipeDeck;
    UserModel userGender;
    UserInterface userInterface;
    String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pilih_calon);

        swipeDeck = findViewById(R.id.swipe_deck);
        token = "Bearer "+getIntent().getStringExtra("token");

        userInterface = ApiClient.getRetrofit().create(UserInterface.class);

        try{
            Gson gson = new Gson();
            userGender = gson.fromJson(JwtUtil.getBodyDecode(getIntent().getStringExtra("token")),UserModel.class);
//            JwtUtil.getBodyDecode(getIntent().getStringExtra("token"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        if(userGender.getGender().equalsIgnoreCase("MAlE")){
            callFemaleList();
            Toast.makeText(PilihCalon.this,"halaman male",Toast.LENGTH_LONG).show();
        }else{
            callMaleList();
        }

        swipeActivity();

    }

    private void callMaleList() {

        Call<ArrayList<UserModel>> call = userInterface.getUserByGender(token,"MALE");
        call.enqueue(new Callback<ArrayList<UserModel>>() {
            @Override
            public void onResponse(Call<ArrayList<UserModel>> call, Response<ArrayList<UserModel>> response) {
                JodohPilihAdapter jodohPilihAdapter = new JodohPilihAdapter(response.body(), PilihCalon.this);
                swipeDeck.setAdapter(jodohPilihAdapter);
            }

            @Override
            public void onFailure(Call<ArrayList<UserModel>> call, Throwable t) {
                System.out.println("===============================");
                System.out.println(t);
            }
        });

    }

    private void callFemaleList() {
        Call<ArrayList<UserModel>> call = userInterface.getUserByGender(token,"FEMALE");
        call.enqueue(new Callback<ArrayList<UserModel>>() {
            @Override
            public void onResponse(Call<ArrayList<UserModel>> call, Response<ArrayList<UserModel>> response) {
                JodohPilihAdapter jodohPilihAdapter = new JodohPilihAdapter(response.body(), PilihCalon.this);
                swipeDeck.setAdapter(jodohPilihAdapter);
            }

            @Override
            public void onFailure(Call<ArrayList<UserModel>> call, Throwable t) {
                System.out.println("===============================");
                System.out.println(t);
            }
        });
    }

    private void swipeActivity(){
        swipeDeck.setEventCallback(new SwipeDeck.SwipeEventCallback() {
            @Override
            public void cardSwipedLeft(int position) {
                Toast.makeText(PilihCalon.this, "Card Swiped Left", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void cardSwipedRight(int position) {
                Toast.makeText(PilihCalon.this, "Card Swiped Right", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void cardsDepleted() {
                Toast.makeText(PilihCalon.this, "No more choices", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void cardActionDown() {

            }

            @Override
            public void cardActionUp() {

            }
        });
    }
}