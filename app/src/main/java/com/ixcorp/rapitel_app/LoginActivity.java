package com.ixcorp.rapitel_app;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ixcorp.rapitel_app.Model.Login;
import com.ixcorp.rapitel_app.Utils.Api;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class LoginActivity extends AppCompatActivity {

    TextView txtUsuario, txtContrasenia;
    String usuario, contrasenia;
    Button btnIniciarSesion;

    String URL = Api.URL_API;
    String URL1 = URL + "accounts/login";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        asignarReferencias();
    }

//    public void recibirDatos(){
//        usuario
//    }

    private void asignarReferencias() {

        txtUsuario = findViewById(R.id.txtUsuario);
        txtContrasenia = findViewById(R.id.txtContrasenia);
        btnIniciarSesion = findViewById(R.id.btnEntregarPedido);

        btnIniciarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iniciarSesion();

            }
        });
    }


    private void iniciarSesion() {

        HashMap data = new HashMap();

        data.put("userName", txtUsuario.getText().toString());
        data.put("password", txtContrasenia.getText().toString());

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, URL1, new JSONObject(data), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //String code = response.optString("message");

                Boolean rsp = response.optBoolean("success");

                Log.d("MSJ", "onResponse: " + rsp);
                if (rsp == true){
                    Toast.makeText(LoginActivity.this, "Sesión iniciada", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(LoginActivity.this, "Usuario y/o contraseña incorrecta", Toast.LENGTH_SHORT).show();
                }

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("ID", "onResponse: " + error.toString());
                        Toast.makeText(LoginActivity.this, "Error al iniciar sesión", Toast.LENGTH_SHORT).show();
                    }
                }
        ) {
            //here I want to post data to sever
        };
        RequestQueue cola = Volley.newRequestQueue(this);
        cola.add(jsonObjectRequest);


//
//        StringRequest peticion = new StringRequest(Request.Method.POST, URL1, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                Log.d("respuesta =>", ""+response);
//
//                Toast.makeText(LoginActivity.this,"Sesión iniciada",Toast.LENGTH_SHORT).show();
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Log.d("=>", error.toString());
//                Toast.makeText(LoginActivity.this, "Error al iniciar sesion", Toast.LENGTH_SHORT).show();
//            }
//        }){
//            @Nullable
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String,String> parametros = new HashMap<>();
//                parametros.put("userName",txtUsuario.getText().toString());
//                parametros.put("password",txtContrasenia.getText().toString());
//                return parametros;
//            }
//        };
//        RequestQueue cola = Volley.newRequestQueue(this);
//        cola.add(peticion);
    }

//    private boolean validarCampos(){
//        boolean resultado = true;
//        txtUsuario = txtUsuario.getText().toString();
//
//    }
}