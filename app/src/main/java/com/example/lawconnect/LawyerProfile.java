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
        MaterialButton chatButton = findViewById(R.id.chat_button);

        // Get data from intent
        String name = getIntent().getStringExtra("lawyer_name");
        String expertise = getIntent().getStringExtra("lawyer_expertise");
        int price = getIntent().getIntExtra("lawyer_price", 0);

        nameText.setText(name);
        expertiseText.setText("Expertise: " + expertise);
        priceText.setText("Price per Case: ₹" + price);

        // Fade-in animation
        View profileView = findViewById(android.R.id.content);
        profileView.setAlpha(0f);
        profileView.animate().alpha(1f).setDuration(500).start();

        // Chat button (mocked)
        chatButton.setOnClickListener(v -> Toast.makeText(this, "Chat feature coming soon!", Toast.LENGTH_SHORT).show());

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}