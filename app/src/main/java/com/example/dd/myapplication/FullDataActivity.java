package com.example.dd.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by dd on 04.04.2017.
 */

public class FullDataActivity extends AppCompatActivity {
    TextView textView;
    ListView listView;
    ArrayList<HashMap<String, String>> contactList;
    HashMap<String, String> contact;
    Intent intent;
    List<String> your_array_list;
    ArrayAdapter<String> arrayAdapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.full_data_activity);

        //определили компоненты
        listView = (ListView) findViewById(R.id.listview_new_window);
        textView = (TextView) findViewById(R.id.textview_new_window);

        //создали интент
        intent = getIntent();

        //получили json arraylist
        contactList = (ArrayList<HashMap<String, String>>) intent.getExtras().getSerializable("properties");

        //из arraylist взяли первый item в виде hashmap
        contact = contactList.get(0);

        //создали новый arraylist
        your_array_list = new ArrayList<String>();

        //добавили данные в новый arraylist. данные взяли  из hashmap contacts по ключу
        try {
            for (int i = 0; i < new JsonToArray().AllProperties().size(); i++) {
                your_array_list.add(contact.get(new JsonToArray().AllProperties().get(i)));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


//        создали адаптер по умолчанию и добавили туда hashmap
        arrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                your_array_list);

        //ListView определили адаптер
        listView.setAdapter(arrayAdapter);



    }
}
