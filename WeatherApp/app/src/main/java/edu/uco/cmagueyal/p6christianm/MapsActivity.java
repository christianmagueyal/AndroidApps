package edu.uco.cmagueyal.p6christianm;

import android.app.Activity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends Activity implements OnMapReadyCallback {

    private GoogleMap mMap;
    public static final String CITY_LAT = "0.0";
    public static final String CITY_LON = "0.0";
    public static  Double LAT =0.0;
    public static  Double LON =0.0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        Bundle b = getIntent().getExtras();
        LAT = b.getDouble("lat");
        LON = b.getDouble("lon");
        LatLng city = new LatLng(LAT, LON);
        mMap.addMarker(new MarkerOptions().position(city).title(b.getString("mark")));
        //// mMap.moveCamera(CameraUpdateFactory.newLatLng(city));
         mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(city, 10));
    }
}
