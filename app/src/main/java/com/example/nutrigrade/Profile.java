package com.example.nutrigrade;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.Objects;

public class Profile extends AppCompatActivity {
    String[] items = {"Downloaded Products","Calorie Calculator","Feedback Replies"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        ListView listView = (ListView) findViewById(R.id.listview);
        ArrayAdapter<String> adapter= new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(Objects.equals(items[position], "Downloaded Products")){
                    Intent i = new Intent(getApplicationContext(), DownloadProducts.class);
                    startActivity(i);
                }else if(Objects.equals(items[position], "Feedback Replies")){
                    Intent i = new Intent(getApplicationContext(), Feedback.class);
                    startActivity(i);
                }else if(Objects.equals(items[position], "Calorie Calculator")){
                    Intent i = new Intent(getApplicationContext(), CalorieCalculate.class);
                    startActivity(i);
                }
//                Toast.makeText(Profile.this, items [position], Toast.LENGTH_SHORT).show();
            }
        });
    }
}