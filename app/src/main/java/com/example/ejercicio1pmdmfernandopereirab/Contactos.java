package com.example.ejercicio1pmdmfernandopereirab;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.SimpleCursorAdapter;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.io.IOException;

public class Contactos extends AppCompatActivity implements View.OnClickListener {

    Button btnCancionOnline,btContactos;
    ListView lvContactos;
    SoundPool sndPool;
    SeekBar volumen,balance,velocidad;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contactos);
        Toolbar toolbar = findViewById(R.id.toolbarC);
        setSupportActionBar(toolbar);

        btnCancionOnline = (Button) findViewById(R.id.btn_cancionOnline);
        btContactos = (Button) findViewById(R.id.btnContactos);
        lvContactos = (ListView)findViewById(R.id.listview);

        //Añadimos el logo y el titulo en la Toolbar
        getSupportActionBar().setTitle("Información Asturias");
        getSupportActionBar().setIcon(R.mipmap.asturias);
        toolbar.setTitleTextColor(getResources().getColor(android.R.color.holo_orange_light));


        /*  SOUNDPOOL
       // volumen = (SeekBar)findViewById(R.id.seekBarVolumen);
       // balance = (SeekBar)findViewById(R.id.seekBarlance);
       // velocidad = (SeekBar)findViewById(R.id.seekvelocidad);
        volumen.setMax(4);
        volumen.setProgress(2);
        balance.setMax(4);
        balance.setProgress(2);
        velocidad.setMax(4);
        velocidad.setProgress(2);

        sndPool = new SoundPool(16, AudioManager.STREAM_MUSIC,100);

        AudioAttributes attributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_MEDIA)
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .build();
        sndPool = new SoundPool.Builder()
                .setAudioAttributes(attributes)
                .setMaxStreams(16)
                .build();

      //  int s1 = sndPool.load(this, R.raw.babycrying, 1);
      //  int s2 = sndPool.load(this, R.raw.bomb, 2);
      //  int s3 = sndPool.load(this, R.raw.cat, 3);
      //  int s4 = sndPool.load(this, R.raw.dog, 4);
      //  int s5 = sndPool.load(this, R.raw.policesiren, 5);
        // soundId for reuse later on
        // sndPool.play(s1, 1, 1, 0, 0, 1);

       */


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Ya estás en contactos", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        //Cargamos los contactos en el telefono
        btContactos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,null,null,null);
                startManagingCursor(cursor);
                String [] from = {ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,ContactsContract.CommonDataKinds.Phone.NUMBER,
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID};

                int [] to = {android.R.id.text1,android.R.id.text2};

                SimpleCursorAdapter simpleCursorAdapter = new SimpleCursorAdapter(getApplicationContext(), android.R.layout.simple_expandable_list_item_2,cursor,from,to);
                lvContactos.setAdapter(simpleCursorAdapter);
                lvContactos.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
            }
        });


        //Reproducimos canción de internet al hacer click en botón y capturamos errores mediante snackbar
        btnCancionOnline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MediaPlayer mp = new MediaPlayer();
                try {
                    mp.setDataSource("https://www.mboxdrive.com/killua26.mp3");
                    //https://www.mboxdrive.com/GodsPlan.mp3
                } catch (IOException e) {
                    e.printStackTrace();
                    Snackbar.make(view, "Error" + String.valueOf(e.toString()), Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
                try {
                    mp.prepare();
                } catch (IOException e) {
                    Snackbar.make(view, "Error" + String.valueOf(e.toString()), Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
                mp.start();
            }
        });

        //Al hacer click en Toolbar volvemos a la pestaña principal
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Contactos.this, MainActivity.class);
                startActivity(i);
            }
        });
    }


    @Override
    public void onClick(View view) {

    }
}



