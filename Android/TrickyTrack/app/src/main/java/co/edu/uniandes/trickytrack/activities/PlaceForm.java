package co.edu.uniandes.trickytrack.activities;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;

import java.util.ArrayList;
import java.util.List;

import co.edu.uniandes.trickytrack.R;
import co.edu.uniandes.trickytrack.adapters.RecyclerViewAdapter;
import co.edu.uniandes.trickytrack.models.Model;
import co.edu.uniandes.trickytrack.retrofit.Elementos;
import co.edu.uniandes.trickytrack.retrofit.Example;
import co.edu.uniandes.trickytrack.retrofit.GenericaInterfazRetrofitJson;
import co.edu.uniandes.trickytrack.retrofit.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlaceForm extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener,
        GoogleApiClient.ConnectionCallbacks,
        com.google.android.gms.location.LocationListener {
    private List<Model> mModelList;
    private RecyclerView mRecyclerView;
    private RecyclerViewAdapter mAdapter;

    private static final String LOGTAG = "android-localizacion";

    private static final int PETICION_PERMISO_LOCALIZACION = 101;
    private static final int PETICION_CONFIG_UBICACION = 201;

    private GoogleApiClient apiClient;


    double latitudn, longitudn;

    private LocationRequest locRequest;
    private List<String> seleccionados;
    EditText search_bar;
    CheckBox validate_schedule;
    Button search_btn;
    String generos="";
    String url="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_form);
        getSupportActionBar().setTitle(getString(R.string.place_title));

        mRecyclerView = (RecyclerView) findViewById(R.id.gender_spinner);
        mAdapter = new RecyclerViewAdapter(getListData(),getApplicationContext());
         search_bar=(EditText)findViewById(R.id.search_bar);

         validate_schedule=(CheckBox)findViewById(R.id.validate_schedule);
        search_btn=(Button)findViewById(R.id.search_btn);
        LinearLayoutManager manager = new LinearLayoutManager(PlaceForm.this);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setNestedScrollingEnabled(false);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        //Construcción cliente API Google
        apiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addConnectionCallbacks(this)
                .addApi(LocationServices.API)
                .build();
        toggleLocationUpdates(true);

        //btn event clic
        search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                seleccionados= new ArrayList<>();
                generos="";

               for(int i=0;i<mAdapter.getmModelList().size();i++){
                   if(mAdapter.getmModelList().get(i).isSelected()){
                       seleccionados.add(mAdapter.getmModelList().get(i).getText());
                   }
                }
                 url="establecimientos?nombre";
               String url_coordenada="&longitud&latitud";
               if(latitudn!=0||longitudn!=0){
                url_coordenada="&longitud="+longitudn+"&latitud="+latitudn;
               }
               url=url+url_coordenada;
               String valida_horario="&validaHorario="+validate_schedule.isChecked();
               url=url+valida_horario;

               if(seleccionados.size()>0){
                   for(int i=0; i<seleccionados.size();i++){
                       generos=generos+"&generos"+"="+seleccionados.get(i);
                   }
               }else{
                   generos="&generos";
               }

               url=url+generos;
                final ProgressDialog progress = new ProgressDialog(PlaceForm.this);
                progress.setMessage("Cargando");
                progress.show();
                progress.setCancelable(true);
                GenericaInterfazRetrofitJson mService = null;
                mService = Util.getService();
                mService.getPlaces(url).enqueue(new Callback<List<Example>>() {
                    @Override
                    public void onResponse(Call<List<Example>> call, Response<List<Example>> response) {

                        if (response.isSuccessful()) {

                                if(response.body().size()>0){
                                    Elementos elementos= new Elementos();
                                    elementos.setElementos(response.body());
                                    Intent intent= new Intent(PlaceForm.this,MapsActivity.class);
                                    intent.putExtra("puntos",elementos);
                                    intent.putExtra("latitud",latitudn);
                                    intent.putExtra("longitud",longitudn);
                                    startActivity(intent);
                                }else{
                                    Toast.makeText(PlaceForm.this,"Busqueda sin resultados",Toast.LENGTH_SHORT).show();

                                }

                        } else {
                            Toast.makeText(PlaceForm.this,"Busqueda sin resultados",Toast.LENGTH_SHORT).show();

                        }
                        progress.dismiss();
                    }

                    @Override
                    public void onFailure(Call<List<Example>>call, Throwable t) {
                        progress.dismiss();
                        Toast.makeText(PlaceForm.this,"Busqueda sin resultados",Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
    }
    //llenar generos musicales
    private List<Model> getListData() {
        mModelList = new ArrayList<>();
        String[] resourceString =getResources().getStringArray(R.array.genders);

        for (int i = 0; i < resourceString.length; i++) {
            mModelList.add(new Model(resourceString[i]));
        }
        return mModelList;
    }

    //seccion de gps
    private void toggleLocationUpdates(boolean enable) {
        if (enable) {
            enableLocationUpdates();
        } else {
            disableLocationUpdates();
        }
    }

    private void enableLocationUpdates() {

        locRequest = new LocationRequest();
        locRequest.setInterval(2000);
        locRequest.setFastestInterval(1000);
        locRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationSettingsRequest locSettingsRequest =
                new LocationSettingsRequest.Builder()
                        .addLocationRequest(locRequest)
                        .build();

        PendingResult<LocationSettingsResult> result =
                LocationServices.SettingsApi.checkLocationSettings(
                        apiClient, locSettingsRequest);

        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(LocationSettingsResult locationSettingsResult) {
                final Status status = locationSettingsResult.getStatus();
                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:

                        Log.i(LOGTAG, "Configuración correcta");
                        startLocationUpdates();

                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        try {
                            Toast.makeText(PlaceForm.this, "Se requiere activar GPS primero", Toast.LENGTH_SHORT).show();
                            status.startResolutionForResult(PlaceForm.this, PETICION_CONFIG_UBICACION);
                        } catch (IntentSender.SendIntentException e) {
                            //btnActualizar.setChecked(false);
                            Toast.makeText(PlaceForm.this, "Error al intentar solucionar configuración de ubicación", Toast.LENGTH_SHORT).show();
                        }

                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        Toast.makeText(PlaceForm.this, "No se puede cumplir la configuración de ubicación necesaria", Toast.LENGTH_SHORT).show();
                        // btnActualizar.setChecked(false);
                        break;
                }
            }
        });
    }

    private void disableLocationUpdates() {

        LocationServices.FusedLocationApi.removeLocationUpdates(
                apiClient, this);

    }

    private void startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(PlaceForm.this,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            //Ojo: estamos suponiendo que ya tenemos concedido el permiso.
            //Sería recomendable implementar la posible petición en caso de no tenerlo.

            Log.i(LOGTAG, "Inicio de recepción de ubicaciones");

            LocationServices.FusedLocationApi.requestLocationUpdates(
                    apiClient, locRequest, PlaceForm.this);
        }
    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {
        //Se ha producido un error que no se puede resolver automáticamente
        //y la conexión con los Google Play Services no se ha establecido.

        Log.i(LOGTAG, "Error grave al conectar con Google Play Services");
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        //Conectado correctamente a Google Play Services

        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    PETICION_PERMISO_LOCALIZACION);
        } else {

            Location lastLocation =
                    LocationServices.FusedLocationApi.getLastLocation(apiClient);

            //updateUI(lastLocation);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        //Se ha interrumpido la conexión con Google Play Services

        Log.i(LOGTAG, "Se ha interrumpido la conexión con Google Play Services");
    }

    private void updateUI(Location loc) {
        if (loc != null) {

            Log.i(LOGTAG, "Latitud: " + String.valueOf(loc.getLatitude()));
            Log.i(LOGTAG, "Longitud: " + String.valueOf(loc.getLongitude()));
            latitudn = loc.getLatitude();
            longitudn = loc.getLongitude();


        } else {
            Log.i(LOGTAG, "Latitud: (desconocida)");
            Log.i(LOGTAG, "Longitud: (desconocida)");
            latitudn = 0;
            longitudn = 0;
        }




    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == PETICION_PERMISO_LOCALIZACION) {
            if (grantResults.length == 1
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                //Permiso concedido

                @SuppressWarnings("MissingPermission")
                Location lastLocation =
                        LocationServices.FusedLocationApi.getLastLocation(apiClient);

                updateUI(lastLocation);

            } else {
                //Permiso denegado:
                //Deberíamos deshabilitar toda la funcionalidad relativa a la localización.
                Toast.makeText(getApplicationContext(),  "permiso denegado",Toast.LENGTH_SHORT).show();

            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case PETICION_CONFIG_UBICACION:
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        startLocationUpdates();
                        break;
                    case Activity.RESULT_CANCELED:
                        Log.i(LOGTAG, "El usuario no ha realizado los cambios de configuración necesarios");
                        // btnActualizar.setChecked(false);
                        break;
                }
                break;
        }
    }

    @Override
    public void onLocationChanged(Location location) {

        Log.i(LOGTAG, "Recibida nueva ubicación!");

        //Mostramos la nueva ubicación recibida

        updateUI(location);
    }

//end region gps


}
