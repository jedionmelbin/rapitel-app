package com.ixcorp.rapitel_app.Adapters.Pedido;

import android.content.Context;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;


import com.ixcorp.rapitel_app.MainActivity;
import com.ixcorp.rapitel_app.Model.Orders;
import com.ixcorp.rapitel_app.R;
import com.ixcorp.rapitel_app.ui.detallePedido.DetallePedidoFragment;


import org.jetbrains.annotations.NotNull;

import java.util.List;

public class PedidoAdapter extends RecyclerView.Adapter<PedidoAdapter.MyViewHolder> {
    private Context context;
    private List<Orders> listaPedidos;
    final MainActivity activity = (MainActivity) context;


    public PedidoAdapter(Context context,List<Orders> listaPedidos) {
        this.context = context;
        this.listaPedidos = listaPedidos;
    }

    @NonNull
    @NotNull
    @Override
    public PedidoAdapter.MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View vista = inflater.inflate(R.layout.cardlist_pedidos,parent,false);
        return new MyViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull PedidoAdapter.MyViewHolder holder, int position) {
        holder.txtNumPedido.setText(listaPedidos.get(position).getMumOrder()+"");
        holder.txtDniCliente.setText(listaPedidos.get(position).getNumerDocument()+"");
        holder.txtCliente.setText(listaPedidos.get(position).getFirstName()+" "+listaPedidos.get(position).getLastName()+"");
        holder.txtFechaPedido.setText(listaPedidos.get(position).getCreateDate()+"");
        holder.txtSubtotal.setText("S/ "+ listaPedidos.get(position).getSubTotal()+"");
        holder.txtIgv.setText("S/ "+ listaPedidos.get(position).getIgv()+"");
        holder.txtTotal.setText("S/ "+ listaPedidos.get(position).getTotal()+"");

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // Toast.makeText(context,listaPedidos.get(position).getMumOrder(),Toast.LENGTH_SHORT).show();

                MainActivity activity = (MainActivity) view.getContext();
//                Fragment myFragment = new DetallePedidoFragment();
//                activity.getSupportFragmentManager()
//                        .beginTransaction()
//                        .replace(R.id.nav_host_fragment_activity_main,myFragment)
//                        .commit();

                Fragment newFragment = new DetallePedidoFragment();
                Bundle envData = new Bundle();
                envData.putString("idPedido", listaPedidos.get(position).getOrderId()+"");
                envData.putString("numPedido", listaPedidos.get(position).getMumOrder()+"");
                envData.putString("dni", listaPedidos.get(position).getNumerDocument()+"");
                envData.putString("cliente", listaPedidos.get(position).getFirstName()+" "+listaPedidos.get(position).getLastName()+"");
                envData.putString("telefono", listaPedidos.get(position).getTelefono()+"");
                envData.putString("direccion",  listaPedidos.get(position).getDireccion()+"");
                envData.putString("email",  listaPedidos.get(position).getEmail()+"");
                envData.putString("fechaPedido", listaPedidos.get(position).getCreateDate()+"");
                envData.putString("horaEntrega", listaPedidos.get(position).getDeliveyTime()+"");

                envData.putDouble("subtotal",listaPedidos.get(position).getSubTotal());
                envData.putDouble("igv",listaPedidos.get(position).getIgv());
                envData.putDouble("total",listaPedidos.get(position).getTotal());

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
        return listaPedidos.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txtNumPedido,txtDniCliente,txtCliente,txtFechaPedido,txtSubtotal,txtIgv,txtTotal;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNumPedido = itemView.findViewById(R.id.lblNumPedido);
            txtDniCliente = itemView.findViewById(R.id.lblDniCliente);
            txtCliente = itemView.findViewById(R.id.lblCliente);
            txtFechaPedido = itemView.findViewById(R.id.lblFechaPedido);
            txtSubtotal = itemView.findViewById(R.id.lblSubtotal);
            txtIgv = itemView.findViewById(R.id.lblIgv);
            txtTotal = itemView.findViewById(R.id.lblTotal);

        }
    }
}
