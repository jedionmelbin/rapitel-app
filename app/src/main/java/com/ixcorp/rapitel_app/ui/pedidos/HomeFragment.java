package com.ixcorp.rapitel_app.ui.pedidos;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ixcorp.rapitel_app.Adapters.Pedido.PedidoAdapter;
import com.ixcorp.rapitel_app.Model.Orders;
import com.ixcorp.rapitel_app.R;
import com.ixcorp.rapitel_app.Utils.Api;
import com.ixcorp.rapitel_app.databinding.FragmentPedidosBinding;
import com.ixcorp.rapitel_app.ui.detallePedido.DetallePedidoFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private FragmentPedidosBinding binding;
    String URL = Api.URL_API;
    String URL1 = URL+"orders/list/A";

    List<Orders> listaPedidos = new ArrayList<>();
    RecyclerView recyclerView;
    RequestQueue requestQueue;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        View root = inflater.inflate(R.layout.fragment_pedidos,container,false);
//        recyclerView = root.findViewById(R.id.listaPedidosEntregas);
//        root = binding.getRoot();


        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        binding = FragmentPedidosBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
       return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        requestQueue = Volley.newRequestQueue(getContext());

        recyclerView = view.findViewById(R.id.listaPedidosEntregas);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

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
                           JSONArray  array = new JSONArray(response);

                            for (int i=0; i<array.length(); i++){
                                Orders pedido = new Orders();

                                JSONObject ped = array.getJSONObject(i);
                                JSONObject cust = ped.getJSONObject("customers");

                                pedido.setOrderId(ped.getInt("orderId"));
                                pedido.setMumOrder(ped.getString("mumOrder"));
                                pedido.setCreateDate(ped.getString("createDate"));
                                pedido.setDeliveyTime(ped.getString("deliveyTime"));
                                pedido.setSubTotal(ped.getDouble("subTotal"));
                                pedido.setIgv(ped.getDouble("igv"));
                                pedido.setTotal(ped.getDouble("total"));

                                pedido.setNumerDocument(cust.getString("numerDocument"));
                                pedido.setFirstName(cust.getString("firstName"));
                                pedido.setLastName (cust.getString("lastName"));
                                pedido.setTelefono(cust.getString("phone"));
                                pedido.setEmail(cust.getString("email"));
                                pedido.setDireccion(cust.getString("address"));

                                listaPedidos.add(pedido);
                            }
                            //Log.d("******", String.valueOf(listaPedidos));
                            recyclerView.setAdapter(new PedidoAdapter(getContext(),listaPedidos));
                        } catch (JSONException e) {
                            Log.i("Error => ", e.getMessage());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError e) {
                        Log.d("Error =>",e.getMessage());
                        Toast.makeText(getContext(), "No se pudo conectar al servidor", Toast.LENGTH_SHORT).show();
                    }
                }
        );

        requestQueue.add(request);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}