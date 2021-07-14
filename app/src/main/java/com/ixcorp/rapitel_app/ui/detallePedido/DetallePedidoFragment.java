package com.ixcorp.rapitel_app.ui.detallePedido;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.ixcorp.rapitel_app.Model.OrderDetails;
import com.ixcorp.rapitel_app.R;
import com.ixcorp.rapitel_app.databinding.FragmentPedidosBinding;
import com.ixcorp.rapitel_app.ui.pedidos.HomeViewModel;

import java.util.ArrayList;
import java.util.List;


public class DetallePedidoFragment extends Fragment {

    TextView txtnumPedido,txtdniCliente,txtnombreCliente,txttelefono,txtemail,txtdireccion,txtfechaEntrega,txthoraEntrega,txtsubtotal,txtigv,txttotal;
    Button btnEntregarPedido;
    List<OrderDetails> listOrderDetails = new ArrayList<>();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_detalle_pedido, container, false);


    }



    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        asignarReferencias(view);

        Bundle datosRecuperados = getArguments();
        if (datosRecuperados == null) {
            // No hay datos, manejar excepción
            return;
        }

        //String nombre = datosRecuperados.getString("idPedido");
        //Log.d("====>>>>", "El nombre: " + nombre);
        String idOrder = datosRecuperados.getString("idPedido");
        String numPedido = datosRecuperados.getString("numPedido");
        String dni = datosRecuperados.getString("dni");
        String cliente = datosRecuperados.getString("cliente");
        String telefono = datosRecuperados.getString("telefono");
        String direccion = datosRecuperados.getString("direccion");
        String email = datosRecuperados.getString("email");
        String fechaPedido = datosRecuperados.getString("fechaPedido");
        String horaEntrega = datosRecuperados.getString("horaEntrega");

        Double subtotal = datosRecuperados.getDouble("subtotal");
        Double igv = datosRecuperados.getDouble("igv");
        Double total = datosRecuperados.getDouble("total");

        txtnumPedido.setText(numPedido);
        txtdniCliente.setText(dni);
        txtnombreCliente.setText(cliente);
        txttelefono.setText(telefono);
        txtdireccion.setText(direccion);
        txtemail.setText(email);
        txtfechaEntrega.setText(fechaPedido);
        txthoraEntrega.setText(horaEntrega);
        txtsubtotal.setText("S/ " + subtotal);
        txtigv.setText("S/ " + igv);
        txttotal.setText("S/ " + total);

        Log.d("====>>>>", "El idOrder: " + idOrder);
        Log.d("====>>>>", "El numPedido: " + numPedido);
        Log.d("====>>>>", "El dni: " + dni);
        Log.d("====>>>>", "El cliente: " + cliente);
        Log.d("====>>>>", "El telefono: " + telefono);
        Log.d("====>>>>", "El direccion: " + direccion);
        Log.d("====>>>>", "El email: " + email);
        Log.d("====>>>>", "El fechaPedido: " + fechaPedido);
        Log.d("====>>>>", "El horaEntrega: " + horaEntrega);
        Log.d("====>>>>", "El subtotal: " + subtotal);
        Log.d("====>>>>", "El igv: " + igv);
        Log.d("====>>>>", "El total: " + total);
    }

    private void asignarReferencias(View view) {
        txtnumPedido = view.findViewById(R.id.txtNumPedido);
        txtdniCliente = view.findViewById(R.id.txtDniCliente);
        txtnombreCliente = view.findViewById(R.id.txtNombresCliente);
        txttelefono = view.findViewById(R.id.txtTelefono);
        txtemail = view.findViewById(R.id.txtEmail);
        txtdireccion = view.findViewById(R.id.txtDireccion);
        txtfechaEntrega = view.findViewById(R.id.txtFechaEntrega);
        txthoraEntrega = view.findViewById(R.id.txtHoraEntrega);

        txtsubtotal = view.findViewById(R.id.txtSubtotal);
        txtigv = view.findViewById(R.id.txtIgv);
        txttotal = view.findViewById(R.id.txtTotal);
    }
}