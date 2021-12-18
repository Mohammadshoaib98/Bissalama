package com.example.regester.activites;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.regester.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ActivityDeleteClerk extends AppCompatActivity {
    private static final String PATH_CLERKS_AGENCIES = "clerksCodes";
    Button m_btn_delete_clerk;
    Spinner m_sp_clerk_num_delete;
    FirebaseDatabase db_root;
    DatabaseReference ref_clerk_num;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_clerk);
        Intent int1 = getIntent();
        String agencyName = int1.getStringExtra("AgencyName");
        String agencyNumber = int1.getStringExtra("AgencyNumber");
        String agencyID=agencyName+agencyNumber;
        m_btn_delete_clerk =findViewById(R.id.btn_delete_clerk);
        m_sp_clerk_num_delete=findViewById(R.id.sp_clerk_num_delete);
        m_btn_delete_clerk.setOnClickListener(v -> {
            String clerk_num = String.valueOf(m_sp_clerk_num_delete.getSelectedItem());
            db_root = FirebaseDatabase.getInstance();
            ref_clerk_num = db_root.getReference(PATH_CLERKS_AGENCIES).child(agencyName).child(clerk_num);
            ref_clerk_num.child("code").setValue(null);
            ref_clerk_num.child("agencyID").setValue(null);


            Intent intent = new Intent(ActivityDeleteClerk.this, ActivityMainAgency.class)
                    .putExtra("AgencyName",agencyName)
                    .putExtra("AgencyNumber",agencyNumber);
            Toast.makeText(ActivityDeleteClerk.this, "Delete Clerk Successfully", Toast.LENGTH_SHORT).show();
            startActivity(intent);
        });
    }
}