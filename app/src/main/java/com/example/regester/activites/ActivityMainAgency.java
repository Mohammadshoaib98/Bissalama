package com.example.regester.activites;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.regester.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class ActivityMainAgency extends AppCompatActivity implements View.OnClickListener {
    private TextView m_tv_helloAgency, m_txt_name;
    private Button m_btn_add_clerk, m_btn_remove_clerk, m_btn_sign_out_agency, m_btn_add_journeys, m_btn_agency_journeys;
    private String agency_number, agency_name, agency_ID;
    private FirebaseAuth auth;
    private FirebaseDatabase db_root;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_agency);
        initializeViews();
        initializeJourneyAndAgencyIDs();
        agency_ID = auth.getCurrentUser().getUid();
        m_txt_name.setText(agency_name);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add_journeys:
                Intent intent = new Intent(ActivityMainAgency.this, ActivityAddJourney.class)
                        .putExtra("AgencyNumber", agency_number)
                        .putExtra("AgencyName", agency_name);
                startActivity(intent);

                break;
            case R.id.btn_add_clerk:
                Intent intent1 = new Intent(ActivityMainAgency.this, ActivityAddClerk.class)
                        .putExtra("AgencyNumber", agency_number)
                        .putExtra("AgencyName", agency_name);
                startActivity(intent1);

                break;
            case R.id.btn_remove_clerk:
                Intent intent3 = new Intent(ActivityMainAgency.this, ActivityDeleteClerk.class)
                        .putExtra("AgencyNumber", agency_number)
                        .putExtra("AgencyName", agency_name);
                startActivity(intent3);
                break;
            case R.id.btn_sign_out_agency:
                auth.signOut();
                Intent intent2 = new Intent(ActivityMainAgency.this, ActivitySignIn.class);
                startActivity(intent2);
                finish();
                break;
            case R.id.btn_agency_journeys:
                String agencyId = agency_name + agency_number;
                Intent intent4 = new Intent(ActivityMainAgency.this, ActivityAgencyJourneysDisplay.class)
                        .putExtra("agencyId", agencyId);
                startActivity(intent4);

        }

    }

    private void initializeViews() {
        m_tv_helloAgency = findViewById(R.id.tv_agency);
        m_txt_name = findViewById(R.id.txt_name);
        m_btn_add_journeys = findViewById(R.id.btn_add_journeys);
        m_btn_add_journeys.setOnClickListener(this);
        m_tv_helloAgency.setOnClickListener(this);
        m_btn_add_clerk = findViewById(R.id.btn_add_clerk);
        m_btn_add_clerk.setOnClickListener(this);
        m_btn_remove_clerk = findViewById(R.id.btn_remove_clerk);
        m_btn_remove_clerk.setOnClickListener(this);
        m_btn_sign_out_agency = findViewById(R.id.btn_sign_out_agency);
        m_btn_agency_journeys = findViewById(R.id.btn_agency_journeys);
        m_btn_agency_journeys.setOnClickListener(this);
        m_btn_sign_out_agency.setOnClickListener(this);
        auth = FirebaseAuth.getInstance();
        db_root = FirebaseDatabase.getInstance();
    }

    private void initializeJourneyAndAgencyIDs() {
        Intent i = getIntent();
        agency_number = i.getStringExtra("AgencyNumber");
        agency_name = i.getStringExtra("AgencyName");
    }
}
