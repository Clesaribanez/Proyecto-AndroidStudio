package com.example.cezar_000.registrotareas;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MostrarTarea extends AppCompatActivity {
    EditText Titulo, Descripcion, Fecha, Hora;
    Button Modificar, Eliminar;
    int ID;
    String titulo, descripcion, fecha, hora, id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_tarea);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            ID = bundle.getInt("ID");

            id = bundle.getString("ID");
            titulo = bundle.getString("TITULO");
            descripcion = bundle.getString("DESCRIPCION");
            fecha = bundle.getString("FECHA");
            hora = bundle.getString("HORA");

        }
        Titulo = (EditText) findViewById(R.id.EditTextTitulo);
        Descripcion = (EditText) findViewById(R.id.EditTextDescripcion);
        Fecha = (EditText) findViewById(R.id.EditTextFecha);
        Hora = (EditText) findViewById(R.id.EditTextHora);

        Titulo.setText(titulo);
        Descripcion.setText(descripcion);
        Fecha.setText(fecha);
        Hora.setText(hora);

        Modificar = (Button) findViewById(R.id.buttonModificar);
        Modificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Modificar(id, Titulo.getText().toString(), Descripcion.getText().toString(), Fecha.getText().toString(), Hora.getText().toString());
                onBackPressed();
            }
        });

        Eliminar = (Button) findViewById(R.id.buttonEliminar);
        Eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Eliminar(id);
                onBackPressed();
            }
        });

    }

    private void Modificar(String ID, String Titulo, String Descripcion, String Fecha, String Hora){
        BaseSQLiteHelper helper = new BaseSQLiteHelper(this, "DB", null, 1);
        SQLiteDatabase db = helper.getWritableDatabase();

        String sql = "UPDATE TAREAS SET TITULO='" + Titulo +"', DESCRIPCION='" + Descripcion + "', FECHA='" + Fecha + "', HORA='" + Hora + "' WHERE ID='" + ID + "'";
        db.execSQL(sql);
        db.close();
    }

    private void Eliminar(String ID){
        BaseSQLiteHelper helper = new BaseSQLiteHelper(this, "DB", null, 1);
        SQLiteDatabase db = helper.getWritableDatabase();

        String sql = "DELETE FROM TAREAS WHERE ID='" + ID + "'";
        db.execSQL(sql);
        db.close();
    }
}
