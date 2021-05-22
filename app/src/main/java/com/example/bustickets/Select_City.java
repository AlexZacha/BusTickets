package com.example.bustickets;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.example.bustickets.GetInformations.GetBuses;

import java.util.ArrayList;

//Second activity. With this activity, the user can select the city where wants to buy tickets or to see some information about the buses.
public class Select_City extends AppCompatActivity {
    DataBase db = new DataBase(Select_City.this);
    private ListView cities; // List with all the available cities. The data comes from the database.
    private EditText etSearch; // Search bar
    private String city = "";
    private  ArrayAdapter<String> arrayAdapter;
    private GetBuses getBuses = new GetBuses();
    private Bundle bundle = new Bundle();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select__cities);
        cities = findViewById(R.id.citiesList);
        etSearch = findViewById(R.id.etSearch);

        ArrayList<String> arrayList = new ArrayList<>();

        //The variable listCities contains all the cities where are stored in the database. The method getCities() returns a long string with all the cities.
        String listCities = db.getCities().toString().replaceAll("\\s+","").replaceAll("\\[", "").replaceAll("\\]", "") + ",";

        //With this for loop we seperate each city and we add the name of the city to the arrayList. When the city name ends follows a comma.
        for (int i = 0; i != listCities.length(); i++){
            if (listCities.charAt(i) != ','){
                city += listCities.charAt(i);
            }else{
                arrayList.add(city);
                city = "";
            }
        }
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arrayList);

        cities.setAdapter(arrayAdapter); // Display the cities.


        cities.setOnItemClickListener(new AdapterView.OnItemClickListener() { //If the user click a city, call new activity and send's the position and the name of the city with the method putExtra()
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(Select_City.this, Options.class);
                    StaticFinalVariables.NameOfSelectedCity = arrayAdapter.getItem(position);
                    StaticFinalVariables.PositionOfSelectedCity = position;
                    startActivity(intent);// Start the activity
            }
        });


        etSearch.addTextChangedListener(new TextWatcher() {// Search in the listCities
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                (Select_City.this).arrayAdapter.getFilter().filter(s);
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }
}