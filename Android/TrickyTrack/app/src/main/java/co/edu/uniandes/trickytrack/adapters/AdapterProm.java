package co.edu.uniandes.trickytrack.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import co.edu.uniandes.trickytrack.R;
import co.edu.uniandes.trickytrack.models.Model;
import co.edu.uniandes.trickytrack.retrofit.ElementosPromociones;
import co.edu.uniandes.trickytrack.retrofit.ExamplePromocion;

public class AdapterProm extends RecyclerView.Adapter<AdapterProm.MyViewHolder2> {
    Context context;
    public List<ExamplePromocion> mModelList;

    public AdapterProm(List<ExamplePromocion> modelList, Context context) {
        mModelList = modelList;
        this.context=context;
    }

    public List<ExamplePromocion> getmModelList(){

        return  mModelList;
    }

    @Override
    public MyViewHolder2 onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_promocion, parent, false);
        return new MyViewHolder2(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder2 holder, int position) {
        final ExamplePromocion model = mModelList.get(position);
        holder.item_nombre.setText("Promocion: "+model.getNombre());
        holder.inicio.setText("Inicio: "+model.getFechaInicio());
        holder.fin.setText("Fin: "+model.getFechaFin());
        holder.fin.setText("Fin: "+model.getFechaFin());
    }


    @Override
    public int getItemCount() {
        return mModelList == null ? 0 : mModelList.size();
    }

    public class MyViewHolder2 extends RecyclerView.ViewHolder {

        private View view;
        private TextView item_nombre,valor,inicio,fin;

        private MyViewHolder2(View itemView) {
            super(itemView);
            view = itemView;
            item_nombre=(TextView) itemView.findViewById(R.id.item_nombre);
            valor=(TextView) itemView.findViewById(R.id.valor);
            inicio=(TextView) itemView.findViewById(R.id.inicio);
            fin=(TextView) itemView.findViewById(R.id.fin);
        }
    }
}