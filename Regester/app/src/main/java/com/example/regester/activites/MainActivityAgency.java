package com.example.regester.activites;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.regester.R;

public class MainActivityAgency extends AppCompatActivity {
    TextView m_tv_helloAgency;
    Button m_btn_add_clerk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_agency);

        m_tv_helloAgency = findViewById(R.id.tv_hello_agency);
        m_btn_add_clerk = findViewById(R.id.btn_add_clerk);
        Intent i = getIntent();
        String s1 = i.getStringExtra("AgencyNumber");
        String s2 = i.getStringExtra("AgencyName");

        m_tv_helloAgency.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivityAgency.this, ActivityAddJourney.class)
                    .putExtra("AgencyNumber", s1)
                    .putExtra("AgencyName", s2);

            startActivity(intent);
        });
        m_btn_add_clerk.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivityAgency.this, ActivityAddCode.class)
                    .putExtra("AgencyNumber", s1)
                    .putExtra("AgencyName", s2);
            startActivity(intent);

        });
    }
}
