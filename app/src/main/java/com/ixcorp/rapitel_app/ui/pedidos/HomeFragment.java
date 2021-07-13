package com.ixcorp.rapitel_app.ui.pedidos;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
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
import com.ixcorp.rapitel_app.databinding.FragmentPedidosBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private FragmentPedidosBinding binding;

    private static final String URL1="https://rapitel-api.azurewebsites.net/orders/list";

    List<Orders> listaPedidos = new ArrayList<>();
    RecyclerView recyclerView;
    RequestQueue requestQueue;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_pedidos,container,false);
//        recyclerView = view.findViewById(R.id.listaPedidosEntregas);
//        listPedido = new ArrayList<>();
//        view = binding.getRoot();


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

                                pedido.setMumOrder(ped.getString("mumOrder"));
                                pedido.setNumerDocument(cust.getString("numerDocument"));
                                pedido.setFirstName(cust.getString("firstName"));
                                pedido.setLastName (cust.getString("lastName"));
                                pedido.setCreateDate(ped.getString("createDate"));
                                pedido.setSubTotal(ped.getDouble("subTotal"));
                                pedido.setIgv(ped.getDouble("igv"));
                                pedido.setTotal(ped.getDouble("total"));

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
                        Log.i("Error => ", e.getMessage());
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