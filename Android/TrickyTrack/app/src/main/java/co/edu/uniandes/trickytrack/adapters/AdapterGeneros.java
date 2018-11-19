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
import co.edu.uniandes.trickytrack.retrofit.ExampleGeneros;
import co.edu.uniandes.trickytrack.retrofit.ExamplePromocion;

public class AdapterGeneros extends RecyclerView.Adapter<AdapterGeneros.MyViewHolder> {
    Context context;
    public List<ExampleGeneros> mModelList;

    public AdapterGeneros(List<ExampleGeneros> modelList, Context context) {
        mModelList = modelList;
        this.context=context;
    }

    public List<ExampleGeneros> getmModelList(){

        return  mModelList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final ExampleGeneros model = mModelList.get(position);
        holder.textView.setText(model.getNombre());
        holder.view.setBackgroundColor(model.isSelected() ? context.getResources().getColor(R.color.colorPrimary) : Color.TRANSPARENT);
        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                model.setSelected(!model.isSelected());
                holder.view.setBackgroundColor(model.isSelected() ? context.getResources().getColor(R.color.colorPrimary) : Color.TRANSPARENT);
            }
        });
    }


    @Override
    public int getItemCount() {
        return mModelList == null ? 0 : mModelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private View view;
        private TextView textView;

        private MyViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            textView = (TextView) itemView.findViewById(R.id.text_view);
        }
    }
}