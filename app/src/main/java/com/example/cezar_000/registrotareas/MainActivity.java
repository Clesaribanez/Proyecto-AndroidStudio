package com.example.cezar_000.registrotareas;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    FloatingActionButton Agregar;

    ListView listView;
    ArrayList<String> Lista;

    @Override
    protected void onPostResume() {
        super.onPostResume();
        CargarLista();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        listView = (ListView) findViewById(R.id.listView);
        CargarLista();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ArrayList<String> datos = new ArrayList<String>();
                BaseSQLiteHelper helper = new BaseSQLiteHelper(MainActivity.this, "DB", null, 1);
                SQLiteDatabase db = helper.getReadableDatabase();
                id = id + 1;
                String sql = "SELECT ID, TITULO, DESCRIPCION, FECHA, HORA FROM TAREAS WHERE ID = '" + id + "'";
                Cursor c = db.rawQuery(sql, null);
                if(c.moveToFirst()){
                        String ID = c.getString(0);
                        String titulo = c.getString(1);
                        String descripcion = c.getString(2);
                        String fecha = c.getString(3);
                        String hora = c.getString(4);

                        Intent intent = new Intent(MainActivity.this, MostrarTarea.class);
                        intent.putExtra("ID", ID);
                        intent.putExtra("TITULO", titulo);
                        intent.putExtra("DESCRIPCION", descripcion);
                        intent.putExtra("FECHA", fecha);
                        intent.putExtra("HORA", hora);
                        startActivity(intent);
                }
                db.close();
            }
        });

        Agregar = (FloatingActionButton) findViewById(R.id.fab);
        Agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), AgregarTarea.class);
                startActivity(intent);
            }
        });
    }

    private void CargarLista(){
        Lista = ListadeTareas();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, Lista);
        listView.setAdapter(adapter);

    }

    private ArrayList<String> ListadeTareas(){
        ArrayList<String> datos = new ArrayList<String>();
        BaseSQLiteHelper helper = new BaseSQLiteHelper(this, "DB", null, 1);
        SQLiteDatabase db = helper.getReadableDatabase();

        String sql = "SELECT ID, TITULO, DESCRIPCION, FECHA, HORA FROM TAREAS";
        Cursor c = db.rawQuery(sql, null);
        if(c.moveToFirst()){
            do{
                String linea = c.getInt(0) +" - "+  c.getString(1);
                datos.add(linea);
            }while(c.moveToNext());
        }
        db.close();
        return datos;
    }

}
