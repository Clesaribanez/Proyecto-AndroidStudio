package com.example.cezar_000.registrotareas;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class ModificarTarea extends AppCompatActivity {
    EditText editTextTitulo, editTextDescripcion;
    Button buttonFecha, buttonHora, buttonGuardar;

    Calendar calendarDate = Calendar.getInstance();
    Calendar calendarTime = Calendar.getInstance();


    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener(){

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            //Se ponen los datos de año, mes y día
            calendarDate.set(Calendar.YEAR, year);
            calendarDate.set(Calendar.MONTH, monthOfYear);
            calendarDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabelDate();
        }
    };

    TimePickerDialog.OnTimeSetListener time = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            calendarTime.set(Calendar.HOUR, hourOfDay);
            calendarTime.set(Calendar.MINUTE, minute);
            updateLabelTime();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_tarea);

        editTextTitulo = (EditText) findViewById(R.id.EditTextTitulo);
        editTextDescripcion = (EditText) findViewById(R.id.EditTextDescripcion);

        buttonFecha   = (Button) findViewById(R.id.buttonFecha);
        buttonHora    = (Button) findViewById(R.id.buttonHora);
        buttonGuardar = (Button) findViewById(R.id.buttonGuardar);

        buttonFecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Se guardan los datos en el botón.
                new DatePickerDialog(ModificarTarea.this, date, calendarDate.get(calendarDate.YEAR),
                        calendarDate.get(calendarDate.MONTH), calendarDate.get(calendarDate.DAY_OF_MONTH)).show();
            }
        });

        buttonHora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new TimePickerDialog(ModificarTarea.this, time, calendarTime.get(calendarTime.HOUR),
                        calendarTime.get(calendarTime.MINUTE), true).show();

            }
        });

        buttonGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardarTarea(editTextTitulo.getText().toString(), editTextDescripcion.getText().toString(),
                        (String) buttonFecha.getText().toString(), (String) buttonHora.getText().toString());
            }
        });

        if(getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void updateLabelDate(){
        String format = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format, Locale.US);

        buttonFecha.setText(simpleDateFormat.format(calendarDate.getTime()));
    }

    private void updateLabelTime(){
        String format = "hh:mm";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format, Locale.US);

        buttonHora.setText(simpleDateFormat.format(calendarTime.getTime()));
    }

    private void guardarTarea(String titulo, String descripcion, String fecha, String hora){
        BaseSQLiteHelper helper = new BaseSQLiteHelper(this, "DB", null, 1);
        SQLiteDatabase db = helper.getWritableDatabase();

        try {
            // Creando un contenedor de valores
            ContentValues c = new ContentValues();
            c.put("TITULO", titulo);
            c.put("DESCRIPCION", descripcion);
            c.put("FECHA", fecha);
            c.put("HORA", hora);
            db.insert("TAREAS", null, c);
            db.close();
            Toast.makeText(this, "Tarea registrada correctamente.", Toast.LENGTH_SHORT).show();
            onBackPressed();
        }catch (Exception e){
            Toast.makeText(this, "Error al registrar tarea" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }


}
