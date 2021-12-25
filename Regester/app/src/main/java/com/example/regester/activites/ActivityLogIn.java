package com.example.regester.activites;

import static com.example.regester.constants.CommonConstants.PATH_USERS;

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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class ActivityLogIn extends AppCompatActivity {

    private static final String TAG = "Login";
    private FirebaseAuth auth;
    private Button m_btnSignUpLink;
    private Button m_btn_goSignIn;
    private EditText m_et_phoneNumberSignIn, m_et_passwordSignIn;
    private Spinner m_sp_logIn_userType, m_sp_login_agency_name;
    private TextView m_tv_register;
    private SharedPreferences sharedPreferences;
    private FirebaseDatabase db_root;
    private DatabaseReference users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        initialize_db_auth();
        initializeViews();
        selectUserType();


    }

    private void initialize_db_auth() {
        auth = FirebaseAuth.getInstance();
        db_root = FirebaseDatabase.getInstance();
        users = db_root.getReference(PATH_USERS);
    }

    private void initializeViews() {
        m_et_phoneNumberSignIn = findViewById(R.id.et_phone_number_signin);
        m_et_passwordSignIn = findViewById(R.id.et_password_sign_in);
        m_btnSignUpLink = findViewById(R.id.btn_sign_up);
        m_sp_logIn_userType = findViewById(R.id.sp_login_user_type);
        m_sp_login_agency_name = findViewById(R.id.sp_login_agency_name);
        m_tv_register = findViewById(R.id.tv_register);
        m_btn_goSignIn = findViewById(R.id.btn_go_sign_in);
        m_btnSignUpLink.setOnClickListener(v -> {
            Intent intent = new Intent(ActivityLogIn.this, ActivitySignUp.class);
            startActivity(intent);
            finish();
        });
        m_btn_goSignIn.setOnClickListener(v -> {
            Log.d(TAG, "onCreate:sign in ");
            if (String.valueOf(m_sp_logIn_userType.getSelectedItem()).equals("Agency")) {
                Log.d(TAG, "onCreate:sign in Agency");

                if (auth.getCurrentUser() != null) {
                    Log.d(TAG, "onCreate:sign in Agency  != null");

                    if (TextUtils.isEmpty(m_et_phoneNumberSignIn.getText().toString()) && !Patterns.EMAIL_ADDRESS
                            .matcher(m_et_phoneNumberSignIn.getText().toString()).matches()) {
                        Toast.makeText(ActivityLogIn.this, "Enter A valid Email Please", Toast.LENGTH_SHORT).show();
                    } else if (TextUtils.isEmpty(m_et_passwordSignIn.getText().toString())) {
                        Toast.makeText(ActivityLogIn.this, "Enter A valid Password Please", Toast.LENGTH_SHORT).show();
                    } else
                       /* users.child("agency").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for (DataSnapshot sp : snapshot.getChildren()) {
                                    String g = sp.getKey();
                                    m_tv_register.setText(g);

                                    String uid = auth.getCurrentUser().getUid();



                         }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                          }
                        });*/
                        auth.signInWithEmailAndPassword(m_et_phoneNumberSignIn.getText().toString().concat("@gmail.com"),
                                m_et_passwordSignIn.getText().toString()).addOnSuccessListener(authResult -> {
                            users.child("agency").addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    for (DataSnapshot sp : snapshot.getChildren()) {
                                        Log.d(TAG, sp.getKey());
                                        Log.d(TAG, auth.getCurrentUser().getUid());

                                        if (sp.getKey().equals(auth.getCurrentUser().getUid())) {


                                           /* SharedPreferences.Editor editor = sharedPreferences.edit();
                                            editor.putString("userTypeAgency", String.valueOf(m_sp_logIn_userType.getSelectedItem()));
                                            editor.apply();*/
                                            startActivity(new Intent(ActivityLogIn.this, MainActivityAgency.class)
                                                    .putExtra("AgencyNumber", String.valueOf(m_et_phoneNumberSignIn.getText()))
                                                    .putExtra("AgencyName", String.valueOf(m_sp_login_agency_name.getSelectedItem())));
                                            finish();
                                        } else
                                            Toast.makeText(ActivityLogIn.this, "Enter A valid Agency Please", Toast.LENGTH_SHORT).show();
                                    }


                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {
                                    Toast.makeText(ActivityLogIn.this, "Enter A valid Agency Please", Toast.LENGTH_SHORT).show();
                                }
                            });


                        }).addOnFailureListener(e -> Toast.makeText(ActivityLogIn.this, "Authentication failed" + e.getMessage(), Toast.LENGTH_SHORT).show());

                }

            } else if (String.valueOf(m_sp_logIn_userType.getSelectedItem()).equals("Clerk")) {

                if (auth.getCurrentUser() != null) {
                    if (TextUtils.isEmpty(m_et_phoneNumberSignIn.getText().toString()) && !Patterns.EMAIL_ADDRESS.matcher(m_et_phoneNumberSignIn.getText().toString()).matches()) {
                        Toast.makeText(ActivityLogIn.this, "Enter A valid Email Please", Toast.LENGTH_SHORT).show();
                    } else if (TextUtils.isEmpty(m_et_passwordSignIn.getText().toString())) {
                        Toast.makeText(ActivityLogIn.this, "Enter A valid Password Please", Toast.LENGTH_SHORT).show();
                    } else
                        auth.signInWithEmailAndPassword(m_et_phoneNumberSignIn.getText().toString().concat("@gmail.com"), m_et_passwordSignIn.getText().toString()).addOnSuccessListener(authResult -> {

                            users.child("clerk").addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    for (DataSnapshot sp : snapshot.getChildren()) {
                                        Log.d(TAG, sp.getKey());
                                        Log.d(TAG, auth.getCurrentUser().getUid());
                                        if (sp.getKey().equals(auth.getCurrentUser().getUid())) {
                                          /*  SharedPreferences.Editor editor = sharedPreferences.edit();
                                            editor.putString("userTypeClerk", String.valueOf(m_sp_logIn_userType.getSelectedItem()));
                                            editor.apply();*/

                                            startActivity(new Intent(ActivityLogIn.this, MainActivityClerk.class));
                                        } else
                                            Toast.makeText(ActivityLogIn.this, "Enter A valid Clerk Please", Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {
                                    Toast.makeText(ActivityLogIn.this, "Enter A valid Clerk Please", Toast.LENGTH_SHORT).show();
                                }
                            });


                        }).addOnFailureListener(e -> Toast.makeText(ActivityLogIn.this, "Authentication failed" + e.getMessage(), Toast.LENGTH_SHORT).show());

                }


            } else if (String.valueOf(m_sp_logIn_userType.getSelectedItem()).equals("Passenger")) {


                if (auth.getCurrentUser() != null) {
                    if (TextUtils.isEmpty(m_et_phoneNumberSignIn.getText().toString()) && !Patterns.EMAIL_ADDRESS.matcher(m_et_phoneNumberSignIn.getText().toString()).matches()) {
                        Toast.makeText(ActivityLogIn.this, "Enter A valid Email Please", Toast.LENGTH_SHORT).show();
                    } else if (TextUtils.isEmpty(m_et_passwordSignIn.getText().toString())) {
                        Toast.makeText(ActivityLogIn.this, "Enter A valid Password Please", Toast.LENGTH_SHORT).show();
                    } else
                        auth.signInWithEmailAndPassword(m_et_phoneNumberSignIn.getText().toString().concat("@gmail.com"), m_et_passwordSignIn.getText().toString()).addOnSuccessListener(authResult -> {
                            users.child("end-User").addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    for (DataSnapshot sp : snapshot.getChildren()) {
                                        Log.d(TAG, sp.getKey());
                                        Log.d(TAG, auth.getCurrentUser().getUid());
                                        if (sp.getKey().equals(auth.getCurrentUser().getUid())) {

                                         /*   SharedPreferences.Editor editor = sharedPreferences.edit();
                                            editor.putString("userTypePass", String.valueOf(m_sp_logIn_userType.getSelectedItem()));
                                            editor.apply();*/
                                            startActivity(new Intent(ActivityLogIn.this, MainActivityPassenger.class));
                                        }
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {
                                    Toast.makeText(ActivityLogIn.this, "Enter A valid Passenger Please", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }).addOnFailureListener(e -> Toast.makeText(ActivityLogIn.this, "Authentication failed" + e.getMessage(), Toast.LENGTH_SHORT).show());


                }


            } else {
                Toast.makeText(ActivityLogIn.this, "Press Sign Up", Toast.LENGTH_SHORT).show();
            }


            // TODO   عند تسجيل الدخول يجب ان ارسل رقم ا
            //  لموبايل واسم الشركة فور الانتهاء من المهمة الحالية

        });

    }

    private void selectUserType() {
        m_sp_logIn_userType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                VisibilityCode();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void VisibilityCode() {


        if (m_sp_logIn_userType.getSelectedItem().toString().equals("Clerk")) {
            m_sp_login_agency_name.setVisibility(View.VISIBLE);
        } else if (m_sp_logIn_userType.getSelectedItem().toString().equals("Agency")) {
            m_sp_login_agency_name.setVisibility(View.VISIBLE);
        } else if (m_sp_logIn_userType.getSelectedItem().toString().equals("Passenger")) {
            m_sp_login_agency_name.setVisibility(View.GONE);
        }
    }


}

