package com.example.apprenti.proutmobile;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.FloatProperty;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MesureActivity extends AppCompatActivity {

    private EditText latitude, longitude;
    private TextView textSeek, proutDate;
    private SeekBar odeur;
    private Button valider;
    private int resultOdeur;

    DatabaseReference database = FirebaseDatabase.getInstance().getReference();
    DatabaseReference myRef = database.child("prout");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mesure);

        proutDate = (TextView) findViewById(R.id.idDate);
        latitude = (EditText) findViewById(R.id.idLat);
        longitude = (EditText) findViewById(R.id.idLong);
        odeur = (SeekBar) findViewById(R.id.idSeek);
        textSeek = (TextView) findViewById(R.id.idTextSeek);
        textSeek.setText("Odeur: " + odeur.getProgress() + " / " + odeur.getMax());
        valider = (Button) findViewById(R.id.idValide);



        odeur.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                textSeek.setText("Odeur: " + odeur.getProgress() + " / " + odeur.getMax());
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                resultOdeur = odeur.getProgress();
            }
        });

        valider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String lat = "lat="+latitude.getText().toString();
                String lon = "lon="+longitude.getText().toString();
                String strengh = "strength="+resultOdeur;
                new Background_get().execute(lat, lon, strengh);
                Log.d("TAGGGG", lat + lon + strengh);

                String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
                proutDate.setText(date);

                MesureModel mesure = new MesureModel(Float.parseFloat(latitude.getText().toString()), Float.parseFloat(longitude.getText().toString()), resultOdeur, proutDate.toString());
                myRef.setValue(mesure);
            }
        });
    }
    private class Background_get extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            try {


            /* Change the IP to the IP you set in the arduino sketch */
                URL url = new URL(" http://192.168.0.117/prout-spotter/api/new.php?" + params[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder result = new StringBuilder();
                String inputLine;
                while ((inputLine = in.readLine()) != null)
                    result.append(inputLine).append("\n");

                in.close();
                connection.disconnect();
                return result.toString();

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
