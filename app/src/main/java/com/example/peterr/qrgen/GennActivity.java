package com.example.peterr.qrgen;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.journeyapps.barcodescanner.BarcodeEncoder;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.os.Environment.DIRECTORY_DOCUMENTS;

public class GennActivity extends AppCompatActivity {
    EditText Name;
    EditText RegNo;
    EditText email;
    ImageView QRview;
    String Name1;
    String RegNo1;
    String text;
    Button gen_btn;

    static Image image;
    static ImageView img;
    Bitmap bmp;
    static Bitmap bt;
    static byte[] bArray;

    FloatingActionButton fab_pdf;
    FloatingActionButton fab_mail;
    FloatingActionButton fab_gall;
    Animation fab_close;
    Animation fab_open;
    Animation rotate_anticlockwise;
    Animation rotate_clockwise;
    boolean isOpen = false;
    String LOG_TAG;

    File root;
    AssetManager assetManager;
    Bitmap pageImage;
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_genn);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Name = findViewById(R.id.Name);
        RegNo = findViewById(R.id.RegNo);
        email = findViewById(R.id.email);
        QRview = findViewById(R.id.QRview);
        gen_btn = findViewById(R.id.gen_btn);
        fab_gall = findViewById(R.id.fab_gall);
        fab_pdf = findViewById(R.id.fab_pdf);
        fab_mail = findViewById(R.id.fab_mail);
        fab_close = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_close);
        fab_open = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);
        rotate_anticlockwise = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_anticlockwise);
        rotate_clockwise = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_clockwise);


        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        //FloatingActionButton fab1= (FloatingActionButton) findViewById(R.id**.fab1);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isOpen) {
                    fab_gall.startAnimation(fab_close);
                    fab_mail.startAnimation(fab_close);
                    fab_pdf.startAnimation(fab_close);
                    fab.startAnimation(rotate_anticlockwise);

                    fab_gall.setClickable(false);
                    fab_pdf.setClickable(false);
                    fab_mail.setClickable(false);
                    isOpen = false;

                } else {
                    fab_gall.startAnimation(fab_open);
                    fab_mail.startAnimation(fab_open);
                    fab_pdf.startAnimation(fab_open);
                    fab.startAnimation(rotate_clockwise);

                    fab_gall.setClickable(true);
                    fab_pdf.setClickable(true);
                    fab_mail.setClickable(true);
                    isOpen = true;
                }
            }
        });

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

        fab_gall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BitmapDrawable draw = (BitmapDrawable) QRview.getDrawable();
                final Bitmap bitmap = draw.getBitmap();

                FileOutputStream outputStream = null;
                File sdCard = Environment.getExternalStorageDirectory();
                File dir = new File(sdCard.getAbsolutePath() + "/QRcodes");
                dir.mkdirs();
                String Filename = String.format("QRCode_" + Name1 + ".jpg");
                File outFile = new File(dir, Filename);
                try {
                    outputStream = new FileOutputStream(outFile);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
                    outputStream.flush();
                    outputStream.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                intent.setData(Uri.fromFile(outFile));
                sendBroadcast(intent);

                Snackbar.make(view, "Saved to Gallery", Snackbar.LENGTH_SHORT)
                        .setAction("Action", null).show();

               /** PDFBoxResourceLoader.init(getApplicationContext());
                // Find the root of the external storage.
                root = android.os.Environment.getExternalStorageDirectory();
                PDDocument document = new PDDocument();
                PDPage page = new PDPage();
                document.addPage(page);

                // Create a new font object selecting one of the PDF base fonts
                PDFont font = PDType1Font.HELVETICA;

                PDPageContentStream contentStream;

                try {
                    // Define a content stream for adding to the PDF
                    contentStream = new PDPageContentStream(document, page);

                    // Write Hello World in blue text
                    contentStream.beginText();
                    contentStream.setNonStrokingColor(15, 38, 192);
                    contentStream.setFont(font, 12);
                    contentStream.newLineAtOffset(100, 700);
                    contentStream.showText("QR Code for " + Name1);
                    contentStream.endText();

                }
                catch (IOException e) {
                    e.printStackTrace();
                }**/

                }

        });

       /** fab_pdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Saved as PDF, function not yet added", Snackbar.LENGTH_SHORT)
                        .setAction("Action", null).show();

                File pdfFolder =new File(Environment.getExternalStoragePublicDirectory(
                        Environment.DIRECTORY_DOCUMENTS), "pdfdemo");
                if (!pdfFolder.exists()){
                    pdfFolder.mkdir();
                    Log.i(LOG_TAG, "pdf Directory created");
                }
                Date date = new Date();
                String timeStamp =new SimpleDateFormat("ddMMyyyy_HHmm").format(date);

                File myFile = new File(pdfFolder + "image"+timeStamp +".pdf");
                try {
                    OutputStream output =new FileOutputStream(myFile);
                    Document document =new Document(PageSize.A4);
                    PdfWriter.getInstance(document, output);
                    document.open();

                    document.close();
                }
                catch (Exception e){
                    e.printStackTrace();
                }


            }


        });**/

        fab_mail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Sends email, function not added yet sha", Snackbar.LENGTH_SHORT)
                        .setAction("Action", null).show();
            }
        });




    }


}
