package com.ixcorp.rapitel_app.ui.subirEvidencias;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;

import androidx.fragment.app.Fragment;

import com.android.volley.NetworkResponse;

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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.ixcorp.rapitel_app.LoginActivity;
import com.ixcorp.rapitel_app.MainActivity;
import com.ixcorp.rapitel_app.Model.DataPart;
import com.ixcorp.rapitel_app.R;
import com.ixcorp.rapitel_app.Utils.Api;
import com.ixcorp.rapitel_app.config.VolleyMultipartRequest;
import com.ixcorp.rapitel_app.ui.motorizado.MotorizadoFragment;
import com.ixcorp.rapitel_app.ui.pedidos.HomeFragment;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;


public class SubirEvidenciasFragment extends Fragment {

    String idPedido, numPedido, idVehiculo;
    TextView txtNumpedido, txtFotoDni, txtFotoEntrega,txtNombDniEvidencias,txtNombEvidenciasEntrega;
    Button btnRegistrarEntregaPedido;
    ImageView imgFotoDni, imgFotoEntrega;

    ImageButton btnCamaraDni, btnCamaraEntrega;
    private static final int REQUEST_PERMISSION_CAMERA = 100;
    private static final int REQUEST_IMAGE_CAMERA = 101;
    public String IMAGEN1;

    String URL = Api.URL_API;
    String URL1 = URL + "evidences/uploadFile";
    String URL2 = URL + "evidences/create";




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_subir_evidencia, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle datosRecuperados = getArguments();
        if (datosRecuperados == null) {
            Toast.makeText(getContext(), "No hay datos para mostrar", Toast.LENGTH_SHORT).show();
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
        txtNombDniEvidencias = view.findViewById(R.id.txtNombDniEvidencias);
        txtNombEvidenciasEntrega = view.findViewById(R.id.txtNombEntregaEvidencias);


        txtNumpedido.setText(numPedido);

        btnRegistrarEntregaPedido = view.findViewById(R.id.btnRegistrarEntregaPedido);
        btnCamaraDni = view.findViewById(R.id.btnCamaraSubirDni);
        btnCamaraEntrega = view.findViewById(R.id.btnCamaraSubirEntrega);

        btnCamaraDni.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                        abrirCamara();
                        IMAGEN1 = "FOTODNI";
                    } else {
                        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, REQUEST_PERMISSION_CAMERA);
                    }
                } else {
                    abrirCamara();
                    IMAGEN1 = "FOTODNI";
                }
            }
        });

        btnCamaraEntrega.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                        abrirCamara();
                        IMAGEN1 = "FOTOENTREGA";
                    } else {
                        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, REQUEST_PERMISSION_CAMERA);
                    }
                } else {
                    abrirCamara();
                    IMAGEN1 = "FOTOENTREGA";
                }
            }
        });

        btnRegistrarEntregaPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getContext(), "Registrar entrega pedido", Toast.LENGTH_SHORT).show();

                AlertDialog.Builder ventana = new AlertDialog.Builder(getContext());
                ventana.setTitle("Exito");
                ventana.setMessage("El pedido se regsitro correctamente");
                ventana.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        MainActivity activity = (MainActivity) view.getContext();
                        Fragment newFragment = new HomeFragment();
                        activity.getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.nav_host_fragment_activity_main,newFragment)
                                .addToBackStack(null)
                                .commit();
                    }
                });

                ventana.create().show();

            }
        });
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull @NotNull String[] permissions, @NonNull @NotNull int[] grantResults) {
        if (requestCode == REQUEST_PERMISSION_CAMERA) {
            if (permissions.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                abrirCamara();
            } else {
                Toast.makeText(getContext(), "Se requiere dar permisos a la camara", Toast.LENGTH_SHORT).show();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAMERA) {
            if (resultCode == Activity.RESULT_OK) {
                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                Log.i("TAGIMG", "Result=> " + bitmap);

                uploadBitmap(bitmap);

                if (IMAGEN1.equals("FOTODNI")){
                    imgFotoDni.setImageBitmap(bitmap);
                }else {
                    imgFotoEntrega.setImageBitmap(bitmap);
                }


            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public String getPath(Uri uri) {
        Cursor cursor = getActivity().getApplicationContext().getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        String document_id = cursor.getString(0);
        document_id = document_id.substring(document_id.lastIndexOf(":") + 1);
        cursor.close();

        cursor = getActivity().getApplicationContext().getContentResolver().query(
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                null, MediaStore.Images.Media._ID + " = ? ", new String[]{document_id}, null);
        cursor.moveToFirst();
        String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
        cursor.close();

        return path;
    }


    private void abrirCamara() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (cameraIntent.resolveActivity(getContext().getPackageManager()) != null) {
            getActivity().startActivityFromFragment(SubirEvidenciasFragment.this, cameraIntent, REQUEST_IMAGE_CAMERA);

        } else {
            Toast.makeText(getContext(), "No se puede abrir la camara", Toast.LENGTH_LONG).show();
        }
    }

    private void uploadBitmap(final Bitmap bitmap) {

        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, URL1,
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {
                        try {
                            JSONObject obj = new JSONObject(new String(response.data));

                            String nomImage = obj.getString("fileName");

                            if (IMAGEN1.equals("FOTODNI")){
                                txtNombDniEvidencias.setText(nomImage);
                            }else {
                                txtNombEvidenciasEntrega.setText(nomImage);
                            }

                            if (nomImage.length()>0){
                                guardarEvidencia(nomImage);
                            }else{
                                Toast.makeText(getContext(), "Vuelva a tomar nueva fotografía", Toast.LENGTH_SHORT).show();
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                        Log.e("GotError", "" + error.getMessage());

                    }
                }) {


            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                long imagename = System.currentTimeMillis();
                params.put("file", new DataPart(imagename + ".png", getFileDataFromDrawable(bitmap)));
                return params;
            }
        };

        //adding the request to volley
        Volley.newRequestQueue(getContext()).add(volleyMultipartRequest);
    }


    public byte[] getFileDataFromDrawable(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 80, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    private void guardarEvidencia(String nombImage){
        String nombreImage = nombImage;
        Integer orderId =  Integer.parseInt(idPedido);

        HashMap data = new HashMap();

        data.put("evidenceId",0);
        data.put("orderId", orderId);

        if (IMAGEN1.equals("FOTODNI")){
            data.put("dniImage", nombreImage);
            data.put("picture", null);
        }else {
            data.put("dniImage", null);
            data.put("picture", nombreImage);
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, URL2, new JSONObject(data), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //String code = response.optString("message");
                Toast.makeText(getContext(), "Evidencia registrada con éxito 2021", Toast.LENGTH_SHORT).show();
                //Boolean rsp = response.optBoolean("success");


            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("ID", "onResponse: " + error.toString());
                        //Toast.makeText(getContext(), "Error al guardar evidencia", Toast.LENGTH_SHORT).show();
                    }
                }
        ) {
            //here I want to post data to sever
        };

        btnRegistrarEntregaPedido.setVisibility(View.VISIBLE);
        Toast.makeText(getContext(), "Evidencia registrada con éxito", Toast.LENGTH_SHORT).show();

        RequestQueue cola = Volley.newRequestQueue(getContext());
        cola.add(jsonObjectRequest);

        IMAGEN1 = "";

    }

}