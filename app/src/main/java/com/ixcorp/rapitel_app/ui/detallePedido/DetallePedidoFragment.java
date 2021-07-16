package com.ixcorp.rapitel_app.ui.detallePedido;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ixcorp.rapitel_app.Adapters.DetallePedido.DetallePedidoAdapter;
import com.ixcorp.rapitel_app.MainActivity;
import com.ixcorp.rapitel_app.Model.OrderDetails;
import com.ixcorp.rapitel_app.R;
import com.ixcorp.rapitel_app.Utils.Api;
import com.ixcorp.rapitel_app.ui.motorizado.MotorizadoFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class DetallePedidoFragment extends Fragment {

    TextView txtnumPedido,txtdniCliente,txtnombreCliente,txttelefono,txtemail,txtdireccion,txtfechaEntrega,txthoraEntrega,txtsubtotal,txtigv,txttotal;
    String idPedido,numPed,datCliente,datDireccion,datTelefono;
    Button btnEntregarPedido;
    List<OrderDetails> listOrderDetails = new ArrayList<>();
    RecyclerView recyclerView;
    RequestQueue requestQueue;

    String URL = Api.URL_API;
    String URL1 = URL+"datails/list/";

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_detalle_pedido, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        requestQueue = Volley.newRequestQueue(getContext());

        recyclerView = view.findViewById(R.id.listaMotorizados);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        asignarReferencias(view);

        Bundle datosRecuperados = getArguments();
        if (datosRecuperados == null) {
            // No hay datos, manejar excepción
            Toast.makeText(getContext(),"No hay datos para mostrar",Toast.LENGTH_SHORT).show();
            return;
        }

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
//        txtfechaEntrega.setText(fechaPedido.substring(0,10));
//        txthoraEntrega.setText(horaEntrega.substring(11));
        txtfechaEntrega.setText(fechaPedido);
        txthoraEntrega.setText("3:30 pm");
        txtsubtotal.setText("S/ " + subtotal);
        txtigv.setText("S/ " + igv);
        txttotal.setText("S/ " + total);

        //Para enviar al fragment motorizado
        idPedido = idOrder;
        numPed = numPedido;
        datCliente = dni +" - " + cliente;
        datDireccion = direccion;
        datTelefono =  telefono;

        //Log.d("IDORDER=>>>",idPedido);

        parseJSON(idOrder);
    }

    private void parseJSON(String idOrder) {
        String miUrl = URL1 + idOrder;

        StringRequest request = new StringRequest(
                Request.Method.GET,
                miUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray array = new JSONArray(response);

                            for (int i=0; i<array.length(); i++){
                                OrderDetails orderDetails = new OrderDetails();
                                JSONObject ped = array.getJSONObject(i);
                                JSONObject prod = ped.getJSONObject("products");

                                orderDetails.setPrice(ped.getDouble("price"));
                                orderDetails.setCount(ped.getString("count"));

                                orderDetails.setCode(prod.getString("code"));
                                orderDetails.setDescription(prod.getString("description"));
                                orderDetails.setUnidadMedida(prod.getString("unidadMedida"));

                                listOrderDetails.add(orderDetails);
                            }

                           // Log.d("******", String.valueOf(listOrderDetails));

                            recyclerView.setAdapter(new DetallePedidoAdapter(getContext(),listOrderDetails));
                        }catch (JSONException e) {
                            Log.i("Error => ", e.getMessage());
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError e) {
                        Log.i("Error => ", e.getMessage());
                    }
                }
        );
        requestQueue.add(request);
    }


    private void asignarReferencias(View view) {
        txtnumPedido = view.findViewById(R.id.txtNumPedidoEvidencias);
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

        btnEntregarPedido = view.findViewById(R.id.btnEntregarPedido);
        btnEntregarPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getContext(),"Boton clickkkk",Toast.LENGTH_SHORT).show();

                MainActivity activity = (MainActivity) view.getContext();
                Fragment newFragment = new MotorizadoFragment();
                Bundle envData = new Bundle();
                envData.putString("idPedido", idPedido);
                envData.putString("numPedido", numPed);
                envData.putString("datCliente",datCliente);
                envData.putString("datDireccion",datDireccion);
                envData.putString("datTelefono",datTelefono);
                newFragment.setArguments(envData);
                activity.getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.nav_host_fragment_activity_main,newFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });
    }
}