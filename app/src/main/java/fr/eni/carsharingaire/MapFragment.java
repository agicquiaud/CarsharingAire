package fr.eni.carsharingaire;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.ItemizedOverlayWithFocus;
import org.osmdroid.views.overlay.OverlayItem;

import java.util.ArrayList;
import java.util.List;

import fr.eni.carsharingaire.pojo.Records;


public class MapFragment extends Fragment {
    private static final String TAG = ListFragment.class.getSimpleName();

    MapView mp = null;
    static List<Records> list = null;

    public MapFragment() {
        // Required empty public constructor
    }

    public static MapFragment newInstance(List<Records> parkings) {
        list = parkings;
        MapFragment fragment = new MapFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_map, container, false);

        mp = view.findViewById(R.id.mapView);
        mp.setTileSource(TileSourceFactory.MAPNIK);

        IMapController mapController = mp.getController();
        mapController.setZoom(17);
        GeoPoint startPoint = new GeoPoint(47.2172500, -1.5533600);
        mapController.setCenter(startPoint);
        mp.setVerticalMapRepetitionEnabled(false);
        mp.setMinZoomLevel(3.0);
        mp.setScrollableAreaLimitLatitude(85,-85,0 );

        List<OverlayItem> overlayItems = new ArrayList<OverlayItem>();
        for (Records record:list) {
            overlayItems.add(new OverlayItem(record.getFields().getNom_complet(), record.getFields().getCapacite_voiture(), new GeoPoint(Double.parseDouble((record.getGeometry().getCoordinates())[1]), Double.parseDouble((record.getGeometry().getCoordinates())[0]))));
        }
        ItemizedOverlayWithFocus<OverlayItem> mOverlay = new ItemizedOverlayWithFocus<OverlayItem>(view.getContext(),overlayItems,
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

        return view;
    }





}
