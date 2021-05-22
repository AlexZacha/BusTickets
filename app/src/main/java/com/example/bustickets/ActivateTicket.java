package com.example.bustickets;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.bustickets.GetInformations.GetBuses;
import com.example.bustickets.fragments.HomeFragment;

import java.util.ArrayList;

public class ActivateTicket extends AppCompatActivity {
    private GetBuses getBuses = new GetBuses();

    String[] tickets = {"-- ΔΙΑΛΕΞΤΕ ΕΙΣΗΤΗΡΙΟ --", "ΒΑΣΙΚΟ ΕΙΣΙΤΗΡΙΟ","ΒΑΣΙΚΟ ΕΙΣΙΤΗΡΙΟ (ΜΕΙΩΜΕΝΟ)", "ΧΡΟΝΙΚΟ ΕΙΣΙΤΗΡΙΟ ΔΥΟ (2) ΜΕΤΑΚΙΝΗΣΕΩΝ","ΧΡΟΝΙΚΟ ΕΙΣΙΤΗΡΙΟ ΔΥΟ (2) ΜΕΤΑΚΙΝΗΣΕΩΝ (ΜΕΙΩΜΕΝΟ)", "ΧΡΟΝΙΚΟ ΕΙΣΙΤΗΡΙΟ ΤΡΙΩΝ (3) ΜΕΤΑΚΙΝΗΣΕΩΝ","ΧΡΟΝΙΚΟ ΕΙΣΙΤΗΡΙΟ ΤΡΙΩΝ (3) ΜΕΤΑΚΙΝΗΣΕΩΝ (ΜΕΙΩΜΕΝΟ)", "ΧΡΟΝΙΚΟ ΕΙΣΙΤΗΡΙΟ ΤΕΣΣΑΡΩΝ (4)", "ΧΡΟΝΙΚΟ ΕΙΣΙΤΗΡΙΟ ΤΕΣΣΑΡΩΝ (4) (ΜΕΙΩΜΕΝΟ)"};
    Spinner spinnerTickets;

    String[] directions = {"-- ΔΙΑΛΕΞΤΕ ΚΑΤΕΥΘΥΝΣΗ --", "ΜΕΤΑΒΑΣΗ", "ΕΠΙΣΤΡΟΦΗ"};
    Spinner spinnerDirections;

    Spinner spinnerBuses;
    Button buttonAct;

    DataBase dataBase = new DataBase(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activate_ticket);

        int ticketType = getIntent().getIntExtra("ticketType", 0) + 1;

        spinnerTickets = findViewById(R.id.spinnerTickets);
        spinnerDirections = findViewById(R.id.spinnerDirection);
        spinnerBuses = findViewById(R.id.spinnerBuses);
        buttonAct = findViewById(R.id.buttonAct);

        ArrayList<String> arrayList;
        arrayList = getBuses.getBuses(this, StaticFinalVariables.getNameOfSelectedCity());

        ArrayAdapter<String> arrayAdapterTickets = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, tickets);
        ArrayAdapter<String> arrayAdapterDirections = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, directions);
        ArrayAdapter<String> arrayAdapterBuses = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, arrayList);

        spinnerTickets.setAdapter(arrayAdapterTickets);
        spinnerDirections.setAdapter(arrayAdapterDirections);
        spinnerBuses.setAdapter(arrayAdapterBuses);

        spinnerTickets.setSelection(ticketType);



        buttonAct.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                if (spinnerTickets.getSelectedItemPosition() != 0 && spinnerDirections.getSelectedItemPosition() != 0){
                    dataBase.activateTicket(spinnerTickets.getSelectedItem().toString(), spinnerBuses.getSelectedItem().toString(), spinnerDirections.getSelectedItem().toString());
                    StaticFinalVariables.setTicketType(spinnerTickets.getSelectedItemPosition());
                    Intent intent = new Intent(getApplicationContext(), Options.class);
                    startActivity(intent);
                }
                else Toast.makeText(getApplicationContext(), "Παρακαλώ διαλέξτε όλα τα πεδία", Toast.LENGTH_SHORT).show();
            }
        });

    }
}