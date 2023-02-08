package com.example.buscador_filtrador_reconocimiento_voz_java_ejemplo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdaptadorNombres extends RecyclerView.Adapter<AdaptadorNombres.ViewHolder> {
    ArrayList<String> listaNombres;

    public AdaptadorNombres(ArrayList<String> listaNombres) {
        this.listaNombres = listaNombres;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rv_nombre, parent, false);
        return new ViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String nombre = listaNombres.get(position);

        holder.tvNombre.setText(nombre);
    }

    @Override
    public int getItemCount() {
        return listaNombres.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvNombre = itemView.findViewById(R.id.tvNombre);
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    public void filtrar(ArrayList<String> listaFiltrada) {
        this.listaNombres = listaFiltrada;
        notifyDataSetChanged();
    }
}
