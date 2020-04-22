package com.joor.roomapplication.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.joor.roomapplication.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    //launches activity ShowItems
    public void onClickShowRooms(View view){
        Intent intent = new Intent(getApplicationContext(),
                ShowRoomsActivity.class);
        startActivity(intent);
    }

    //launches activity ShowItems
    public void onClickShowDay(View view){
        Intent intent = new Intent(getApplicationContext(),
                ShowDayActivity.class);
        startActivity(intent);
    }

}
