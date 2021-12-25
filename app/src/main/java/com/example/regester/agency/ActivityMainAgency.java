package com.example.regester.agency;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.regester.R;
import com.example.regester.auth.ActivitySignIn;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class ActivityMainAgency extends AppCompatActivity implements View.OnClickListener {
    private TextView m_tv_helloAgency, m_txt_name;
    private Button m_btn_add_clerk, m_btn_remove_clerk, m_btn_sign_out_agency, m_btn_add_journeys, m_btn_agency_journeys;
    private String agency_number, agency_name, agencyID;
    private FirebaseAuth auth;
    private FirebaseDatabase db_root;
    private Bundle agencyInfo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_agency);
        initializeViews();
        initialize_DB_Refs_and_Auth();
        setUpAgencyInfo();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add_journeys:

                startActivity(new Intent(ActivityMainAgency.this, ActivityAddJourney.class).putExtras(agencyInfo));

                break;
            case R.id.btn_add_clerk:

                startActivity(new Intent(ActivityMainAgency.this, ActivityAddClerk.class).putExtras(agencyInfo));

                break;
            case R.id.btn_remove_clerk:

                startActivity(new Intent(ActivityMainAgency.this, ActivityDeleteClerk.class).putExtras(agencyInfo));
                break;
            case R.id.btn_sign_out_agency:
                auth.signOut();
                startActivity(new Intent(ActivityMainAgency.this, ActivitySignIn.class).putExtras(agencyInfo));
                finish();
                break;
            case R.id.btn_agency_journeys:

                startActivity(new Intent(ActivityMainAgency.this, ActivityAgencyJourneysDisplay.class).putExtras(agencyInfo));

        }

    }


    private void initializeViews() {
        m_txt_name = findViewById(R.id.txt_name);
        m_txt_name.setText(agency_name);

        m_tv_helloAgency = findViewById(R.id.tv_agency);
        m_tv_helloAgency.setOnClickListener(this);

        m_btn_add_journeys = findViewById(R.id.btn_add_journeys);
        m_btn_add_journeys.setOnClickListener(this);

        m_btn_add_clerk = findViewById(R.id.btn_add_clerk);
        m_btn_add_clerk.setOnClickListener(this);

        m_btn_remove_clerk = findViewById(R.id.btn_remove_clerk);
        m_btn_remove_clerk.setOnClickListener(this);

        m_btn_sign_out_agency = findViewById(R.id.btn_sign_out_agency);
        m_btn_sign_out_agency.setOnClickListener(this);

        m_btn_agency_journeys = findViewById(R.id.btn_agency_journeys);
        m_btn_agency_journeys.setOnClickListener(this);

    }

    private void initialize_DB_Refs_and_Auth() {
        auth = FirebaseAuth.getInstance();
        db_root = FirebaseDatabase.getInstance();


    }

    private void setUpAgencyInfo() {
        Intent intent = getIntent();
        agency_number = intent.getStringExtra("AgencyNumber");
        agency_name = intent.getStringExtra("AgencyName");
        agencyID = agency_name + agency_number;
         agencyInfo = new Bundle();
        agencyInfo.putString("AgencyNumber", agency_number);
        agencyInfo.putString("AgencyName", agency_name);
        agencyInfo.putString("AgencyID", agency_name + agency_number);

    }


}
