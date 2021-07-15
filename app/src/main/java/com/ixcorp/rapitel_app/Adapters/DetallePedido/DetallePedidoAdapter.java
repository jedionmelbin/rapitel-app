package com.ixcorp.rapitel_app.Adapters.DetallePedido;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ixcorp.rapitel_app.Model.OrderDetails;
import com.ixcorp.rapitel_app.R;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class DetallePedidoAdapter extends RecyclerView.Adapter<DetallePedidoAdapter.MyViewHolder> {
    private Context context;
    private List<OrderDetails> listaDetalle;

    public DetallePedidoAdapter(Context context,List<OrderDetails> listaDetalle) {
        this.context = context;
        this.listaDetalle = listaDetalle;
    }

    @NonNull
    @NotNull
    @Override
    public DetallePedidoAdapter.MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View vista = inflater.inflate(R.layout.cardlist_producto_pedido,parent,false);
        return new MyViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull DetallePedidoAdapter.MyViewHolder holder, int position) {
        holder.txtPrecio.setText("S/ "+listaDetalle.get(position).getPrice()+"");
        holder.txtCantidad.setText(listaDetalle.get(position).getCount()+"");
        holder.txtcodigo.setText(listaDetalle.get(position).getCode()+"");
        holder.txtDescripcion.setText(listaDetalle.get(position).getDescription()+"");
        holder.txtUnd.setText(listaDetalle.get(position).getUnidadMedida()+"");

        Double importe = (listaDetalle.get(position).getPrice()) * Integer.parseInt(listaDetalle.get(position).getCount());
        holder.txtImporte.setText("S/ "+importe+"");

        //Toast.makeText(context,"MOSTRAR DETALLE PEDIDO",Toast.LENGTH_SHORT).show();
    }

    @Override
    public int getItemCount() {
        return listaDetalle.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txtPrecio,txtCantidad,txtcodigo,txtDescripcion,txtUnd,txtImporte;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtPrecio = itemView.findViewById(R.id.txtPrecio);
            txtCantidad = itemView.findViewById(R.id.txtCantidad);
            txtcodigo = itemView.findViewById(R.id.txtCodigoProducto);
            txtDescripcion = itemView.findViewById(R.id.txtProducto);
            txtUnd= itemView.findViewById(R.id.txtUnm);
            txtImporte = itemView.findViewById(R.id.txtImporte);

        }
    }
}
