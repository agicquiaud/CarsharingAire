package fr.eni.carsharingaire;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;
import org.osmdroid.config.Configuration;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


import fr.eni.carsharingaire.pojo.Parking;
import fr.eni.carsharingaire.pojo.Parkings;
import fr.eni.carsharingaire.pojo.Records;

public class MainActivity extends AppCompatActivity {
   //MapView mp = null;
    List<Parking> parkings = null;
    private ActionBar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Configuration.getInstance().load(this, PreferenceManager.getDefaultSharedPreferences(this));
        setContentView(R.layout.activity_main);
        ActivityCompat.requestPermissions(this, new String[]{"android.permission.WRITE_EXTERNAL_STORAGE"},21);
        parkings = new ArrayList<>();

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo info = cm.getActiveNetworkInfo();

        if(info != null && info.isConnected())
        {
            AccesRessourceTask task = new AccesRessourceTask();
            task.execute();
        }
        else
        {
            Toast.makeText(MainActivity.this, "Pas internet", Toast.LENGTH_SHORT).show();
        }

        //mise en place du menu
        toolbar = getSupportActionBar();

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.navigation_list:
                    toolbar.setTitle("Parkings");
                    loadFragment(ListFragment.newInstance(parkings));
                    return true;
                case R.id.navigation_map:
                    toolbar.setTitle("Carte");
                    loadFragment(MapFragment.newInstance(parkings));
                    return true;
            }
            return false;
        }
    };

    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private class AccesRessourceTask extends AsyncTask<String, Void, String>
    {
        @Override
        protected String doInBackground(String... strings)
        {
            StringBuffer stringBuffer = new StringBuffer();

            try {
                URL url = new URL("https://data.nantesmetropole.fr/api/records/1.0/search/?dataset=244400404_aires-covoiturage-nantes-metropole&rows=50");

                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                InputStream in = connection.getInputStream();
                InputStreamReader isr = new InputStreamReader(in);

                int unCharacter;

                while((unCharacter = isr.read()) != -1)
                {
                    stringBuffer.append((char)unCharacter);
                }

                String json = stringBuffer.toString();
                JSONObject resultat = new JSONObject(json);
                JSONArray resultatRecords = resultat.getJSONArray("records");
                Gson gson = new Gson();


                List<Records> records = gson.fromJson(resultatRecords.toString(), new TypeToken<List<Records>>(){}.getType());
                for (Records park:records) {
                    Parking parking = new Parking(park.getFields().getAdresse(),
                            Integer.parseInt(park.getFields().getCapacite_voiture()),
                            park.getFields().getCode_postal(),
                            park.getFields().getCommune(),
                            park.getFields().getConditions_d_acces(),
                            park.getFields().getExploitant(),
                            Double.parseDouble((park.getFields().getLocation())[0]),
                            Double.parseDouble((park.getFields().getLocation())[1]),
                            park.getFields().getNom_complet(),
                            park.getFields().getPresentation(),
                            Boolean.parseBoolean(park.getFields().getService_velo()),
                            Boolean.parseBoolean(park.getFields().getStationnement_velo()),
                            Boolean.parseBoolean(park.getFields().getStationnement_velo_securise()),
                            park.getFields().getSite_web());
                    parkings.add(parking);
                }

                //List<Parkings> listParking = gson.fromJson(json, new TypeToken<List<Parkings>>(){}.getType());

                connection.disconnect();
            }
            catch(Exception ex)
            {
                Log.e("ACOS","ERREUR : " + ex.getMessage());
            }

            Log.i("ACOS","Résultat : " + stringBuffer.toString());
            return stringBuffer.toString();
        }

        @Override
        protected void onPostExecute(String s)
        {
            // load the store fragment by default
            toolbar.setTitle("Carte");
            loadFragment(MapFragment.newInstance(parkings));
        }
    }
}
