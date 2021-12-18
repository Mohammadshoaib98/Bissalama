package com.example.regester.activites;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.regester.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class ActivityAddClerk extends AppCompatActivity {
    private static final String PATH_CLERKS_AGENCIES = "clerksCodes";
    private static final String PATH_AGENCY_NAME = "agencyName";
    FirebaseDatabase db_root;
    DatabaseReference ref_clerk_num;
    private EditText m_et_clerk_code;
    private Button m_btn_code;
    Spinner m_sp_num_clerk_code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_clerk);

        Intent int1 = getIntent();
        String agencyName = int1.getStringExtra("AgencyName");
        String agencyNumber = int1.getStringExtra("AgencyNumber");
        String agencyID = agencyName + agencyNumber;
        System.out.println("Code" + agencyName);
        m_et_clerk_code = findViewById(R.id.et_clerk_code);
        m_btn_code = findViewById(R.id.btn_add_code);
        m_sp_num_clerk_code = findViewById(R.id.sp_num_clerk_code);
        m_btn_code.setOnClickListener(v -> {
            String clerk_num = String.valueOf(m_sp_num_clerk_code.getSelectedItem());
            String code = String.valueOf(m_et_clerk_code.getText());
            db_root = FirebaseDatabase.getInstance();
            ref_clerk_num = db_root.getReference(PATH_CLERKS_AGENCIES).child(agencyName).child(clerk_num);
            ref_clerk_num.child("code").setValue(code);
            ref_clerk_num.child("agencyID").setValue(agencyID);
            Toast.makeText(ActivityAddClerk.this, "Add Code Successfully", Toast.LENGTH_SHORT).show();
            m_et_clerk_code.setText("");
            Intent intent = new Intent(ActivityAddClerk.this, ActivityMainAgency.class)
                    .putExtra("AgencyName",agencyName)
                    .putExtra("AgencyNumber",agencyNumber);
            startActivity(intent);
            finish();

        });
    }
}