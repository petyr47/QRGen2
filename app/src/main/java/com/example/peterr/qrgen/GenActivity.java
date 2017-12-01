package com.example.peterr.qrgen;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.OvershootInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


public class GenActivity extends AppCompatActivity {

    EditText Name;
    EditText RegNo;
    EditText email;
    ImageView QRview;
    String Name1;
    String RegNo1;
    String text;
    Button gen_btn;
   /** FloatingActionButton fab_pdf;
    FloatingActionButton fab_mail;
    FloatingActionButton fab_gall;
    FloatingActionButton fab_plus;
    Animation fab_close;
    Animation fab_open;
    Animation rotate_anticlockwise;
    Animation rotate_clockwise;
    boolean isOpen= false;**/



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gen);
        Name = findViewById(R.id.Name);
        RegNo = findViewById(R.id.RegNo);
        email = findViewById(R.id.email);
        QRview = findViewById(R.id.QRview);
        gen_btn = findViewById(R.id.gen_btn);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //FloatingActionButton fab = findViewById(R.id.fab);
      /**  fab_gall=findViewById(R.id.fab_gall);
       fab_pdf=findViewById(R.id.fab_pdf);
       fab_mail=findViewById(R.id.fab_mail);
       fab_plus=findViewById(R.id.fab_plus);
       fab_close =AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_close);
       fab_open =AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);
       rotate_anticlockwise=AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_anticlockwise);
       rotate_clockwise= AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_clockwise);


        fab_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isOpen)
                {
                    fab_gall.startAnimation(fab_close);
                    fab_mail.startAnimation(fab_close);
                    fab_pdf.startAnimation(fab_close);
                    fab_plus.startAnimation(rotate_anticlockwise);

                    fab_gall.setClickable(false);
                    fab_pdf.setClickable(false);
                    fab_mail.setClickable(false);
                    isOpen=false;

                }

                else {
                    fab_gall.startAnimation(fab_open);
                    fab_mail.startAnimation(fab_open);
                    fab_pdf.startAnimation(fab_open);
                    fab_plus.startAnimation(rotate_clockwise);

                    fab_gall.setClickable(true);
                    fab_pdf.setClickable(true);
                    fab_mail.setClickable(true);
                    isOpen=true;
                }

            }
        });**/





        gen_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Name1 = Name.getText().toString().trim();
                RegNo1 = RegNo.getText().toString().trim();
                String email1 = email.toString();
                text = Name1 + RegNo1;
                text = text.trim();


                if (Name1.equals("")) {
                    Snackbar.make(view, "Name field is empty, Fill it and try again", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
                if (RegNo1.equals("")) {
                    Snackbar.make(view, "Reg Number field is empty, Fill it and try again", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                } else {


                    MultiFormatWriter multiFormatWriter = new MultiFormatWriter();

                    try {
                        BitMatrix bitMatrix = multiFormatWriter.encode(text, BarcodeFormat.QR_CODE, 920, 920);
                        BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                        Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
                        QRview.setImageBitmap(bitmap);
                    } catch (WriterException e) {
                        e.printStackTrace();

                    }

                    Snackbar.make(view, "QR Code Generated Successfully", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }

                if (email1.equals("")) {
                    Snackbar.make(view, "Email field is empty, Fill it and try again", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }

            }
        });
    }


       /** fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    BitmapDrawable draw=(BitmapDrawable) QRview.getDrawable();
                    Bitmap bitmap =draw.getBitmap();

                    FileOutputStream outputStream =null;
                    File sdCard= Environment.getExternalStorageDirectory();
                    File dir =new File(sdCard.getAbsolutePath()+"/QRcodes");
                    dir.mkdirs();
                    String Filename=String.format("QRCode_"+Name1+".jpg");
                    File outFile =new File(dir, Filename);
                    try {
                        outputStream= new FileOutputStream(outFile);
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100,outputStream);
                        outputStream.flush();
                        outputStream.close();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    Intent intent =new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                    intent.setData(Uri.fromFile(outFile));
                    sendBroadcast(intent);

                    Snackbar.make(view, "Saved to Gallery", Snackbar.LENGTH_SHORT)
                            .setAction("Action", null).show();




            }
        });**/


    }


