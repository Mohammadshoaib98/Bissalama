package com.example.regester.activites;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.regester.R;

public class MainActivityPassenger extends AppCompatActivity {
    TextView m_tv_helloPassenger;
    Button m_btn_myJourneys;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_passenger);
        m_tv_helloPassenger = findViewById(R.id.tv_hello_passenger);
        m_btn_myJourneys=findViewById(R.id.btn_my_journeys);


        m_tv_helloPassenger.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivityPassenger.this, ActivityJourneysSearch.class);
            startActivity(intent);
        });
        m_btn_myJourneys.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivityPassenger.this,ActivityPassengerJourneysDisplay.class);
                startActivity(intent);
            }
        });

    }


}