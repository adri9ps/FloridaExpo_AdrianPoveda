package com.example.adri9ps.floridaexpo_adrianpoveda;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.adri9ps.floridaexpo_adrianpoveda.ConfiguracionFragment;

public class LayoutFragments extends AppCompatActivity implements FragmentBotones.OnFragmentInteractionListener, PerfilFragment.OnFragmentInteractionListener, ConfiguracionFragment.OnFragmentInteractionListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout_fragments);
    }

    public void onFragmentInteraction(Uri uri) {

    }
}
