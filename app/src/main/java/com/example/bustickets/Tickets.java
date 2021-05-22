package com.example.bustickets;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.paypal.android.sdk.payments.PayPalConfiguration;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class Tickets extends AppCompatActivity {

    String[] tickets = {"-- ΔΙΑΛΕΞΤΕ ΕΙΣΗΤΗΡΙΟ --", "ΒΑΣΙΚΟ ΕΙΣΙΤΗΡΙΟ", "ΧΡΟΝΙΚΟ ΕΙΣΙΤΗΡΙΟ ΔΥΟ (2) ΜΕΤΑΚΙΝΗΣΕΩΝ", "ΧΡΟΝΙΚΟ ΕΙΣΙΤΗΡΙΟ ΤΡΙΩΝ (3) ΜΕΤΑΚΙΝΗΣΕΩΝ", "ΧΡΟΝΙΚΟ ΕΙΣΙΤΗΡΙΟ ΤΕΣΣΑΡΩΝ (4)"};
    String[] quantity = {"-- ΔΙΑΛΕΞΤΕ ΠΟΣΟΤΗΤΑ --", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
    String[] payment = {"-- ΔΙΑΛΕΞΤΕ ΤΡΟΠΟ ΠΛΗΡΩΜΗΣ --", "Πορτοφόλι", "PayPal - Κάρτα Τραπέζης", "PaySafeCard"};
    String[] type = {"-- ΔΙΑΛΕΞΤΕ ΕΙΔΟΣ ΕΙΣΗΤΗΡΙΟΥ --", "ΚΑΝΟΝΙΚΟ", "ΜΕΙΩΜΕΝΟ"};

    double[] prices = {0, 0.90, 1.10, 1.30, 1.80};

    Spinner spinnerTickets;
    Spinner spinnerType;
    Spinner spinnerQuantity;
    Spinner spinnerPayment;

    TextView textView, infoPayMethod;

    Button button;

    int posTicket, posType, posQ, posPay;

    double price = 0;

    private static DecimalFormat df2 = new DecimalFormat("#.##");

    NumberFormat formatter = new DecimalFormat("#0.00");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tickets);

        SharedPreferences sp = getSharedPreferences("your_prefs", Activity.MODE_PRIVATE);
        double balance = sp.getFloat("balance", 0);

        spinnerTickets = findViewById(R.id.spinnerTickets);
        spinnerType = findViewById(R.id.spinnerType);
        spinnerQuantity = findViewById(R.id.spinnerQuantity);
        spinnerPayment = findViewById(R.id.spinnerPayment);
        button = findViewById(R.id.continueBtn);
        textView = findViewById(R.id.price);
        infoPayMethod = findViewById(R.id.infoPayMethod);

        ArrayAdapter<String> arrayAdapterTickets = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, tickets);
        ArrayAdapter<String> arrayAdapterQuantity = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, quantity);
        ArrayAdapter<String> arrayAdapterPayment = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, payment);
        ArrayAdapter<String> arrayAdapterType = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, type);

        spinnerTickets.setAdapter(arrayAdapterTickets);
        spinnerQuantity.setAdapter(arrayAdapterQuantity);
        spinnerPayment.setAdapter(arrayAdapterPayment);
        spinnerType.setAdapter(arrayAdapterType);

        spinnerTickets.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                posTicket = position;
                calculatePrice();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerQuantity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                posQ = position;
                calculatePrice();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerPayment.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                posPay = position;
                calculatePrice();
                if (position == 3){
                    infoPayMethod.setText("ΠΡΟΣΟΧΗ! Η διαφορά θα προστεθεί στο πορτοφόλι σας αυτόματα μετά την ολοκλήρωση της αγοράς.");
                }else if (position == 1){
                    infoPayMethod.setText("Έχετε " + balance + "€ στο πορτοφόλι σας.");
                }else infoPayMethod.setText("");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                posType = position;
                calculatePrice();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (posTicket == 0 || posType == 0 || posQ == 0 || posPay ==0){
                    Toast.makeText(Tickets.this, "Παρακαλώ διαλέξτε όλα τα πεδία", Toast.LENGTH_SHORT).show();
                }else if (posPay == 2){
                    Intent intent = new Intent(Tickets.this, PayPal.class);
                    intent.putExtra("price", price);
                    intent.putExtra("quantity", arrayAdapterQuantity.getItem(posQ));
                    intent.putExtra("ticket", arrayAdapterTickets.getItem(posTicket));
                    startActivity(intent);
                }
            }
        });
    }

    private void calculatePrice(){
        price = 0;
        if (posType == 1 && posQ != 0 && posTicket != 0){
            price = prices[posTicket] * (posQ);
            textView.setText("Σύνολο: " + formatter.format(price) + "€");
        }else if (posType == 2 && posQ != 0 && posTicket != 0){
            price = (prices[posTicket ]/2) * (posQ);
            textView.setText("Σύνολο: " + formatter.format(price) + "€");
        }
    }
}