package fr.eni.carsharingaire;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;

import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import fr.eni.carsharingaire.pojo.Parkings;

public class MainActivity extends AppCompatActivity {
    MapView mp = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Configuration.getInstance().load(this, PreferenceManager.getDefaultSharedPreferences(this));
        setContentView(R.layout.activity_main);
        mp = findViewById(R.id.mapView);
        mp.setTileSource(TileSourceFactory.MAPNIK);
        IMapController mapController = mp.getController();
        mapController.setZoom(17);
        GeoPoint startPoint = new GeoPoint(47.2172500, -1.5533600);
        mapController.setCenter(startPoint);
        mp.setVerticalMapRepetitionEnabled(false);
        mp.setMinZoomLevel(3.0);
        mp.setScrollableAreaLimitLatitude(85,-85,0 );


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

                Parkings parkings = gson.fromJson(json, Parkings.class);

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
            //etMotTransforme.setText(s);
        }
    }
}
