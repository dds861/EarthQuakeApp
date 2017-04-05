package com.example.dd.myapplication;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by dd on 05.04.2017.
 */

public class JsonToArray {

    private HttpHandler sh = new HttpHandler();
    String url;
    HashMap<String, String> contact;
    ArrayList<String> allProperties;
    ArrayList<String> allProperties1;
    JSONObject properties;
    ArrayList<HashMap<String, String>> contactList = new ArrayList<>();

    //Геттер на contactList
    public ArrayList<HashMap<String, String>> getContactList() {
        return contactList;
    }

    //Конструктор без параметров
    JsonToArray(){}

    //Конструктор с параметрами
    JsonToArray(HttpHandler sh, String url, ArrayList<HashMap<String, String>> contactList) throws JSONException {
        this.sh = sh;
        this.url = url;
        this.contactList = contactList;
        JsonToArraylist();
    }


    // Making a request to url and getting response
    public void JsonToArraylist() {
        try

        {
            //
            String jsonStr = sh.makeServiceCall(url);
            JSONObject jsonObj = new JSONObject(jsonStr);

            // Getting JSON Array node
            JSONArray contacts = jsonObj.getJSONArray("features");

            // looping through All Contacts
            for (int i = 0; i < contacts.length(); i++) {

                //из куча properties получаю данные из какого-то онкретного properties
                JSONObject c = contacts.getJSONObject(i);

                // все данные этого properties вставляю в созданный JSONObject
                properties = c.getJSONObject("properties");

                //создал массив чтоб загрузить туда все данные из "properties"

                AllProperties();
                allProperties1 = new ArrayList<String>();

                //все данные ищ properties вставляем в allProperties1
                for (int j = 0; j < new Properties().Properties().size(); j++) {
                    allProperties1.add(properties.getString(allProperties.get(j)));

                }



                //разделяю place на пробелы, получаем массив слов из строки
                String[] splited = allProperties1.get(1).split("\\s+");

                // tmp hash map for single contact
                contact = new HashMap<>();

                //в contact добавили все данные
                for (int j = 0; j < allProperties1.size(); j++) {
                    contact.put(allProperties.get(j), allProperties1.get(j));

                }

                //из place вытаскиваем km
                contact.put("km", splited[0]);

                //из place вытаскиваем Штат в США
                contact.put("place1", splited[splited.length - 1]);
                //----------------------------------------------
                //из place вытаскиваем то что по середине
                String temp = "";
                for (int j = 1; j < splited.length; j++) {
                    if (j != splited.length - 1)
                        temp += splited[j] + " ";
                }
                contact.put("place2", temp);

                //добавляем contact в contactList
                contactList.add(contact);
            }
        } catch (final JSONException e) {
        }


    }

    public ArrayList<String> AllProperties() throws JSONException {

        allProperties = new Properties().Properties();
        return allProperties;
    }
}
