package com.ixcorp.rapitel_app.ui.motorizado;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ixcorp.rapitel_app.Adapters.Vehiculo.VehiculoAdapter;
import com.ixcorp.rapitel_app.Model.Vehicle;
import com.ixcorp.rapitel_app.R;
import com.ixcorp.rapitel_app.Utils.Api;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class MotorizadoFragment extends Fragment {

    //TextView txtVehiulo,txtPlaca,txtColor;
    TextView txtNumPedidoMot;
    String idPedido,numPedido,dCliente,dDireccion,dTelefono;
    List<Vehicle> listaVehiculo = new ArrayList<>();
    RecyclerView recyclerView;
    RequestQueue requestQueue;

    String URL = Api.URL_API;
    String URL1 = URL+"vehicles/list";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_motorizado, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        requestQueue = Volley.newRequestQueue(getContext());

        txtNumPedidoMot = view.findViewById(R.id.txtNumPedidoEvidencias);
        recyclerView = view.findViewById(R.id.listaMotorizados);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

       // asignarReferencias(view);

        Bundle datosRecuperados = getArguments();
        if (datosRecuperados == null) {
            Toast.makeText(getContext(),"No hay datos para mostrar",Toast.LENGTH_SHORT).show();
            return;
        }

        String idOrder = datosRecuperados.getString("idPedido");
        String numPed = datosRecuperados.getString("numPedido");
        String datCliente = datosRecuperados.getString("datCliente");
        String datDireccion = datosRecuperados.getString("datDireccion");
        String datTelefono = datosRecuperados.getString("datTelefono");

        idPedido = idOrder;
        numPedido = numPed;
        dCliente = datCliente;
        dDireccion = datDireccion;
        dTelefono = datTelefono;

        txtNumPedidoMot.setText(numPed);
        parseJSON();

    }

    private void parseJSON() {
        StringRequest request = new StringRequest(
                Request.Method.GET,
                URL1,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray array = new JSONArray(response);

                            for (int i=0; i<array.length(); i++){
                                Vehicle vehicle = new Vehicle();
                                JSONObject veh = array.getJSONObject(i);

                                vehicle.setNameVehiculo(veh.getString("name"));
                                vehicle.setPlate(veh.getString("plate"));
                                vehicle.setColor(veh.getString("color"));
                                vehicle.setIdPedido(idPedido);
                                vehicle.setNumPedido(numPedido);
                                vehicle.setsCliente(dCliente);
                                vehicle.setsDireccion(dDireccion);
                                vehicle.setsTelefono(dTelefono);

                                listaVehiculo.add(vehicle);
                            }

                            //Log.d("******", String.valueOf(listaVehiculo));

                            recyclerView.setAdapter(new VehiculoAdapter(getContext(),listaVehiculo));
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


}