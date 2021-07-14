package com.ixcorp.rapitel_app.ui.perfil;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.ixcorp.rapitel_app.databinding.FragmentPerfilBinding;

public class NotificationsFragment extends Fragment {

    private NotificationsViewModel perfilViewModel;
    private FragmentPerfilBinding binding;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        perfilViewModel = new ViewModelProvider(this).get(NotificationsViewModel.class);

        binding = FragmentPerfilBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textNotifications;
        perfilViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

//        final Button btnVerdetalle = binding.btnVerdetalle;
//        btnVerdetalle.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                // Crear fragmento de tu clase
//                Fragment fragment = new detalle_pedido();
//                // Obtener el administrador de fragmentos a través de la actividad
//                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//                // Definir una transacción
//                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                // Remplazar el contenido principal por el fragmento
//                fragmentTransaction.replace(R.layout.fragment_detalle_pedido,fragment);
//                fragmentTransaction.addToBackStack(null);
//                // Cambiar
//                fragmentTransaction.commit();
//
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