package com.example.android.vermiubicacion;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    double lat,lon;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        //se obtiene el valor de la class que la llamo

        Intent recepcion=getIntent();
        Bundle b=recepcion.getExtras();
        if(b!=null)
        {
            lat=(double)b.get("latitud");
            lon=(double)b.get("longitud");
        }else
        {
            lat=0.0;
            lon=0.0;
        }

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

        UiSettings configuracionMapa=mMap.getUiSettings();
        //son el mas y menos
        configuracionMapa.setZoomControlsEnabled(true);
    //tipo de mapa
        mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);


        // Add a marker in Sydney and move the camera
        LatLng Lima = new LatLng(lat, lon);
        LatLng PlazaGrau = new LatLng(-12.0598022,-77.0379457);
        mMap.addMarker(new MarkerOptions().position(Lima).title("Soy yo").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));
        mMap.addMarker(new MarkerOptions().position(PlazaGrau).title("Plaza Grau").icon(BitmapDescriptorFactory.fromResource(R.drawable.volvo)));

        //mMap.moveCamera(CameraUpdateFactory.newLatLng(Lima));

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(Lima,16));

    }
}
