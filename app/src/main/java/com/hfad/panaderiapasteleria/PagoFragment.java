package com.hfad.panaderiapasteleria;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import com.hfad.panaderiapasteleria.entidades.Galleta.GalletaAtributos;
import com.hfad.panaderiapasteleria.entidades.Pan.PanAtributos;
import com.hfad.panaderiapasteleria.entidades.Pastel.PastelAtributos;
import com.hfad.panaderiapasteleria.entidades.Venta;

public class PagoFragment extends Fragment {
    View view;
    TextView txt;
    TextView lbl_total;
    Button btn_enviar;
    int total = 0 ;
    EnvioFinalFragment envioFinalFragment;

    public PagoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_pago, container, false);
        txt = view.findViewById(R.id.test);
        lbl_total = view.findViewById(R.id.lbl_total);
        btn_enviar = view.findViewById(R.id.btnEnviarPedido);
        envioFinalFragment = new EnvioFinalFragment();

        btn_enviar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                selectFragmentCheckout();
            }
        });
        readProducts();
        readTotal();
        return view;
    }

    private void readProducts(){
        DatabaseHelper databaseHelper = new DatabaseHelper(getActivity());
        SQLiteDatabase database = databaseHelper.getReadableDatabase();

        //LEO PEDIDO DE GALLETAS
        Cursor cursor = databaseHelper.readGalleta(database);
        String info = "";
        String info2 = "", info3="";
        while(cursor.moveToNext())
        {
            @SuppressLint("Range") String cantidad = Integer.toString(cursor.getInt(cursor.getColumnIndex(GalletaAtributos.CANTIDAD)));
            @SuppressLint("Range") String sabor = cursor.getString(cursor.getColumnIndex(GalletaAtributos.SABOR));
            @SuppressLint("Range") String relleno = cursor.getString(cursor.getColumnIndex(GalletaAtributos.RELLENO));
            info = info + "\n\n" +"GALLETA\n"+ "Relleno: "+relleno+"\nSabor: "+sabor+"\nCantidad: "+cantidad;
        }
        txt.setText(info);


        //LEO PEDIDO DE PAN
        cursor = databaseHelper.readPan(database);
        info2 = info + "";
        while(cursor.moveToNext())
        {
            @SuppressLint("Range") String tipo = cursor.getString(cursor.getColumnIndex(PanAtributos.TIPO));
            @SuppressLint("Range") String tam = cursor.getString(cursor.getColumnIndex(PanAtributos.TAM));
            @SuppressLint("Range") String harina = cursor.getString(cursor.getColumnIndex(PanAtributos.HARINA));
            @SuppressLint("Range") String cantidadPan = Integer.toString(cursor.getInt(cursor.getColumnIndex(PanAtributos.CANTIDAD)));
            info2 = info2 + "\n\n" +"PAN\n"+ "Tipo de Pan: "+tipo+"\nTipo de Harina: "+harina+"\nTamaño: "+tam+"\nCantidad: "+cantidadPan;
        }
        txt.setText(info2);


        //LEO PEDIDO DE PASTEL
        cursor = databaseHelper.readPastel(database);
        info3 = info2 + "";
        while(cursor.moveToNext())
        {
            @SuppressLint("Range") String saborPast = cursor.getString(cursor.getColumnIndex(PastelAtributos.SABOR));
            @SuppressLint("Range") String relleno = cursor.getString(cursor.getColumnIndex(PastelAtributos.RELLENO));
            @SuppressLint("Range") String deco = cursor.getString(cursor.getColumnIndex(PastelAtributos.DECO));
            @SuppressLint("Range") String msg = cursor.getString(cursor.getColumnIndex(PastelAtributos.MSG));
            @SuppressLint("Range") String tamPast = cursor.getString(cursor.getColumnIndex(PastelAtributos.TAM));
            info3 = info3 + "\n\n" +"PASTEL\n"+ "Sabor de Pan: "+saborPast+"\nTipo de relleno: "+relleno+"\nDecoración: "+deco+"\nMensaje: "+msg+"\nTamaño de Pastel: "+tamPast;
        }
        info3 = info3 + "\n\n\n\n\n";
        txt.setText(info3);

        databaseHelper.close();
    }

    @SuppressLint("Range")
    private void readTotal(){
        String cantidad;

        DatabaseHelper databaseHelper = new DatabaseHelper(getActivity());
        SQLiteDatabase database = databaseHelper.getReadableDatabase();

        //LEO EL TOTAL DE CADA PRODUCTO Y SE SUMA
        Cursor cursor = databaseHelper.readVenta(database);
        String info = "";
        while(cursor.moveToNext())
        {
            total += cursor.getInt(cursor.getColumnIndex(Venta.VentaAtributos.TOTAL));

        }
        cantidad = String.valueOf(total);
        info = info + "\n\n" + "Total a pagar es: $" + cantidad + ".00";
        lbl_total.setText(info);
    }

    private void selectFragmentCheckout( ){
        getActivity().getSupportFragmentManager().beginTransaction().
                replace(R.id.frame2,envioFinalFragment)
                .addToBackStack(null)
                .commit();
    }
}