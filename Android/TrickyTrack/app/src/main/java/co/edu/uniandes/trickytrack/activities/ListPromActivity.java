package co.edu.uniandes.trickytrack.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import co.edu.uniandes.trickytrack.R;

public class ListPromActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_prom);
        Integer idEstablecimiento=getIntent().getExtras().getInt("idEstablecimiento");
        Toast.makeText(getApplicationContext(),"idEstablecimiento "+idEstablecimiento,Toast.LENGTH_SHORT).show();
    }
}
