package com.example.adri9ps.floridaexpo_adrianpoveda;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class VisualizaProyecto extends AppCompatActivity {

    private TextView nombre, descrip, ciclo, lugar;
    private Button atras, votar;
    String nombreProyecto;
    Proyecto proyectoActual;
    ArrayList<String> proyectosVotados;
    Integer votos;
    DatabaseReference bbdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualiza_proyecto);

        nombre = (TextView) findViewById(R.id.txtNombre_V);
        descrip = (TextView) findViewById(R.id.txtDescripcion_V);
        ciclo = (TextView) findViewById(R.id.txtCiclo_V);
        lugar = (TextView) findViewById(R.id.txtlugar_V);
        atras = (Button) findViewById(R.id.btnAtras);
        votar = (Button) findViewById(R.id.btnVotar);
        bbdd = FirebaseDatabase.getInstance().getReference("proyectos");

        nombreProyecto = getIntent().getStringExtra("nombreProyecto");
        Log.d("#TEMP", "He recibido el PROYECTO --> " + nombreProyecto);

        //TODO Cargar información de la clave recibida

        // CARGAMOS EL PROYECTO
        if (nombreProyecto != null) {
            DatabaseReference refservicio = FirebaseDatabase.getInstance().getReference("proyectos").child(nombreProyecto);
            refservicio.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Proyecto proy = dataSnapshot.getValue(Proyecto.class);
                    proyectoActual = proy;
                    Log.d("#TEMP", "Estoy dentro con el proyecto " + dataSnapshot.getKey());
                    Log.d("#TEMP", "He cargado el proyecto " + proyectoActual.getNombreProyecto());

                    CargarDatos();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });


        }
        atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent volver = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(volver);

            }

        });

        votar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                votarProyecto();


            }

        });



    }

    private void CargarDatos(){

        nombre.setText(proyectoActual.getNombreProyecto());
        descrip.setText(proyectoActual.getDescripcionProyecto());
        ciclo.setText(proyectoActual.getCicloProyecto());
        lugar.setText(proyectoActual.getLugarProyecto());


    }

    private void votarProyecto(){

        // SUMA EN 1 LOS VOTOS DEL PROYECTO

        votos = proyectoActual.getVotosProyecto() + 1;
        bbdd.child(nombreProyecto).child("votosProyecto").setValue(votos);
        Toast.makeText(VisualizaProyecto.this, "VOTADO!", Toast.LENGTH_LONG).show();
        votar.setEnabled(false);
    }
}

