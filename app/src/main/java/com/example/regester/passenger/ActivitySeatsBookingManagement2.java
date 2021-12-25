package com.example.regester.passenger;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.regester.R;
import com.example.regester.models.Seat;
import com.example.regester.models_viewholders.MyViewHolder_Seat;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import static com.example.regester.constants.CommonConstants.PATH_AGENCIES_JOURNEYS;
import static com.example.regester.constants.CommonConstants.PATH_JOURNEYS_PASSENGERS;
import static com.example.regester.constants.CommonConstants.PATH_JOURNEY_PASSENGERS;
import static com.example.regester.constants.CommonConstants.PATH_PHONE_NUMBER;
import static com.example.regester.constants.CommonConstants.PATH_SEATS;
import static com.example.regester.constants.CommonConstants.PATH_SEAT_NUMBER;
import static com.example.regester.constants.CommonConstants.PATH_SEAT_PASSENGER_ID;
import static com.example.regester.constants.CommonConstants.PATH_SEAT_STATE_CHECKED;
import static com.example.regester.constants.CommonConstants.PATH_SEAT_STATE_ENABLED;
import static com.example.regester.constants.CommonConstants.PATH_USERS;
import static com.example.regester.constants.CommonConstants.PATH_USER_PASSENGER;

public class ActivitySeatsBookingManagement2 extends AppCompatActivity {

    String agency_ID, journey_ID, passengerID, userPhoneNumber;
    FirebaseDatabase db_root;
    DatabaseReference ref_journey_seats, ref_journey_passengers, ref_passenger_id;

    FirebaseRecyclerAdapter<Seat, MyViewHolder_Seat> adapter;
    RecyclerView recyclerView;
    Query query;

    HashMap<String, Boolean> hasMap_bookedSeats = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.passengers_seats);

        setUpJourneyAgencyPassengerIDs();
        setUpDBRefs();
        populateSeatsFromDB();
        confirmSeatsBooking();

    }


    private void setUpJourneyAgencyPassengerIDs() {
        Intent intent = getIntent();
        journey_ID = intent.getStringExtra("journeyID");
        agency_ID = intent.getStringExtra("agencyID");
        passengerID = FirebaseAuth.getInstance().getCurrentUser().getUid();
    }

    private void setUpDBRefs() {
        db_root = FirebaseDatabase.getInstance();
        ref_journey_seats = db_root.getReference(PATH_AGENCIES_JOURNEYS).child(agency_ID).child(journey_ID).child(PATH_SEATS);
        ref_journey_passengers = db_root.getReference(PATH_JOURNEYS_PASSENGERS).child(agency_ID).child(journey_ID).child(PATH_JOURNEY_PASSENGERS);


        ref_passenger_id = db_root.getReference(PATH_USERS).child(PATH_USER_PASSENGER).child(passengerID);
        ref_passenger_id.child(PATH_PHONE_NUMBER).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        userPhoneNumber = String.valueOf(snapshot.getValue());

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });


    }

    private void populateSeatsFromDB() {
        query = ref_journey_seats.orderByChild(PATH_SEAT_NUMBER);
        FirebaseRecyclerOptions<Seat> options = new FirebaseRecyclerOptions.Builder<Seat>()
                .setQuery(query, Seat.class)
                .build();
        adapter = new FirebaseRecyclerAdapter<Seat, MyViewHolder_Seat>(options) {
            @Override
            protected void onBindViewHolder(@NonNull MyViewHolder_Seat viewHolder_seat,
                                            int position,
                                            @NonNull Seat model_seat) {

                viewHolder_seat.setSeatNumber(String.valueOf(model_seat.getSeatNumber()));
                viewHolder_seat.setSeatEnabled(model_seat.isSeatStateEnabled());
                viewHolder_seat.setSeatChecked(model_seat.isSeatStateChecked());


                viewHolder_seat.itemView.findViewById(R.id.chb_seatState).setOnClickListener(v -> {
                    updateLocalBookedSeats(model_seat.getSeatID(), ((CheckBox)v).isChecked(), v.isEnabled());
                });


            }


            @NonNull
            @Override
            public MyViewHolder_Seat onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                LayoutInflater inflater = LayoutInflater.from(ActivitySeatsBookingManagement2.this);
                View view = inflater.inflate(R.layout.seat, parent, false);
                return new MyViewHolder_Seat(view);
            }
        };

        recyclerView = findViewById(R.id._recycler_passengers_seats);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        recyclerView.setAdapter(adapter);
        adapter.startListening();

    }

    private void updateLocalBookedSeats(String seatID, boolean checked, boolean isEnabled) {
        if (isEnabled)// if this seat has not been booked yet by another user
            hasMap_bookedSeats.put(seatID, Boolean.valueOf(checked));
        Log.d("TAG", "updateBookedSeats: " + seatID + " " + Boolean.valueOf(checked));
    }

    private void confirmSeatsBooking() {
        findViewById(R.id.btn_confirm_row).setOnClickListener(v ->
                storeLocalBookedSeatsToDB(hasMap_bookedSeats, passengerID));
    }

    private void storeLocalBookedSeatsToDB(HashMap<String, Boolean> hasMap_bookedSeats, String passenger_id) {
        for (Map.Entry<String, Boolean> seatEntry : hasMap_bookedSeats.entrySet()) {

            //First DB location: ref_journey_seats
            ref_journey_seats.child(seatEntry.getKey()).child(PATH_SEAT_STATE_CHECKED).setValue(seatEntry.getValue());
            ref_journey_seats.child(seatEntry.getKey()).child(PATH_SEAT_STATE_ENABLED).setValue(!seatEntry.getValue());
            ref_journey_seats.child(seatEntry.getKey()).child(PATH_SEAT_PASSENGER_ID).setValue(passenger_id);

            //Second DB location: ref_journey_passengers
            ref_journey_passengers.child(passenger_id).child(seatEntry.getKey()).setValue(true);

        }
    }


}






