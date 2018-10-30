package co.edu.uniandes.trickytrack.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.List;

import co.edu.uniandes.trickytrack.R;
import co.edu.uniandes.trickytrack.activities.adapters.RecyclerViewAdapter;
import co.edu.uniandes.trickytrack.activities.models.Model;

public class PlaceForm extends AppCompatActivity {
    private List<Model> mModelList;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_form);
        getSupportActionBar().setTitle(getString(R.string.place_title));

        mRecyclerView = (RecyclerView) findViewById(R.id.gender_spinner);
        mAdapter = new RecyclerViewAdapter(getListData(),getApplicationContext());
        LinearLayoutManager manager = new LinearLayoutManager(PlaceForm.this);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setNestedScrollingEnabled(false);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);


    }

    private List<Model> getListData() {
        mModelList = new ArrayList<>();
        String[] resourceString =getResources().getStringArray(R.array.genders);

        for (int i = 0; i < resourceString.length; i++) {
            mModelList.add(new Model(resourceString[i]));
        }
        return mModelList;
    }
}
