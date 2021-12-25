package com.example.regester.activites;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.regester.R;
import com.example.regester.models.Seat;
import com.example.regester.constants.MyViewHolderSeatMangement;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class ActivitySeatsBookMangement extends AppCompatActivity {
    private static final String PATH_SEATS_JOURNEY = "seats";
    RecyclerView recyclerView;
    Query query;
    public static final String PATH_AGENCY_NAME = "agencyName";
    FirebaseRecyclerAdapter<Seat, MyViewHolderSeatMangement> Adapter;
    FirebaseDatabase db_root;
    DatabaseReference journey_ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seats_book_mangment);
        Intent _int = getIntent();
        String agencyID = _int.getStringExtra("agencyID");
        String journeyID = _int.getStringExtra("journeyID");
        journey_ref = db_root.getReference(PATH_AGENCY_NAME).child(agencyID).child(journeyID).child(PATH_SEATS_JOURNEY);
        query = journey_ref.orderByChild("1");
        FirebaseRecyclerOptions<Seat> options = new FirebaseRecyclerOptions.Builder<Seat>().setQuery(query, Seat.class).build();
        Adapter = new FirebaseRecyclerAdapter<Seat, MyViewHolderSeatMangement>(options) {
            @Override
            protected void onBindViewHolder(@NonNull MyViewHolderSeatMangement holder, int position, @NonNull Seat model) {
                final String seatKey = getRef(position).getKey();
                holder.setSeatState(model.getSeatStatus());
                holder.setSeatNumber(model.getSeatNumber());
                holder.itemView.findViewById(R.id.btn_confirm).setOnClickListener(v -> {
                    AlertDialog.Builder builder = new AlertDialog.Builder(holder.itemView.getContext());
                    builder.setTitle("Delete Panel");
                    builder.setMessage("Do You Want Confirm It ? ");
                    builder.setPositiveButton(" Yes ", (dialogInterface, i) -> {
                        db_root = FirebaseDatabase.getInstance();

                        journey_ref.child(seatKey).child("seatStatus").setValue(true);
                    });
                    builder.setNegativeButton(" No ", (dialogInterface, i) -> {

                    });
                    builder.show();
                });
                holder.itemView.findViewById(R.id.btn_un_confirm).setOnClickListener(v -> {
                    AlertDialog.Builder builder = new AlertDialog.Builder(holder.itemView.getContext());
                    builder.setTitle("Delete Panel");
                    builder.setMessage("Do You Want Un Confirm It ? ");
                    builder.setPositiveButton(" Yes ", (dialogInterface, i) -> {
                        db_root = FirebaseDatabase.getInstance();

                        journey_ref.child(seatKey).setValue(null);
                    });
                    builder.setNegativeButton(" No ", (dialogInterface, i) -> {

                    });
                    builder.show();
                });
                holder.itemView.findViewById(R.id.btn_call).setOnClickListener(v ->{




                });

            }

            @NonNull
            @Override
            public MyViewHolderSeatMangement onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                LayoutInflater inflater=LayoutInflater.from(ActivitySeatsBookMangement.this);
               View view=inflater.inflate(R.layout.item_seat,parent,false);
                return new MyViewHolderSeatMangement(view);
            }
        };

        recyclerView =findViewById(R.id.rcy_clerk_booked_seats);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(Adapter);
        Adapter.startListening();

    }
}