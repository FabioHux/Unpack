package com.hackathon.unpack;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Scanner;

public class Disorder extends AppCompatActivity {

    private String disorder;
    private ListView listView;
    private ArrayAdapter<String> myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disorder);

        Intent intent = getIntent();
        disorder=intent.getStringExtra("com.hackathon.unpack.disorder");
        ((TextView)findViewById(R.id.disorder)).setText(disorder);

        listView = (ListView) findViewById(R.id.mylist);
        myAdapter = new ArrayAdapter<String>(this, R.layout.line);

        listView.setAdapter(myAdapter);


        listView.setOnItemClickListener((parent, view, position, id) -> {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(myAdapter.getItem(position)));
            startActivity(browserIntent);
        });

        try {
            InputStream is = getResources().openRawResource(R.raw.mental_disorder_help);
            Writer writer = new StringWriter();
            char[] buffer = new char[1024];
            try {
                Reader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                int n;
                while ((n = reader.read(buffer)) != -1) {
                    writer.write(buffer, 0, n);
                }
            } finally {
                is.close();
            }

            String jsonString = writer.toString();

            /*Scanner scnr = new Scanner(new File("mental_disorder_help.json"));
            StringBuilder json=new StringBuilder();
            while(scnr.hasNext()){
                json.append(scnr.next());
            }*/

            JSONArray resources= (new JSONObject(jsonString)).getJSONArray("disorders");
            int len = resources.length();
            for (int i = 0; i<len; i++) {
                JSONObject resource=resources.getJSONObject(i);
                if(resource.getString("name").equals(disorder)) {
                    JSONArray urls = resource.getJSONArray("URLs");
                    int len2 = urls.length();
                    for (int j = 0; j<len2; j++) {
                        myAdapter.add(urls.getString(j));
                    }
                }
            }

            myAdapter.notifyDataSetChanged();
        }catch (Exception e) {
            e.printStackTrace();
        }

    }

}
