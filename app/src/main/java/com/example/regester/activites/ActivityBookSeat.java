package com.example.regester.activites;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.regester.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Arrays;

public class ActivityBookSeat extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "agencyName";
    private static final String PATH_USERS_JOURNEYS = "usersJourneys";
    private static final String PATH_USER_PASSENGER = "end-User";

    CheckBox m_ch1, m_ch2, m_ch3, m_ch4, m_ch5, m_ch6, m_ch7, m_ch8;
    private Button m_btn_confirm, m_btn_cancelSeat;
    TextView m_tv_test, m_tv_booked, m_tv_yourSeat;

    FirebaseDatabase db_root;
    DatabaseReference ref_journeys_agency;
    DatabaseReference ref_journeys_users;
    private DatabaseReference ref_passenger_id;
    private DatabaseReference ref_agencyJourneyID;

    String agencyID, journeyID;
    ArrayList<Boolean> array_bookedSeatsNumbers_ui = new
            ArrayList<>(Arrays.asList(false, false, false, false, false, false, false, false, false));
    ArrayList<Boolean> array_bookedSeatsNumbers_toStore = new ArrayList<>(Arrays.asList(false, false, false, false, false, false, false, false, false));
    private String userID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_seat);
       /* initializeJourneyAndAgencyIDs();
        initialize_db_and_auth();
        initializeViews();
        populateWithBookedSeats();
*/
    }

    @Override
    public void onClick(View v) {

    }


  /*  private void initializeJourneyAndAgencyIDs() {
        Intent int1 = getIntent();
        journeyID = int1.getStringExtra("journeyID");
        agencyID = int1.getStringExtra("agencyID");
    }

    private void populateWithBookedSeats() {
        ref_agencyJourneyID.child("seats").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot sp : snapshot.getChildren()) {
                    if (sp != null) {
                        if (sp.getKey().equals("1")) {
                            m_ch1.setEnabled(false);
                            m_ch1.setChecked(true);
                        }
                        if (sp.getKey().equals("2")) {
                            m_ch2.setEnabled(false);
                            m_ch2.setChecked(true);
                        }
                        if (sp.getKey().equals("3")) {
                            m_ch3.setEnabled(false);
                            m_ch3.setChecked(true);
                        }
                        if (sp.getKey().equals("4")) {
                            m_ch4.setEnabled(false);
                            m_ch4.setChecked(true);
                        }
                        if (sp.getKey().equals("5")) {
                            m_ch5.setEnabled(false);
                            m_ch5.setChecked(true);
                        }
                        if (sp.getKey().equals("6")) {
                            m_ch6.setEnabled(false);
                            m_ch6.setChecked(true);
                        }
                        if (sp.getKey().equals("7")) {
                            m_ch7.setEnabled(false);
                            m_ch7.setChecked(true);
                        }
                        if (sp.getKey().equals("8")) {
                            m_ch8.setEnabled(false);
                            m_ch8.setChecked(true);
                        }


                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

    }

    private void initialize_db_and_auth() {
        FirebaseAuth mAuth = Fi rebaseAuth.getInstance();
        userID = (mAuth.getCurrentUser().getUid());
        db_root = FirebaseDatabase.getInstance();
        ref_passenger_id = db_root.getReference(PATH_USERS).child(PATH_USER_PASSENGER).child(userID);
        ref_journeys_agency = db_root.getReference(PATH_JOURNEYS_AGENCIES).child(agencyID);
        ref_agencyJourneyID = ref_journeys_agency.child(journeyID);

    }*/

  /*  private void initializeViews() {
        m_ch1 = findViewById(R.id.ch1);
        m_ch2 = findViewById(R.id.ch2);
        m_ch3 = findViewById(R.id.ch3);
        m_ch4 = findViewById(R.id.ch4);
        m_ch5 = findViewById(R.id.ch5);
        m_ch6 = findViewById(R.id.ch6);
        m_ch7 = findViewById(R.id.ch7);
        m_ch8 = findViewById(R.id.ch8);
        m_ch1.setOnClickListener(this);
        m_ch2.setOnClickListener(this);
        m_ch3.setOnClickListener(this);
        m_ch4.setOnClickListener(this);
        m_ch5.setOnClickListener(this);
        m_ch6.setOnClickListener(this);
        m_ch7.setOnClickListener(this);
        m_ch8.setOnClickListener(this);

        m_btn_confirm = findViewById(R.id.btn_confirm_row);
        m_btn_confirm.setOnClickListener(this);

        m_btn_cancelSeat = findViewById(R.id.btn_cancel_row);
        m_btn_cancelSeat.setOnClickListener(this);

        m_tv_test = findViewById(R.id.tv_test);
        m_tv_booked = findViewById(R.id.tv_booked);
        m_tv_yourSeat = findViewById(R.id.tv_your_seat);
        m_tv_test.setText(journeyID);
        m_tv_yourSeat.setText(userID);
        ref_passenger_id.child("phone").addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String phoneUser = String.valueOf(snapshot.getValue());

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
    }*/
/*

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ch1:
                manageSeatsBooking(1, ((CheckBox) v).isChecked());
                break;
            case R.id.ch2:
                manageSeatsBooking(2, ((CheckBox) v).isChecked());
                break;
            case R.id.ch3:
                manageSeatsBooking(3, ((CheckBox) v).isChecked());
                break;
            case R.id.ch4:
                manageSeatsBooking(4, ((CheckBox) v).isChecked());
                break;
            case R.id.ch5:
                manageSeatsBooking(5,((CheckBox)v).isChecked());
                break;
            case R.id.ch6:
                manageSeatsBooking(6,((CheckBox)v).isChecked());
                break;
            case R.id.ch7:
                manageSeatsBooking(7,((CheckBox)v).isChecked());
                break;
            case R.id.ch8:
                manageSeatsBooking(8,((CheckBox)v).isChecked());
                break;

            case R.id.btn_confirm_row:

                storeBookedSeats(array_bookedSeatsNumbers_ui,
                        userID,
                        journeyID,
                        agencyID);
                break;


        }
    }

 private void storeBookedSeats(ArrayList<Boolean> array_bookedSeatsNumbers_ui
    ,String user_id
    ,String journey_id
    ,String agency_id) {


         for (int index=1;index<array_bookedSeatsNumbers_ui.size();index++){
             if (array_bookedSeatsNumbers_ui.get(index)){
                 array_bookedSeatsNumbers_toStore.add(index,true);
             }
         }
         ref_journeys_users = db_root.getReference(PATH_USERS_JOURNEYS).child(user_id).child(journey_id);
         ref_journeys_users.child("journeyID").setValue(journey_id);
         ref_journeys_users.child("agencyID").setValue(agency_id);
         for (int index=1;index<array_bookedSeatsNumbers_toStore.size();index++)
         {
             ref_journeys_users.child("seats").child(String.valueOf(index)).setValue(true);
             ref_journeys_agency.child(journey_id).child("seats").child(String.valueOf(index)).setValue(user_id);
         }
         populateWithBookedSeats();
     }


    private void storeBookedSeats(ArrayList<Boolean> array_bookedSeatsNumbers,
                                  String user_id,
                                  String journey_id,
                                  String agency_id) {
        for (int index = 1; index < array_bookedSeatsNumbers.size(); index++) {
            if (array_bookedSeatsNumbers.get(index)) {
                array_bookedSeatsNumbers_toStore.add(index, true);
            }
        }
        ref_journeys_users = db_root.getReference(PATH_USERS_JOURNEYS).child(user_id).child(journey_id);
        ref_journeys_users.child("journeyID").setValue(journey_id);
        ref_journeys_users.child("agencyID").setValue(agency_id);
        for (int index = 1; index < array_bookedSeatsNumbers_toStore.size(); index++) {
            if (array_bookedSeatsNumbers_toStore.get(index)) {
                Log.d(TAG, "storeBookedSeats: 4 ");
                ref_journeys_users.child("seats").child(String.valueOf(index)).setValue(true);
                ref_journeys_agency.child(journey_id).child("seats").child(String.valueOf(index)).setValue(user_id);
            }
        }
        populateWithBookedSeats();


    }

    private void manageSeatsBooking(int i, boolean checked) {
        array_bookedSeatsNumbers_ui.add(i, checked);
    }
*/

}





