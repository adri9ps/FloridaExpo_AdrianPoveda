package com.example.adri9ps.floridaexpo_adrianpoveda;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    // FIREBASE REFERENCIAS
    FirebaseUser user;
    FirebaseAuth mAuth;
    FirebaseDatabase bbdd;
    DatabaseReference refProyectos;

    //RECYCLER
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    ArrayList<Proyecto> proyectos;
    ArrayList<String> llavesProyectos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        user = FirebaseAuth.getInstance().getCurrentUser();
        mAuth = FirebaseAuth.getInstance();
        if (user != null) {


        } else {
            // SI NO ESTÁ LOGUEADO, QUE VAYA A INICIO DE SESIÓN
            startActivity(new Intent(this, InicioSesion.class));
            finish();

        }

        //RECYCLER REFERENCIAS
        refProyectos = FirebaseDatabase.getInstance().getReference("proyectos");
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerPrincipal);

        proyectos = new ArrayList<Proyecto>();
        llavesProyectos = new ArrayList<String>();

        bbdd = FirebaseDatabase.getInstance();





        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // TODO CORREGIR

        // LLAMAMOS AL MÉTODO
        cargarProyectos();
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            // CIERRA SESIÓN Y LLEVA AL LOGIN
            mAuth.signOut();
            Intent main = new Intent(MainActivity.this, InicioSesion.class);
            startActivity(main);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_proyectos) {

            // VA HASTA EL MAIN
            Intent volver = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(volver);

        } else if (id == R.id.nav_configuracion) {

            // VA HASTA EL ACTIVITY QUE CONTIENE LOS FRAGMENTS
            Intent conf = new Intent(getApplicationContext(), LayoutFragments.class);
            startActivity(conf);

        } else if (id == R.id.nav_cerrarSesion) {

            // CIERRA SESIÓN Y LLEVA AL LOGIN
            mAuth.signOut();
            Intent main = new Intent(MainActivity.this, InicioSesion.class);
            startActivity(main);
            finish();

        } else if (id == R.id.nav_info) {

            // ALERT DIALOG QUE TE REDIRIGE A LA PÁGINA WEB DE FLORIDA EXPO SI LO DESEAS
            AlertDialog.Builder a_builder = new AlertDialog.Builder(MainActivity.this);
            a_builder.setMessage("Web de FloridaExpo para ver los proyectos")
                    .setTitle("INFORMACIÓN")
                    .setNegativeButton("No", null)
                    .setPositiveButton("Acceder a la página",new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Uri uri = Uri.parse("http://floridaexpo.florida.es/exposicion-de-proyectos-2/");
                            Intent i = new Intent(Intent.ACTION_VIEW);
                            i.setData(uri);
                            startActivity(i);
                        }
                    });

            a_builder.show();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    // MÉTODO QUE RECOGE LOS DATOS DE FIREBASE Y LOS CARGA
    private void cargarProyectos() {

        //DE ESTA MANERA LOS PROYECTOS SE CARGAN ORDENADOS POR LOS VOTOS (DE MENOR A MAYOR)
        Query q = refProyectos.orderByChild("votosProyecto");
        q.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot datasnapshot : dataSnapshot.getChildren()) {
                    Log.d("#TEMPORAL", datasnapshot.getKey());
                    Proyecto servicioTEMP = datasnapshot.getValue(Proyecto.class);

                    Log.d("#TEMP", "Proyecto cargado --> " + servicioTEMP.getNombreProyecto());
                    proyectos.add(servicioTEMP);
                    // --> Guardamos llave del proyecto actual, para pasarlo al onClick
                    llavesProyectos.add(datasnapshot.getKey());
                }


                Log.d("#TEMP", "La lista tiene " + proyectos.size());



                mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
                // DEBERÍA HACER QUE SE ORDENARAN AL REVÉS, YA QUE EL LAYOUT MANAGER SE ORDENARÍA
                // DE FORMA CONTRARIA
                //mLayoutManager.setReverseLayout(true);
                mRecyclerView.setLayoutManager(mLayoutManager);
                mAdapter = new AdaptadorProyectos(proyectos, llavesProyectos);
                mRecyclerView.setAdapter(mAdapter);


            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        // DE ESTA MANERA LOS PORYECTOS SE CARGAN POR ORDEN SEGÚN ESTÉN AÑADIDOS EN FIREBASE, SIN NINGUNA CONDICIÓN
       /* refProyectos.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //Creamos adaptador y limpiamos listas

                proyectos.clear();
                llavesProyectos.clear();

                //Obtenemos Proyectos
                for (DataSnapshot datasnapshot : dataSnapshot.getChildren()) {
                    Log.d("#TEMPORAL", datasnapshot.getKey());
                    Proyecto servicioTEMP = datasnapshot.getValue(Proyecto.class);

                    Log.d("#TEMP", "Proyecto cargado --> " + servicioTEMP.getNombreProyecto());
                    proyectos.add(servicioTEMP);
                    // --> Guardamos llave del proyecto actual, para pasarlo al onClick
                    llavesProyectos.add(datasnapshot.getKey());
                }


                Log.d("#TEMP", "La lista tiene " + proyectos.size());

                mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
                mRecyclerView.setLayoutManager(mLayoutManager);
                mAdapter = new AdaptadorProyectos(proyectos, llavesProyectos);
                mRecyclerView.setAdapter(mAdapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });*/
    }


}