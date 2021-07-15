package com.ixcorp.rapitel_app.Adapters.Vehiculo;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.ixcorp.rapitel_app.MainActivity;
import com.ixcorp.rapitel_app.Model.Vehicle;
import com.ixcorp.rapitel_app.R;
import com.ixcorp.rapitel_app.ui.detallePedido.DetallePedidoFragment;
import com.ixcorp.rapitel_app.ui.rutaMapa.RutaMapaFragment;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class VehiculoAdapter extends RecyclerView.Adapter<VehiculoAdapter.MyViewHolder>  {
    private Context context;
    private List<Vehicle> listaVehiculos;

    public VehiculoAdapter(Context context,List<Vehicle> listaVehiculos) {
        this.context = context;
        this.listaVehiculos = listaVehiculos;
    }

    @NonNull
    @NotNull
    @Override
    public VehiculoAdapter.MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View vista = inflater.inflate(R.layout.cardlist_motorizado,parent,false);
        return new MyViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull VehiculoAdapter.MyViewHolder holder, int position) {
        holder.txtVehiulo.setText(listaVehiculos.get(position).getNameVehiculo() +"");
        holder.txtPlaca.setText(listaVehiculos.get(position).getPlate() +"");
        holder.txtColor.setText(listaVehiculos.get(position).getColor() +"");

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity activity = (MainActivity) view.getContext();
                Fragment newFragment = new RutaMapaFragment();
                Bundle envData = new Bundle();
                envData.putString("idVehiculo", listaVehiculos.get(position).getVehicleId()+"");

                newFragment.setArguments(envData);
                activity.getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.nav_host_fragment_activity_main,newFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return listaVehiculos.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txtVehiulo,txtPlaca,txtColor;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtVehiulo = itemView.findViewById(R.id.txtVehiculo);
            txtPlaca = itemView.findViewById(R.id.txtplaca);
            txtColor = itemView.findViewById(R.id.txtColor);
        }
    }
}
