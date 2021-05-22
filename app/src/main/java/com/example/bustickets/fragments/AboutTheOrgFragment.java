package com.example.bustickets.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.widget.EditText;
import com.example.bustickets.R;
import com.example.bustickets.StaticFinalVariables;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

import info.WebScrap;

public class AboutTheOrgFragment extends Fragment {
    EditText infoAboutLine;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_abouttheorg, container, false);

        infoAboutLine = view.findViewById(R.id.infoAboutLineFragment);
        infoAboutLine.setFocusable(false);
        getInfo();
        return view;
    }

    private void getInfo() {
        infoAboutLine.setText("");
        int num = StaticFinalVariables.getPositionOfSelectedCity();
        WebScrap webScrap = new WebScrap();
        String url = webScrap.getInfoForBus(num);
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                if (!webScrap.getInfoForBus(num).equals("no")){
                    StringBuilder text = new StringBuilder();
                    try {
                        Document document = Jsoup.connect(url).get();
                        Elements elements = document.getElementsByClass("info-container").select("p");
                        for (Element i: elements){
                            text.append(i.text());
                            text.append(" \n");
                            text.append(" \n");
                        }
                        infoAboutLine.setText(text.toString());
                    } catch (IOException e) {
                        e.printStackTrace();
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
