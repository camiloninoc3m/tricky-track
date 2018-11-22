package co.edu.uniandes.trickytrack.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import co.edu.uniandes.trickytrack.R;
import co.edu.uniandes.trickytrack.retrofit.Elementos;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    String nombre = "";
    String direccion="";
    String horario="";
    String dias="";
    int idEstablecimiento=0;
    private GoogleMap mMap;
Button search_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        search_btn=(Button)findViewById(R.id.search_btn);
        search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MapsActivity.this,PlaceForm.class));
            }
        });
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        final Elementos elementos;
        elementos = (Elementos)getIntent().getSerializableExtra("puntos");
        double latitudn, longitudn;
        latitudn=getIntent().getExtras().getDouble("latitud");
        longitudn=getIntent().getExtras().getDouble("longitud");


        for(int i=0;i<elementos.getElementos().size();i++){
            dias="";
            if(elementos.getElementos().get(i).getLunes()){
                dias=dias+"Lunes -";
            } if(elementos.getElementos().get(i).getMartes()){
                dias=dias+"Martes -";

            } if(elementos.getElementos().get(i).getMiercoles()){
                dias=dias+"Miercoles-";

            } if(elementos.getElementos().get(i).getJueves()){
                dias=dias+"Jueves-";

            } if(elementos.getElementos().get(i).getViernes()){
                dias=dias+"Viernes-";

            } if(elementos.getElementos().get(i).getSabado()){
                dias=dias+"Sabado-";

            }
             if(elementos.getElementos().get(i).getDomingo()){
                dias=dias+"Domingo";

            }

            LatLng sydney = new LatLng(elementos.getElementos().get(i).getLatitud(), elementos.getElementos().get(i).getLongitud());
            Marker marker=
            mMap.addMarker(new MarkerOptions().position(sydney).title(elementos.getElementos().get(i).getNombre()).snippet(
                    "Direccion: "+elementos.getElementos().get(i).getDireccion()+"--Dias "+dias+"--Horario atencion: "+
                            elementos.getElementos().get(i).getHoraInicio()+"-"+elementos.getElementos().get(i).getHoraCierre()
            ));
            //marker .setTag(elementos.getElementos().get(i).getId());
            marker .setTag(i);
                 marker.showInfoWindow();


        }


        LatLng me = new LatLng(latitudn,longitudn);
        mMap.addMarker(new MarkerOptions().position(me).title("Mi ubicacion")).setTag(11011);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(me));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(me,18));
        mMap.getUiSettings().setZoomControlsEnabled(true);

        googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(final Marker marker) {
                if((Integer)marker.getTag()!=11011){
                    int posicion=(Integer)marker.getTag();

                    nombre=elementos.getElementos().get(posicion).getNombre();
                    direccion="Direccion: "+elementos.getElementos().get(posicion).getDireccion();
                    dias="";
                    if(elementos.getElementos().get(posicion).getLunes()){
                        dias=dias+"Lunes -";
                    } if(elementos.getElementos().get(posicion).getMartes()){
                        dias=dias+"Martes -";

                    } if(elementos.getElementos().get(posicion).getMiercoles()){
                        dias=dias+"Miercoles-";

                    } if(elementos.getElementos().get(posicion).getJueves()){
                        dias=dias+"Jueves-";

                    } if(elementos.getElementos().get(posicion).getViernes()){
                        dias=dias+"Viernes-";

                    } if(elementos.getElementos().get(posicion).getSabado()){
                        dias=dias+"Sabado-";

                    }
                    if(elementos.getElementos().get(posicion).getDomingo()){
                        dias=dias+"Domingo";

                    }

                    horario="Horario atencion: "+ elementos.getElementos().get(posicion).getHoraInicio()+"-"+elementos.getElementos().get(posicion).getHoraCierre();
                    idEstablecimiento=elementos.getElementos().get(posicion).getId();
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                            MapsActivity.this);

                    // set title
                    alertDialogBuilder.setTitle(nombre);

                    // set dialog message
                    alertDialogBuilder
                            .setMessage(direccion+"\n"+ "Dias: "+dias+"\n"+ horario)
                            .setCancelable(false)
                            .setPositiveButton("Ver promociones",new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,int id) {
                                    Intent intent= new Intent(MapsActivity.this,ListPromActivity.class);
                                    //intent.putExtra("idEstablecimiento",((Integer) marker.getTag()));
                                    intent.putExtra("idEstablecimiento",(idEstablecimiento));
                                    intent.putExtra("nombre",nombre);
                                    startActivity(intent);

                                }
                            })
                            .setNegativeButton("Cerrar",new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,int id) {
                                    // if this button is clicked, just close
                                    // the dialog box and do nothing
                                    dialog.cancel();
                                }
                            });

                    // create alert dialog
                    AlertDialog alertDialog = alertDialogBuilder.create();

                    // show it
                    alertDialog.show();


                }

                return false;
            }
        });
    }
}
