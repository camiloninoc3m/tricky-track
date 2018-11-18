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

        String myFormat = "yyyy/MM/dd'T'HH:mm:ss Z"; //In which you need put here
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

        String myFormat = "yyyy/MM/dd'T'HH:mm:ss Z"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        end_date.setText(sdf.format(myCalendar.getTime()));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prom_place);
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

            Toast.makeText(getApplicationContext(), "No se puede completar carga de su numero de telefono\n.Contactar admin ", Toast.LENGTH_SHORT).show();
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
/*RequestQueue requestQueue = Volley.newRequestQueue(PromPlaceActivity.this);
                        JSONObject jsonBodyObj = new JSONObject();
                        jsonBodyObj.put("celular", numberPhone);
                        jsonBodyObj.put("nombre", promotion_bar.getText().toString());
                        jsonBodyObj.put("fechaInicio", start_date.getText().toString());
                        jsonBodyObj.put("fechaFin", end_date.getText().toString());
                        jsonBodyObj.put("cantidad", cantidad.getText().toString());
                        jsonBodyObj.put("valor", valor_prom.getText().toString());
                        jsonBodyObj.put("imagen","");
                        jsonBodyObj.put("activa", String.valueOf(prom_active.isChecked()));
                        final String requestBody = jsonBodyObj.toString();*/
                    try {
   RequestQueue requestQueue = Volley.newRequestQueue(PromPlaceActivity.this);
                        JSONObject jsonBodyObj = new JSONObject();
                        jsonBodyObj.put("celular", numberPhone);
                        jsonBodyObj.put("nombre", promotion_bar.getText().toString());
                        jsonBodyObj.put("fechaInicio", start_date.getText().toString());
                        jsonBodyObj.put("fechaFin", end_date.getText().toString());
                        jsonBodyObj.put("cantidad", cantidad.getText().toString());
                        jsonBodyObj.put("valor", valor_prom.getText().toString());
                        jsonBodyObj.put("imagen","");
                        jsonBodyObj.put("activa", String.valueOf(prom_active.isChecked()));
                        final String requestBody = jsonBodyObj.toString();
                        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                progress.dismiss();
                                Log.i("LOG_RESPONSE", response);
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                progress.dismiss();
                                Log.e("LOG_RESPONSE", error.toString());
                            }
                        }) {
                            @Override
                            public String getBodyContentType() {
                                return "application/json; charset=utf-8";
                            }

                            @Override
                            public byte[] getBody() throws AuthFailureError {
                                try {
                                    return requestBody == null ? null : requestBody.getBytes("utf-8");
                                } catch (UnsupportedEncodingException uee) {
                                    VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody , "utf-8");
                                    return null;
                                }
                            }

                            @Override
                            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                                String responseString = "";
                                if (response != null) {
                                    responseString = String.valueOf(response.statusCode);
                                }
                                return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
                            }
                        };

                        requestQueue.add(stringRequest);

                    } catch (Exception e) {
                        progress.dismiss();
                        Toast.makeText(getApplicationContext(),"error de formulario",Toast.LENGTH_SHORT).show();

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





}
