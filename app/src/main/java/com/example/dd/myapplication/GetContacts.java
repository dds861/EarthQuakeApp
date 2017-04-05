package com.example.dd.myapplication;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by dd on 05.04.2017.
 */

public class GetContacts extends AsyncTask<Void, Void, Void> {
    private ProgressDialog pDialog;
    private Context context;
    private String url;
    private ListView lv;
    JsonToArray jsonToArray;

    GetContacts(Context context, String url, ListView lv) {
        this.context = context;
        this.url = url;
        this.lv = lv;
    }

    public ArrayList<HashMap<String, String>> getContactList() {
        return contactList;
    }

    ArrayList<HashMap<String, String>> contactList = new ArrayList<>();

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        // Showing progress dialog
        pDialog = new ProgressDialog(context);
        pDialog.show();

    }

    @Override
    protected Void doInBackground(Void... arg0) {


        HttpHandler sh = new HttpHandler();

        try {
            jsonToArray = new JsonToArray(sh,url,contactList);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        super.onPostExecute(result);
        // Dismiss the progress dialog
        if (pDialog.isShowing())
            pDialog.dismiss();
        /**
         * Updating parsed JSON data into ListView
         * */
        ListAdapter adapter = new SimpleAdapter(
                context,
                jsonToArray.getContactList(),
                R.layout.list_item,
                new String[]{"place2", "place1", "km", "mag"},
                new int[]{R.id.address1,R.id.address3, R.id.km, R.id.magnitude});

        lv.setAdapter(adapter);
    }

}
