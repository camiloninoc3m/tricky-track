package co.edu.uniandes.trickytrack.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.StrictMode;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import co.edu.uniandes.trickytrack.R;
import co.edu.uniandes.trickytrack.retrofit.Util;

public class RedimirActivity extends AppCompatActivity {
TextView nodata;
LinearLayout redencion;
ImageView qr_redencion;
    String numberPhone = "3115321435";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_redimir);
        getSupportActionBar().setTitle("Redimir Cupón");
         nodata=(TextView)findViewById(R.id.nodata);
         redencion=(LinearLayout)findViewById(R.id.redencion);
         qr_redencion=(ImageView)findViewById(R.id.qr_redencion);
        String idPromocion=getIntent().getExtras().getString("idPromocion");

        try {
            numberPhone=getPhone();
            Log.i("numtelef", "numtelf " + numberPhone);
        } catch (Exception e) {
            Log.i("numtelef", "numtelf No se puede completar carga de su numero de telefono\\n.Contactar admin" );
            //Toast.makeText(getApplicationContext(), "No se puede completar carga de su numero de telefono\n.Contactar admin ", Toast.LENGTH_SHORT).show();
        }

        final ProgressDialog progress = new ProgressDialog(RedimirActivity.this);
        progress.setMessage("Generenado cupón...");
        progress.show();
        progress.setCancelable(true);

        String url = Util.BASE_URL + "cupon";
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        try {
            RequestQueue requestQueue = Volley.newRequestQueue(RedimirActivity.this);
            Map<String, String> postParam= new HashMap<String, String>();
            postParam.put("celular", numberPhone);
            postParam.put("idPromocion",idPromocion);
            Log.i("promocion","promocion info: "+new JSONObject(postParam));
            JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                    url, new JSONObject(postParam),
                    new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            progress.dismiss();
                            try {
                                if(response.has("url")){
                                    nodata.setVisibility(View.GONE);

                                    redencion.setVisibility(View.VISIBLE);
                                    //Glide.with(RedimirActivity.this).load(response.getString("url")).into(qr_redencion);
                                    Glide.with(RedimirActivity.this).load("https://eogn.files.wordpress.com/2015/12/static_qr_code_without_logo.png").into(qr_redencion);
                                }else{
                                    nodata.setVisibility(View.VISIBLE);
                                    nodata.setText(response.getString("message"));
                                    redencion.setVisibility(View.GONE);
                                }

                            } catch (Exception e) {
                                e.printStackTrace();

                            }
                            Log.i("promocion", "promocion response: "+response.toString());


                        }
                    }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    progress.dismiss();
                    try {
                        String jsonError = new String(error.networkResponse.data);
                        JSONObject obj = new JSONObject(jsonError);
                        nodata.setVisibility(View.VISIBLE);
                        nodata.setText( obj.getString("message"));
                        redencion.setVisibility(View.GONE);

                    } catch (Throwable t) {
                        nodata.setVisibility(View.VISIBLE);
                        nodata.setText("1. Ha ocurrido un error. Contacte con su administrador");
                        redencion.setVisibility(View.GONE);
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
            nodata.setVisibility(View.VISIBLE);
            nodata.setText("2. Ha ocurrido un error. Contacte con su administrador");
            redencion.setVisibility(View.GONE);


        }
    }

    @SuppressLint("MissingPermission")
    private String getPhone() {
        TelephonyManager tMgr = (TelephonyManager)
                RedimirActivity.this.getSystemService(Context.TELEPHONY_SERVICE);

        String MyPhoneNumber = "3115321435";

        try {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_NUMBERS) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                //return TODO;
            }
            MyPhoneNumber = tMgr.getLine1Number();
        } catch (NullPointerException ex) {
        }

        if (MyPhoneNumber.equals("")) {
            MyPhoneNumber = tMgr.getSubscriberId();
        }
        return MyPhoneNumber;
    }
}
