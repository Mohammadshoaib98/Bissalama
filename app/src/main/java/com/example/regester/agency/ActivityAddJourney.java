package com.example.regester.agency;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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

import static com.example.regester.constants.CommonConstants.PATH_AGENCIES_JOURNEYS;
import static com.example.regester.constants.CommonConstants.PATH_AGENCY_ID;
import static com.example.regester.constants.CommonConstants.PATH_AGENCY_NAME;
import static com.example.regester.constants.CommonConstants.PATH_CITY_DESTINATION;
import static com.example.regester.constants.CommonConstants.PATH_CITY_SOURCE;
import static com.example.regester.constants.CommonConstants.PATH_JOURNEY_DATE;
import static com.example.regester.constants.CommonConstants.PATH_JOURNEY_ID;
import static com.example.regester.constants.CommonConstants.PATH_JOURNEY_SEATS;
import static com.example.regester.constants.CommonConstants.PATH_JOURNEY_TIME;
import static com.example.regester.constants.CommonConstants.PATH_RESULTS_ROUTES;
import static com.example.regester.constants.CommonConstants.PATH_SEAT_ID;
import static com.example.regester.constants.CommonConstants.PATH_SEAT_JOURNEY_ID;
import static com.example.regester.constants.CommonConstants.PATH_SEAT_NUMBER;
import static com.example.regester.constants.CommonConstants.PATH_SEAT_PASSENGER_ID;
import static com.example.regester.constants.CommonConstants.PATH_SEAT_STATE_CHECKED;
import static com.example.regester.constants.CommonConstants.PATH_SEAT_STATE_ENABLED;


public class ActivityAddJourney extends AppCompatActivity implements View.OnClickListener {
    Spinner m_spinner_citySrc, m_spinner_cityDes;
    Button m_btn_addJourney;
    ImageButton m_btn_time, m_btn_date;
    TextView m_tv_date, m_tv_time, m_txt_agencyName;
    int year, month, day, hour, minute;
    String agencyPhoneNumber, agencyName, agencyId, journeyID;
    String src, des, date, time;
    DatabaseReference db_root;
    DatabaseReference ref_journey;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_city);

        initialize_DB_Refs();
        setUpAgencyInfo();
        initializeViews();


    }

    private void initialize_DB_Refs() {
        db_root = FirebaseDatabase.getInstance().getReference();

    }

    private void setUpAgencyInfo() {
        Intent i = getIntent();
        agencyPhoneNumber = i.getStringExtra("AgencyNumber");
        agencyName = i.getStringExtra("AgencyName");
        agencyId = agencyName + agencyPhoneNumber;
    }

    private void initializeViews() {
        m_txt_agencyName = findViewById(R.id.txt_agency_name);
        m_txt_agencyName.setText(agencyName);
        m_spinner_citySrc = findViewById(R.id.spinner_city_src);
        m_spinner_cityDes = findViewById(R.id.spinner_city_des);

        m_btn_addJourney = findViewById(R.id.btn_add_journey);
        m_btn_addJourney.setOnClickListener(this);

        m_btn_time = findViewById(R.id.btn_time);
        m_btn_time.setOnClickListener(this);

        m_btn_date = findViewById(R.id.btn_date);
        m_btn_date.setOnClickListener(this);

        m_tv_date = findViewById(R.id.tv_date);
        m_tv_time = findViewById(R.id.tv_time);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_add_journey) {
            addJourney(src, des, date, time);
        }
        if (v.getId() == R.id.btn_date) {
            m_btn_date.setOnClickListener(view1 -> {

                final Calendar calendar = Calendar.getInstance();
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                day = calendar.get(Calendar.DAY_OF_MONTH);
//TODO error one month earlier
                DatePickerDialog datePickerDialog = new DatePickerDialog(ActivityAddJourney.this, (view, year, month, dayOfMonth) -> m_tv_date.setText(year + "/" + month + "/" + dayOfMonth), year, month, day);
                datePickerDialog.show();
            });

        }
        if (v.getId() == R.id.btn_time) {

            m_btn_time.setOnClickListener(view1 -> {
                final Calendar calendar = Calendar.getInstance();
                hour = calendar.get(Calendar.HOUR_OF_DAY);
                minute = calendar.get(Calendar.MINUTE);
                TimePickerDialog timePickerDialog = new TimePickerDialog(ActivityAddJourney.this, (view, hourOfDay, minute) -> m_tv_time.setText(hourOfDay + ":" + minute), hour, minute, false);
                timePickerDialog.show();

            });
        }
    }

    private boolean validateJourneyInfo() {
        src = String.valueOf(m_spinner_citySrc.getSelectedItem());
        des = String.valueOf(m_spinner_cityDes.getSelectedItem());
        date = String.valueOf(m_tv_date.getText());
        time = String.valueOf(m_tv_time.getText());

        if (src.equals("Select a journey source")) {
            Toast.makeText(ActivityAddJourney.this, "Please a journey source ", Toast.LENGTH_LONG).show();
            return false;
        }
        if (des.equals("Select a journey destination")) {
            Toast.makeText(ActivityAddJourney.this, "Please a journey destination ", Toast.LENGTH_LONG).show();
            return false;
        }
        if (TextUtils.isEmpty(m_tv_date.getText().toString())) {
            Toast.makeText(ActivityAddJourney.this, "Please Enter Journey Date ", Toast.LENGTH_LONG).show();
            return false;
        } else if (TextUtils.isEmpty(m_tv_time.getText().toString())) {
            Toast.makeText(ActivityAddJourney.this, "Please Enter Journey Time", Toast.LENGTH_LONG).show();
            return false;

        }
        return true;

    }

    private void addJourney(String src, String des, String date, String time) {
        if (!validateJourneyInfo()) return;
        addJourneyToAgenciesJourneys(src, des, date, time);
        addJourneyToResultsRouts();
        initialJourneySeats();
        navigateToJourneysDisplay();
    }

    private void navigateToJourneysDisplay() {
        Intent intent = new Intent(this, ActivityAgencyJourneysDisplay.class).putExtra("agencyId", agencyId);
        startActivity(intent);
    }

    private void addJourneyToAgenciesJourneys(String src, String des, String date, String time) {

        DatabaseReference ref_agency = db_root.child(PATH_AGENCIES_JOURNEYS).child(agencyId);
        ref_journey = ref_agency.push();
        journeyID = ref_journey.getKey();
        //DataReference `agencies journeys`
        ref_journey.child(PATH_JOURNEY_ID).setValue(journeyID);
        ref_journey.child(PATH_AGENCY_NAME).setValue(agencyName);
        ref_journey.child(PATH_CITY_SOURCE).setValue(src);
        ref_journey.child(PATH_CITY_DESTINATION).setValue(des);
        ref_journey.child(PATH_JOURNEY_DATE).setValue(date);
        ref_journey.child(PATH_JOURNEY_TIME).setValue(time);
    }

    private void addJourneyToResultsRouts() {
        String srcDes = src + '_' + des;
        DatabaseReference ref_rout = db_root.child(PATH_RESULTS_ROUTES).child(srcDes);
        DatabaseReference ref_routes_journey = ref_rout.child(journeyID);
        ref_routes_journey.child(PATH_JOURNEY_ID).setValue(journeyID);
        ref_routes_journey.child(PATH_AGENCY_ID).setValue(agencyId);
        ref_routes_journey.child(PATH_AGENCY_NAME).setValue(agencyName);
        ref_routes_journey.child(PATH_CITY_SOURCE).setValue(src);
        ref_routes_journey.child(PATH_CITY_DESTINATION).setValue(des);
        ref_routes_journey.child(PATH_JOURNEY_DATE).setValue(date);
        ref_routes_journey.child(PATH_JOURNEY_TIME).setValue(time);
    }

    private void initialJourneySeats() {
        for (int i = 1; i <= 32; i++) {
            DatabaseReference ref_seat = ref_journey.child(PATH_JOURNEY_SEATS).push();
            ref_seat.child(PATH_SEAT_NUMBER).setValue(i);
            ref_seat.child(PATH_SEAT_ID).setValue(ref_seat.getKey());
            ref_seat.child(PATH_SEAT_STATE_ENABLED).setValue(true);
            ref_seat.child(PATH_SEAT_STATE_CHECKED).setValue(false);
            ref_seat.child(PATH_SEAT_PASSENGER_ID).setValue("false");
            ref_seat.child(PATH_SEAT_JOURNEY_ID).setValue("false");
        }


    }


}



