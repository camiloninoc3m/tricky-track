package co.edu.uniandes.trickytrack.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import co.edu.uniandes.trickytrack.R;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Button place_btn=(Button)findViewById(R.id.place_btn);
        place_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileActivity.this, PromPlaceActivity.class);
                startActivity(intent);
            }
        });
               Button client_btn=(Button)findViewById(R.id.client_btn);
        client_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileActivity.this, SearchingActivity.class);
                startActivity(intent);
            }
        });
    }
}
