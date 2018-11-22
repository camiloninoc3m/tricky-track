package co.edu.uniandes.trickytrack.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;

import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;

import java.util.List;

import co.edu.uniandes.trickytrack.R;
import co.edu.uniandes.trickytrack.Utils;
import co.edu.uniandes.trickytrack.adapters.AdapterGeneros;
import co.edu.uniandes.trickytrack.retrofit.ExampleGeneros;
import co.edu.uniandes.trickytrack.retrofit.ExampleValidacion;
import co.edu.uniandes.trickytrack.retrofit.GenericaInterfazRetrofitJson;
import co.edu.uniandes.trickytrack.retrofit.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashActivity extends Activity {
    // private Handler mHandler = new Handler();
    String numberPhone = "3115321435";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        try {
            numberPhone = Utils.getPhone(SplashActivity.this);
            Log.i("numtelef", "numtelf " + numberPhone);
        } catch (Exception e) {
            Log.i("numtelef", "numtelf No se puede completar carga de su numero de telefono\\n.Contactar admin");

        }
        final LinearLayoutManager manager = new LinearLayoutManager(SplashActivity.this);
        GenericaInterfazRetrofitJson mService = null;
        mService = Util.getService();
        mService.getValidacion("usuario/celular/" + numberPhone).enqueue(new Callback<ExampleValidacion>() {
            @Override
            public void onResponse(Call<ExampleValidacion> call, Response<ExampleValidacion> response) {

                if (response.isSuccessful()) {
                    if (response.body().getIdEstablecimiento() != null) {
                        setIdEstablecimiento(String.valueOf(response.body().getIdEstablecimiento()));
                    } else {
                        setIdEstablecimiento("000");
                    }
                    if (response.body().getIdCliente() != null) {
                        setIdCliente(String.valueOf(response.body().getIdCliente()));
                    } else {
                        setIdCliente("000");
                    }

                    lanzarPerfil();
                } else {
                    Log.i("respuesta", "respuesta no sucess " + response.body().toString());
                    setIdEstablecimiento("000");
                    setIdCliente("000");
                    lanzarPerfil();
                }

            }

            @Override
            public void onFailure(Call<ExampleValidacion> call, Throwable t) {

                Log.i("respuesta", "respuesta no error " + t.toString());
                setIdEstablecimiento("000");
                setIdCliente("000");
                lanzarPerfil();
            }
        });

        }

    private void lanzarPerfil() {
        Intent intent = new Intent(SplashActivity.this, ProfileActivity.class);
        startActivity(intent);
        finish();
    }

    private void setIdEstablecimiento(String idEstablecimiento) {

        SharedPreferences prefs =
                getSharedPreferences("PreferenciasEstablecimiento", Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("idEstablecimiento", idEstablecimiento);


        editor.commit();
    }

    private void setIdCliente(String idCliente) {

        SharedPreferences prefs =
                getSharedPreferences("PreferenciasEstablecimiento", Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = prefs.edit();

        editor.putString("idCliente", idCliente);

        editor.commit();
    }
}
