package com.example.regester.activites;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import com.example.regester.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.Calendar;
import static com.example.regester.utils.CommonConstants.PATH_AGENCIES_JOURNEYS;
import static com.example.regester.utils.CommonConstants.PATH_JOURNEY_DATE;
import static com.example.regester.utils.CommonConstants.PATH_JOURNEY_TIME;


public class ActivityUpdate extends AppCompatActivity {
    Button  m_btn_update_time;
    TextView m_tv_update_date, m_tv_update_time;
        int year, month, day, hour, minute;
    FirebaseDatabase db_root;
    DatabaseReference journeys_ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        Intent i = getIntent();
        String journeyKey = i.getStringExtra("jourKey");
        String agencyId = i.getStringExtra("agencyId");

        journeys_ref = db_root.getReference(PATH_AGENCIES_JOURNEYS).child(agencyId).child(journeyKey);
        journeys_ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                m_tv_update_date.setText(String.valueOf(snapshot.child(PATH_JOURNEY_DATE).getValue()));
                m_tv_update_time.setText(String.valueOf(snapshot.child(PATH_JOURNEY_TIME).getValue()));

            

              /* for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                   if(dataSnapshot.child("src").equals("Aleppo")){
                       dataSnapshot.child("des");
                        }

               }*/
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        findViewById(R.id.btn_update_date).setOnClickListener(v -> {
            final Calendar calendar = Calendar.getInstance();
            year = calendar.get(Calendar.YEAR);
            month = calendar.get(Calendar.MONTH);
            day = calendar.get(Calendar.DAY_OF_MONTH);

            @SuppressLint("SetTextI18n") DatePickerDialog datePickerDialog = new DatePickerDialog(ActivityUpdate.this, (view, year, month, dayOfMonth) -> m_tv_update_date.setText(year + "/" + month + "/" + dayOfMonth), year, month, day);
            datePickerDialog.show();
        });

        findViewById(R.id.btn_update_time).setOnClickListener(v -> {
            final Calendar calendar = Calendar.getInstance();
            hour = calendar.get(Calendar.HOUR_OF_DAY);
            minute = calendar.get(Calendar.MINUTE);
            @SuppressLint("SetTextI18n") TimePickerDialog timePickerDialog = new TimePickerDialog(ActivityUpdate.this, (view, hourOfDay, minute) -> m_btn_update_time.setText(hourOfDay + ":" + minute), hour, minute, false);
            timePickerDialog.show();


        });

        findViewById(R.id.btn_cancel_update).setOnClickListener(v -> {
            Intent intent = new Intent(ActivityUpdate.this, ActivityAgencyJourneysDisplay.class);
            startActivity(intent);
        });


        findViewById(R.id.btn_save_update).setOnClickListener(v -> {


        });

    }


}