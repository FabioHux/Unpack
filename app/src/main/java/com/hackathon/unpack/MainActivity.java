package com.hackathon.unpack;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void openQuestionnaire(View view){
        startActivity(new Intent(this, Questionnaire.class));
    }

    public void openResources(View view){
        startActivity(new Intent(this, ResourceList.class));
    }
}