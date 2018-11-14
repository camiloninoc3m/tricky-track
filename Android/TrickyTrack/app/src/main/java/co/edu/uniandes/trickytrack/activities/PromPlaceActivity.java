package co.edu.uniandes.trickytrack.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import co.edu.uniandes.trickytrack.R;

public class PromPlaceActivity extends AppCompatActivity {
    String numberPhone = "3115321435";
    String image = "";
    EditText promotion_bar, start_date, end_date, valor_prom, cantidad;
    CheckBox prom_active;
    Button search_image, save;
    TextView ruta;

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
            Log.i("numtelef", "numtelf " + getPhone());
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
        }
        catch(NullPointerException ex)
        {
        }

        if(MyPhoneNumber.equals("")){
            MyPhoneNumber = tMgr.getSubscriberId();
        }
    return MyPhoneNumber;
    }
}
