package co.edu.uniandes.trickytrack.activities;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
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

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import co.edu.uniandes.trickytrack.R;
import co.edu.uniandes.trickytrack.Utils;
import co.edu.uniandes.trickytrack.adapters.AdapterGeneros;
import co.edu.uniandes.trickytrack.adapters.AdapterProm;
import co.edu.uniandes.trickytrack.retrofit.ExampleGeneros;
import co.edu.uniandes.trickytrack.retrofit.ExamplePromocion;
import co.edu.uniandes.trickytrack.retrofit.GenericaInterfazRetrofitJson;
import co.edu.uniandes.trickytrack.retrofit.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterPlaceActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener,
        GoogleApiClient.ConnectionCallbacks,
        com.google.android.gms.location.LocationListener {
    private static final String LOGTAG = "android-localizacion";

    private static final int PETICION_PERMISO_LOCALIZACION = 101;
    private static final int PETICION_CONFIG_UBICACION = 201;

    private GoogleApiClient apiClient;
    AdapterGeneros mAdapter;

    double latitudn, longitudn;

    private LocationRequest locRequest;
    String numberPhone = "3115321435";

    EditText search_bar, direccion_bar;
    CheckBox lunes, martes, miercoles, jueves, viernes, sabado, domingo;
Button search_btn;
RecyclerView mRecyclerView;
List<ExampleGeneros> modelList;
//seleccionados;
    JSONObject principal;
    JSONArray generos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_place);
        getSupportActionBar().setTitle("Registrar establecimiento");
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        //Construcción cliente API Google
        apiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addConnectionCallbacks(this)
                .addApi(LocationServices.API)
                .build();
        toggleLocationUpdates(true);
        search_bar = (EditText) findViewById(R.id.search_bar);
        direccion_bar = (EditText) findViewById(R.id.direccion_bar);
        lunes = (CheckBox) findViewById(R.id.lunes);
        martes = (CheckBox) findViewById(R.id.martes);
        miercoles = (CheckBox) findViewById(R.id.miercoles);
        jueves = (CheckBox) findViewById(R.id.jueves);
        viernes = (CheckBox) findViewById(R.id.viernes);
        sabado = (CheckBox) findViewById(R.id.sabado);
        domingo = (CheckBox) findViewById(R.id.domingo);
        search_btn=(Button)findViewById(R.id.search_btn);
        mRecyclerView=(RecyclerView)findViewById(R.id.generos_spinner);

        try {
            numberPhone = Utils.getPhone(RegisterPlaceActivity.this);
            Log.i("numtelef", "numtelf " + numberPhone);
        } catch (Exception e) {
            Log.i("numtelef", "numtelf No se puede completar carga de su numero de telefono\\n.Contactar admin");
            //Toast.makeText(getApplicationContext(), "No se puede completar carga de su numero de telefono\n.Contactar admin ", Toast.LENGTH_SHORT).show();
        }

        search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(search_bar.getText().toString().isEmpty()||direccion_bar.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(),"Llenar los campos (*)",Toast.LENGTH_SHORT).show();
                }else{
                    generosSeleccionados();
                    enviar();

                }
            }
        });
        modelList= new ArrayList<>();
        getGeneros();

    }

private void enviar(){


    final ProgressDialog progress = new ProgressDialog(RegisterPlaceActivity.this);
    progress.setMessage("Registrando establecimiento...");
    progress.show();
    progress.setCancelable(true);

    String url = Util.BASE_URL + "establecimiento";
    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
    StrictMode.setThreadPolicy(policy);
    try {
        RequestQueue requestQueue = Volley.newRequestQueue(RegisterPlaceActivity.this);

        /*Map<String, String> postParam= new HashMap<String, String>();
        postParam.put("nombre", search_bar.getText().toString());
        postParam.put("direccion",direccion_bar.getText().toString());
        postParam.put("longitud", String.valueOf(longitudn));
        postParam.put("latitud", String.valueOf(latitudn));
        postParam.put("lunes", String.valueOf(lunes.isChecked()));
        postParam.put("martes", String.valueOf(martes.isChecked()));
        postParam.put("miercoles", String.valueOf(miercoles.isChecked()));
        postParam.put("jueves", String.valueOf(jueves.isChecked()));
        postParam.put("viernes", String.valueOf(viernes.isChecked()));
        postParam.put("sabado", String.valueOf(sabado.isChecked()));
        postParam.put("domingo", String.valueOf(domingo.isChecked()));
        postParam.put("generos", generos);
        postParam.put("celular", numberPhone);*/
        principal.put("nombre", search_bar.getText().toString());
        principal.put("direccion",direccion_bar.getText().toString());
        principal.put("longitud", String.valueOf(longitudn));
        principal.put("latitud", String.valueOf(latitudn));
        principal.put("lunes", String.valueOf(lunes.isChecked()));
        principal.put("martes", String.valueOf(martes.isChecked()));
        principal.put("miercoles", String.valueOf(miercoles.isChecked()));
        principal.put("jueves", String.valueOf(jueves.isChecked()));
        principal.put("viernes", String.valueOf(viernes.isChecked()));
        principal.put("sabado", String.valueOf(sabado.isChecked()));
        principal.put("domingo", String.valueOf(domingo.isChecked()));
        principal.put("celular", numberPhone);

        Log.i("promocion","promocion info: "+principal);
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                //url, new JSONObject(postParam),
                url, principal,
                new com.android.volley.Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        progress.dismiss();
                        try {
                            if(response.has("id")){
                                setIdEstablecimiento(response.getString("id"));
                                msgPositive("Establecimiento creado correctamente");


                            }else{
                               // setIdEstablecimiento(response.getString("id"));
                                msgNegative(response.getString("message"));


                            }

                        } catch (Exception e) {
                            e.printStackTrace();

                        }
                        Log.i("promocion", "promocion response: "+response.toString());


                    }
                }, new com.android.volley.Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                progress.dismiss();
                try {
                    String jsonError = new String(error.networkResponse.data);
                    JSONObject obj = new JSONObject(jsonError);
                    //setIdEstablecimiento(obj.getString("id"));
                    msgNegative( obj.getString("message"));



                } catch (Throwable t) {
                    msgNegative( "1. Ha ocurrido un error. Contacte con su administrador "+t.toString());

                }



            }
        }) {

            /**
             * Passing some request headers
             * */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }



        };

        jsonObjReq.setTag("promocion");

        requestQueue.add(jsonObjReq);


    } catch (Exception e) {
        progress.dismiss();
        msgNegative( "2. Ha ocurrido un error. Contacte con su administrador");


    }
}

private void setIdEstablecimiento(String id){

    SharedPreferences prefs =
            getSharedPreferences("PreferenciasEstablecimiento", Context.MODE_PRIVATE);

    SharedPreferences.Editor editor = prefs.edit();
    editor.putString("idEstablecimiento", id);

    editor.commit();
}
private void generosSeleccionados(){
   // seleccionados= new ArrayList<>();
    principal= new JSONObject();
     generos= new JSONArray();
    for(int i=0;i<mAdapter.getmModelList().size();i++){
        if(mAdapter.getmModelList().get(i).isSelected()) {
            //seleccionados.add(mAdapter.getmModelList().get(i));

            JSONObject jGroup = new JSONObject();// /sub Object
            try {
                jGroup.put("id", mAdapter.getmModelList().get(i).getId());
                jGroup.put("nombre", mAdapter.getmModelList().get(i).getNombre());


                generos.put(jGroup);


            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
    }
    try {
        principal.put("generos", generos);
    } catch (JSONException e) {
        e.printStackTrace();
    }
}
    private void getGeneros(){
        final ProgressDialog progress = new ProgressDialog(RegisterPlaceActivity.this);
        progress.setMessage("Cargando");
        progress.show();
        progress.setCancelable(true);
        final LinearLayoutManager manager = new LinearLayoutManager(RegisterPlaceActivity.this);
        GenericaInterfazRetrofitJson mService = null;
        mService = Util.getService();
        mService.getGeneros("generos").enqueue(new Callback<List<ExampleGeneros>>() {
            @Override
            public void onResponse(Call<List<ExampleGeneros>> call, Response<List<ExampleGeneros>> response) {

                if (response.isSuccessful()) {

                    if (response.body().size() > 0) {
                        mRecyclerView.setVisibility(View.VISIBLE);

                        modelList= response.body();
                         mAdapter = new AdapterGeneros(modelList, getApplicationContext());
                        mRecyclerView.setHasFixedSize(true);
                        mRecyclerView.setLayoutManager(manager);
                        mRecyclerView.setAdapter(mAdapter);
                        mRecyclerView.setNestedScrollingEnabled(false);
                    } else {
                        mRecyclerView.setVisibility(View.GONE);


                    }

                } else {
                    mRecyclerView.setVisibility(View.GONE);


                }
                progress.dismiss();
            }

            @Override
            public void onFailure(Call<List<ExampleGeneros>> call, Throwable t) {
                progress.dismiss();
                mRecyclerView.setVisibility(View.GONE);

            }
        });
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
                            Toast.makeText(RegisterPlaceActivity.this, "Se requiere activar GPS primero", Toast.LENGTH_SHORT).show();
                            status.startResolutionForResult(RegisterPlaceActivity.this, PETICION_CONFIG_UBICACION);
                        } catch (IntentSender.SendIntentException e) {
                            //btnActualizar.setChecked(false);
                            Toast.makeText(RegisterPlaceActivity.this, "Error al intentar solucionar configuración de ubicación", Toast.LENGTH_SHORT).show();
                        }

                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        Toast.makeText(RegisterPlaceActivity.this, "No se puede cumplir la configuración de ubicación necesaria", Toast.LENGTH_SHORT).show();
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
        if (ActivityCompat.checkSelfPermission(RegisterPlaceActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            //Ojo: estamos suponiendo que ya tenemos concedido el permiso.
            //Sería recomendable implementar la posible petición en caso de no tenerlo.

            Log.i(LOGTAG, "Inicio de recepción de ubicaciones");

            LocationServices.FusedLocationApi.requestLocationUpdates(
                    apiClient, locRequest, RegisterPlaceActivity.this);
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

    private void msgNegative(String orden){
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder
                .setTitle(getString(R.string.app_name))
                .setMessage(orden.concat(orden))
                .setCancelable(false)
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                }).show();
    }

    private void msgPositive(String orden){
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder
                .setTitle(getString(R.string.app_name))
                .setMessage(orden)
                .setCancelable(false)
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        finish();
                    }
                }).show();
    }
}
