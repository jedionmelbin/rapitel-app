package com.ixcorp.rapitel_app.ui.rutaMapa;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.ixcorp.rapitel_app.MainActivity;
import com.ixcorp.rapitel_app.R;
import com.ixcorp.rapitel_app.ui.subirEvidencias.SubirEvidenciasFragment;

import java.util.List;


public class RutaMapaFragment extends Fragment {

    TextView txtDireccion,txtCliente,txtTelefono;
    String idPedido,numPedido,dCliente,dDireccion,dTelefono,idVehiculo;
    Button btnMapEntregarPedido;
    ImageButton btnLlamarCliente;

    //Para el mapa
    SupportMapFragment supportMapFragment;
    GoogleMap mMap;
    FusedLocationProviderClient fusedLocationProviderClient;
    SearchView svDireccion;

    double latitudActual = 0, longitudActual = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_ruta_mapa, container, false);
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
        dCliente = datosRecuperados.getString("dCliente");
        dDireccion = datosRecuperados.getString("dDireccion");
        dTelefono = datosRecuperados.getString("dTelefono");
        idVehiculo = datosRecuperados.getString("idVehiculo");

        asignarReferencias(view);
        asignarReferenciasMapa(view);
    }

    private void asignarReferencias(View view) {
        txtDireccion = view.findViewById(R.id.txtNombDniEvidencias);
        txtCliente = view.findViewById(R.id.txtMapCliente);
        txtTelefono = view.findViewById(R.id.txtMapTelefonoCliente);

        txtDireccion.setText(dDireccion);
        txtCliente.setText(dCliente);
        txtTelefono.setText(dTelefono);

        btnMapEntregarPedido = view.findViewById(R.id.btnMapEntregarPedido);
        btnLlamarCliente = view.findViewById(R.id.btnMapLlamarCliente);

        btnLlamarCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(getContext(), "Sin permisos para llamar", Toast.LENGTH_SHORT).show();
                    ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.CALL_PHONE},100);
                }else{
                    String dial = "tel:" + dTelefono;
                    startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse(dial)));
                }
            }
        });

        btnMapEntregarPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Entregar pedido", Toast.LENGTH_SHORT).show();

                MainActivity activity = (MainActivity) view.getContext();
                Fragment newFragment = new SubirEvidenciasFragment();
                Bundle envData = new Bundle();
                envData.putString("idPedido", idPedido);
                envData.putString("numPedido", numPedido);
                envData.putString("idVehiculo", idVehiculo);
                newFragment.setArguments(envData);
                activity.getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.nav_host_fragment_activity_main,newFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });
    }

    private void asignarReferenciasMapa(View view) {
//        supportMapFragment = (SupportMapFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.idMapaFragment);
        supportMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.idMapaFragment);
        getUbicacionActual();
        //getUbicacionCliente();
        svDireccion = view.findViewById(R.id.idSvUbicacion);
        svDireccion.setTag(dDireccion);
        svDireccion.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                try {
                    String direccion = svDireccion.getQuery().toString();
                    List<Address> listaDirecciones = null;

                    if (direccion != null || !direccion.equals("")){
                        Geocoder geocoder = new Geocoder(getContext());
                        listaDirecciones = geocoder.getFromLocationName(direccion,1);

                        Address address = listaDirecciones.get(0);
                        LatLng posicion = new LatLng(address.getLatitude(),address.getLongitude());
                        mMap.addMarker(new MarkerOptions().position(posicion).title(direccion));
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(posicion,15));
                    }

                }catch (Exception e){
                    Toast.makeText(getContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

    }



    private void getUbicacionCliente(){
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION},44);
        }else{
            try {
                String direccion = "Calle los zorzales 130";

                List<Address> listaDirecciones = null;

                if (direccion != null || !direccion.equals("")){
                    Geocoder geocoder = new Geocoder(getContext());
                    listaDirecciones = geocoder.getFromLocationName(direccion,1);

                    Address address = listaDirecciones.get(0);
                    LatLng posicion = new LatLng(address.getLatitude(),address.getLongitude());
                    mMap.addMarker(new MarkerOptions().position(posicion).title(direccion));
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(posicion,15));
                }else{
                    Toast.makeText(getContext(), "No se encontró la ubicación del cliente", Toast.LENGTH_SHORT).show();
                }

            }catch (Exception e){
                Toast.makeText(getContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void getUbicacionActual() {
        //Verificar permiso
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION},44);
        }else {
            fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getContext());
            Task<Location> tarea = fusedLocationProviderClient.getLastLocation();

            tarea.addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if (location != null){
                        latitudActual = location.getLatitude();
                        longitudActual = location.getLongitude();

                        //Sincronizar con el mapa
                        supportMapFragment.getMapAsync(new OnMapReadyCallback() {
                            @Override
                            public void onMapReady(@NonNull GoogleMap googleMap) {
                                mMap = googleMap;
                                mMap.getUiSettings().setZoomControlsEnabled(true);
                                LatLng posicionActual = new LatLng(latitudActual, longitudActual);
                                mMap.addMarker(new MarkerOptions()
                                        .position(posicionActual)
                                        .title("Posicion Actual")
                                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))
                                );
                                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(posicionActual,16));
                            }
                        });
                    }
                }
            });
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 44) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getUbicacionActual();
            }
        }
    }

    @Override
    public void onDestroyView() {
        Fragment f = getChildFragmentManager().findFragmentById(R.id.idMapaFragment);
        if (f!=null)
            getChildFragmentManager().beginTransaction()
                    .remove(f).commitAllowingStateLoss();
        super.onDestroyView();
    }

}