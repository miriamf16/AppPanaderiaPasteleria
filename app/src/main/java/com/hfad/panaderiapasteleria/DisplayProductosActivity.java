package com.hfad.panaderiapasteleria;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class DisplayProductosActivity extends AppCompatActivity  {
    GalletasCamposFragment galletasCamposFragment;
    PanDulceCamposFragment panDulceCamposFragment;
    PastelCamposFragment pastelCamposFragment;
    PagoFragment pagoFragment;
    Button btnPagar;
    private boolean running,wasRunning;
    int pos = 0;
    Spinner sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         if(savedInstanceState != null){
             pos = savedInstanceState.getInt("int_value");
            running = savedInstanceState.getBoolean("running");
            wasRunning = savedInstanceState.getBoolean("wasRunning");
        }

        setContentView(R.layout.activity_productos);

        sp = findViewById(R.id.product_spin);
        galletasCamposFragment = new GalletasCamposFragment();
        panDulceCamposFragment = new PanDulceCamposFragment();
        pastelCamposFragment = new PastelCamposFragment();
        pagoFragment = new PagoFragment();
        btnPagar = findViewById(R.id.btnpagar);

        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String text = adapterView.getItemAtPosition(i).toString();
                pos = i;
                switch (pos){
                        case 1:
                            selectFragment(panDulceCamposFragment);
                            btnPagar.setVisibility(View.VISIBLE);
                            btnPagar.setEnabled(true);
                            break;
                        case 2:
                            selectFragment(pastelCamposFragment);
                            btnPagar.setVisibility(View.VISIBLE);
                            btnPagar.setEnabled(true);
                            break;
                        case 3:
                            selectFragment(galletasCamposFragment);
                            btnPagar.setVisibility(View.VISIBLE);
                            btnPagar.setEnabled(true);
                            break;
                    default:
                            break;
                }
            }


            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                adapterView.getLastVisiblePosition();
            }
        });


        btnPagar = (Button) findViewById(R.id.btnpagar);
        btnPagar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

              selectFragmentCheckout(pagoFragment);
              btnPagar.setVisibility(View.INVISIBLE);
            }
        });

    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("int_value",pos);
        outState.putBoolean("running", running);
        outState.putBoolean("wasRunning", wasRunning);

    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        pos = savedInstanceState.getInt("int_value",0);
    }


    public void onPause() {
        super.onPause();
        wasRunning = running;
        running = false;
    }

    public void onResume() {
        super.onResume();
        if(wasRunning){
            running = true;
        }
    }

    private void selectFragment(Fragment fragmentAction ){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout,fragmentAction);
        fragmentTransaction.commit();
    }

    private void selectFragmentCheckout(Fragment fragmentAction ){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame2,fragmentAction);
        fragmentTransaction.commit();
    }

}


