package co.edu.uniandes.trickytrack.activities;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

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
import co.edu.uniandes.trickytrack.retrofit.ExampleGeneros;
import co.edu.uniandes.trickytrack.retrofit.GenericaInterfazRetrofitJson;
import co.edu.uniandes.trickytrack.retrofit.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterCustomerActivity extends AppCompatActivity {
    String numberPhone = "3115321435";
    Button search_btn;
    EditText name, age;
    List<ExampleGeneros> modelList;
    Spinner sexo;
    AdapterGeneros mAdapter;
    RecyclerView mRecyclerView;


    JSONObject principal;
    JSONArray generos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_customer);
        getSupportActionBar().setTitle("Registrar cliente");
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        search_btn=(Button)findViewById(R.id.search_btn);
        name = (EditText) findViewById(R.id.name);
        age = (EditText) findViewById(R.id.age);
        sexo=(Spinner)findViewById(R.id.sexo);
        mRecyclerView=(RecyclerView)findViewById(R.id.generos_spinner);
        try {
            numberPhone = Utils.getPhone(RegisterCustomerActivity.this);
            Log.i("numtelef", "numtelf " + numberPhone);
        } catch (Exception e) {
            Log.i("numtelef", "numtelf No se puede completar carga de su numero de telefono\\n.Contactar admin");

        }

        search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(name.getText().toString().isEmpty()||age.getText().toString().isEmpty()){
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


        final ProgressDialog progress = new ProgressDialog(RegisterCustomerActivity.this);
        progress.setMessage("Registrando cliente...");
        progress.show();
        progress.setCancelable(true);

        String url = Util.BASE_URL + "cliente";
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        try {
            RequestQueue requestQueue = Volley.newRequestQueue(RegisterCustomerActivity.this);


            principal.put("nombre", name.getText().toString());
            principal.put("celular", numberPhone);
            principal.put("edad", age.getText().toString());
            principal.put("genero", sexo.getSelectedItem().toString());

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
                                    setIdCliente(response.getString("id"));
                                    msgPositive("Cliente creado correctamente");


                                }else{

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

            jsonObjReq.setTag("cliente");

            requestQueue.add(jsonObjReq);


        } catch (Exception e) {
            progress.dismiss();
            msgNegative( "2. Ha ocurrido un error. Contacte con su administrador");


        }
    }

    private void setIdCliente(String id){

        SharedPreferences prefs =
                getSharedPreferences("PreferenciasEstablecimiento", Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("idCliente", id);

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
        final ProgressDialog progress = new ProgressDialog(RegisterCustomerActivity.this);
        progress.setMessage("Cargando");
        progress.show();
        progress.setCancelable(true);
        final LinearLayoutManager manager = new LinearLayoutManager(RegisterCustomerActivity.this);
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
