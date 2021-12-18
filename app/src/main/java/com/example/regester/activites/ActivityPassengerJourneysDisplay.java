package com.example.regester.activites;

import static com.example.regester.utils.CommonConstants.PATH_AGENCIES_JOURNEYS;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.regester.R;
import com.example.regester.models.Journey;
import com.example.regester.utils.AdapterPassJourneys;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ActivityPassengerJourneysDisplay extends AppCompatActivity {

    RecyclerView recyclerView;
    FirebaseDatabase db_root;
    // private JourneysAdapter journeysAdapter;
    private static final String PATH_USERS_JOURNEYS = "usersJourneys";
    FirebaseAuth mAuth;

    DatabaseReference ref_journeys_agencies;
    DatabaseReference ref_journeys_users;
    private String TAG = "success";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passenger_journeys_display);
        mAuth = FirebaseAuth.getInstance();
        String userID = (mAuth.getCurrentUser().getUid());
        db_root = FirebaseDatabase.getInstance();
        ref_journeys_agencies = db_root.getReference(PATH_AGENCIES_JOURNEYS);
        db_root.getReference(PATH_USERS_JOURNEYS).child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                final ArrayList<Journey> journeysArrayList=new ArrayList<>();
                for (DataSnapshot sp : snapshot.getChildren()) {
                    String agency_id = String.valueOf(sp.child("agencyID").getValue());
                    String journey_id = String.valueOf(sp.child("journeyID").getValue());
                    Log.d(TAG, agency_id);
                    Log.d(TAG, journey_id);

                    ref_journeys_agencies.child(agency_id).child(journey_id).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            String agencyName = String.valueOf(snapshot.child("agencyName").getValue());
                            String desCity = String.valueOf(snapshot.child("destinationCity").getValue());
                            String srcCity = String.valueOf(snapshot.child("sourceCity").getValue());
                            String journeyDate = String.valueOf(snapshot.child("journeyDate").getValue());
                            String journeyTime = String.valueOf(snapshot.child("journeyTime").getValue());
                            String seat=String.valueOf(snapshot.child("Seat").getValue());
                            Log.d(TAG, agencyName);
                            Log.d(TAG, desCity);
                            Log.d(TAG, srcCity);
                            Log.d(TAG, journeyDate);
                            Log.d(TAG, journeyTime);

                            Journey journey = new Journey(agencyName,journeyDate, journeyTime,srcCity,desCity,seat);
                            journeysArrayList.add(journey);
                            Log.d(TAG, "onDataChange: "+journeysArrayList.size());
                            recyclerView = findViewById(R.id.recyclerview_passenger_journeys);
                            recyclerView.setHasFixedSize(true);
                            recyclerView.setLayoutManager(new LinearLayoutManager(ActivityPassengerJourneysDisplay.this));
                            AdapterPassJourneys journeysAdapter = new AdapterPassJourneys(ActivityPassengerJourneysDisplay.this, journeysArrayList);
                            recyclerView.setAdapter(journeysAdapter);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });


                }

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
