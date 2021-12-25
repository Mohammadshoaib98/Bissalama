package com.example.regester.auth;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.regester.R;
import com.example.regester.agency.ActivityMainAgency;
import com.example.regester.clerk.ActivityMainClerk;
import com.example.regester.passenger.ActivityMainPassenger;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static com.example.regester.constants.CommonConstants.PATH_USERS;
import static com.example.regester.constants.CommonConstants.PATH_USER_PASSENGER;
import static com.example.regester.constants.CommonConstants.PATH_USER_TYPE_AGENCY;
import static com.example.regester.constants.CommonConstants.PATH_USER_TYPE_CLERK;
import static com.example.regester.constants.CommonConstants.PATH_USER_TYPE_PASSENGER;


public class ActivitySignIn extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "Login";
    private FirebaseAuth auth;
    private Button m_btn_signUp, m_btn_signIn;
    private EditText m_et_phoneNumber, m_et_password;
    private Spinner m_sp_login_userType, m_sp_login_agency_name;
    private DatabaseReference ref_users;
    private String selectedUserType = "agency";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        initialize_DB_Refs_and_Auth();
        initializeViews();
        manage_UserType_Selection();

    }

    private void initialize_DB_Refs_and_Auth() {
        auth = FirebaseAuth.getInstance();
        FirebaseDatabase db_root = FirebaseDatabase.getInstance();
        ref_users = db_root.getReference(PATH_USERS);
    }

    private void initializeViews() {
        m_et_phoneNumber = findViewById(R.id.et_phone_number);
        m_et_password = findViewById(R.id.et_password);

        m_btn_signUp = findViewById(R.id.btn_sign_up);
        m_btn_signUp.setOnClickListener(this);

        m_btn_signIn = findViewById(R.id.btn_sign_in);
        m_btn_signIn.setOnClickListener(this);


        m_sp_login_userType = findViewById(R.id.sp_user_type);
        m_sp_login_agency_name = findViewById(R.id.sp_agency_name);


    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_sign_in) {
            startSignIn();

        }

        if (view.getId() == R.id.btn_sign_up) {
            startSignUp();
        }


    }

    private void manage_UserType_Selection() {
        m_sp_login_userType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (view != null) {
                    selectedUserType = ((TextView) view).getText().toString().toLowerCase();

                    manageCodeVisibility(selectedUserType, m_sp_login_agency_name);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void manageCodeVisibility(String selectedItem, Spinner spnAgencyName) {
        switch (selectedItem) {
            case "clerk":
                spnAgencyName.setVisibility(View.VISIBLE);
                break;
            case "agency":
                spnAgencyName.setVisibility(View.VISIBLE);
                break;
            case "passenger":
                spnAgencyName.setVisibility(View.GONE);
                break;
        }
    }

    private void startSignIn() {
        if (!validateCredentials()) return;
        switch (selectedUserType) {
            case "agency": {
                sigIn(PATH_USER_TYPE_AGENCY);
            }
            break;
            case "clerk": {
                sigIn(PATH_USER_TYPE_CLERK);
            }
            break;
            case "passenger": {
                Log.d(" type passenger", "passenger");
                sigIn(PATH_USER_TYPE_PASSENGER);
            }
            break;
        }


    }

    private void startSignUp() {
        Intent intent = new Intent(ActivitySignIn.this, ActivitySignUp.class);
        startActivity(intent);
        finish();
    }

    private boolean validateCredentials() {
        if (TextUtils.isEmpty(m_et_phoneNumber.getText().toString()) &&
                !Patterns.PHONE.matcher(m_et_phoneNumber.getText().toString()).matches()) {
            Toast.makeText(ActivitySignIn.this, "Please enter a valid phone number!", Toast.LENGTH_SHORT).show();
            return false;
        } else if (TextUtils.isEmpty(m_et_password.getText().toString())) {
            Toast.makeText(ActivitySignIn.this, "Please enter a valid password!", Toast.LENGTH_SHORT).show();
            return false;

        }
        return true;

    }

    private void sigIn(String userTypePath) {

        auth.signInWithEmailAndPassword(
                m_et_phoneNumber.getText().toString().concat("@gmail.com"),
                m_et_password.getText().toString()).addOnSuccessListener(authResult -> {
            ref_users.child(userTypePath).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.hasChild(auth.getUid())) {
                        navigateToUserMainActivity(userTypePath);

                    } else {
                        Toast.makeText(ActivitySignIn.this, "Authentication failed",
                                Toast.LENGTH_SHORT).show();
                    }
                }


                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });


        })
                .addOnFailureListener(e -> {
                    String msg = "";

                    msg = e.getMessage();

                    if (e.getMessage().contains("[<!DOCTYPE"))
                        msg = "Please check VPN!";

                    Toast.makeText(ActivitySignIn.this,
                            "Authentication failed. " + msg, Toast.LENGTH_SHORT).show();
                });
        ;
    }

    private void navigateToUserMainActivity(String userTypePath) {
        switch (userTypePath) {
            case PATH_USER_TYPE_AGENCY: {
                startActivity(new Intent(ActivitySignIn.this, ActivityMainAgency.class)
                        .putExtra("AgencyNumber", String.valueOf(m_et_phoneNumber.getText()))
                        .putExtra("AgencyName", String.valueOf(m_sp_login_agency_name.getSelectedItem())));
                finish();
            }
            break;
            case PATH_USER_TYPE_CLERK: {
                startActivity(new Intent(ActivitySignIn.this, ActivityMainClerk.class));
                finish();


            }
            break;
            case PATH_USER_PASSENGER: {
                startActivity(new Intent(ActivitySignIn.this, ActivityMainPassenger.class));
                finish();


            }
            break;


        }

    }


}
