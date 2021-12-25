package com.example.regester.auth;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.example.regester.models.User;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

import static com.example.regester.constants.CommonConstants.PATH_AGENCIES;
import static com.example.regester.constants.CommonConstants.PATH_CLERKS;
import static com.example.regester.constants.CommonConstants.PATH_CODES;
import static com.example.regester.constants.CommonConstants.PATH_USERS;
import static com.example.regester.constants.CommonConstants.PATH_USER_PASSENGER;
import static com.example.regester.constants.CommonConstants.PATH_USER_TYPE_AGENCY;
import static com.example.regester.constants.CommonConstants.PATH_USER_TYPE_CLERK;
import static com.example.regester.constants.CommonConstants.PATH_USER_TYPE_PASSENGER;
import static com.example.regester.constants.CommonConstants.SHARED_PREF;

public class ActivitySignUp extends AppCompatActivity implements View.OnClickListener {

    //Firebase
    private FirebaseAuth auth;
    private FirebaseDatabase db_root;
    private DatabaseReference ref_users, ref_codes, ref_codes_agency, ref_codes_clerk, ref_clerk_num;

    //widgets
    private TextInputLayout m_til_clerk_code, m_til_codeAgency;
    private EditText m_et_userName, m_et_phoneNumber, m_et_password, m_et_confirmPassword;
    private Spinner m_sp_userType, m_sp_agencyName, m_sp_clerkNumber;
    private Button m_btn_signUp, m_btn_signIn;


    private String selectedUserType = "agency", m_selectedAgencyName;
    private String m_password, m_phoneNumber, m_userName;
    SharedPreferences sharedPreferences;
    private static final String TAG = "TAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        initialize_DB_Refs_and_Auth();
        initializeViews();
        manage_UserType_Selection();
        manage_Agency_Selection();


    }

    private void initialize_DB_Refs_and_Auth() {

        auth = FirebaseAuth.getInstance();
        db_root = FirebaseDatabase.getInstance();

        ref_users = db_root.getReference(PATH_USERS);
        ref_codes = db_root.getReference(PATH_CODES);
        ref_codes_agency = ref_codes.child(PATH_AGENCIES);
        ref_codes_clerk = ref_codes.child(PATH_CLERKS);

        sharedPreferences = getSharedPreferences(SHARED_PREF, MODE_PRIVATE);

    }

    private void initializeViews() {
        m_et_userName = findViewById(R.id.et_user_name);
        m_et_phoneNumber = findViewById(R.id.et_phone_number);
        m_et_password = findViewById(R.id.et_password);
        m_et_confirmPassword = findViewById(R.id.et_confirm_password);


        m_til_clerk_code = findViewById(R.id.til_clerk_code);
        m_til_codeAgency = findViewById(R.id.til_code_agency);

        m_btn_signIn = findViewById(R.id.btn_sign_in);
        m_btn_signIn.setOnClickListener(this);

        m_btn_signUp = findViewById(R.id.btn_sign_up);
        m_btn_signUp.setOnClickListener(this);

        m_sp_userType = findViewById(R.id.sp_user_type);
        m_sp_agencyName = findViewById(R.id.sp_agency_name);
        m_sp_clerkNumber = findViewById(R.id.sp_clerk_no);


    }

    private void manage_UserType_Selection() {
        m_sp_userType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (view != null) {
                    selectedUserType = ((TextView) view).getText().toString().toLowerCase();

                    manageCodeVisibility(selectedUserType, m_sp_agencyName);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void manage_Agency_Selection() {
        m_sp_agencyName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (view != null) {
                    m_selectedAgencyName = ((TextView) view).getText().toString().toLowerCase();

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

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_sign_up) {
            startSignUp();

        }

        if (view.getId() == R.id.btn_sign_in) {
            Intent intent = new Intent(ActivitySignUp.this, ActivitySignIn.class);
            startActivity(intent);
            finish();
        }


    }

    private boolean validateCredentials(String phoneNumber, String password, String userName, Context context) {
        if (TextUtils.isEmpty(phoneNumber) &&
                !Patterns.PHONE.matcher(phoneNumber).matches()) {
            Toast.makeText(context, "Please enter a valid phone number!", Toast.LENGTH_SHORT).show();
            return false;
        } else if (TextUtils.isEmpty(password)) {
            Toast.makeText(context, "Please enter a valid password!", Toast.LENGTH_SHORT).show();
            return false;

        } else if (TextUtils.isEmpty(userName)) {
            Toast.makeText(context, "Please enter a valid username!", Toast.LENGTH_SHORT).show();
            return false;

        }
        return true;

    }

    private void checkUserTypeValidation(String m_phoneNumber) {
        switch (selectedUserType) {
            case "agency": {
                Log.d(TAG, "checkUserTypeValidation: agency " + m_phoneNumber);

                ref_codes_agency.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.hasChild(m_phoneNumber)) {
                            //TODO manage AgencyName vs AgencyPhoneNumber
                            signUp(PATH_USER_TYPE_AGENCY);

                        } else {
                            Toast.makeText(ActivitySignUp.this,
                                    "Authentication failed.You have to contact developer in order to create Agency account! ", Toast.LENGTH_SHORT).show();
                        }


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
            break;
            case "clerk": {
                Log.d(TAG, "checkUserTypeValidation: clerk " + m_phoneNumber);

                ref_codes_clerk.child(m_selectedAgencyName).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.hasChild(m_phoneNumber)) {
                            signUp(PATH_USER_TYPE_CLERK);

                        } else {

                            Toast.makeText(ActivitySignUp.this,
                                    "Authentication failed.You have to contact your agency in order to create Clerk account! "
                                    , Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
            break;
        }


    }

    private void startSignUp() {
        m_phoneNumber = m_et_phoneNumber.getText().toString();
        m_password = m_et_password.getText().toString();
        m_userName = m_et_userName.getText().toString();
        if (!validateCredentials(
                m_phoneNumber,
                m_password,
                m_userName, this)

        ) return;
        switch (selectedUserType) {
            case "agency": {
                checkUserTypeValidation(m_phoneNumber);
            }
            break;
            case "clerk": {
                checkUserTypeValidation(m_phoneNumber);
            }
            break;
            case "passenger": {
                signUp(PATH_USER_TYPE_PASSENGER);
            }
            break;
        }


    }

    private void signUp(String userTypePath) {
        Log.d(TAG, "enter signUp: ");
        auth.createUserWithEmailAndPassword(m_phoneNumber.concat("@gmail.com"), m_password)
                .addOnSuccessListener(authResult -> manageSignUpSuccess(userTypePath))
                .addOnFailureListener(e -> manageSignUpFailure(e));


    }

    private void manageSignUpFailure(Exception e) {
        Log.d(TAG, "manageSignUpFailure: ");
        String msg = "";

        msg = e.getMessage();

        if (e.getMessage().contains("[<!DOCTYPE"))
            msg = "Please check VPN!";

        Toast.makeText(ActivitySignUp.this,
                "Authentication failed. " + msg, Toast.LENGTH_SHORT).show();
    }

    private void manageSignUpSuccess(String userTypePath) {
        Log.d(TAG, "manageSignUpSuccess: ");
        addUserToDB(createUser());
        saveUserInfoToLocalStorage();
        navigateToUserMainActivity(userTypePath, getBaseContext());
    }

    private User createUser() {
        User user = new User();
        user.setUserName(m_et_userName.getText().toString());
        user.setUserEmail(m_et_phoneNumber.getText().toString().concat("@gmail.com"));
        user.setUserPhoneNumber(m_et_phoneNumber.getText().toString());
        return user;

    }

    private void addUserToDB(User user) {
        ref_users.child(PATH_USER_TYPE_AGENCY).child(Objects.requireNonNull(auth.getCurrentUser()).getUid())
                .setValue(user);
        Toast.makeText(ActivitySignUp.this, "Successful Registration", Toast.LENGTH_SHORT).show();
    }

    private void saveUserInfoToLocalStorage() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String phone_number = String.valueOf(m_et_phoneNumber.getText());
        String userName = String.valueOf(m_et_userName.getText());
        editor.putString("phone_number", phone_number);
        editor.putString("userName", userName);
        editor.apply();
    }

    private void navigateToUserMainActivity(String userTypePath, Context context) {
        switch (userTypePath) {
            case PATH_USER_TYPE_AGENCY: {
                startActivity(new Intent(context, ActivityMainAgency.class)
                        .putExtra("AgencyNumber", String.valueOf(m_et_phoneNumber.getText()))
                        .putExtra("AgencyName", String.valueOf(m_sp_agencyName.getSelectedItem())));
                finish();

            }
            break;
            case PATH_USER_TYPE_CLERK: {
                startActivity(new Intent(context, ActivityMainClerk.class));
                finish();


            }
            break;
            case PATH_USER_PASSENGER: {
                startActivity(new Intent(context, ActivityMainPassenger.class));
                finish();


            }
            break;


        }

    }


}