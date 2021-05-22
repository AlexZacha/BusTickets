package com.example.bustickets.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;

import com.example.bustickets.ActivateTicket;
import com.example.bustickets.DataBase;
import com.example.bustickets.R;
import com.example.bustickets.Tickets;

import java.util.ArrayList;

public class TicketsFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tickets, container, false);

        Button button = view.findViewById(R.id.buyTickets);
        Button moreInfo = view.findViewById(R.id.moreInfo);

        DataBase dataBase = new DataBase(getActivity());
        int[] tickets = dataBase.getTickets();
        int[] ticketsRed = dataBase.getTicketsRed();

        ListView listView = view.findViewById(R.id.tickets);

        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("ΒΑΣΙΚΟ ΕΙΣΙΤΗΡΙΟ: " + tickets[0]);
        arrayList.add("ΒΑΣΙΚΟ ΕΙΣΙΤΗΡΙΟ (ΜΕΙΩΜΕΝΟ): " + ticketsRed[0]);
        arrayList.add("ΧΡΟΝΙΚΟ ΕΙΣΙΤΗΡΙΟ ΔΥΟ (2) ΜΕΤΑΚΙΝΗΣΕΩΝ: " + tickets[1]);
        arrayList.add("ΧΡΟΝΙΚΟ ΕΙΣΙΤΗΡΙΟ ΔΥΟ (2) ΜΕΤΑΚΙΝΗΣΕΩΝ (ΜΕΙΩΜΕΝΟ): " + ticketsRed[1]);
        arrayList.add("ΧΡΟΝΙΚΟ ΕΙΣΙΤΗΡΙΟ ΤΡΙΩΝ (3) ΜΕΤΑΚΙΝΗΣΕΩΝ: " + tickets[2]);
        arrayList.add("ΧΡΟΝΙΚΟ ΕΙΣΙΤΗΡΙΟ ΤΡΙΩΝ (3) ΜΕΤΑΚΙΝΗΣΕΩΝ (ΜΕΙΩΜΕΝΟ): " + ticketsRed[2]);
        arrayList.add("ΧΡΟΝΙΚΟ ΕΙΣΙΤΗΡΙΟ ΤΕΣΣΑΡΩΝ (4) " + tickets[3]);
        arrayList.add("ΧΡΟΝΙΚΟ ΕΙΣΙΤΗΡΙΟ ΤΕΣΣΑΡΩΝ (4) (ΜΕΙΩΜΕΝΟ): " + ticketsRed[3]);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, arrayList);

        listView.setAdapter(arrayAdapter);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), Tickets.class);
                startActivity(intent);
            }
        });

        moreInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://oasth.gr/el/komistra/"));
                startActivity(browserIntent);
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), ActivateTicket.class);
                intent.putExtra("ticketType", position);
                startActivity(intent);
            }
        });
        
        return view;
    }
}
