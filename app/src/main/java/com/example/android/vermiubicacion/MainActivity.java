package com.example.android.vermiubicacion;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class MainActivity extends AppCompatActivity
        implements GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks {

    Button buton1;
    Snackbar snack;
    RelativeLayout milayout;
    GoogleApiClient cliente;
    Location miUbicacion=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buton1 = findViewById(R.id.button2);
        milayout = findViewById(R.id.layout1);
        //--inicializar componentes (Cliente)--//
        cliente = new GoogleApiClient.Builder(this).enableAutoManage(this, this).addConnectionCallbacks(this).addApi(LocationServices.API).build();

        //--desactivamos el botoon para que no lo presionen--//
        buton1.setEnabled(false);

        //---solicitar permisos----//
        if (verificar_permisos()) {
            iniciarcomponenete();
        } else {
            solicitarpermisos();
        }
        buton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //---obtener coordenadas---
                if(miUbicacion!=null){
                    Intent vermapa=new Intent(MainActivity.this,MapsActivity.class);
                    vermapa.putExtra("latitud",miUbicacion.getLatitude());
                    vermapa.putExtra("longitud",miUbicacion.getLongitude());
                    startActivity(vermapa);
                    Log.d("Latitud",String.valueOf(miUbicacion.getLatitude()));
                    Log.d("Longitud",String.valueOf(miUbicacion.getLongitude()));
                }
                else
                {
                    //ver error en timepo de ejecucion
                    Log.d("ERROR","mi ubicacion es nulo");
                }


            }
        });


    }

    private void solicitarpermisos() {
        //solicitar los permisos
        if ( ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, ACCESS_FINE_LOCATION)
                || ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, ACCESS_COARSE_LOCATION))
        {
            snack=Snackbar.make(milayout,"Te has olvidado de los permisos.",Snackbar.LENGTH_INDEFINITE).setAction(android.R.string.ok, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ActivityCompat.requestPermissions(MainActivity.this,new String[]{

                            ACCESS_FINE_LOCATION,
                            ACCESS_COARSE_LOCATION,
                    },100);
                }
            });
            snack.show();
        }
        else
        {
            ActivityCompat.requestPermissions(MainActivity.this,new String[]{

                    ACCESS_FINE_LOCATION,
                    ACCESS_COARSE_LOCATION,
            },100);
        }



    }

    private boolean verificar_permisos() {
        //--compoara la version de android pordefecto la 23 que es la 5.5
        if (Build.VERSION.SDK_INT<Build.VERSION_CODES.M)
        {
            return true;
        }
        //--Verificar los permisos si es que los tiene--//
        if (ContextCompat.checkSelfPermission(MainActivity.this,ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(MainActivity.this,ACCESS_COARSE_LOCATION)==PackageManager.PERMISSION_GRANTED
                )
        {
            return true;
        }


        return false;
    }

    private void iniciarcomponenete() {
        buton1.setEnabled(true);
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
         miUbicacion = LocationServices.FusedLocationApi.getLastLocation(cliente);


    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.d("Error","Conexion Suspendida");

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d("ERROR","SinConexion al servidor");

    }
}
