package com.hfad.panaderiapasteleria;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.hfad.panaderiapasteleria.entidades.Galleta;
import com.hfad.panaderiapasteleria.entidades.Pan;
import com.hfad.panaderiapasteleria.entidades.Pastel;
import com.hfad.panaderiapasteleria.entidades.Venta;

public class DatabaseHelper extends SQLiteOpenHelper {

    //NAME OF OUR DATABASE
    private static final String DB_NAME = "panaderiapasteleria";
    private static  final int DB_VERSION = 1; //VERSION OF THE DATABASE

    //CONSTRUCTOR
    DatabaseHelper(Context context){
        super(context,DB_NAME,null,DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createMyDataBase(db,0,"PAN");
        createMyDataBase(db,0,"PASTEL");
        createMyDataBase(db,0,"GALLETA");
        createMyDataBase(db,0,"VENTA");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion,int newversion) {
       db.execSQL("DROP TABLE IF EXISTS PAN");
       db.execSQL("DROP TABLE IF EXISTS PASTEL");
       db.execSQL("DROP TABLE IF EXISTS GALLETA");
       db.execSQL("DROP TABLE IF EXISTS VENTA");
       onCreate(db);
    }


    private void createMyDataBase(SQLiteDatabase db,int oldVersion,String producttype) {

        if (producttype.matches("PAN")) {

            db.execSQL("CREATE TABLE PAN ("
                    + "_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + "TIPO TEXT, "
                    + "TAM TEXT, "
                    + "HARINA TEXT, "
                    + "CANTIDAD INTEGER) ;");

        }

        if(producttype.matches("PASTEL")){

            db.execSQL("CREATE TABLE PASTEL ("
                    + "_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + "RELLENO TEXT, "
                    + "SABOR TEXT, "
                    + "DECO TEXT, "
                    + "MSG TEXT, "
                    + "TAM TEXT) ;");


        }
        if(producttype.matches("GALLETA"))
        {

            db.execSQL("CREATE TABLE GALLETA ("
                    + "_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + "SABOR TEXT, "
                    + "RELLENO TEXT, "
                    + "CANTIDAD INTEGER) ;");


        }
        if(producttype.matches("VENTA"))
        {

            db.execSQL("CREATE TABLE VENTA ("
                    + "_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + "TOTAL INTEGER, "
                    + "id_pan INTEGER, "
                    + "id_pastel INTEGER, "
                    + "id_galleta INTEGER, "
                    + "FOREIGN KEY(id_pan)REFERENCES PAN(_id), "
                    + "FOREIGN KEY(id_pastel)REFERENCES PASTEL(_id), "
                    + "FOREIGN KEY(id_galleta)REFERENCES GALLETA(_id)) ;");


        }


    }

    public static void insertPastel(SQLiteDatabase db,String sabor ,
                                     String relleno,String decoracion,String mensaje,String tam){
        ContentValues pastelValues = new ContentValues();
        pastelValues.put(Pastel.PastelAtributos.SABOR,sabor);
        pastelValues.put(Pastel.PastelAtributos.RELLENO,relleno);
        pastelValues.put(Pastel.PastelAtributos.DECO,decoracion);
        pastelValues.put(Pastel.PastelAtributos.MSG,mensaje);
        pastelValues.put(Pastel.PastelAtributos.TAM,tam);

        db.insert(Pastel.PastelAtributos.TABLE_NAME,null,pastelValues);

    }


    public void insertPan(SQLiteDatabase db,String tipo ,
                                     String tam, String  harina,int cantidad){
        ContentValues panValues = new ContentValues();
        panValues.put(Pan.PanAtributos.TIPO,tipo);
        panValues.put(Pan.PanAtributos.TAM,tam);
        panValues.put(Pan.PanAtributos.HARINA,harina);
        panValues.put(Pan.PanAtributos.CANTIDAD,cantidad);

        db.insert(Pan.PanAtributos.TABLE_NAME,null,panValues);

    }

    public void insertGalleta(SQLiteDatabase db,String sabor,
                                 String relleno,int cantidad){
        ContentValues galletaValues = new ContentValues();
        galletaValues.put(Galleta.GalletaAtributos.SABOR,sabor);
        galletaValues.put(Galleta.GalletaAtributos.RELLENO,relleno);
        galletaValues.put(Galleta.GalletaAtributos.CANTIDAD,cantidad);

        db.insert(Galleta.GalletaAtributos.TABLE_NAME,null,galletaValues);
        Log.d("Database Operations","ONE RAW INSERTED ...");

    }

    public void insertVenta(SQLiteDatabase db,int total){
        ContentValues ventaValues = new ContentValues();

        ventaValues.put(Venta.VentaAtributos.TOTAL,total);

        db.insert(Venta.VentaAtributos.TABLE_NAME,null,ventaValues);
        Log.d("Database Operations","ONE RAW INSERTED ...");

    }

   public Cursor readGalleta(SQLiteDatabase database)
   {
       String[] atributos = {Galleta.GalletaAtributos.SABOR, Galleta.GalletaAtributos.RELLENO, Galleta.GalletaAtributos.CANTIDAD};
       Cursor cursor = database.query(Galleta.GalletaAtributos.TABLE_NAME,
               atributos,null,null,null,null,null);
       return cursor;
   }

    public Cursor readPastel(SQLiteDatabase database)
    {
        String[] atributos = {Pastel.PastelAtributos.SABOR, Pastel.PastelAtributos.RELLENO,
                Pastel.PastelAtributos.DECO, Pastel.PastelAtributos.MSG, Pastel.PastelAtributos.TAM};
        Cursor cursor = database.query(Pastel.PastelAtributos.TABLE_NAME,
                atributos,null,null,null,null,null);
        return cursor;
    }

    public Cursor readPan(SQLiteDatabase database)
    {
        String[] atributos = {Pan.PanAtributos.TIPO, Pan.PanAtributos.TAM, Pan.PanAtributos.HARINA,
                                Pan.PanAtributos.CANTIDAD};
        Cursor cursor = database.query(Pan.PanAtributos.TABLE_NAME,
                atributos,null,null,null,null,null);
        return cursor;
    }

    public Cursor readVenta(SQLiteDatabase database)
    {
        String[] atributos = {Venta.VentaAtributos.TOTAL};
        Cursor cursor = database.query(Venta.VentaAtributos.TABLE_NAME,
                atributos,null,null,null,null,null);
        return cursor;
    }



}

