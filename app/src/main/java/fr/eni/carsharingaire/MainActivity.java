package fr.eni.carsharingaire;

import android.content.Context;
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

import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.ItemizedOverlayWithFocus;
import org.osmdroid.views.overlay.Overlay;
import org.osmdroid.views.overlay.OverlayItem;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


import fr.eni.carsharingaire.pojo.Parkings;
import fr.eni.carsharingaire.pojo.Records;

public class MainActivity extends AppCompatActivity {
    MapView mp = null;
    List<Records> parkings = null;
    private ActionBar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Configuration.getInstance().load(this, PreferenceManager.getDefaultSharedPreferences(this));
        setContentView(R.layout.activity_main);
        mp = findViewById(R.id.mapView);
        mp.setTileSource(TileSourceFactory.MAPNIK);
        ActivityCompat.requestPermissions(this, new String[]{"android.permission.WRITE_EXTERNAL_STORAGE"},21);
        IMapController mapController = mp.getController();
        mapController.setZoom(17);
        GeoPoint startPoint = new GeoPoint(47.2172500, -1.5533600);
        mapController.setCenter(startPoint);
        mp.setVerticalMapRepetitionEnabled(false);
        mp.setMinZoomLevel(3.0);
        mp.setScrollableAreaLimitLatitude(85,-85,0 );
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

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        // load the store fragment by default
        toolbar.setTitle("Carte");
        loadFragment(new MapFragment());
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.navigation_list:
                    toolbar.setTitle("Parkings");
                    fragment = new ListFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.navigation_map:
                    toolbar.setTitle("Carte");
                    fragment = new MapFragment();
                    loadFragment(fragment);
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
            HttpURLConnection httpUrlConnection = null;

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
                Gson gson = new Gson();

                Parkings parking = gson.fromJson(json, Parkings.class);
                for (Records park:parking.getRecords()) {
                    parkings.add(park);
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
            List<OverlayItem> overlayItems = new ArrayList<OverlayItem>();
            for (Records record:parkings) {
                overlayItems.add(new OverlayItem(record.getFields().getNom_complet(), record.getFields().getCapacite_voiture(), new GeoPoint(Double.parseDouble((record.getGeometry().getCoordinates())[1]), Double.parseDouble((record.getGeometry().getCoordinates())[0]))));
            }
            ItemizedOverlayWithFocus<OverlayItem> mOverlay = new ItemizedOverlayWithFocus<OverlayItem>(getApplicationContext(),overlayItems,
                    new ItemizedIconOverlay.OnItemGestureListener()
                    {

                        @Override
                        public boolean onItemSingleTapUp(int index, Object item) {
                            Log.i("SIMPLECLIC","Simple clic");
                            return false;
                        }

                        @Override
                        public boolean onItemLongPress(int index, Object item) {
                            Log.i("LONGCLIC","Long clic");
                            return false;
                        }
                    });
            mOverlay.setFocusItemsOnTap(true);
            mp.getOverlays().add(mOverlay);
        }
    }
}
