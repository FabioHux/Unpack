package com.hackathon.unpack;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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
    private ListView websiteListView;
    private ArrayAdapter<String> websiteAdapter;
    private ListView phoneListView;
    private ArrayAdapter<String> phoneAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disorder);

        Intent intent = getIntent();
        disorder = intent.getStringExtra("com.hackathon.unpack.disorder");
        ((TextView) findViewById(R.id.disorder)).setText(disorder);

        websiteListView = (ListView) findViewById(R.id.websitelist);
        websiteAdapter = new ArrayAdapter<String>(this, R.layout.line);

        websiteListView.setAdapter(websiteAdapter);


        websiteListView.setOnItemClickListener((parent, view, position, id) -> {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(websiteAdapter.getItem(position)));
            startActivity(browserIntent);
        });

        phoneListView = (ListView) findViewById(R.id.phonelist);
        phoneAdapter = new ArrayAdapter<String>(this, R.layout.line);

        phoneListView.setAdapter(phoneAdapter);


        phoneListView.setOnItemClickListener((parent, view, position, id) -> {
            Intent phoneIntent = new Intent(Intent.ACTION_CALL);
            phoneIntent.setData(Uri.parse("tel:"+phoneAdapter.getItem(position)));
            if (ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                startActivity(phoneIntent);
            }
            else{
                Toast.makeText(getApplicationContext(),"Permission not granted. Cannot access phone",Toast.LENGTH_LONG).show();
            }
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

            JSONArray resources = (new JSONObject(jsonString)).getJSONArray("disorders");
            int len = resources.length();
            for (int i = 0; i < len; i++) {
                JSONObject resource = resources.getJSONObject(i);
                if (resource.getString("name").equals(disorder)) {

                    try {
                        JSONArray urls = resource.getJSONArray("URLs");
                        int len2 = urls.length();
                        for (int j = 0; j < len2; j++) {
                            websiteAdapter.add(urls.getString(j));
                        }
                        websiteAdapter.notifyDataSetChanged();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    try {
                        JSONArray phoneNumbers = resource.getJSONArray("number");
                        int len2 = phoneNumbers.length();
                        for (int j = 0; j < len2; j++) {
                            phoneAdapter.add(phoneNumbers.getString(j));
                        }
                        phoneAdapter.notifyDataSetChanged();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    try {
                        ((TextView)findViewById(R.id.symptoms)).setText(resource.getString("symptoms"));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
