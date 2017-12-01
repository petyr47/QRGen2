package com.example.peterr.qrgen;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.vision.barcode.Barcode;

public class MainActivity1 extends AppCompatActivity {

    public static final int REQUEST_CODE = 100;
    public static final int PERMISSION_REQUEST = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab =  findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i=new Intent(MainActivity1.this, AuthAct.class);
                startActivityForResult(i, REQUEST_CODE);
            }
        });

        if (ContextCompat.checkSelfPermission(MainActivity1.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity1.this, new String[]{Manifest.permission.CAMERA}, PERMISSION_REQUEST);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            if (data != null) {
                Toast.makeText(this, "Authenticating....", Toast.LENGTH_SHORT).show();
                final Barcode barcode = data.getParcelableExtra("barcode");
                String rslt=barcode.displayValue;

                if (rslt.equals("master_key_peter is awesome and he made me")){

                    Toast.makeText(this, "Access Granted!!, You can now generate QR Codes", Toast.LENGTH_LONG).show();
                    Intent i= new Intent(MainActivity1.this, GennActivity.class);
                    startActivity(i);

                }

                else {
                    Toast.makeText(this, "Wrong PassKey, Access Denied!!", Toast.LENGTH_LONG).show();
                }

            }


        }


        super.onActivityResult(requestCode, resultCode, data);
    }
    }


