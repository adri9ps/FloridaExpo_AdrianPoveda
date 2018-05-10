package com.example.adri9ps.floridaexpo_adrianpoveda;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * Created by adri9ps on 26/4/18.
 */

public class AdaptadorProyectos extends RecyclerView.Adapter<AdaptadorProyectos.ElementHolder> {


    ArrayList<Proyecto> listaProyectos;
    ArrayList<String> llavesProyectos;

    public AdaptadorProyectos(ArrayList<Proyecto> listaProyectos, ArrayList<String> llaves) {
        this.listaProyectos = listaProyectos;
        this.llavesProyectos = llaves;
    }

    @Override
    public ElementHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card,parent,false);
        return new AdaptadorProyectos.ElementHolder(v);
    }

    @Override
    public void onBindViewHolder(final ElementHolder holder, final int position) {
        Proyecto p = listaProyectos.get(position);
        holder.nombreProject.setText(p.getNombreProyecto());
        holder.cicloProject.setText(p.getCicloProyecto());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mostrar = new Intent(holder.itemView.getContext(), VisualizaProyecto.class);

                // --> Cargamos la correspondiente llave del proyecto actual
                mostrar.putExtra("nombreProyecto", llavesProyectos.get(position));
                holder.itemView.getContext().startActivity(mostrar);

            }
        });

    }

    @Override
    public int getItemCount() {
        return listaProyectos.size();
    }

    public static class ElementHolder extends RecyclerView.ViewHolder{

        TextView nombreProject, cicloProject;

        public ElementHolder(View itemView){
            super(itemView);
            nombreProject = (TextView) itemView.findViewById(R.id.txtCard_NombreProyecto);
            cicloProject = (TextView) itemView.findViewById(R.id.txtCard_cicloProyecto);
        }
    }
}