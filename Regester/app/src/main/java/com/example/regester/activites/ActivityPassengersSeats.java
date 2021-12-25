package com.example.regester.activites;

import static com.example.regester.constants.CommonConstants.PATH_USERS;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.regester.R;
import com.example.regester.models.Seat;
import com.example.regester.constants.MyViewHolder_Seats;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;

public class ActivityPassengersSeats extends AppCompatActivity {

    RecyclerView recyclerView;
    Query query;
    FirebaseDatabase db_root;
    private String agency_ID, journey_ID;
    DatabaseReference ref_journeys_agency;
    DatabaseReference ref_journeys_users;
    private DatabaseReference ref_passenger_id;

    FirebaseRecyclerAdapter<Seat, MyViewHolder_Seats> adapter;
    ArrayList<Boolean> array_bookedSeatsNumbers_ui = new ArrayList<>(Arrays.asList(false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false));
    ArrayList<Boolean> array_bookedSeatsNumbers_toStore = new ArrayList<>(Arrays.asList(false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false));

    private static final String TAG = "agencyName";
    private static final String PATH_USERS_JOURNEYS = "usersJourneys";
    private static final String PATH_USER_PASSENGER = "end-User";
    private String userID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passengers_seats);
        initializeJourneyAndAgencyIDs();
        db_root = FirebaseDatabase.getInstance();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        userID = (mAuth.getCurrentUser().getUid());
        db_root = FirebaseDatabase.getInstance();
        ref_passenger_id = db_root.getReference(PATH_USERS).child(PATH_USER_PASSENGER).child(userID);
        ref_journeys_agency = db_root.getReference(PATH_USERS_JOURNEYS).child(agency_ID).child(journey_ID).child("seats");
        query = ref_journeys_agency.orderByChild("seatNumber");
        FirebaseRecyclerOptions<Seat> options = new FirebaseRecyclerOptions.Builder<Seat>().setQuery(query, Seat.class).build();

        adapter = new FirebaseRecyclerAdapter<Seat, MyViewHolder_Seats>(options) {
            @Override
            protected void onBindViewHolder(@NonNull MyViewHolder_Seats holder, int position, @NonNull Seat model) {
                holder.setAgencyName(model.getAgencyName());
         /*       holder.setCh1(model.getCh1());
                holder.setCh2(model.getCh2());
                holder.setCh3(model.getCh3());
                holder.setCh4(model.getCh4());
                holder.setCh5(model.getCh5());
                holder.setCh6(model.getCh6());
                holder.setCh7(model.getCh7());
                holder.setCh8(model.getCh8());
                holder.setCh9(model.getCh9());
                holder.setCh10(model.getCh10());
                holder.setCh11(model.getCh11());
                holder.setCh12(model.getCh12());
                holder.setCh13(model.getCh13());
                holder.setCh14(model.getCh14());
                holder.setCh15(model.getCh15());
                holder.setCh16(model.getCh16());
                holder.setCh17(model.getCh17());
                holder.setCh18(model.getCh18());
                holder.setCh19(model.getCh19());
                holder.setCh20(model.getCh20());
                holder.setCh21(model.getCh21());
                holder.setCh22(model.getCh22());
                holder.setCh23(model.getCh23());
                holder.setCh24(model.getCh24());
                holder.setCh25(model.getCh25());
                holder.setCh26(model.getCh26());
                holder.setCh27(model.getCh27());
                holder.setCh28(model.getCh28());
                holder.setCh29(model.getCh29());
                holder.setCh30(model.getCh30());
                holder.setCh31(model.getCh31());
                holder.setCh32(model.getCh32());*/
                holder.itemView.findViewById(R.id.ch1).setOnClickListener(v -> {
                    manageSeatsBooking(1, ((CheckBox) v).isChecked());
                });
                holder.itemView.findViewById(R.id.ch2).setOnClickListener(v -> {
                    manageSeatsBooking(2, ((CheckBox) v).isChecked());
                });
                holder.itemView.findViewById(R.id.ch3).setOnClickListener(v -> {
                    manageSeatsBooking(3, ((CheckBox) v).isChecked());
                });
                holder.itemView.findViewById(R.id.ch4).setOnClickListener(v -> {
                    manageSeatsBooking(4, ((CheckBox) v).isChecked());
                });
                holder.itemView.findViewById(R.id.ch5).setOnClickListener(v -> {
                    manageSeatsBooking(5, ((CheckBox) v).isChecked());
                });
                holder.itemView.findViewById(R.id.ch6).setOnClickListener(v -> {
                    manageSeatsBooking(6, ((CheckBox) v).isChecked());
                });
                holder.itemView.findViewById(R.id.ch7).setOnClickListener(v -> {
                    manageSeatsBooking(7, ((CheckBox) v).isChecked());
                });
                holder.itemView.findViewById(R.id.ch8).setOnClickListener(v -> {
                    manageSeatsBooking(8, ((CheckBox) v).isChecked());
                });
                holder.itemView.findViewById(R.id.ch9).setOnClickListener(v -> {
                    manageSeatsBooking(9, ((CheckBox) v).isChecked());
                });
                holder.itemView.findViewById(R.id.ch10).setOnClickListener(v -> {
                    manageSeatsBooking(10, ((CheckBox) v).isChecked());
                });
                holder.itemView.findViewById(R.id.ch11).setOnClickListener(v -> {
                    manageSeatsBooking(11, ((CheckBox) v).isChecked());
                });
                holder.itemView.findViewById(R.id.ch12).setOnClickListener(v -> {
                    manageSeatsBooking(12, ((CheckBox) v).isChecked());
                });
                holder.itemView.findViewById(R.id.ch13).setOnClickListener(v -> {
                    manageSeatsBooking(13, ((CheckBox) v).isChecked());
                });
                holder.itemView.findViewById(R.id.ch14).setOnClickListener(v -> {
                    manageSeatsBooking(14, ((CheckBox) v).isChecked());
                });
                holder.itemView.findViewById(R.id.ch15).setOnClickListener(v -> {
                    manageSeatsBooking(15, ((CheckBox) v).isChecked());
                });
                holder.itemView.findViewById(R.id.ch16).setOnClickListener(v -> {
                    manageSeatsBooking(16, ((CheckBox) v).isChecked());
                });
                holder.itemView.findViewById(R.id.ch17).setOnClickListener(v -> {
                    manageSeatsBooking(17, ((CheckBox) v).isChecked());
                });
                holder.itemView.findViewById(R.id.ch18).setOnClickListener(v -> {
                    manageSeatsBooking(18, ((CheckBox) v).isChecked());
                });
                holder.itemView.findViewById(R.id.ch19).setOnClickListener(v -> {
                    manageSeatsBooking(19, ((CheckBox) v).isChecked());
                });
                holder.itemView.findViewById(R.id.ch20).setOnClickListener(v -> {
                    manageSeatsBooking(20, ((CheckBox) v).isChecked());
                });
                holder.itemView.findViewById(R.id.ch21).setOnClickListener(v -> {
                    manageSeatsBooking(21, ((CheckBox) v).isChecked());
                });
                holder.itemView.findViewById(R.id.ch22).setOnClickListener(v -> {
                    manageSeatsBooking(22, ((CheckBox) v).isChecked());
                });
                holder.itemView.findViewById(R.id.ch23).setOnClickListener(v -> {
                    manageSeatsBooking(23, ((CheckBox) v).isChecked());
                });
                holder.itemView.findViewById(R.id.ch24).setOnClickListener(v -> {
                    manageSeatsBooking(24, ((CheckBox) v).isChecked());
                });
                holder.itemView.findViewById(R.id.ch25).setOnClickListener(v -> {
                    manageSeatsBooking(25, ((CheckBox) v).isChecked());
                });
                holder.itemView.findViewById(R.id.ch26).setOnClickListener(v -> {
                    manageSeatsBooking(26, ((CheckBox) v).isChecked());
                });
                holder.itemView.findViewById(R.id.ch27).setOnClickListener(v -> {
                    manageSeatsBooking(27, ((CheckBox) v).isChecked());
                });
                holder.itemView.findViewById(R.id.ch28).setOnClickListener(v -> {
                    manageSeatsBooking(28, ((CheckBox) v).isChecked());
                });
                holder.itemView.findViewById(R.id.ch29).setOnClickListener(v -> {
                    manageSeatsBooking(29, ((CheckBox) v).isChecked());
                });
                holder.itemView.findViewById(R.id.ch30).setOnClickListener(v -> {
                    manageSeatsBooking(30, ((CheckBox) v).isChecked());
                });
                holder.itemView.findViewById(R.id.ch31).setOnClickListener(v -> {
                    manageSeatsBooking(31, ((CheckBox) v).isChecked());
                });
                holder.itemView.findViewById(R.id.ch32).setOnClickListener(v -> {
                    manageSeatsBooking(32, ((CheckBox) v).isChecked());
                });
                holder.itemView.findViewById(R.id.btn_confirm_row).setOnClickListener(v -> {
                    storeBookedSeats(array_bookedSeatsNumbers_ui,
                            userID,
                            journey_ID,
                            agency_ID);
                });
                holder.itemView.findViewById(R.id.btn_confirm_row).setOnClickListener(v -> {

                });
            }

            @NonNull
            @Override
            public MyViewHolder_Seats onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                LayoutInflater inflater = LayoutInflater.from(ActivityPassengersSeats.this);
                View view = inflater.inflate(R.layout.row_passengers_seats, parent, false);
                return new MyViewHolder_Seats(view);
            }
        };
        recyclerView = findViewById(R.id.recyclerview_passengers_seats);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        adapter.startListening();
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

    }


    private void initializeJourneyAndAgencyIDs() {
        Intent int1 = getIntent();
        journey_ID = int1.getStringExtra("journeyID");

        agency_ID = int1.getStringExtra("agencyID");
        Log.d(TAG,"AGENCYID&JOURNEYID"+agency_ID+" "+journey_ID);

    }


    private void storeBookedSeats(ArrayList<Boolean> array_bookedSeatsNumbers,
                                  String user_id,
                                  String journey_id,
                                  String agency_id) {
        for (int index = 1; index < array_bookedSeatsNumbers.size(); index++) {
            if (array_bookedSeatsNumbers.get(index)) {
                array_bookedSeatsNumbers_toStore.add(index, false);
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
    }

    private void manageSeatsBooking(int i, boolean checked) {
        array_bookedSeatsNumbers_ui.add(i, checked);
    }


}



