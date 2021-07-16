package com.ixcorp.rapitel_app.ui.subirEvidencias;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ixcorp.rapitel_app.R;

import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;


public class SubirEvidenciasFragment extends Fragment {

    String idPedido,numPedido,idVehiculo;
    TextView txtNumpedido,txtFotoDni,txtFotoEntrega;
    Button btnRegistrarEntregaPedido;
    ImageView imgFotoDni,imgFotoEntrega;

    ImageButton btnCamaraDni,btnCamaraEntrega;
    private static final int REQUEST_PERMISSION_CAMERA = 100;
    private static final int REQUEST_IMAGE_CAMERA = 101;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_subir_evidencia, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle datosRecuperados = getArguments();
        if (datosRecuperados == null) {
            Toast.makeText(getContext(),"No hay datos para mostrar",Toast.LENGTH_SHORT).show();
            return;
        }

        idPedido = datosRecuperados.getString("idPedido");
        numPedido = datosRecuperados.getString("numPedido");
        idVehiculo = datosRecuperados.getString("idVehiculo");
        asignarReferencias(view);
    }

    private void asignarReferencias(View view) {
        txtNumpedido = view.findViewById(R.id.txtNumPedidoEvidencias);
        txtFotoDni = view.findViewById(R.id.txtNombDniEvidencias);
        txtFotoEntrega = view.findViewById(R.id.txtNombEntregaEvidencias);
        imgFotoDni = view.findViewById(R.id.imgFotoDni);
        imgFotoEntrega = view.findViewById(R.id.imgFotoEntrega);


        txtNumpedido.setText(numPedido);

        btnRegistrarEntregaPedido = view.findViewById(R.id.btnRegistrarEntregaPedido);
        btnCamaraDni = view.findViewById(R.id.btnCamaraSubirDni);
        btnCamaraEntrega = view.findViewById(R.id.btnCamaraSubirEntrega);

        btnCamaraDni.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    if(ActivityCompat.checkSelfPermission(getContext(),Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED){
                        abrirCamara();
                    }else {
                        ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.CAMERA},REQUEST_PERMISSION_CAMERA);
                    }
                }else{
                    abrirCamara();
                }
            }
        });

        btnRegistrarEntregaPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Registrar entrega pedido", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull @NotNull String[] permissions, @NonNull @NotNull int[] grantResults) {
        if (requestCode == REQUEST_PERMISSION_CAMERA){
            if (permissions.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                abrirCamara();
            }else{
                Toast.makeText(getContext(), "Se requiere dar permisos a la camara", Toast.LENGTH_SHORT).show();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        if (requestCode == REQUEST_IMAGE_CAMERA){
            if (resultCode == Activity.RESULT_OK){
                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                Log.i("TAGIMG","Result=> " + bitmap);
                //seteamos la imagen
                imgFotoDni.setImageBitmap(bitmap);

            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void abrirCamara(){
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (cameraIntent.resolveActivity(getContext().getPackageManager())!= null){
            getActivity().startActivityFromFragment(SubirEvidenciasFragment.this, cameraIntent, REQUEST_IMAGE_CAMERA);

        }else{
            Toast.makeText(getContext(), "No se puede abrir la camara", Toast.LENGTH_LONG).show();
        }
    }


}