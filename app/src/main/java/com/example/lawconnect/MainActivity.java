package com.example.lawconnect;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView lawyerList;
    private LawyerAdapter adapter;
    private List<Lawyer> lawyers = new ArrayList<>();
    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        lawyerList = findViewById(R.id.lawyer_list);
        lawyerList.setLayoutManager(new LinearLayoutManager(this));
        adapter = new LawyerAdapter(lawyers, lawyer -> {
            Intent intent = new Intent(MainActivity.this, LawyerProfile.class);
            intent.putExtra("lawyer_name", lawyer.getName());
            intent.putExtra("lawyer_expertise", lawyer.getExpertise());
            intent.putExtra("lawyer_price", lawyer.getPrice());

            ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                    this, lawyerList.findViewHolderForAdapterPosition(lawyers.indexOf(lawyer)).itemView,
                    "lawyer_card_transition");
            startActivity(intent, options.toBundle());
        });
        lawyerList.setAdapter(adapter);

        FirebaseDatabase.getInstance().getReference("lawyers")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        lawyers.clear();
                        for (DataSnapshot data : snapshot.getChildren()) {
                            try {
                                Lawyer lawyer = data.getValue(Lawyer.class);
                                if (lawyer != null) {
                                    lawyers.add(lawyer);
                                }
                            } catch (Exception e) {
                                e.printStackTrace(); // Log any deserialization errors
                            }
                        }
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        // Handle database errors (e.g., permissions)
                        error.toException().printStackTrace();
                    }
                });

        FloatingActionButton chatbotButton = findViewById(R.id.chatbot_button);
        chatbotButton.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, ChatBot.class));
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}