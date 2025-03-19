package com.example.lawconnect;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.button.MaterialButton;

public class LawyerProfile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_lawyer);
        TextView nameText = findViewById(R.id.profile_name);
        TextView expertiseText = findViewById(R.id.profile_expertise);
        TextView priceText = findViewById(R.id.profile_price);
        TextView phoneText = findViewById(R.id.profile_phone);
        TextView locationText = findViewById(R.id.profile_location);

        // Get data from intent
        String name = getIntent().getStringExtra("lawyer_name");
        String expertise = getIntent().getStringExtra("lawyer_expertise");
        int price = getIntent().getIntExtra("lawyer_price", 0);
        String phone = getIntent().getStringExtra("lawyer_phone");
        String location = getIntent().getStringExtra("lawyer_location");

        nameText.setText(name);
        expertiseText.setText("Expertise: " + expertise);
        priceText.setText("Price per Case: ₹" + price);
        phoneText.setText("Phone: " + phone);
        locationText.setText("Location: " + location);

        // Fade-in animation
        View profileView = findViewById(android.R.id.content);
        profileView.setAlpha(0f);
        profileView.animate().alpha(1f).setDuration(500).start();


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}