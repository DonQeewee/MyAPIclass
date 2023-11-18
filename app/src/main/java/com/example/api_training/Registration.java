package com.example.api_training;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.api_training.databinding.ActivityRegistrationBinding;

import org.json.JSONException;
import org.json.JSONObject;

import Validators.EmailValidator;
import Validators.PwordValidator;

public class Registration extends AppCompatActivity {

    String regurl = "https://ayomideandroidtrainingapi-production.up.railway.app/api/v1/admin";

    ActivityRegistrationBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegistrationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());



            binding.signup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    final String fname = binding.fname.getText().toString().trim();
                    final String lname = binding.lname.getText().toString().trim();
                    final String email = binding.email.getText().toString().trim();
                    final String pword = binding.pword.getText().toString().trim();

                    if (fname.isEmpty() || lname.isEmpty() || email.isEmpty() || pword.isEmpty()) {
                        binding.thanks.setText("All fields are required");
                        binding.thanks.setVisibility(View.VISIBLE);
                    } else {
                        register();
                    }
                }
            });
            
        }

        private void register(){

        final String fname = binding.fname.getText().toString().trim();
        final String lname = binding.lname.getText().toString().trim();
        final String email = binding.email.getText().toString().trim();
        final String pword = binding.pword.getText().toString().trim();

            if (!EmailValidator.isEmailValid(email)){
                binding.thanks.setText("Invalid Email");
                binding.thanks.setVisibility(View.VISIBLE);
                return;
            }
           if (!PwordValidator.isPwordValid(pword)) {
                binding.thanks.setText("Invalid password. Please check the requirements.");
                binding.thanks.setVisibility(View.VISIBLE);
                return;
            }

            RequestQueue q = Volley.newRequestQueue(this);

        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("firstName", fname);
            jsonObject.put("lastName", lname);
            jsonObject.put("email", email);
            jsonObject.put("role", "ADMIN");
            jsonObject.put("password", pword);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }




            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, regurl, jsonObject, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject jsonObject) {
                    binding.thanks.setText("REGISTRATION SUCCESSFUL.");
                    binding.thanks.setVisibility(View.VISIBLE);
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    Log.i("Error", volleyError.toString());
                    binding.thanks.setText("Registration unsuccessful, try again.");
                    binding.thanks.setVisibility(View.VISIBLE);
                }
            });
            q.add(jsonObjectRequest);
        }

    }


