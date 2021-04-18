package com.hackathon.unpack;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;

public class ResourceList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resource_list);

        final ArrayAdapter<CharSequence> disorders = new ArrayAdapter<>(getApplicationContext(), R.layout.disorder_name, R.id.disorder_name_tv, Arrays.asList(getResources().getStringArray(R.array.disorders)));
        ListView disorderList = findViewById(R.id.resources_list);

        disorderList.setAdapter(disorders);

        disorderList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectDisorder(position);
                Toast.makeText(getApplicationContext(), getResources().getStringArray(R.array.disorders)[position], Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void goBackHome(View view){
        super.onBackPressed();
    }

    private void selectDisorder(int position){
        Intent intent = new Intent(this, Disorder.class);
        intent.putExtra("com.hackathon.unpack.disorder", getResources().getStringArray(R.array.disorders)[position]);
        startActivity(intent);
    }
}