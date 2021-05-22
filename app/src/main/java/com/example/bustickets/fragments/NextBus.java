package com.example.bustickets.fragments;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.bustickets.GetInformations.GetBuses;
import com.example.bustickets.Options;
import com.example.bustickets.R;
import com.example.bustickets.StaticFinalVariables;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

@RequiresApi(api = Build.VERSION_CODES.O)
public class NextBus extends AppCompatActivity {
    private GetBuses getBuses = new GetBuses();

    String[] directions = {"-- ΔΙΑΛΕΞΤΕ ΚΑΤΕΥΘΥΝΣΗ --", "ΜΕΤΑΒΑΣΗ", "ΕΠΙΣΤΡΟΦΗ"};
    Spinner spinnerDirections;

    Spinner spinnerBuses;
    Button buttonAct;

    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
    LocalDateTime now = LocalDateTime.now();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_next_bus);

        spinnerDirections = findViewById(R.id.spinnerDirection);
        spinnerBuses = findViewById(R.id.spinnerBuses);
        buttonAct = findViewById(R.id.buttonAct);

        ArrayList<String> arrayList;
        arrayList = getBuses.getBuses(this, StaticFinalVariables.getNameOfSelectedCity());

        ArrayAdapter<String> arrayAdapterDirections = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, directions);
        ArrayAdapter<String> arrayAdapterBuses = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, arrayList);

        spinnerDirections.setAdapter(arrayAdapterDirections);
        spinnerBuses.setAdapter(arrayAdapterBuses);

        buttonAct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StaticFinalVariables.setNextBus("<h3>Επόμενο Λεωφορείο</h3> <br><b>Λεοφοείο:</b> " + spinnerBuses.getSelectedItem().toString() + "<br><b>Κατεύθυνση:</b> " + spinnerDirections.getSelectedItem().toString() + "<br><b>Ημερομηνία ακύρωσης:</b> " + dtf.format(now));
                Intent intent = new Intent(getApplicationContext(), Options.class);
                StaticFinalVariables.setNext(true);
                startActivity(intent);
            }
        });
    }
}