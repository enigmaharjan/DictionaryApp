package com.dictionaryapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class EnglishToNepaliDictionary extends AppCompatActivity {

    private ListView lstDictionary;
    private Button btnChange;
    private Map<String, String> dictionary;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_english_to_nepali_dictionary);

        lstDictionary = findViewById(R.id.lstDictionary);
        btnChange = findViewById(R.id.btnChange);
        dictionary = new HashMap<>();

        readFromFile();
        ArrayAdapter adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1,
                new ArrayList<String>(dictionary.keySet())
        );
        lstDictionary.setAdapter(adapter);

        btnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EnglishToNepaliDictionary.this, AddWord.class);

                startActivity(intent);
            }
        });

        lstDictionary.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String key = parent.getItemAtPosition(position).toString();
                String meaning = dictionary.get(key);

                Intent intent = new Intent(EnglishToNepaliDictionary.this, SecondActivity.class);
                intent.putExtra("meaning", meaning);
                startActivity(intent);
            }
        });


    }

    private void readFromFile() {
        try{
            FileInputStream fis = openFileInput("words.txt");
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            String line = "";
            while ((line = br.readLine()) !=null){
                String[] parts = line.split("->");
                dictionary.put(parts[0], parts[1]);
            }
        }
        catch (IOException e ){
            e.printStackTrace();
        }
    }
}
