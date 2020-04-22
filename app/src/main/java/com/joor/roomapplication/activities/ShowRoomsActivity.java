package com.joor.roomapplication.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;


import com.android.volley.toolbox.JsonArrayRequest;
import com.joor.roomapplication.R;
import com.joor.roomapplication.adapters.RoomAdapter;
import com.joor.roomapplication.controllers.AppController;
import com.joor.roomapplication.models.Reservation;


import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ShowRoomsActivity extends AppCompatActivity {

    private ListView listView;
    //private static final String url = "https://cloud.timeedit.net/uu/web/schema/ri12Y693Y23063QQ26Z552690558655965396351Y855X85X896X2658815856960969XY788256655YY85X76597553YX95X132Y16Y53X678656662559Q7.json";
    private static final String url = "https://timeeditrestapi.herokuapp.com/reservations/";
    private List<Reservation> reservations = new ArrayList<Reservation>();
    private RoomAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_rooms);

        bindViews();
        adapter = new RoomAdapter(this, reservations);
        listView.setAdapter(adapter);


        // Request a json response from the provided URL
        JsonArrayRequest jsonRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject JSONreservation = response.getJSONObject(i);
                                System.out.println("name added to list: " + JSONreservation.getString("id"));
                                //int id, String starttime, String startdate, String endtime, String enddate, String[] columns

                                //required variables for each reservation object
                                int reservationId = Integer.parseInt(JSONreservation.getString("id"));
                                String startTime = JSONreservation.getString("startTime");
                                String startDate = JSONreservation.getString("startDate");
                                String endTime = JSONreservation.getString("endTime");
                                String endDate = JSONreservation.getString("endDate");
                                JSONArray reservationColumns = JSONreservation.getJSONArray("columns");
                                JSONArray reservationNames = JSONreservation.getJSONArray("name");

                                //create reservation object and add to reservations (list)
                                Reservation reservation = new Reservation(reservationId, startTime, startDate, endTime, endDate,
                                        toStringArray(reservationColumns));
                                reservation.setName(toStringArray(reservationNames));
                                reservations.add(reservation);

                                System.out.println("Reservation added: " + reservation.toString());
                            }
                        } catch (Exception e) {
                            System.out.println("something wrong..");
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //txtView.setText("That didn't work!");
            }
        });

        /*
        try {
            HttpUtility.sendGetRequest(url);

        String[] httpResponse = HttpUtility.readMultipleLinesRespone();
        Gson gson = new Gson();
        String jsonString = httpResponse.toString();

        Type reservationListType = new TypeToken<ArrayList<Reservation>>(){}.getType();
        reservations = gson.fromJson(jsonString, reservationListType);
        for (Reservation r : reservations
        ) {
            System.out.println(r.getId());
        }

    }
        catch (IOException e) {
        e.printStackTrace();
    }

         */

    // Add the request to the RequestQueue.
        AppController.getmInstance().addToRequestQueue(jsonRequest);
    //queue.add(jsonRequest);
}

    //gets id for view
    private void bindViews() {
        listView = findViewById(R.id.list_view);
    }

    //when user navigates back
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(),
                MainActivity.class);
        getApplicationContext().startActivity(intent);
    }

    public static String[] toStringArray(JSONArray array) {
        if(array==null)
            return null;

        String[] arr=new String[array.length()];
        for(int i=0; i<arr.length; i++) {
            arr[i]=array.optString(i);
        }
        return arr;
    }
}

