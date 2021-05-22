package com.example.bustickets.fragments;

import androidx.appcompat.app.AppCompatDialogFragment;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;
import com.example.bustickets.PayPal;
import com.example.bustickets.R;

public class AddBalance extends AppCompatDialogFragment {

    NumberPicker numberPicker;
    EditText editText;
    Spinner spinner;
    String[] payment = {"-- ΔΙΑΛΕΞΤΕ ΤΡΟΠΟ ΠΛΗΡΩΜΗΣ --", "PayPal - Κάρτα Τραπέζης", "PaySafeCard"};
    int pos;
    TextView textView;

    @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

            LayoutInflater inflater = getActivity().getLayoutInflater();
            View view = inflater.inflate(R.layout.activity_add_balance, null);

            textView = view.findViewById(R.id.balancePaySafe);
            textView.setVisibility(View.INVISIBLE);

            editText = view.findViewById(R.id.editTextBalance);
            editText.setEnabled(false);
            editText.setVisibility(View.INVISIBLE);

            numberPicker = view.findViewById(R.id.numberSpinner);
            numberPicker.setMinValue(1);
            numberPicker.setMaxValue(20);
            numberPicker.setEnabled(false);
            numberPicker.setVisibility(View.INVISIBLE);

            spinner = view.findViewById(R.id.spinnerPayment);
            ArrayAdapter<String> arrayAdapterPayment = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, payment);
            spinner.setAdapter(arrayAdapterPayment);

            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    pos = position;
                    if (position == 2) {
                        editText.setEnabled(true);
                        editText.setVisibility(View.VISIBLE);
                        numberPicker.setEnabled(false);
                        numberPicker.setVisibility(View.INVISIBLE);
                    }else if (pos == 1){
                        numberPicker.setEnabled(true);
                        numberPicker.setVisibility(View.VISIBLE);
                        editText.setEnabled(false);
                        editText.setVisibility(View.INVISIBLE);
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });


            builder.setView(view)
                    .setTitle("Προσθήκη χρημάτων")
                    .setNegativeButton("Ακύρωση", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    }).setPositiveButton("Προσθήκη", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            if (pos == 1){
                                Intent intent = new Intent(getContext(), PayPal.class);
                                intent.putExtra("price", 5);
                                intent.putExtra("quantity", 1);
                                intent.putExtra("ticket", "Προσθήκη Χρημάτων");
                                startActivity(intent);
                            }
                }
            });


        return builder.create();
    }
}