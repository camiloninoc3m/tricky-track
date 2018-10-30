package co.edu.uniandes.trickytrack.activities;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import co.edu.uniandes.trickytrack.R;
import co.edu.uniandes.trickytrack.retrofit.Elementos;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        Elementos elementos;
        elementos = (Elementos)getIntent().getSerializableExtra("puntos");
        double latitudn, longitudn;
        latitudn=getIntent().getExtras().getDouble("latitud");
        longitudn=getIntent().getExtras().getDouble("longitud");
        for(int i=0;i<elementos.getElementos().size();i++){
            String dias="";
            if(elementos.getElementos().get(i).getLunes()){
                dias=dias+"Lunes -";
            }else if(elementos.getElementos().get(i).getMartes()){
                dias+=dias+"Martes -";

            }else if(elementos.getElementos().get(i).getMiercoles()){
                dias+=dias+"Miercoles-";

            }else if(elementos.getElementos().get(i).getJueves()){
                dias+=dias+"Jueves-";

            }else if(elementos.getElementos().get(i).getViernes()){
                dias+=dias+"Viernes-";

            }else if(elementos.getElementos().get(i).getSabado()){
                dias+=dias+"Sabado-";

            }
            else if(elementos.getElementos().get(i).getDomingo()){
                dias+=dias+"Domingo";

            }


            LatLng sydney = new LatLng(elementos.getElementos().get(i).getLatitud(), elementos.getElementos().get(i).getLongitud());
            mMap.addMarker(new MarkerOptions().position(sydney).title(elementos.getElementos().get(i).getNombre()).snippet(
                    "Direccion: "+elementos.getElementos().get(i).getDireccion()+"--Dias "+dias+"--Horario atencion: "+
                            elementos.getElementos().get(i).getHoraInicio()+"-"+elementos.getElementos().get(i).getHoraCierre()
            ));
        }

        LatLng me = new LatLng(latitudn,longitudn);
        mMap.addMarker(new MarkerOptions().position(me).title("Mi ubicacion")
        );
        mMap.moveCamera(CameraUpdateFactory.newLatLng(me));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(me,18));

    }
}
