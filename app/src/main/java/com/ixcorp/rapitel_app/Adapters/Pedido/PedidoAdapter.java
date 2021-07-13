package com.ixcorp.rapitel_app.Adapters.Pedido;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ixcorp.rapitel_app.Model.Orders;
import com.ixcorp.rapitel_app.R;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class PedidoAdapter extends RecyclerView.Adapter<PedidoAdapter.MyViewHolder> {
    private Context context;
    private List<Orders> listaPedidos;

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
        holder.txtSubtotal.setText(listaPedidos.get(position).getSubTotal()+"");
        holder.txtIgv.setText(listaPedidos.get(position).getIgv()+"");
        holder.txtTotal.setText(listaPedidos.get(position).getTotal()+"");
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
