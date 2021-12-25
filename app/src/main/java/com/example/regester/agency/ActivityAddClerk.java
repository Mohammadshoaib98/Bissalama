package com.example.regester.agency;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.regester.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static com.example.regester.constants.CommonConstants.PATH_AGENCIES_JOURNEYS;
import static com.example.regester.constants.CommonConstants.PATH_CLERKS;
import static com.example.regester.constants.CommonConstants.PATH_CODES;
import static com.example.regester.constants.CommonConstants.PATH_USER_TYPE_AGENCY;


public class ActivityAddClerk extends AppCompatActivity {
    FirebaseDatabase db_root;
    DatabaseReference ref_agency_clerks;
    private EditText m_et_clerk_phone_number;
    private Button m_btn_add_clerk;
    private String agencyID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_clerk);

        initialize_DB_Refs();
        initializeViews();


    }

    private void initializeViews() {
        m_et_clerk_phone_number = findViewById(R.id.et_clerk_phone_number);
        m_btn_add_clerk = findViewById(R.id.btn_add_clerk);
        m_btn_add_clerk.setOnClickListener(v -> {
            addClerk();
        });
    }

    private void addClerk() {
        String clerk_phone_number = String.valueOf(m_et_clerk_phone_number.getText());

        ref_agency_clerks.child(clerk_phone_number).setValue(true);

        Toast.makeText(ActivityAddClerk.this, "Clerk Added Successfully", Toast.LENGTH_SHORT).show();
        m_et_clerk_phone_number.setText("");
    }

    private void initialize_DB_Refs() {
        agencyID = getIntent().getStringExtra("AgencyID");
        db_root = FirebaseDatabase.getInstance();
        ref_agency_clerks = db_root.getReference(PATH_AGENCIES_JOURNEYS)
                .child(agencyID).child(PATH_CLERKS);


    }
}