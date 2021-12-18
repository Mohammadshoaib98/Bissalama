package com.example.regester.activites;

import static com.example.regester.utils.CommonConstants.PATH_AGENCIES_JOURNEYS;
import static com.example.regester.utils.CommonConstants.PATH_JOURNEY_DATE;
import static com.example.regester.utils.CommonConstants.PATH_JOURNEY_TIME;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.regester.R;
import com.example.regester.models.Journey;
import com.example.regester.utils.MyViewHolder_Journey;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.Calendar;

public class ActivityAgencyJourneysDisplay extends AppCompatActivity {

    RecyclerView recyclerView;
    Query query;
    FirebaseRecyclerAdapter<Journey, MyViewHolder_Journey> myAdapter;
    FirebaseDatabase db_root;
    DatabaseReference journeys_ref;
    int year, month, day, hour, minute;
    public static final String SHARED_PREF = "SHARED_PREF";
    public static final String AGENCYID = "AGENCYID";
    private static final String JOURNEYID = "JOURNEYID";
    public static final String PATH_AGENCY_NAME = "agencyName";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journeys_display);
        db_root = FirebaseDatabase.getInstance();
        Intent a = getIntent();
        String agencyId = a.getStringExtra("agencyId");
        journeys_ref = db_root.getReference(PATH_AGENCIES_JOURNEYS).child(agencyId);
        query = journeys_ref.orderByChild(PATH_AGENCY_NAME);
        FirebaseRecyclerOptions<Journey> options = new FirebaseRecyclerOptions.Builder<Journey>().setQuery(query, Journey.class).build();


        myAdapter = new FirebaseRecyclerAdapter<Journey, MyViewHolder_Journey>(options) {
            @Override
            protected void onBindViewHolder(@NonNull MyViewHolder_Journey viewHolder, int position, @NonNull Journey model) {

                final String journeyKey = getRef(position).getKey();
                viewHolder.setAgencyName(model.getAgencyName());
                viewHolder.setJourneyDate(model.getJourneyDate());
                viewHolder.setJourneyTime(model.getJourneyTime());
                viewHolder.setSourceCity(model.getSourceCity());
                viewHolder.setDestinationCity(model.getDestinationCity());

                viewHolder.itemView.findViewById(R.id.btn_delete).setOnClickListener(view -> {
                    AlertDialog.Builder builder = new AlertDialog.Builder(viewHolder.itemView.getContext());
                    builder.setTitle("Delete Panel");
                    builder.setMessage("Do You Want Delete It ?");
                    builder.setPositiveButton(" Yes ", (dialogInterface, i) -> {
                        db_root = FirebaseDatabase.getInstance();
                        journeys_ref = db_root.getReference(PATH_AGENCIES_JOURNEYS).child(agencyId);
                        journeys_ref.child(journeyKey).removeValue();
                    });
                    builder.setNegativeButton(" No ", (dialogInterface, i) -> {

                    });
                    builder.show();
                });


                saveData(agencyId, journeyKey);

                Log.d("TAG", "onBindViewHolder: ");


                viewHolder.itemView.findViewById(R.id.button_update).

                        setOnClickListener(v ->

                        {
                            final DialogPlus dialogPlus = DialogPlus.newDialog(viewHolder.itemView.getContext())
                                    .setContentHolder(new ViewHolder(R.layout.activity_update))
                                    .setExpanded(true, 750)
                                    .create();
                            dialogPlus.show();


                            View myView = dialogPlus.getHolderView();
                            TextView m_tv_uSourceCity = findViewById(R.id.tv_update_source_city);
                            TextView m_tv_uDestinationCity = findViewById(R.id.tv_update_destination_city);
                            TextView m_tv_update_date = findViewById(R.id.tv_update_date);
                            TextView m_tv_update_time = findViewById(R.id.tv_update_time);
                            m_tv_uSourceCity.setText(model.getSourceCity());
                            m_tv_uDestinationCity.setText(model.getDestinationCity());
                            m_tv_update_date.setText(model.getJourneyDate());
                            m_tv_update_time.setText(model.getJourneyTime());
                            findViewById(R.id.btn_update_time).setOnClickListener(view -> {
                                final Calendar calendar = Calendar.getInstance();
                                hour = calendar.get(Calendar.HOUR_OF_DAY);
                                minute = calendar.get(Calendar.MINUTE);
                                TimePickerDialog timePickerDialog = new TimePickerDialog(ActivityAgencyJourneysDisplay.this, (view1, hourOfDay, minute) -> m_tv_update_time.setText(hourOfDay + ":" + minute), hour, minute, false);
                                timePickerDialog.show();
                            });
                            findViewById(R.id.btn_update_date).setOnClickListener(view -> {
                                final Calendar calendar = Calendar.getInstance();
                                year = calendar.get(Calendar.YEAR);
                                month = calendar.get(Calendar.MONTH);

                                day = calendar.get(Calendar.DAY_OF_MONTH);

                                DatePickerDialog datePickerDialog = new DatePickerDialog(ActivityAgencyJourneysDisplay.this, (view12, year, month, dayOfMonth) -> m_tv_update_date.setText(year + "/" + month + "/" + dayOfMonth), year, month, day);
                                datePickerDialog.show();
                            });
                            findViewById(R.id.btn_save_update).setOnClickListener(view -> {

                                journeys_ref = db_root.getReference(PATH_AGENCIES_JOURNEYS).child(agencyId).child(journeyKey);
                                journeys_ref.child(PATH_JOURNEY_DATE).setValue(m_tv_update_date.getText().toString());
                                journeys_ref.child(PATH_JOURNEY_TIME).setValue(m_tv_update_time.getText().toString());

                                journeys_ref.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        Toast.makeText(ActivityAgencyJourneysDisplay.this, "Data Updated", Toast.LENGTH_SHORT).show();
                                        dialogPlus.dismiss();
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {
                                        dialogPlus.dismiss();
                                    }
                                });
                            });
                            findViewById(R.id.btn_cancel_update).setOnClickListener(view -> {
                                dialogPlus.dismiss();
                            });


                        });

            }

            @NonNull
            @Override
            public MyViewHolder_Journey onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                LayoutInflater inflater = LayoutInflater.from(ActivityAgencyJourneysDisplay.this);
                View view = inflater.inflate(R.layout.row_journey, parent, false);
                return new MyViewHolder_Journey(view);
            }
        };

        recyclerView = findViewById(R.id.recycler_journey_list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(myAdapter);
        myAdapter.startListening();


    }


    public void saveData(String a, String b) {

        SharedPreferences sharedPrefs = getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.putString(AGENCYID, String.valueOf(a));
        editor.putString(JOURNEYID, String.valueOf(b));
        editor.apply();
        Toast.makeText(this, "Data Saved", Toast.LENGTH_SHORT).show();


    }
}

    // in Activity display
    // intent.putExtra("agencyID",agencyID);// get it when click on journey card
    //  intent.putExtra("journeyKey",journeyKey);/// get it when click on journey card

    // in Activity bookment
    //agencyID = getIntent().getString("agencyID");
    //journeyKey= getIntent().getString("journeyKey");

    //db_root.child(PATH_JOURNEYS).child(agencyID).child(journeyKey).child(PATH_JOURNEY_PASSENGERS_IDS) [seat_view_order]=...uid
