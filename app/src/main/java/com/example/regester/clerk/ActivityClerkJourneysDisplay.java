package com.example.regester.clerk;

import static com.example.regester.constants.CommonConstants.PATH_AGENCIES_JOURNEYS;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.regester.R;
import com.example.regester.models.Journey;
import com.example.regester.passenger.ActivitySeatsBookingManagement2;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class ActivityClerkJourneysDisplay extends AppCompatActivity {
    private static final String TAG = "tag";
    RecyclerView recyclerView;
    Query query;
    FirebaseRecyclerAdapter<Journey, MyViewHolder_Clerk_Journey> myAdapter;
    FirebaseDatabase db_root;
    DatabaseReference journeys_ref;
    public static final String PATH_AGENCY_NAME = "agencyName";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clerk_journeys_display);
        db_root = FirebaseDatabase.getInstance();
        Intent a = getIntent();
        String agencyId = a.getStringExtra("agencyID");
        Log.d(TAG, "AGENCYID" + agencyId);
        journeys_ref = db_root.getReference(PATH_AGENCIES_JOURNEYS).child(agencyId);
        query = journeys_ref.orderByChild(PATH_AGENCY_NAME);
        FirebaseRecyclerOptions<Journey> options = new FirebaseRecyclerOptions.Builder<Journey>().setQuery(query, Journey.class).build();


        myAdapter = new FirebaseRecyclerAdapter<Journey, MyViewHolder_Clerk_Journey>(options) {
            @Override
            protected void onBindViewHolder(@NonNull MyViewHolder_Clerk_Journey viewHolder, int position, @NonNull Journey model) {
                String ref_journey_id = getRef(position).getKey();
                viewHolder.setAgencyName(model.getAgencyName());
                viewHolder.setJourneyDate(model.getJourneyDate());
                viewHolder.setJourneyTime(model.getJourneyTime());
                viewHolder.setSourceCountry(model.getSourceCity());
                viewHolder.setDestinationCountry(model.getDestinationCity());
                viewHolder.itemView.setOnClickListener(v -> {
                    Log.d(TAG, "ref_journey" + ref_journey_id);
                    Toast.makeText(ActivityClerkJourneysDisplay.this, ref_journey_id, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ActivityClerkJourneysDisplay.this, ActivitySeatsBookingManagement2.class)
                            .putExtra("agencyID", agencyId)
                            .putExtra("journeyID", ref_journey_id);

                    startActivity(intent);
                });

            }

            @NonNull
            @Override
            public MyViewHolder_Clerk_Journey onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                LayoutInflater inflater = LayoutInflater.from(ActivityClerkJourneysDisplay.this);
                View view = inflater.inflate(R.layout.row_clerk_journey, parent, false);
                return new MyViewHolder_Clerk_Journey(view);
            }
        };

        recyclerView = findViewById(R.id.recycler_clerk_journeys_list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(myAdapter);
        myAdapter.startListening();


    }
}