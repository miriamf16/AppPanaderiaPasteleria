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
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.hfad.panaderiapasteleria.entidades.Venta;


public class PanDulceCamposFragment extends Fragment {
    View view;
    Button btnAgregar;
    String tipo,harina,tam,cantidad;
    int subtotal,precio = 15 ,cantidad_int;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_pan_dulce_campos, container, false);

        //OBTENGO VALOR DEL TIPO DE PAN
        Spinner spPan = view.findViewById(R.id.spinnertipoPan);
        spPan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                tipo =  adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        //OBTENGO VALOR DE LA HARINA
        Spinner spHarina = view.findViewById(R.id.spinnerHarina);
        spHarina.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                harina =  adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        //OBTENGO VALOR DEL TAMAÑO
        Spinner spTam = view.findViewById(R.id.spinnerTamPan);
        spTam.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                tam =  adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        //OBTENGO LA CANTIDAD
        EditText input = (EditText) view.findViewById(R.id.cantidadPanDulce);
        TextView txt = (TextView) view.findViewById(R.id.subtotal_pan);


        btnAgregar = (Button) view.findViewById(R.id.agregarPanDulce);
        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseHelper databaseHelper = new DatabaseHelper(getActivity());
                SQLiteDatabase database = databaseHelper.getWritableDatabase();

                cantidad = input.getText().toString();
                databaseHelper.insertPan(database,tipo,tam,harina,Integer.parseInt(cantidad));
                cantidad_int = Integer.parseInt(cantidad);
                readSubtotal();
                subtotal = subtotal + (cantidad_int * precio);
                txt.setText("Precio: $ "+String.valueOf(subtotal).toString()+".00");
                databaseHelper.insertVenta(database,subtotal);
                Toast.makeText(getActivity(),"Pan Agregado..",Toast.LENGTH_SHORT).show();
                input.setText("");
                databaseHelper.close();

            }
        });

        return view ;
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