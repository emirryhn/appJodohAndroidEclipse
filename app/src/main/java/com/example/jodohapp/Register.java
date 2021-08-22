package com.example.jodohapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.jodohapp.entity.UserModel;
import com.example.jodohapp.service.ApiClient;
import com.example.jodohapp.service.UserInterface;
import com.google.gson.Gson;

import java.io.File;

import in.mayanknagwanshi.imagepicker.ImageSelectActivity;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Register extends AppCompatActivity {
    EditText editUsername, editName, editPass, editAge, editPhone;
    Spinner spnItem;
    Button btnRegister, btnUplaod;
    private int requestCode = 1;
    String mediaPath;
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        editUsername = findViewById(R.id.edit_username_regis);
        editName = findViewById(R.id.edit_name_regis);
        editPass = findViewById(R.id.edit_password_regis);
        editAge = findViewById(R.id.edit_age_regis);
        editPhone = findViewById(R.id.edit_phone_regis);
        btnRegister = findViewById(R.id.btn_register);
        btnUplaod = findViewById(R.id.btn_upload);
        imageView = findViewById(R.id.imageView);
        spnItem = findViewById(R.id.spinner_regis);

        btnUplaod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gambar = new Intent(getApplicationContext(), ImageSelectActivity.class);
                gambar.putExtra(ImageSelectActivity.FLAG_COMPRESS, true);
                gambar.putExtra(ImageSelectActivity.FLAG_CAMERA, true);
                gambar.putExtra(ImageSelectActivity.FLAG_GALLERY, true);
                startActivityForResult(gambar, 1);
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            submit();
            finish();
            }
        });
    }

    private void submit() {
        UserModel user = new UserModel();
        user.setUsername(editUsername.getText().toString());
        user.setName(editName.getText().toString());
        user.setPassword(editPass.getText().toString());
        user.setPhone(editPhone.getText().toString());
        user.setAge(editAge.getText().toString());
        //spinner
        String itemVal =String.valueOf(spnItem.getSelectedItem());
        user.setGender(itemVal);

        Gson gson = new Gson();
        String json = gson.toJson(user);

        UserInterface userInterface = ApiClient.getRetrofit().create(UserInterface.class);

        File file = new File(mediaPath);
        RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), file);
        MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("file", file.getName(), requestBody);
        RequestBody data =RequestBody.create(MediaType.parse("text/plain"),json);
        Call<String> call = userInterface.userRegister(fileToUpload,data);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                System.out.println("=================");
                Toast.makeText(Register.this, response.body(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(Register.this, "register unsuccessful", Toast.LENGTH_LONG).show();
                System.out.println(t);
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (this.requestCode == requestCode && resultCode == RESULT_OK) {

            mediaPath = data.getStringExtra(ImageSelectActivity.RESULT_FILE_PATH);

            imageView.setImageBitmap(BitmapFactory.decodeFile(mediaPath));
        }
    }
}