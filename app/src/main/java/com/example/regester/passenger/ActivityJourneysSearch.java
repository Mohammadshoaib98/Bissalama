package com.example.regester.passenger;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.regester.R;
import com.example.regester.models.Route;
import com.example.regester.models_viewholders.MyViewHolder_Route;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class ActivityJourneysSearch extends AppCompatActivity {
    public static final String PATH_RESULTS_ROUTE = "resultsRoute";
    public static final String PATH_AGENCY_NAME = "agencyName";
    Button m_btn_search_journey;
    RecyclerView recyclerView;
    Spinner m_sp_search_src, m_sp_search_des;
    Query query;
    FirebaseRecyclerAdapter<Route, MyViewHolder_Route> adapter;
    FirebaseDatabase db_root;
    DatabaseReference ref_route;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journeys_search);

        initializeViews();
        setupSearch();


    }

    private void initializeViews() {
        m_btn_search_journey = findViewById(R.id.btn_search_journey);
        m_sp_search_src = findViewById(R.id.sp_search_src);
        m_sp_search_des = findViewById(R.id.sp_search_des);
    }

    private String getSearchRout() {
        String src = String.valueOf(m_sp_search_src.getSelectedItem());
        String des = String.valueOf(m_sp_search_des.getSelectedItem());
        return src + '_' + des;
    }

    private void setupSearch() {
        db_root = FirebaseDatabase.getInstance();
        m_btn_search_journey.setOnClickListener(view -> {

            ref_route = db_root.getReference(PATH_RESULTS_ROUTE).child(getSearchRout());
            buildSearchResultsFromDB(ref_route);

        });


    }

    private void buildSearchResultsFromDB(DatabaseReference ref_route) {
        query = ref_route.orderByChild(PATH_AGENCY_NAME);
        FirebaseRecyclerOptions<Route> routeFirebaseRecyclerOptions = new FirebaseRecyclerOptions.Builder<Route>().setQuery(query, Route.class).build();
        adapter = new FirebaseRecyclerAdapter<Route, MyViewHolder_Route>(routeFirebaseRecyclerOptions) {
            @Override
            protected void onBindViewHolder(@NonNull MyViewHolder_Route holder, int position, @NonNull Route model) {
                final String route_key = getRef(position).getKey();
                holder.setAgencyName(model.getAgencyName());
                holder.setSourceCity(model.getSourceCity());
                holder.setDesCity(model.getDestinationCity());
                holder.setJourneyTime(model.getJourneyTime());
                holder.setJourneyDate(model.getJourneyDate());
                String agencyID = model.getAgencyID();
                String journeyID = model.getJourneyID();

                holder.itemView.setOnClickListener(view -> {
                    Intent intent = new Intent(ActivityJourneysSearch.this, ActivitySeatsBookingManagement2.class)
                            .putExtra("journeyID", journeyID)
                            .putExtra("agencyID", agencyID);
                    startActivity(intent);
                });

            }

            @NonNull
            @Override
            public MyViewHolder_Route onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                LayoutInflater inflater = LayoutInflater.from(ActivityJourneysSearch.this);
                View view = inflater.inflate(R.layout.row_results_route, parent, false);
                return new MyViewHolder_Route(view);
            }
        };

        recyclerView = findViewById(R.id.row_search);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }


}