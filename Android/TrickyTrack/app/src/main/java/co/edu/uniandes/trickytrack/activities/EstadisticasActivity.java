package co.edu.uniandes.trickytrack.activities;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.LegendRenderer;
import com.jjoe64.graphview.ValueDependentColor;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;

import co.edu.uniandes.trickytrack.R;
import co.edu.uniandes.trickytrack.retrofit.ExampleEstadistica;
import co.edu.uniandes.trickytrack.retrofit.ExampleValidacion;
import co.edu.uniandes.trickytrack.retrofit.GenericaInterfazRetrofitJson;
import co.edu.uniandes.trickytrack.retrofit.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EstadisticasActivity extends AppCompatActivity {
    GraphView graph, graphedad, graphmusica;
    TextView personas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estadisticas);
        getSupportActionBar().setTitle("Estadisticas");
        graph = (GraphView) findViewById(R.id.graph);
        graphedad = (GraphView) findViewById(R.id.graphedad);
        graphmusica = (GraphView) findViewById(R.id.graphmusica);
        personas = (TextView) findViewById(R.id.personas);
        final ProgressDialog progress = new ProgressDialog(EstadisticasActivity.this);
        progress.setMessage("Cargando...");
        progress.show();
        progress.setCancelable(true);
        SharedPreferences prefs =
                getSharedPreferences("PreferenciasEstablecimiento", Context.MODE_PRIVATE);

        String idEstablecimiento = prefs.getString("idEstablecimiento", "000");

        GenericaInterfazRetrofitJson mService = null;
        mService = Util.getService();
        mService.getEstadisticas("establecimiento/" + idEstablecimiento + "/reporteClientes").enqueue(new Callback<ExampleEstadistica>() {
            @Override
            public void onResponse(Call<ExampleEstadistica> call, Response<ExampleEstadistica> response) {

                if (response.isSuccessful()) {
                    graficar(response.body());
                } else {
                    msgNegative("1. ");
                }
                progress.dismiss();
            }

            @Override
            public void onFailure(Call<ExampleEstadistica> call, Throwable t) {
                progress.dismiss();
                Log.i("falla", "falla " + t.toString());
                msgNegative("2. ");
            }
        });
    }


    private void msgNegative(String orden) {
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(EstadisticasActivity.this);
        alertDialogBuilder
                .setTitle(getString(R.string.app_name))
                .setMessage(orden + "Ha ocurrido un error con las estadisticas. Contacte con el administrador de la aplicación")
                .setCancelable(false)
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                }).show();
    }

    void graficar(ExampleEstadistica datos) {
        //personas
        try {
            personas.setText("Total personas: " + datos.getClientes().getTotal());
            StaticLabelsFormatter staticLabelsFormatter = new StaticLabelsFormatter(graph);
            staticLabelsFormatter.setHorizontalLabels(new String[]{"Hombres", "Mujeres"});


            BarGraphSeries<DataPoint> series = new BarGraphSeries<>(new DataPoint[]{
                    new DataPoint(0, datos.getClientes().getHombres()),//hombres
                    new DataPoint(1, datos.getClientes().getMujeres())//mujeres


            });
            graph.getViewport().setScrollable(true);
            graph.getViewport().setXAxisBoundsManual(true);
            graph.getViewport().setMinX(0);
            graph.getViewport().setMaxX(1);

            graph.getViewport().setYAxisBoundsManual(true);
            graph.getViewport().setMinY(0);
            graph.getViewport().setMaxY(datos.getClientes().getTotal());//total

            graph.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter);
            graph.addSeries(series);
            series.setValueDependentColor(new ValueDependentColor<DataPoint>() {
                @Override
                public int get(DataPoint data) {
                    return Color.rgb((int) data.getX() * 255 / 4, (int) Math.abs(data.getY() * 255 / 6), 100);
                }
            });

            series.setSpacing(20);
            series.setDrawValuesOnTop(true);
            series.setValuesOnTopColor(Color.WHITE);
        } catch (Exception e) {

        }
        //edades
        try {
            StaticLabelsFormatter staticLabelsFormatter2 = new StaticLabelsFormatter(graphedad);
            staticLabelsFormatter2.setHorizontalLabels(new String[]{"Hasta 26", "de 27 a 35", "de 36 a 45", "desde 49"});

            int total = datos.getClientes().getTotal();


            BarGraphSeries<DataPoint> series2 = new BarGraphSeries<>(new DataPoint[]{
                    new DataPoint(0, (datos.getEdades().getHasta26()*100)/total),
                    new DataPoint(1, (datos.getEdades().getDe27a35()*100)/total),
                    new DataPoint(2, (datos.getEdades().getDe36a45()*100)/total),
                    new DataPoint(3, (datos.getEdades().getDesde49()*100)/total),


            });
            graphedad.getViewport().setScrollable(true);
            graphedad.getViewport().setXAxisBoundsManual(true);
            graphedad.getViewport().setMinX(0);
            graphedad.getViewport().setMaxX(3);

// set manual Y bounds
            graphedad.getViewport().setYAxisBoundsManual(true);
            graphedad.getViewport().setMinY(0);
            graphedad.getViewport().setMaxY(100);//total porcentual
            graphedad.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter2);
            graphedad.addSeries(series2);
            series2.setValueDependentColor(new ValueDependentColor<DataPoint>() {
                @Override
                public int get(DataPoint data) {
                    return Color.rgb((int) data.getX() * 255 / 4, (int) Math.abs(data.getY() * 255 / 6), 100);
                }
            });

            series2.setSpacing(20);
            series2.setDrawValuesOnTop(true);
            series2.setValuesOnTopColor(Color.WHITE);

        } catch (Exception e) {

        }
//musica
        try {

            StaticLabelsFormatter staticLabelsFormatter3 = new StaticLabelsFormatter(graphmusica);
            String[] generosLabel = new String[datos.getGeneros().size()];
            for (int i = 0; i < datos.getGeneros().size(); i++) {
                generosLabel[i] = datos.getGeneros().get(i).getGenero();
            }
            staticLabelsFormatter3.setHorizontalLabels(generosLabel);

            int total=datos.getClientes().getTotal();


            DataPoint [] puntosgeneros= new DataPoint[datos.getGeneros().size()];
            for (int i = 0; i < datos.getGeneros().size(); i++) {
                DataPoint temporal =new DataPoint(i,datos.getGeneros().get(i).getTotal());
                puntosgeneros[i] = temporal;
            }
            BarGraphSeries<DataPoint> series3 = new BarGraphSeries<>(puntosgeneros);
            graphmusica.getViewport().setScrollable(true);
            graphmusica.getViewport().setXAxisBoundsManual(true);
            graphmusica.getViewport().setMinX(0);
            graphmusica.getViewport().setMaxX(datos.getGeneros().size());// total generos en x
            graphmusica.getViewport().setYAxisBoundsManual(true);
            graphmusica.getViewport().setMinY(0);
            graphmusica.getViewport().setMaxY(total);//total personas
            graphmusica.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter3);
            graphmusica.addSeries(series3);
            series3.setValueDependentColor(new ValueDependentColor<DataPoint>() {
                @Override
                public int get(DataPoint data) {
                    return Color.rgb((int) data.getX() * 255 / 4, (int) Math.abs(data.getY() * 255 / 6), 100);
                }
            });

            series3.setSpacing(5);
            series3.setDrawValuesOnTop(true);
            series3.setValuesOnTopColor(Color.WHITE);

        } catch (Exception e) {

        }

    }
}
