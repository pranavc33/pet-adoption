package com.example.furrishta;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

public class Register extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private TextInputEditText emailInput, passwordInput;
    private Button registerButton, loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();
        TextInputLayout emailLayout = findViewById(R.id.registeremail);
        TextInputLayout passwordLayout = findViewById(R.id.registerpassword);
        emailInput = (TextInputEditText) emailLayout.getEditText();
        passwordInput = (TextInputEditText) passwordLayout.getEditText();

        registerButton = findViewById(R.id.btn_register);
        loginButton = findViewById(R.id.btn_login);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Register.this, Login.class));
            }
        });
    }

    private void registerUser() {
        String email = emailInput.getText().toString().trim();
        String password = passwordInput.getText().toString().trim();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        startActivity(new Intent(Register.this, MainActivity.class));
                        finish();
                    } else {
                        // Handle failed registration (e.g., show a message to the user)
                    }
                });
    }
}
