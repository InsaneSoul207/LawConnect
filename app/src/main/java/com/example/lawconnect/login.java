package com.example.lawconnect;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        EditText emailInput = findViewById(R.id.email_input);
        EditText passwordInput = findViewById(R.id.password_input);
        Button loginButton = findViewById(R.id.login_button);
        TextView registerLink = findViewById(R.id.register_link);

        loginButton.setOnClickListener(v -> {
            String email = emailInput.getText().toString();
            String password = passwordInput.getText().toString();
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            startActivity(new Intent(login.this, MainActivity.class));
                            finish();
                        } else {
                            Toast.makeText(login.this, "Login failed", Toast.LENGTH_SHORT).show();
                        }
                    });
        });

        registerLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(login.this,registeration.class);
                startActivity(intent);
                finish();
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public class registeration extends AppCompatActivity {

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            EdgeToEdge.enable(this);
            setContentView(R.layout.activity_registeration);
            FirebaseAuth mAuth = FirebaseAuth.getInstance();
            FirebaseDatabase database = FirebaseDatabase.getInstance();

            EditText emailInput = findViewById(R.id.email_input);
            EditText passwordInput = findViewById(R.id.password_input);
            RadioGroup roleGroup = findViewById(R.id.user_type_group);
            EditText nameInput = findViewById(R.id.name_input);
            EditText expertiseInput = findViewById(R.id.expertise_input);
            EditText priceInput = findViewById(R.id.price_input);
            Button registerButton = findViewById(R.id.register_button);
            TextView LoginLink = findViewById(R.id.loginlink);

            roleGroup.setOnCheckedChangeListener((group, checkedId) -> {
                if (checkedId == R.id.lawyer_radio) {
                    nameInput.setVisibility(View.VISIBLE);
                    expertiseInput.setVisibility(View.VISIBLE);
                    priceInput.setVisibility(View.VISIBLE);
                } else {
                    nameInput.setVisibility(View.GONE);
                    expertiseInput.setVisibility(View.GONE);
                    priceInput.setVisibility(View.GONE);
                }
            });

            registerButton.setOnClickListener(v -> {
                String email = emailInput.getText().toString();
                String password = passwordInput.getText().toString();
                boolean isLawyer = roleGroup.getCheckedRadioButtonId() == R.id.lawyer_radio;

                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                String uid = mAuth.getCurrentUser().getUid();
                                if (isLawyer) {
                                    Lawyer lawyer = new Lawyer(
                                            nameInput.getText().toString(),
                                            expertiseInput.getText().toString(),
                                            Integer.parseInt(priceInput.getText().toString())
                                    );
                                    database.getReference("lawyers").child(uid).setValue(lawyer);
                                }
                                database.getReference("users").child(uid).child("role").setValue(isLawyer ? "lawyer" : "client");
                                Toast.makeText(registeration.this, "Registration successful", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(registeration.this, MainActivity.class));
                                finish();
                            } else {
                                Toast.makeText(registeration.this, "Registration failed", Toast.LENGTH_SHORT).show();
                            }
                        });
            });
            LoginLink.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(registeration.this,login.class);
                    startActivity(intent);
                    finish();
                }
            });

            ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
                Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
                return insets;
            });
        }
    }
}