package com.example.bustickets.GetInformations;

import android.content.Context;
import com.example.bustickets.DataBase;
import java.util.ArrayList;

public class GetBuses {
    private DataBase db;
    public ArrayList<String> getBuses(Context context, String city){
        String bus = "";
        db = new DataBase(context);
        ArrayList<String> arrayList = new ArrayList<>();
        if (db.isTableExist(city) == 0){
            arrayList.add("Λυπούμαστε! Αυτή την στηγμή δεν εξυπηρετουμαι την περιοχή σας.");
        }else{
            String listBuses = " " + db.getBuses(city).toString().replaceAll("\\[", "").replaceAll("]", "") + ",";
            for (int i = 1; i <= listBuses.length(); i++){
                if (listBuses.charAt(i) != ','){
                    bus += listBuses.charAt(i);
                }else{
                    arrayList.add(bus);
                    bus = "";
                    i++;
                }
            }
        }
        return arrayList;
    }
}
