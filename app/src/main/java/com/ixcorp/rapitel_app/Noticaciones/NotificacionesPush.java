package com.ixcorp.rapitel_app.Noticaciones;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.google.firebase.installations.FirebaseInstallations;
import com.ixcorp.rapitel_app.R;

public class NotificacionesPush extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notificaciones_push);
        //Log.d("=>",""+ FirebaseInstallations.getInstance().getToken(true));
    }
}