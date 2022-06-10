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

import org.w3c.dom.Text;

public class GalletasCamposFragment extends Fragment {
    View vista;
    Button btnAgregar;
    String sabor,relleno,cantidad;
    int subtotal= 0,precio = 20,cantidad_int;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         vista = inflater.inflate(R.layout.fragment_galletas_campos, container, false);

        //OBTENGO VALOR DEL SABOR
        Spinner spSabor = vista.findViewById(R.id.spinnersabores);
        spSabor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                sabor =  adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //OBTENGO VALOR DEL RELLENO
        Spinner spRelleno = vista.findViewById(R.id.spinnerrellenos);
        spRelleno.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                relleno =  adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //OBTENGO LA CANTIDAD
        EditText input = (EditText) vista.findViewById(R.id.cantidadGalletas);
        TextView txt = (TextView) vista.findViewById(R.id.subtotal);


        btnAgregar = (Button) vista.findViewById(R.id.agregarGalleta);
        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseHelper databaseHelper = new DatabaseHelper(getActivity());
                SQLiteDatabase database = databaseHelper.getWritableDatabase();

                cantidad = input.getText().toString();
                databaseHelper.insertGalleta(database,sabor,relleno,Integer.parseInt(cantidad));
                cantidad_int = Integer.parseInt(cantidad);
                readSubtotal();
                subtotal = subtotal + (cantidad_int * precio);
                txt.setText("Precio: $ "+String.valueOf(subtotal).toString()+".00");
                databaseHelper.insertVenta(database,subtotal);
                Toast.makeText(getActivity(),"Galleta Agregada..",Toast.LENGTH_SHORT).show();
                input.setText("");

                databaseHelper.close();
            }
        });

        return vista;
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