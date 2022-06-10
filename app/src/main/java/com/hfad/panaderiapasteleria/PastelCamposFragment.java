package com.hfad.panaderiapasteleria;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.hfad.panaderiapasteleria.entidades.Venta;


public class PastelCamposFragment extends Fragment {
    View view;
    String saborPan,relleno,deco,msg,tam;
    Button btnAgregar;
    int subtotal,precio = 300,cantidad_int;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_pastel_campos, container, false);

        //OBTENGO VALOR DE SABOR DEL PAN
        Spinner spSaborPan = view.findViewById(R.id.spinnersaborPan);
        spSaborPan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                saborPan =  adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        //OBTENGO VALOR DEL RELLENO
        Spinner spRelleno = view.findViewById(R.id.spinnerrellenos);
        spRelleno.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                relleno =  adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        //OBTENGO VALOR DE LA DECORACION
        Spinner spDeco = view.findViewById(R.id.spinnerDeco);
        spDeco.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                deco =  adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        //OBTENGO VALOR DEL MENSAJE ESCRITO EN EL PASTEL
        Spinner spMsg = view.findViewById(R.id.spinnerMensaje);
        spMsg.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                msg =  adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        //OBTENGO VALOR DEL TAMAÑO DEL PASTEL
        Spinner spTam = view.findViewById(R.id.spinnerTam);
        spTam.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                tam =  adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        TextView txt = (TextView) view.findViewById(R.id.subtotal_pastel);
        btnAgregar = (Button) view.findViewById(R.id.agregarPastel);
        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseHelper databaseHelper = new DatabaseHelper(getActivity());
                SQLiteDatabase database = databaseHelper.getWritableDatabase();

                databaseHelper.insertPastel(database,saborPan,relleno,deco,msg,tam);
                readSubtotal();
                subtotal = subtotal + precio;
                txt.setText("Precio: $ "+String.valueOf(subtotal).toString()+".00");
                databaseHelper.insertVenta(database,subtotal);
                Toast.makeText(getActivity(),"Pastel Agregado..",Toast.LENGTH_SHORT).show();
                databaseHelper.close();
            }
        });

        return view;
    }

    @SuppressLint("Range")
    private void readSubtotal(){
        int subtotal_base;
        DatabaseHelper databaseHelper = new DatabaseHelper(getActivity());
        SQLiteDatabase database = databaseHelper.getReadableDatabase();

        //LEO subtotal de la venta
        Cursor cursor = databaseHelper.readVenta(database);

        if(cursor.getPosition() > 0){
            cursor.moveToLast();
            subtotal_base = cursor.getInt(cursor.getColumnIndex(Venta.VentaAtributos.TOTAL));
        }
        else
        {
            subtotal_base = 0;
        }

        subtotal += subtotal_base;
    }
}