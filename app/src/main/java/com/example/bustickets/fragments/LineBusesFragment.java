package com.example.bustickets.fragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import com.example.bustickets.GetInformations.GetBuses;
import com.example.bustickets.R;
import com.example.bustickets.StaticFinalVariables;

import java.util.ArrayList;

public class LineBusesFragment extends Fragment {
    private GetBuses getBuses = new GetBuses();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_line_buses, container, false);

        ArrayAdapter<String> arrayAdapter;

        EditText etSearch = (EditText) view.findViewById(R.id.etSearchLineBuses);

        ArrayList<String> arrayList;
        arrayList = getBuses.getBuses(getActivity(), StaticFinalVariables.getNameOfSelectedCity());

        ListView listView = (ListView) view.findViewById(R.id.busesListView);

        TextView textView = view.findViewById(R.id.textView_Line_Buses);

        StringBuffer title = new StringBuffer();
        title.append("Λεωφορεία ");
        title.append(StaticFinalVariables.getNameOfSelectedCity());
        textView.setText(title);

        arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, arrayList);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                StaticFinalVariables.setPostiotionOfSelectedBus(position);
                StaticFinalVariables.setNameOfSelectedBus(arrayAdapter.getItem(position));
                getFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new MoreInformationAboutALine()).commit();
            }
        });

        etSearch.addTextChangedListener(new TextWatcher() {// Search in the listCities
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                arrayAdapter.getFilter().filter(s);
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
       return view;
   }

}
