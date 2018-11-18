package co.edu.uniandes.trickytrack.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Base64;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import co.edu.uniandes.trickytrack.R;
import co.edu.uniandes.trickytrack.retrofit.Util;

public class PromPlaceActivity extends AppCompatActivity {
    String numberPhone = "3115321435";
    String image = "";
    EditText promotion_bar, start_date, end_date, valor_prom, cantidad;
    CheckBox prom_active;
    Button search_image, save;
    TextView ruta;
    int SELECT_FILE=22;
    int REQUEST_CAMERA=3;
    Calendar myCalendar = Calendar.getInstance();

    DatePickerDialog.OnDateSetListener date1 = new
            DatePickerDialog.OnDateSetListener() {

                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear,
                                      int dayOfMonth) {
                    // TODO Auto-generated method stub
                    myCalendar.set(Calendar.YEAR, year);
                    myCalendar.set(Calendar.MONTH, monthOfYear);
                    myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    updateLabel1();
                }

            };

    private void updateLabel1() {

        String myFormat = "yyyy-MM-dd'T'HH:mm:ssZ"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        start_date.setText(sdf.format(myCalendar.getTime()));
    }

    DatePickerDialog.OnDateSetListener date2 = new
            DatePickerDialog.OnDateSetListener() {

                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear,
                                      int dayOfMonth) {
                    // TODO Auto-generated method stub
                    myCalendar.set(Calendar.YEAR, year);
                    myCalendar.set(Calendar.MONTH, monthOfYear);
                    myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    updateLabel2();
                }

            };

    private void updateLabel2() {

        String myFormat = "yyyy-MM-dd'T'HH:mm:ssZ"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        end_date.setText(sdf.format(myCalendar.getTime()));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prom_place);
        getSupportActionBar().setTitle("Crear Promociones");
        promotion_bar = (EditText) findViewById(R.id.promotion_bar);
        start_date = (EditText) findViewById(R.id.start_date);
        end_date = (EditText) findViewById(R.id.end_date);
        valor_prom = (EditText) findViewById(R.id.valor_prom);
        cantidad = (EditText) findViewById(R.id.cantidad);
        prom_active = (CheckBox) findViewById(R.id.prom_active);
        search_image = (Button) findViewById(R.id.search_image);
        ruta = (TextView) findViewById(R.id.ruta);
        save = (Button) findViewById(R.id.save);
        try {
            numberPhone=getPhone();
            Log.i("numtelef", "numtelf " + numberPhone);
        } catch (Exception e) {
            Log.i("numtelef", "numtelf No se puede completar carga de su numero de telefono\\n.Contactar admin" );
            //Toast.makeText(getApplicationContext(), "No se puede completar carga de su numero de telefono\n.Contactar admin ", Toast.LENGTH_SHORT).show();
        }

        start_date.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    new DatePickerDialog(PromPlaceActivity.this, date1, myCalendar
                            .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                            myCalendar.get(Calendar.DAY_OF_MONTH)).show();

                }
                return false;
            }
        });

        end_date.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    new DatePickerDialog(PromPlaceActivity.this, date2, myCalendar
                            .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                            myCalendar.get(Calendar.DAY_OF_MONTH)).show();

                }
                return false;
            }
        });

        search_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //promotion_bar, start_date, end_date, valor_prom, cantidad

                if(promotion_bar.getText().toString().isEmpty()||start_date.getText().toString().isEmpty()||end_date.getText().toString().isEmpty()
                        ||valor_prom.getText().toString().isEmpty()||cantidad.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(),"LLenar los campos",Toast.LENGTH_SHORT).show();
                }else{

                    final ProgressDialog progress = new ProgressDialog(PromPlaceActivity.this);
                    progress.setMessage("enviando...");
                    progress.show();
                    progress.setCancelable(true);

                    String url = Util.BASE_URL + "promocion";
                    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                    StrictMode.setThreadPolicy(policy);
                    try {
                          RequestQueue requestQueue = Volley.newRequestQueue(PromPlaceActivity.this);
                        Map<String, String> postParam= new HashMap<String, String>();
                        postParam.put("celular", numberPhone);
                        postParam.put("nombre", promotion_bar.getText().toString());
                        postParam.put("fechaInicio", start_date.getText().toString());
                        postParam.put("fechaFin", end_date.getText().toString());
                        postParam.put("cantidad", cantidad.getText().toString());
                        postParam.put("valor", valor_prom.getText().toString());
                        postParam.put("imagen","");
                        postParam.put("activa", String.valueOf(prom_active.isChecked()));
                        Log.i("promocion","promocion info: "+new JSONObject(postParam));
                        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                                url, new JSONObject(postParam),
                                new Response.Listener<JSONObject>() {

                                    @Override
                                    public void onResponse(JSONObject response) {
                                        progress.dismiss();
                                        try {
                                            msgPositive(response.getString("id"));
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                        Log.i("promocion", "promocion response: "+response.toString());


                                    }
                                }, new Response.ErrorListener() {

                            @Override
                            public void onErrorResponse(VolleyError error) {
                                progress.dismiss();
                                msgNegative("1");
                                Log.i("promocion", "promocion Error: " + error.getMessage() +" codigo "+error.networkResponse.statusCode);

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
                        msgNegative("2");


                    }
                }
            }
        });
    }

    @SuppressLint("MissingPermission")
    private String getPhone() {
        TelephonyManager tMgr = (TelephonyManager)
                PromPlaceActivity.this.getSystemService(Context.TELEPHONY_SERVICE);

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
private void msgNegative(String orden){
    final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
    alertDialogBuilder
            .setTitle(getString(R.string.app_name))
            .setMessage(orden.concat(" Ha ocurrido un error. Por favor contacte al administrador de la aplicación"))
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
                .setMessage("Promocion "+orden+" creada")
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
