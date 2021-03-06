package fr.eni.carsharingaire;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.osmdroid.api.IMapController;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.infowindow.MarkerInfoWindow;

import java.util.ArrayList;
import java.util.List;

import fr.eni.carsharingaire.pojo.Parking;


public class MapFragment extends Fragment {
    private static final String TAG = ListFragment.class.getSimpleName();
    private static OnItineraireClickListener listener;

    MapView mp = null;
    static private List<Parking> list = null;
    static private Parking center = null;

    public MapFragment() {
        // Required empty public constructor
    }

    public static MapFragment newInstance(List<Parking> parkings, OnItineraireClickListener listener) {
        list = parkings;
        MapFragment.listener = listener;

        MapFragment fragment = new MapFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public static MapFragment newInstance(List<Parking> parkings, Parking parking , OnItineraireClickListener listener) {
        list = parkings;
        center = parking;
        MapFragment.listener = listener;
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
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_map, container, false);

        mp = view.findViewById(R.id.mapView);
        mp.setTileSource(TileSourceFactory.MAPNIK);

        final IMapController mapController = mp.getController();
        mapController.setZoom(13.0);
        GeoPoint startPoint = null;
        if(center == null){
            startPoint = new GeoPoint(47.2172500, -1.5533600);
        } else {
            startPoint = new GeoPoint(center.getLatitude(), center.getLongitude());
        }
        mapController.setCenter(startPoint);
        mp.setVerticalMapRepetitionEnabled(false);
        mp.setMinZoomLevel(3.0);
        mp.setScrollableAreaLimitLatitude(85,-85,0 );

        final List<Marker> overlayItems = new ArrayList<Marker>();
        for (final Parking parking:list) {

            Marker marker1 = new Marker(mp);
            marker1.setPosition(new GeoPoint(parking.getLatitude(), parking.getLongitude()));
            marker1.setIcon(getContext().getDrawable(R.drawable.ic_place_black_24dp));
            marker1.setTitle(parking.getNom());
            marker1.setOnMarkerClickListener(new Marker.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(final Marker marker, MapView mapView) {
                    hideMarker(overlayItems);
                    marker.setVisible(true);
                    MarkerInfoWindow markerInfoWindow = new MarkerInfoWindow(R.layout.bonuspack_bubble,mp);
                    String address = parking.getAdresse().concat(" ").concat(", ").concat(parking.getCommune());
                    address = address.replace(' ', '+');
                    Uri uri = Uri.parse("google.navigation:q=".concat(address));
                    if (listener != null)
                        listener.onItineraireClick(uri);
                    marker.setInfoWindow(new MarkerInfoWindow(R.layout.bonuspack_bubble,mp));
                    marker.showInfoWindow();
                    return false;
                }
            });
            overlayItems.add(marker1);

        }
        mp.getOverlays().addAll(overlayItems);

        /*
        List<IGeoPoint> points = new ArrayList<>();
        for (int i = 0; i < 10000; i++) {
            points.add(new LabelledGeoPoint(37 + Math.random() * 5, -8 + Math.random() * 5
                    , "Point #" + i));
        }

        // wrap them in a theme
        SimplePointTheme pt = new SimplePointTheme(points, true);

        // create label style
        Paint textStyle = new Paint();
        textStyle.setStyle(Paint.Style.FILL);
        textStyle.setColor(Color.parseColor("#0000ff"));
        textStyle.setTextAlign(Paint.Align.CENTER);
        textStyle.setTextSize(24);

        // set some visual options for the overlay
        // we use here MAXIMUM_OPTIMIZATION algorithm, which works well with >100k points
        SimpleFastPointOverlayOptions opt = SimpleFastPointOverlayOptions.getDefaultStyle()
                .setAlgorithm(SimpleFastPointOverlayOptions.RenderingAlgorithm.MAXIMUM_OPTIMIZATION)
                .setRadius(7).setIsClickable(true).setCellSize(15).setTextStyle(textStyle);

        // create the overlay with the theme
        final SimpleFastPointOverlay sfpo = new SimpleFastPointOverlay(pt, opt);

        // onClick callback
        sfpo.setOnClickListener(new SimpleFastPointOverlay.OnClickListener() {
            @Override
            public void onClick(SimpleFastPointOverlay.PointAdapter points, Integer point) {
                Toast.makeText(mp.getContext()
                        , "You clicked " + ((LabelledGeoPoint) points.get(point)).getLabel()
                        , Toast.LENGTH_SHORT).show();


            }
        });

        // add overlay
        mp.getOverlays().add(sfpo);*/

        return view;
    }

    public void hideMarker(List<Marker> listMarker){
        for (Marker marker:listMarker) {
            marker.getInfoWindow().close();
        }
    }

    public interface OnItineraireClickListener{
        void onItineraireClick(Uri uri);
    }





}
