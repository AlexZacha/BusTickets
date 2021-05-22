package com.example.bustickets.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import androidx.annotation.Nullable;
import com.example.bustickets.R;
import com.example.bustickets.StaticFinalVariables;
import androidx.fragment.app.Fragment;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.util.ArrayList;

import info.WebScrap;

public class MoreInformationAboutALine extends Fragment {

    private WebScrap webScrap = new WebScrap();
    ListView listView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_more_info, container, false);

        listView = view.findViewById(R.id.stopsListView);

        TextView textView = view.findViewById(R.id.textView_More_Info);

        StringBuffer title = new StringBuffer();
        title.append("Στασεις Λεωφορείου \n");
        title.append(StaticFinalVariables.getNameOfSelectedBus());
        textView.setText(title);

        getInfo();

        return view;
    }

    private void getInfo(){
        int num = StaticFinalVariables.getPositionOfSelectedCity();
        int busNum = StaticFinalVariables.getPostiotionOfSelectedBus();
        Thread thread = new Thread(new Runnable() { // We need to use a new thread cuz java doesn't allow you to open an internet website on main thread.
            @Override
            public void run() {
                if (!webScrap.getInfoForBus(num).equals("no")){
                    if (num != 14) {
                        Document doc;
                        try {// Here we use the Jsoup library to get some information from the internet.
                            doc = Jsoup.connect(webScrap.getInfoForBus(num)).get();
                            ArrayList<String> arrayList = new ArrayList<>();
                            Elements href = doc.getElementsByClass("line-item line-data mobile-line").select("a");
                            for (Element element : href) { // Get all the link for the info.
                                arrayList.add(element.attr("href"));
                            }
                            String url = arrayList.get(busNum); // Get the url on index num. Num is the position from the bus that the user clicked
                            Document document = Jsoup.connect(url).get();
                            Elements elements = document.getElementsByClass("stop-wrapper").select("h3");
                            Elements elements1 = document.getElementsByClass("stop-address");
                            arrayList.clear();
                            for (int i =0; i<elements.size(); i++) { // Add to list all the bus stops.
                                    arrayList.add(elements.get(i).text());
                                }

                            ArrayAdapter<String> arrayAdapter;
                            arrayAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, arrayList);
                            listView.setAdapter(arrayAdapter);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }else {
                        if (busNum <= 77){
                            try {// Here we using the Jsoup library to get some information from the internet.
                                String url = "https://moovitapp.com/index/el/%CE%B4%CE%B7%CE%BC%CF%8C%CF%83%CE%B9%CE%B5%CF%82_%CF%83%CF%85%CE%B3%CE%BA%CE%BF%CE%B9%CE%BD%CF%89%CE%BD%CE%AF%CE%B5%CF%82-lines-Thessaloniki_%CE%98%CE%B5%CF%83%CF%83%CE%B1%CE%BB%CE%BF%CE%BD%CE%B9%CE%BA%CE%B7-2860-852864";
                                Document doc = Jsoup.connect(url).get();
                                ArrayList<String> arrayList2 = new ArrayList<>();
                                Elements href = doc.getElementsByClass("line-item line-data mobile-line").select("a");
                                for (Element element : href) { // Get all the link for the info.
                                    arrayList2.add(element.attr("href"));
                                }
                                url = arrayList2.get(busNum);
                                Document document = Jsoup.connect(url).get();
                                Elements elements = document.getElementsByClass("stop-wrapper").select("h3");
                                Elements elements1 = document.getElementsByClass("stop-address");
                                arrayList2.clear();
                                for (int i =0; i<elements.size(); i++) { // Add to list all the bus stops.
                                    arrayList2.add(elements.get(i).text());
                                }
                                ArrayAdapter<String> arrayAdapter;
                                arrayAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, arrayList2);
                                listView.setAdapter(arrayAdapter);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }else {
                            try {// Here we using the Jsoup library to get some information from the internet.
                                String url = "https://moovitapp.com/index/el/%CE%B4%CE%B7%CE%BC%CF%8C%CF%83%CE%B9%CE%B5%CF%82_%CF%83%CF%85%CE%B3%CE%BA%CE%BF%CE%B9%CE%BD%CF%89%CE%BD%CE%AF%CE%B5%CF%82-lines-Thessaloniki_%CE%98%CE%B5%CF%83%CF%83%CE%B1%CE%BB%CE%BF%CE%BD%CE%B9%CE%BA%CE%B7-2860-1401887";
                                Document doc = Jsoup.connect(url).get();
                                ArrayList<String> arrayList3 = new ArrayList<>();
                                Elements href = doc.getElementsByClass("line-item line-data mobile-line").select("a");
                                for (Element element : href) { // Get all the link for the info.
                                    arrayList3.add(element.attr("href"));
                                }
                                url = arrayList3.get(busNum - 77);
                                Document document = Jsoup.connect(url).get();
                                Elements elements = document.getElementsByClass("stop-wrapper").select("h3");
                                Elements elements1 = document.getElementsByClass("stop-address");
                                arrayList3.clear();
                                for (int i =0; i<elements.size(); i++) { // Add to list all the bus stops.
                                    if (elements1.get(i).text().equals("")){
                                        arrayList3.add(elements.get(i).text());
                                    }else{
                                        arrayList3.add(elements.get(i).text() + "\n" + elements1.get(i).text());
                                    }

                                }
                                ArrayAdapter<String> arrayAdapter;
                                arrayAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, arrayList3);
                                listView.setAdapter(arrayAdapter);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                }

            }

        });
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
