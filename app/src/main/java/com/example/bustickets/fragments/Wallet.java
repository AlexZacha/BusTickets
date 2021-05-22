package com.example.bustickets.fragments;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;

import com.example.bustickets.DataBase;
import com.example.bustickets.R;

import java.util.ArrayList;
import java.util.List;

public class Wallet extends Fragment {

    TextView balanceView;
    Button addCredits;
    SwitchCompat history;
    ListView historyList;
    ArrayList<String> arrayList = new ArrayList<>();
    float balance;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_wallet, container, false);

        DataBase dataBase = new DataBase(getContext());

        balanceView = view.findViewById(R.id.balanceView);
        addCredits = view.findViewById(R.id.addCredits);
        history = view.findViewById(R.id.historySwitch);
        historyList = view.findViewById(R.id.historyList);

        SharedPreferences sp =getContext().getSharedPreferences("your_prefs", Activity.MODE_PRIVATE);
        balance = sp.getFloat("balance", 0);

        balanceView.setText("Έχετε " + balance + "€ στο ηλεκτρονικό σας πορτοφόλι");

        arrayList = (ArrayList<String>) dataBase.getHistory();
        ArrayAdapter arrayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, arrayList);
        historyList.setAdapter(arrayAdapter);
        historyList.setVisibility(View.INVISIBLE);

        history.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    historyList.setVisibility(View.VISIBLE);
                }else historyList.setVisibility(View.INVISIBLE);
            }
        });

        addCredits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData(10);
                balanceView.setText("Έχετε " + balance + "€ στο ηλεκτρονικό σας πορτοφόλι");
                openDialog();
            }
        });

        return view;
    }

    public void openDialog(){
        AddBalance addBalance = new AddBalance();
        addBalance.show(getActivity().getSupportFragmentManager(), "show dialog");
    }

    public void saveData(float balance) {
        SharedPreferences sp = getContext().getSharedPreferences("your_prefs", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putFloat("balance", balance);
        balanceView.setText("Έχετε " + balance + "€ στο ηλεκτρονικό σας πορτοφόλι");
        editor.apply();
    }

}