package com.example.regester.activites;


import static com.example.regester.utils.CommonConstants.PATH_USERS;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.regester.R;
import com.example.regester.models.User;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class ActivitySignUp extends AppCompatActivity implements View.OnClickListener {

    private static final String SHARED_PREF = "SHARED_PREF";
    private static final String PATH_USER_TYPE_CLERK = "clerk";
    private static final String PATH_USER_TYPE_PASSENGER = "end-User";
    private static final String PATH_CLERKS_AGENCIES = "clerksCodes";
    private static final String TAG = "TAG";
    //Firebase
    FirebaseDatabase db_root = FirebaseDatabase.getInstance();
    DatabaseReference users;
    FirebaseAuth mAuth;
    DatabaseReference ref_clerk_num;
    //widgets
    TextInputLayout clerk_code;
    private EditText m_et_userName, m_et_PhoneNo, m_et_passwordSignUp;
    private EditText m_et_confirmPassword, m_tl_codeClerk, m_tl_codeAgency;
    private Spinner m_sp_userType, m_sp_agency_name, m_sp_clerk_num;
    private Button m_btn_goSignUp, m_btn_SignInLink;
    SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);
        initialize_db_and_auth();
        initializeViews();
        selectUserType();
        m_btn_SignInLink.setOnClickListener(v -> {
            Intent intent = new Intent(ActivitySignUp.this, ActivitySignIn.class);
            startActivity(intent);
            finish();
        });
        sharedPreferences = getSharedPreferences(SHARED_PREF, MODE_PRIVATE);
        m_btn_goSignUp = findViewById(R.id.btn_go_sign_up);
        m_btn_goSignUp.setOnClickListener(this);

    }

    private void selectUserType() {
        m_sp_userType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                VisibilityCode();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void initialize_db_and_auth() {
        FirebaseDatabase mFirebaseDatabase;
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        users = mFirebaseDatabase.getReference(PATH_USERS);
    }

    private void initializeViews() {
        clerk_code = findViewById(R.id.clerk_code);
        m_et_userName = findViewById(R.id.et_user_name);
        m_et_PhoneNo = findViewById(R.id.et_phone_no);
        m_tl_codeClerk = findViewById(R.id.tl_code_clerk);
        m_tl_codeAgency = findViewById(R.id.tl_code_agency);
        m_et_passwordSignUp = findViewById(R.id.et_password_sign_up);
        m_et_confirmPassword = findViewById(R.id.et_confirm_password);
        m_btn_SignInLink = findViewById(R.id.btn_sign_in_link);
        m_sp_userType = findViewById(R.id.sp_user_type);
        m_sp_agency_name = findViewById(R.id.sp_agency_name);
        m_sp_clerk_num = findViewById(R.id.sp_clerk_num);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_go_sign_up) {
            signUpButton();
            db_root = FirebaseDatabase.getInstance();
            users = db_root.getReference(PATH_USERS);
        }

    }

    private void signUpButton() {
       /* if (TextUtils.isEmpty(m_et_userName.getText().toString()) && m_et_userName.getText().toString().length() < 1) {
            Toast.makeText(ActivitySignUp.this, "Please Enter a valid Name", Toast.LENGTH_LONG).show();
        } else if (TextUtils.isEmpty(m_et_passwordSignUp.getText().toString())) {
            Toast.makeText(ActivitySignUp.this, "Please Enter a valid Password", Toast.LENGTH_LONG).show();
        } else if (TextUtils.isEmpty(m_et_confirmPassword.getText().toString()) && !m_et_confirmPassword.getText().toString().contains("@")) {
            Toast.makeText(ActivitySignUp.this, "Please Enter a valid Password", Toast.LENGTH_LONG).show();
        } else if (!m_et_passwordSignUp.getText().toString().equalsIgnoreCase(m_et_confirmPassword.getText().toString())) {
            Toast.makeText(ActivitySignUp.this, "Passwords do not match", Toast.LENGTH_LONG).show();
            m_et_passwordSignUp.setText("");
            m_et_confirmPassword.setText("");
        } else if (TextUtils.isEmpty(m_et_PhoneNo.getText().toString()) && m_et_PhoneNo.getText().toString().length() < 2) {
            Toast.makeText(ActivitySignUp.this, "Please Enter a valid Phone Number", Toast.LENGTH_LONG).show();
        } else */
        SelectedTypeUser();

    }

    private void SelectedTypeUser() {

        if (String.valueOf(m_sp_userType.getSelectedItem()).equals("Agency")) {
            mAuth.createUserWithEmailAndPassword(m_et_PhoneNo.getText().toString().concat("@gmail.com"), m_et_passwordSignUp.getText().toString()).addOnSuccessListener(authResult -> {
                User user = new User();
                user.setFullName(m_et_userName.getText().toString());
                user.setEmail(m_et_PhoneNo.getText().toString().concat("@gmail.com"));
                user.setPhone(m_et_PhoneNo.getText().toString());
                users.child("agency").child(Objects.requireNonNull(mAuth.getCurrentUser()).getUid())
                        .setValue(user)
                        .addOnSuccessListener(aVoid -> Toast.makeText(ActivitySignUp.this, "Registration Successful ", Toast.LENGTH_SHORT).show());

                startActivity(new Intent(ActivitySignUp.this, ActivityMainAgency.class)
                        .putExtra("AgencyNumber", String.valueOf(m_et_PhoneNo.getText()))
                        .putExtra("AgencyName", String.valueOf(m_sp_agency_name.getSelectedItem())));
                finish();
            }).addOnFailureListener(e -> Toast.makeText(ActivitySignUp.this, "Authentication Failed" + e.getMessage(), Toast.LENGTH_SHORT).show());
        } else if (String.valueOf(m_sp_userType.getSelectedItem()).equals("Clerk")) {
            String agencyName = String.valueOf(m_sp_agency_name.getSelectedItem());
            String clerk_num = String.valueOf(m_sp_clerk_num.getSelectedItem());
            db_root = FirebaseDatabase.getInstance();
     /*       ref_clerk_num = db_root.getReference(PATH_CLERKS_AGENCIES).child(agencyName).child(clerk_num);
            ref_clerk_num.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot sp : snapshot.getChildren()) {
                        Log.d(TAG, "message" + sp.child("code").getValue());
                        if (Objects.requireNonNull(sp.child("code").getValue()).equals(m_tl_codeClerk.getText())) {
                            Log.d(TAG, "message" + sp.getValue());*/
            mAuth.createUserWithEmailAndPassword(m_et_PhoneNo.getText().toString().concat("@gmail.com"),
                    m_et_passwordSignUp.getText().toString()).addOnSuccessListener(authResult -> {

                User user = new User();
                user.setFullName(m_et_userName.getText().toString());
                user.setEmail(m_et_PhoneNo.getText().toString().concat("@gmail.com"));
                user.setPhone(m_et_PhoneNo.getText().toString());
                users.child(PATH_USER_TYPE_CLERK).child(Objects.requireNonNull(mAuth.getCurrentUser()).getUid())
                        .setValue(user)
                        .addOnSuccessListener(aVoid -> Toast.makeText(ActivitySignUp.this, "Registration Successful ", Toast.LENGTH_SHORT).show());

                startActivity(new Intent(ActivitySignUp.this, ActivityMainClerk.class).putExtra("AgencyName", agencyName).putExtra("Clerk_Num", clerk_num));
                finish();
            })

                    .addOnFailureListener(e -> Toast.makeText(ActivitySignUp.this, "Authentication Failed" + e.getMessage(), Toast.LENGTH_SHORT).show());


        }
/*

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(ActivitySignUp.this, "Enter A valid Information Clerk", Toast.LENGTH_SHORT).show();
                }
            });

        }*/

        //select type user passenger and send his numPhone for Book operation.
        else if (String.valueOf(m_sp_userType.getSelectedItem()).equals("Passenger")) {
            mAuth.createUserWithEmailAndPassword(m_et_PhoneNo.getText().toString().concat("@gmail.com"), m_et_passwordSignUp.getText().toString()).addOnSuccessListener(authResult -> {

                User user = new User();
                String numPass = String.valueOf(m_et_PhoneNo.getText());
                String namPass = String.valueOf(m_et_userName.getText());
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("NUMPASS", numPass);
                editor.putString("NAMPASS", namPass);
                editor.apply();

                user.setFullName(m_et_userName.getText().toString());
                user.setEmail(numPass.concat("@gmail.com"));
                user.setPhone(m_et_PhoneNo.getText().toString());

                users.child(PATH_USER_TYPE_PASSENGER).child(Objects.requireNonNull(mAuth.getCurrentUser()).getUid())
                        .setValue(user)
                        .addOnSuccessListener(aVoid -> Toast.makeText(ActivitySignUp.this, "Registration Successful ", Toast.LENGTH_SHORT).show());

                startActivity(new Intent(ActivitySignUp.this, ActivityMainPassenger.class).putExtra("userType", m_sp_userType.getSelectedItem().toString()));

                finish();
            }).addOnFailureListener(e -> Toast.makeText(ActivitySignUp.this, "Authentication Failed" + e.getMessage(), Toast.LENGTH_SHORT).show());

        }
    }

    private void VisibilityCode() {


        if (m_sp_userType.getSelectedItem().toString().equals("Clerk")) {
            m_tl_codeAgency.setVisibility(View.GONE);
            clerk_code.setVisibility(View.VISIBLE);
            m_sp_agency_name.setVisibility(View.VISIBLE);
            m_sp_clerk_num.setVisibility(View.VISIBLE);
        } else if (m_sp_userType.getSelectedItem().toString().equals("Agency")) {
            m_tl_codeAgency.setVisibility(View.VISIBLE);
            clerk_code.setVisibility(View.GONE);
            m_sp_agency_name.setVisibility(View.VISIBLE);
            m_sp_clerk_num.setVisibility(View.GONE);

        } else if (m_sp_userType.getSelectedItem().toString().equals("Passenger")) {
            m_tl_codeAgency.setVisibility(View.GONE);
            clerk_code.setVisibility(View.GONE);
            m_sp_agency_name.setVisibility(View.GONE);
            m_sp_clerk_num.setVisibility(View.GONE);
        }
    }
}