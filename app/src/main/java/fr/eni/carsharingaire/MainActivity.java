package fr.eni.carsharingaire;

import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;

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

    }
}