package com.ixcorp.rapitel_app.ui.pedidos;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.ixcorp.rapitel_app.R;
import com.ixcorp.rapitel_app.databinding.FragmentDetallePedidoBinding;
import com.ixcorp.rapitel_app.databinding.FragmentPedidosBinding;
import com.ixcorp.rapitel_app.ui.detallePedido.detalle_pedido;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private FragmentPedidosBinding binding;



//    View vista;
//    Button btnIrDetalle;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentPedidosBinding.inflate(inflater, container, false);
        View root = binding.getRoot();




//        vista = inflater.inflate(R.layout.fragment_pedidos,container,false);
//        btnIrDetalle = vista.findViewById(R.id.btnEstadoPedido);

//        btnIrDetalle.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //Toast.makeText(getContext(),"Presiona",Toast.LENGTH_SHORT).show();
//                Fragment fragmentDetalle = new detalle_pedido();
//                FragmentTransaction transaction = getFragmentManager().beginTransaction();
//                transaction.replace(R.id.nav_host_fragment_container,fragmentDetalle);
//                transaction.addToBackStack(null);
//                transaction.commit();
//            }
//        });

//        return vista;


        //final TextView textView = binding.textHome;
//        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}