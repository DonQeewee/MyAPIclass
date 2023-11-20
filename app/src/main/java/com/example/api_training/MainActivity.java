package com.example.api_training;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.api_training.databinding.ActivityMainBinding;

import org.json.JSONObject;

import Validators.EmailValidator;
import Validators.PwordValidator;

public class MainActivity extends AppCompatActivity {

    String url = "https://ayomideandroidtrainingapi-production.up.railway.app/api/v1/admin/login";

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());

        binding.firstTimer2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Registration.class));
            }
        });

        binding.resetPw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), PwReset.class));
            }
        });

        binding.login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = binding.email.getText().toString().trim();
                String pword = binding.pword.getText().toString().trim();

                if (email.isEmpty()||pword.isEmpty()){
                    binding.rideon.setText("All fields are required");
                    binding.rideon.setVisibility(View.VISIBLE);
                }else{
                    login();
                }
            }
        });
    }
    private void login(){

        final String email = binding.email.getText().toString().trim();
        final String pword = binding.pword.getText().toString().trim();
        String welcome = "Welcome, " + email;

        if (!EmailValidator.isEmailValid(email)){
            binding.rideon.setText("Invalid Email");
            binding.rideon.setVisibility(View.VISIBLE);
            return;
        }
        if (!PwordValidator.isPwordValid(pword)) {
            binding.rideon.setText("Invalid password. Please check the requirements.");
            binding.rideon.setVisibility(View.VISIBLE);
            return;
        }

        RequestQueue q = Volley.newRequestQueue(this);

        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("email", email);
            jsonObject.put("password", pword);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                Intent intent = new Intent(getApplicationContext(), Dashboard.class);
                binding.rideon.setText("LOGIN SUCCESSFUL");
                binding.rideon.setVisibility(View.VISIBLE);
                intent.putExtra("welcome", welcome);
                startActivity(intent);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.i("Error", volleyError.toString());
                binding.rideon.setText("Login unsuccessful, try again.");
                binding.rideon.setVisibility(View.VISIBLE);
            }
        });
        q.add(jsonObjectRequest);
    }

}