package com.example.dd.myapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    GetContacts getContacts;
    String url = "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&starttime=2014-01-01&endtime=2014-01-02&limit=10";
    Button button_Call_Service;
    Button preferences;
    TextView tvInfo;
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ListView lv = (ListView) findViewById(R.id.list);
        getContacts = new GetContacts(MainActivity.this, url, lv);
        getContacts.execute();



        registerForContextMenu(lv);
        button_Call_Service = (Button)findViewById(R.id.button_Call_Service);
        button_Call_Service.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MyService.class);
                startService(intent);
            }
        });

        tvInfo = (TextView) findViewById(R.id.tvInfo);
        preferences = (Button)findViewById(R.id.preferences);
        preferences.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // получаем SharedPreferences, которое работает с файлом настроек
                sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                // полная очистка настроек
                // sp.edit().clear().commit();
            }
        });




    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.settings:
                Intent intent = new Intent(getApplicationContext(), FullDataActivity.class);
                intent.putExtra("properties", getContacts.getContactList());

                AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
                intent.putExtra("PositionNumber", info.position);

                startActivity(intent);
                break;
        }
        return super.onContextItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
    }



//    protected void onResume() {
//        Boolean notif = sp.getBoolean("notif", false);
//        String address = sp.getString("address", "");
//        String text = "Notifications are "
//                + ((notif) ? "enabled, address = " + address : "disabled");
//        tvInfo.setText(text);
//        super.onResume();
//    }
//
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuItem mi = menu.add(0, 1, 0, "Preferences");
//        mi.setIntent(new Intent(this, PrefActivity.class));
//        return super.onCreateOptionsMenu(menu);
//    }

}
