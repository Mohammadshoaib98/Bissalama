package com.example.regester.passenger;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.regester.R;
import com.example.regester.models.Journey;
import com.example.regester.models.Seat;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

import static com.example.regester.constants.CommonConstants.PATH_AGENCIES_JOURNEYS;
import static com.example.regester.constants.CommonConstants.PATH_JOURNEY_DATE;
import static com.example.regester.constants.CommonConstants.PATH_JOURNEY_ID;
import static com.example.regester.constants.CommonConstants.PATH_JOURNEY_SEATS;
import static com.example.regester.constants.CommonConstants.PATH_JOURNEY_TIME;
import static com.example.regester.constants.CommonConstants.PATH_PASSENGERS_JOURNEYS;
import static com.example.regester.constants.CommonConstants.PATH_PASSENGER_ID;
import static com.example.regester.constants.CommonConstants.PATH_SEAT_ID;
import static com.example.regester.constants.CommonConstants.PATH_SEAT_NUMBER;
import static com.example.regester.constants.CommonConstants.PATH_SEAT_STATE_CHECKED;
import static com.example.regester.constants.CommonConstants.PATH_SEAT_STATE_ENABLED;

public class ActivityPassengerJourneysDisplay extends AppCompatActivity {

    RecyclerView recyclerView;
    FirebaseDatabase db_root;
    // private JourneysAdapter journeysAdapter;
    FirebaseAuth mAuth;

    DatabaseReference ref_agencies_journeys;
    DatabaseReference ref_passenger_journeys;

    DatabaseReference ref_journeys_users;
    private String TAG = "success";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passenger_journeys_display);

        initializeDBRefs();

        buildPassengerJourneysFromDB();


    }

    private void initializeDBRefs() {
        mAuth = FirebaseAuth.getInstance();
        String passengerID = (mAuth.getCurrentUser().getUid());
        db_root = FirebaseDatabase.getInstance();
        ref_agencies_journeys = db_root.getReference(PATH_AGENCIES_JOURNEYS);
        ref_passenger_journeys = db_root.getReference(PATH_PASSENGERS_JOURNEYS).child(passengerID);
    }

    private void buildPassengerJourneysFromDB() {
        ref_passenger_journeys.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapPassengerJourneys) {
                final ArrayList<Journey> arrayListPassengerJourneys = new ArrayList<>();
                final HashMap<String,Object> hashMapSingleJourneySeats = new HashMap<>();

                for (DataSnapshot snapPassengerJourney : snapPassengerJourneys.getChildren()) {
                    String agency_id = String.valueOf(snapPassengerJourney.child("agencyID").getValue());
                    String journey_id = String.valueOf(snapPassengerJourney.child("journeyID").getValue());

                    DatabaseReference ref_single_journey = ref_agencies_journeys.child(agency_id).child(journey_id);

                    ref_single_journey.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapSingleJourney) {

                            String agencyName = String.valueOf(snapSingleJourney.child("agencyName").getValue());
                            String desCity = String.valueOf(snapSingleJourney.child("destinationCity").getValue());
                            String srcCity = String.valueOf(snapSingleJourney.child("sourceCity").getValue());
                            String journeyDate = String.valueOf(snapSingleJourney.child(PATH_JOURNEY_DATE).getValue());
                            String journeyTime = String.valueOf(snapSingleJourney.child(PATH_JOURNEY_TIME).getValue());
                            HashMap<String, Object> journeySeats = (HashMap<String, Object>) snapSingleJourney.child(PATH_JOURNEY_SEATS).getValue();

                            DatabaseReference ref_single_journey_seats = snapSingleJourney.child("seats").getRef();
                            ref_single_journey_seats.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapSingleJourneySeats) {
                                    for (DataSnapshot snapSingleJourneySeat : snapSingleJourneySeats.getChildren()) {
                                        hashMapSingleJourneySeats.put(
                                                        String.valueOf(snapSingleJourneySeat.child(PATH_SEAT_NUMBER).getValue()),
                                                          new  Seat(
                                                       snapSingleJourneySeat.child(PATH_SEAT_NUMBER).getValue(Integer.class),
                                                        String.valueOf(snapSingleJourneySeat.child(PATH_SEAT_ID).getValue()),
                                                        Boolean.parseBoolean(String.valueOf(snapSingleJourneySeat.child(PATH_SEAT_STATE_ENABLED).getValue())),
                                                        Boolean.parseBoolean(String.valueOf(snapSingleJourneySeat.child(PATH_SEAT_STATE_CHECKED).getValue())),
                                                        String.valueOf(snapSingleJourneySeat.child(PATH_PASSENGER_ID).getValue()),
                                                        String.valueOf(snapSingleJourneySeat.child(PATH_JOURNEY_ID).getValue())
                                                )
                                        );

                                    }

                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });

                            Journey journey = new Journey(agencyName, srcCity, desCity, journeyDate, journeyTime, hashMapSingleJourneySeats);
                            arrayListPassengerJourneys.add(journey);
                            //arrayListPassengerJourneys.add(snapSingleJourney.getValue(Journey.class));

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });


                }
                recyclerView = findViewById(R.id.recyclerview_passenger_journeys);
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(ActivityPassengerJourneysDisplay.this));
                AdapterPassengerJourneys journeysAdapter = new AdapterPassengerJourneys(ActivityPassengerJourneysDisplay.this, arrayListPassengerJourneys);
                recyclerView.setAdapter(journeysAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

   /* public void CreateUserJourneysList(String agency_id, String journey_id) {
        ref_journeys_agencies.child(agency_id).child(journey_id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String agencyName = String.valueOf(snapshot.child("agencyName").getValue());
                String desCity = String.valueOf(snapshot.child("cityDestination").getValue());
                String srcCity = String.valueOf(snapshot.child("citySource").getValue());
                String journeyDate = String.valueOf(snapshot.child("journeyDate").getValue());
                String journeyTime = String.valueOf(snapshot.child("journeyTime").getValue());
                Log.d(TAG, agencyName);
                Log.d(TAG, desCity);
                Log.d(TAG, srcCity);
                Log.d(TAG, journeyDate);
                Log.d(TAG, journeyTime);

                PassJourneys journey = new PassJourneys(agencyName, desCity, srcCity, journeyDate, journeyTime);
                AdapterPassJourneys.journeyArrayList.add(journey);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




    }*/


}
