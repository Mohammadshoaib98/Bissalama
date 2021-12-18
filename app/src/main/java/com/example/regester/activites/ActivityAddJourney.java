package com.example.regester.activites;

import static com.example.regester.utils.CommonConstants.PATH_AGENCY_NAME;
import static com.example.regester.utils.CommonConstants.PATH_DESTINATION_CITY;
import static com.example.regester.utils.CommonConstants.PATH_SOURCE_CITY;
import static com.example.regester.utils.CommonConstants.PATH_AGENCIES_JOURNEYS;
import static com.example.regester.utils.CommonConstants.PATH_JOURNEY_DATE;
import static com.example.regester.utils.CommonConstants.PATH_JOURNEY_TIME;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.regester.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;


public class ActivityAddJourney extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "Journey";
    public static final String PATH_RESULTS_ROUTE = "resultsRoute";
    private static final String PATH_AGENCY_ID = "agencyID";
    private static final String PATH_JOURNEY_ID = "journeyID";
    private static final String PATH_SEATS_JOURNEY = "seats";
    Spinner m_spinner_citySrc, m_spinner_cityDes;
    Button m_btn_addJourney;
    ImageButton m_btn_time, m_btn_date;
    TextView m_tv_date, m_tv_time, m_txt_agencyName;
    int year, month, day, hour, minute;
    String num, agencyName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_city);
        Intent i = getIntent();
        num = i.getStringExtra("AgencyNumber");
        agencyName = i.getStringExtra("AgencyName");
        initializeViews();
        m_btn_date.setOnClickListener(v -> {

            final Calendar calendar = Calendar.getInstance();
            year = calendar.get(Calendar.YEAR);
            month = calendar.get(Calendar.MONTH);
            day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(ActivityAddJourney.this, (view, year, month, dayOfMonth) -> m_tv_date.setText(year + "/" + month + "/" + dayOfMonth), year, month, day);
            datePickerDialog.show();
        });

        m_btn_time.setOnClickListener(v -> {
            final Calendar calendar = Calendar.getInstance();
            hour = calendar.get(Calendar.HOUR_OF_DAY);
            minute = calendar.get(Calendar.MINUTE);
            TimePickerDialog timePickerDialog = new TimePickerDialog(ActivityAddJourney.this, (view, hourOfDay, minute) -> m_tv_time.setText(hourOfDay + ":" + minute), hour, minute, false);
            timePickerDialog.show();

        });

    }

    private void initializeViews() {
        m_txt_agencyName = findViewById(R.id.txt_agency_name);
        m_txt_agencyName.setText(agencyName);
        m_spinner_citySrc = findViewById(R.id.spinner_city_src);
        m_spinner_cityDes = findViewById(R.id.spinner_city_des);
        m_btn_addJourney = findViewById(R.id.btn_add_journey);
        m_btn_addJourney.setOnClickListener(this);
        m_btn_time = findViewById(R.id.btn_time);
        m_btn_date = findViewById(R.id.btn_date);
        m_tv_date = findViewById(R.id.tv_date);
        m_tv_time = findViewById(R.id.tv_time);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_add_journey) {
            if (TextUtils.isEmpty(m_tv_date.getText().toString())) {
                Toast.makeText(ActivityAddJourney.this, "Please Enter Journey Date ", Toast.LENGTH_LONG).show();
            } else if (TextUtils.isEmpty(m_tv_time.getText().toString())) {
                Toast.makeText(ActivityAddJourney.this, "Please Enter Journey Time", Toast.LENGTH_LONG).show();
            } else {


                addJourney(String.valueOf(m_txt_agencyName.getText()),
                        String.valueOf(m_spinner_citySrc.getSelectedItem()),
                        String.valueOf(m_spinner_cityDes.getSelectedItem()),
                        String.valueOf(m_tv_date.getText()),
                        String.valueOf(m_tv_time.getText()),
                        num
                );


                String agencyId = m_txt_agencyName.getText() + num;
                Log.d(TAG, "Journey: " + m_txt_agencyName.getText() + " " +
                        m_spinner_citySrc.getSelectedItem() + " " +
                        m_spinner_cityDes.getSelectedItem() + " " +
                        m_tv_date.getText() + " " +
                        m_tv_time.getText());
                Intent intent = new Intent(this, ActivityAgencyJourneysDisplay.class).putExtra("agencyId", agencyId);
                startActivity(intent);
            }
        }
    }
    private void addJourney(String agencyName, String src, String des, String date, String time, String num) {
        String agencyId = agencyName + num;
        String srcDes = src + des;
        DatabaseReference db_root = FirebaseDatabase.getInstance().getReference();
        DatabaseReference ref_agency = db_root.child(PATH_AGENCIES_JOURNEYS).child(agencyId);
        DatabaseReference ref_journey = ref_agency.push();
        String journeyID = ref_journey.getKey();
        //DataReference `agencies journeys`
        ref_journey.child(PATH_JOURNEY_ID).setValue(journeyID);
        ref_journey.child(PATH_AGENCY_NAME).setValue(agencyName);
        ref_journey.child(PATH_SOURCE_CITY).setValue(src);
        ref_journey.child(PATH_DESTINATION_CITY).setValue(des);
        ref_journey.child(PATH_JOURNEY_DATE).setValue(date);
        ref_journey.child(PATH_JOURNEY_TIME).setValue(time);


        //DataReference resultsRouts
        DatabaseReference ref_srcDes = db_root.child(PATH_RESULTS_ROUTE).child(srcDes);
        DatabaseReference ref_route = ref_srcDes.child(journeyID);
        ref_route.child(PATH_JOURNEY_ID).setValue(journeyID);
        ref_route.child(PATH_AGENCY_ID).setValue(agencyId);
        ref_route.child(PATH_AGENCY_NAME).setValue(agencyName);
        ref_route.child(PATH_SOURCE_CITY).setValue(src);
        ref_route.child(PATH_DESTINATION_CITY).setValue(des);
        ref_route.child(PATH_JOURNEY_DATE).setValue(date);
        ref_route.child(PATH_JOURNEY_TIME).setValue(time);

        for (int i = 1; i <= 32; i++) {
            DatabaseReference ref_seats = ref_journey.child(PATH_SEATS_JOURNEY).push();
            ref_seats.child("seatNumber").setValue(i);
            ref_seats.child("seatState").setValue(0);
            ref_seats.child("passengerID").setValue(false);

        }
    }


}



