package com.hfad.panaderiapasteleria;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;


public class EnvioFinalFragment extends Fragment {
    View view;
    Button btn_regreso;
    ImageButton btn_contact, btn_map;
    String num = "+526641753355";
    String text = "Quiero conocer el status de mi pedido: ";

    public EnvioFinalFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_envio_final, container, false);

        btn_regreso = view.findViewById(R.id.btnRegresarNewPed);
        btn_regreso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().deleteDatabase("panaderiapasteleria");
                Intent intent = new Intent(getActivity(), DisplayProductosActivity.class);
                startActivity(intent);
            }
        });

        btn_map = view.findViewById(R.id.btn_maps);
        btn_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MapActivity.class);
                startActivity(intent);
            }
        });

        btn_contact = view.findViewById(R.id.btn_contact);
        btn_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean installed = isAppInstalled("com.whatsapp");
                if(installed)
                {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse("https://api.whatsapp.com/send?phone="+num+"&text="+text));
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(getActivity(),"Whatsapp need to install",Toast.LENGTH_SHORT).show();
                }
            }

        });

        return view;
    }

    private boolean isAppInstalled(String s) {
        PackageManager packageManager = getActivity().getPackageManager();
        boolean is_installed;

        try {
            packageManager.getPackageInfo(s,packageManager.GET_ACTIVITIES);
            is_installed = true;
        }catch (PackageManager.NameNotFoundException e){
            is_installed = false;
            e.printStackTrace();
        }
        return is_installed;
    }


}
