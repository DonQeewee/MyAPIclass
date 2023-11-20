package com.example.api_training;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.api_training.databinding.ActivityPwResetBinding;

import Validators.EmailValidator;
import Validators.PwordValidator;

public class PwReset extends AppCompatActivity {

    ActivityPwResetBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityPwResetBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());

        String email = binding.email.getText().toString().trim();
        String pw = binding.newPW.getText().toString().trim();
        String pw1 = binding.newPW1.getText().toString().trim();

        if (email.isEmpty()||pw.isEmpty()||pw1.isEmpty()){
            binding.thanks.setText("All fields are required");
            binding.thanks.setVisibility(View.VISIBLE);
            if (!pw.equals(pw1)){
                binding.thanks.setText("Both entries must align");
                binding.thanks.setVisibility(View.VISIBLE);
            }
        }else {
            resetPW();
        }

    }

    private void resetPW(){

        final String newPW = binding.newPW.getText().toString().trim();
        final String email = binding.email.getText().toString().trim();

        if (!PwordValidator.isPwordValid(newPW)) {
            binding.thanks.setText("Invalid password. Please check the requirements.");
            binding.thanks.setVisibility(View.VISIBLE);
            return;
        }
        if(!EmailValidator.isEmailValid(email)){
            binding.thanks.setText("Invalid password. Please check the requirements.");
            binding.thanks.setVisibility(View.VISIBLE);
            return;
        }
        binding.thanks.setText("Password reset successful");
        binding.thanks.setVisibility(View.VISIBLE);
        startActivity(new Intent(getApplicationContext(), MainActivity.class));

    }


}