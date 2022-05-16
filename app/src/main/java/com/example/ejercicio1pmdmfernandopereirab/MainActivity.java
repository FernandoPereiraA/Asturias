package com.example.ejercicio1pmdmfernandopereirab;

import android.content.Context;
import android.content.Intent;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    VideoView miVideoView;
    Button btHimno,btMelendi,btGaleria;
    MediaController mc;
    MediaPlayer mp,mp1;
    private Context pContext;
    private SoundPool sndPool;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbarC);
        setSupportActionBar(toolbar);
        btGaleria = findViewById(R.id.btGaleria);
        btHimno = findViewById(R.id.btn_himno);
        btMelendi = findViewById(R.id.btn_cancionMelendiInternet);

        sndPool = new SoundPool(16, AudioManager.STREAM_MUSIC,100);

        AudioAttributes attributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_MEDIA)
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .build();
        sndPool = new SoundPool.Builder()
                .setAudioAttributes(attributes)
                .setMaxStreams(16)
                .build();

        int s1 = sndPool.load(this, R.raw.asturiasmelendi, 1);
        int s2 = sndPool.load(this, R.raw.himnoasturias, 2);



        //Añadimos el logo y el titulo en la Toolbar
        getSupportActionBar().setTitle("Visita Asturias");
        getSupportActionBar().setIcon(R.mipmap.asturias);
        toolbar.setTitleTextColor(getResources().getColor(android.R.color.holo_orange_light));

        //*********Añadimos el video a la página principal************//
        miVideoView=(VideoView)findViewById(R.id.videoAsturias);
        // Utilizamos video guardado en repositorio
        String paquete = getPackageName();
        miVideoView.setVideoPath("android.resource://"+paquete +"/"+R.raw.asturiasvideoprincipal);
        //Añadimos el mediacontroller
        mc = new MediaController(this);
        mc.setAnchorView(miVideoView);
        miVideoView.setMediaController(mc);
        miVideoView.start();
        miVideoView.requestFocus();


        //****************Boton flotante contactos****************//
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                Intent contactos = new Intent(MainActivity.this, Contactos.class);
                startActivity(contactos);
            }
        });

        //Al hacer click en Toolbar volvemos a la pestaña principal
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, MainActivity.class);
                startActivity(i);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //Si hacemos click en el Toolbar volver a pantalla principal

        //noinspection SimplifiableIfStatement
        if (id == R.id.contactos) {
            Intent i = new Intent(MainActivity.this, Contactos.class);
            startActivity(i);
            return true;
        }
            if (id == R.id.exit) {
                System.exit(1);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void reproducirCancion(String ruta) {
        if (ruta != null)
        {
            try
            {
                MediaPlayer mp = new MediaPlayer();

                // Le pasamos la ruta y lo reproducimos. Se usa el método asíncrono.
                mp.setDataSource(ruta);
                mp.prepareAsync(); //Método asíncrono

                // Configuramos el evento para que cuando esté cargado, se reproduzca
                mp.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        // Arrancamos el fichero
                        mp.start();
                    }
                });
            }
            catch (Exception ex)
            {
                //Mensaje de error
                Toast.makeText(getApplicationContext(), "No se puede reproducir el audio.", Toast.LENGTH_LONG).show();
            }
        }
        else
        {
            //Mensaje de error
            Toast.makeText(getApplicationContext(), "Audio no encontrado.", Toast.LENGTH_LONG).show();
        }
    }

    public void onClick(View v) {
        switch(v.getId())
        {
            case(R.id.btn_cancionMelendiInternet):
                String ruta = "https://www.mboxdrive.com/AsturiasMelendi.mp3";
                //reproducirCancion(ruta);
                sndPool.play(1,1,1, 0, 0, 1);
                break;
            case(R.id.btn_himno):
                sndPool.play(2,1,1, 0, 0, 1);
                break;
            case(R.id.btGaleria):
                 Intent i = new Intent(MainActivity.this, Galeria.class);
                 startActivity(i);
                 break;
            case(R.id.toolbarC):

                break;

        }
    }
}


