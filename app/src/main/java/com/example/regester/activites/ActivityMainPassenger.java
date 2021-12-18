package com.example.regester.activites;

import static com.example.regester.utils.CommonConstants.PATH_USERS;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.regester.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ActivityMainPassenger extends AppCompatActivity implements View.OnClickListener {
    TextView m_tv_helloPassenger, m_passenger_name;
    Button m_btn_my_journeys, m_btn_search_journey, m_btn_log_out;
    private FirebaseDatabase db_root;
    private FirebaseAuth auth;
    private DatabaseReference users;
    String passengerID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_passenger);
        initializeViews();
        initialize_db_auth();
        passengerID = String.valueOf(auth.getCurrentUser().getUid());
        DatabaseReference user_name = db_root.getReference(PATH_USERS).child("end-User").child(passengerID);
        user_name.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                m_passenger_name.setText(String.valueOf(snapshot.child("fullName").getValue()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

      /*  m_btn_search_journey.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivityPassenger.this, ActivityJourneysSearch.class);
            startActivity(intent);
        });
        m_btn_my_journeys.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivityPassenger.this, ActivityPassengerJourneysDisplay.class);
                startActivity(intent);
            }
        });*/

    }

    private void initializeViews() {
        m_passenger_name = findViewById(R.id.passenger_name);
        m_tv_helloPassenger = findViewById(R.id.tv_hello_passenger);
        m_btn_my_journeys = findViewById(R.id.btn_my_journeys);
        m_btn_my_journeys.setOnClickListener(this);
        m_btn_search_journey = findViewById(R.id.btn_search_journey);
        m_btn_search_journey.setOnClickListener(this);
        m_btn_log_out = findViewById(R.id.btn_log_out);
        m_btn_log_out.setOnClickListener(this);


    }

    private void initialize_db_auth() {
        auth = FirebaseAuth.getInstance();
        db_root = FirebaseDatabase.getInstance();
        users = db_root.getReference(PATH_USERS);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_search_journey:
                Intent intent = new Intent(ActivityMainPassenger.this, ActivityJourneysSearch.class);
                startActivity(intent);
                break;
            case R.id.btn_my_journeys:
                Intent intent1 = new Intent(ActivityMainPassenger.this, ActivityPassengerJourneysDisplay.class);
                startActivity(intent1);
                break;
            case R.id.btn_log_out:
                auth.signOut();
                Intent intent2 = new Intent(ActivityMainPassenger.this, ActivitySignIn.class);
                startActivity(intent2);
                finish();
                break;
        }
    }
}