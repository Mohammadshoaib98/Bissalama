package com.example.regester.activites;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.regester.R;
import com.google.android.material.navigation.NavigationView;

public class ActivityMain extends AppCompatActivity {

    DrawerLayout drawer;
    NavigationView navigationView;
    FrameLayout frameLayout;
    ActionBarDrawerToggle toggle;
    ImageView imageView;
    Toolbar toolbar;
    View header;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.top_app_bar);
        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.openDrawer(GravityCompat.START);
            }
        });
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                drawer.closeDrawer(GravityCompat.START);

                switch (id) {
                    case R.id.nav_home:
                        Toast.makeText(ActivityMain.this, "Home is Clicked", Toast.LENGTH_SHORT).show();

                        break;
                    case R.id.nav_my_journeys:
                        Toast.makeText(ActivityMain.this, "My Journeys is Clicked", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_share:
                        Toast.makeText(ActivityMain.this, "Share is Clicked", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        return true;
                }

            return true;
            }



        });

    }}





/*


//        toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open,
//        R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


    toolbar.*/
