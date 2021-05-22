package com.example.bustickets;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bustickets.fragments.AboutTheOrgFragment;
import com.example.bustickets.fragments.DirectionsFragment;
import com.example.bustickets.fragments.HomeFragment;
import com.example.bustickets.fragments.LineBusesFragment;
import com.example.bustickets.fragments.TicketsFragment;
import com.example.bustickets.fragments.Wallet;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class Options extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    private ArrayList<String> arrayList = new ArrayList<>();
    static Boolean bool = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);
        bool = true;

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View headerView = navigationView.getHeaderView(0);
        TextView cityName = (TextView) headerView.findViewById(R.id.nav_header_title);
        cityName.setText(StaticFinalVariables.getNameOfSelectedCity());

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new HomeFragment()).commit();
        navigationView.setCheckedItem(R.id.optionsLayout);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.optionsLayout:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new HomeFragment()).commit();
                bool = true;
                break;
            case R.id.lineInformationLayout:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new LineBusesFragment()).commit();
                bool = false;
                break;
            case R.id.directionsLayout:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new DirectionsFragment()).commit();
                bool = false;
                break;
            case R.id.walletLayout:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new Wallet()).commit();
                bool = false;
                break;
            case R.id.ticketsLayout:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new TicketsFragment()).commit();
                bool = false;
                break;
            case R.id.infoLayout:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new AboutTheOrgFragment()).commit();
                bool = false;
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else if (!bool){
            Intent intent = new Intent(Options.this, Options.class);
            startActivity(intent);
        }else{
            Intent intent = new Intent(Options.this, Select_City.class);
            startActivity(intent);
        }
    }
}