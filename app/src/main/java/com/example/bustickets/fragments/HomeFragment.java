package com.example.bustickets.fragments;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.bustickets.ActivateTicket;
import com.example.bustickets.DataBase;
import com.example.bustickets.R;
import com.example.bustickets.StaticFinalVariables;

public class HomeFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        DataBase dataBase = new DataBase(getContext());
        TextView textView = view.findViewById(R.id.textView);
        TextView timer = view.findViewById(R.id.timer);
        Button delete = view.findViewById(R.id.delete);
        Button activateTicket = view.findViewById(R.id.activateTicket);
        Button next = view.findViewById(R.id.next);
        activateTicket.setVisibility(View.INVISIBLE);
        delete.setVisibility(View.INVISIBLE);
        next.setVisibility(View.INVISIBLE);
        String ticket;
        ticket = dataBase.getActiveTicket();

        if (ticket.equals("")){
            textView.setText(Html.fromHtml("<i>" + "Δεν έχετε κανένα ακυρωμένο εισιτήριο" + "</i>"));
            activateTicket.setVisibility(View.VISIBLE);
        }else if(StaticFinalVariables.getTicketType()<= 2){
            delete.setVisibility(View.VISIBLE);
            textView.setText(Html.fromHtml(ticket));
        }else if(StaticFinalVariables.getTicketType()>2){
            delete.setVisibility(View.VISIBLE);
            if (!StaticFinalVariables.isNext()){
                next.setVisibility(View.VISIBLE);
                new CountDownTimer(4260000, 1000) {

                    public void onTick(long millisUntilFinished) {
                        timer.setText("       Σας απομένουν " + millisUntilFinished / 60000  + " λεπτά μέχρι το επόμενο λεωφορείο");
                    }

                    public void onFinish() {
                        timer.setText("Το εισητήριο έληξε");
                    }
                }.start();
            }else {
                next.setVisibility(View.INVISIBLE);
            }
            textView.setText(Html.fromHtml(ticket) + "\n\n" + Html.fromHtml(StaticFinalVariables.getNextBus()));
        }




        activateTicket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ActivateTicket.class);
                startActivity(intent);
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(getActivity())
                        .setTitle("Βεβαίωση Διαγραφής")
                        .setPositiveButton("Επιβεβαίωση", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dataBase.deleteTicket();
                        Fragment someFragment = new HomeFragment();
                        FragmentTransaction transaction = getFragmentManager().beginTransaction();
                        transaction.replace(R.id.fragment_container, someFragment ); // give your fragment container id in first parameter
                        transaction.addToBackStack(null);  // if written, this transaction will be added to backstack
                        transaction.commit();
                    }
                })
                .setNegativeButton("Ακύρωση", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), NextBus.class);
                startActivity(intent);
            }
        });

        return view;
    }

}
