package com.example.ejercicio1pmdmfernandopereirab;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.Buffer;

public class Galeria extends AppCompatActivity {

    ImageView imgv1,imgv2,imgvadd;
    Bitmap mIcon1,mIcon2,mIcon3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_galeria);
        Toolbar toolbar = findViewById(R.id.toolbarC);
        setSupportActionBar(toolbar);

        //Añadimos el logo y el titulo en la Toolbar
        getSupportActionBar().setTitle("Galería Asturias");
        getSupportActionBar().setIcon(R.mipmap.asturias);
        toolbar.setTitleTextColor(getResources().getColor(android.R.color.holo_orange_light));

        //Buscamos los imageView
        imgv1 = (ImageView)findViewById(R.id.imageView5);
        imgv2 = (ImageView)findViewById(R.id.imageView6);
        imgvadd = (ImageView)findViewById(R.id.imageView7);

        //Les añadimos tres imagenes de internet
        //Imagen 1
        URL imgUr1 = null;
        try {
            imgUr1 = new URL("https://www.destinosinteligentes.es/wp-content/uploads/2020/06/Gijon-para-RedDTI-775x470.jpg");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Picasso.with(getApplicationContext()).load(String.valueOf(imgUr1)).fit().into(imgv1);
        //Imagen 2
        URL imgUr2 = null;
        try {
            imgUr2 = new URL("https://www.rumbo.es/vuelos/rumbo/img/oviedo.jpg");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Picasso.with(getApplicationContext()).load(String.valueOf(imgUr2)).fit().into(imgv2);
        //Imagen 3
        URL imgUr3 = null;
        try {
            imgUr3 = new URL("https://static.thenounproject.com/png/3322766-200.png");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Picasso.with(getApplicationContext()).load(String.valueOf(imgUr3)).fit().into(imgvadd);


        //Acción al hacer click en la imagen para añadir
        imgvadd.setOnClickListener(new View.OnClickListener() {
            //Redirección del botón flotante a contactos
            @Override
            public void onClick(View view) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(photoPickerIntent,3);
            }
        });


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            //Redirección del botón flotante a contactos
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Galeria.this, Contactos.class);
                startActivity(i);
            }
        });

        //Al hacer click en Toolbar volvemos a la pestaña principal
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Galeria.this, MainActivity.class);
                startActivity(i);
            }
        });


    }
    @Override
    protected void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            Uri selectedImage = data.getData();
            imgvadd.setImageURI(selectedImage);
        }
    }

}