package com.example.regester.clerk;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.regester.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ActivityMainClerk extends AppCompatActivity {
    private static final String TAG = "tag";
    TextView m_tv_hello_clerk;
    FirebaseDatabase db_root;
    DatabaseReference ref_agency_id;

    private static final String PATH_CLERKS_AGENCIES = "clerksCodes";
    String agencyID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_clerk);
        m_tv_hello_clerk = findViewById(R.id.tv_hello_clerk);
        db_root = FirebaseDatabase.getInstance();
        Intent a = getIntent();
        String agencyName = a.getStringExtra("AgencyName");
        String clerkNum = a.getStringExtra("Clerk_Num");
        Log.d(TAG, "AgencyNAME" + agencyName);
        Log.d(TAG, "clerkNum" + clerkNum);
        m_tv_hello_clerk.setOnClickListener(v -> {
            ref_agency_id = db_root.getReference(PATH_CLERKS_AGENCIES).child(agencyName).child(clerkNum).child("agencyID");
            Log.d(TAG, "agencyidmain" + ref_agency_id);
            ref_agency_id.addListenerForSingleValueEvent(new ValueEventListener() {
                                                             @Override
                                                             public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                                 agencyID = snapshot.getValue().toString();
                                                                 Log.d(TAG, "agencyidmain" + agencyID);
                                                                 Intent intent = new Intent(ActivityMainClerk.this, ActivityClerkJourneysDisplay.class)
                                                                         .putExtra("agencyID", agencyID);
                                                                 startActivity(intent);
                                                             }

                                                             @Override
                                                             public void onCancelled(@NonNull DatabaseError error) {

                                                             }
                                                         }
            );


        });
    }
}