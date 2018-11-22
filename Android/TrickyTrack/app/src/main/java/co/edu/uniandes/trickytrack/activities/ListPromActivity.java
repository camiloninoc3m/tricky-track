package co.edu.uniandes.trickytrack.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import co.edu.uniandes.trickytrack.R;
import co.edu.uniandes.trickytrack.adapters.AdapterProm;
import co.edu.uniandes.trickytrack.adapters.RecyclerViewAdapter;
import co.edu.uniandes.trickytrack.retrofit.Elementos;
import co.edu.uniandes.trickytrack.retrofit.ElementosPromociones;
import co.edu.uniandes.trickytrack.retrofit.Example;
import co.edu.uniandes.trickytrack.retrofit.ExamplePromocion;
import co.edu.uniandes.trickytrack.retrofit.GenericaInterfazRetrofitJson;
import co.edu.uniandes.trickytrack.retrofit.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListPromActivity extends AppCompatActivity {
    List<ExamplePromocion> modelList = new ArrayList<>();
     RecyclerView mRecyclerView;
    TextView nodata;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_prom);
        final ProgressDialog progress = new ProgressDialog(ListPromActivity.this);
        progress.setMessage("Cargando");
        progress.show();
        progress.setCancelable(true);
        nodata=(TextView)findViewById(R.id.nodata);
         mRecyclerView = (RecyclerView) findViewById(R.id.promociones);
        final LinearLayoutManager manager = new LinearLayoutManager(ListPromActivity.this);

        String nombre=getIntent().getExtras().getString("nombre");
        Integer idEstablecimiento = getIntent().getExtras().getInt("idEstablecimiento");
        getSupportActionBar().setTitle("Promociones "+nombre);
        GenericaInterfazRetrofitJson mService = null;
        mService = Util.getService();
        mService.getPromociones("promociones/establecimiento/"+idEstablecimiento).enqueue(new Callback<List<ExamplePromocion>>() {
            @Override
            public void onResponse(Call<List<ExamplePromocion>> call, Response<List<ExamplePromocion>> response) {

                if (response.isSuccessful()) {

                    if (response.body().size() > 0) {
                        mRecyclerView.setVisibility(View.VISIBLE);
                        nodata.setVisibility(View.GONE);
                        modelList= response.body();
                        AdapterProm mAdapter = new AdapterProm(modelList, getApplicationContext());
                        mRecyclerView.setHasFixedSize(true);
                        mRecyclerView.setLayoutManager(manager);
                        mRecyclerView.setAdapter(mAdapter);
                        mRecyclerView.setNestedScrollingEnabled(false);
                    } else {
                        mRecyclerView.setVisibility(View.GONE);
                        nodata.setVisibility(View.VISIBLE);

                    }

                } else {
                    mRecyclerView.setVisibility(View.GONE);
                    nodata.setVisibility(View.VISIBLE);

                }
                progress.dismiss();
            }

            @Override
            public void onFailure(Call<List<ExamplePromocion>> call, Throwable t) {
                progress.dismiss();
                mRecyclerView.setVisibility(View.GONE);
                nodata.setVisibility(View.VISIBLE);
            }
        });
    }
}
