package com.example.mobiletp2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ActivityMenu extends AppCompatActivity {

    private dbHelper dbHelper;
    Button agregar,buscar,eliminar, desloguear,actualizar;
    EditText edtId, edtNombre, edtApellido, edtDni, edtEmail, edtDireccion, edtCodigoPostal;
    Intent inteninicio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        edtId = findViewById(R.id.edt_idm);
        edtNombre = findViewById(R.id.edt_nombrem);
        edtApellido = findViewById(R.id.edt_apellidom);
        edtDni = findViewById(R.id.edt_dnim);
        edtEmail = findViewById(R.id.edt_emailm);
        edtDireccion = findViewById(R.id.edt_direccionm);
        edtCodigoPostal = findViewById(R.id.edt_codigopostalm);

        inteninicio = new Intent(ActivityMenu.this, MainActivity2.class);

        dbHelper = new dbHelper(this, "db1", null, 1);

        agregar = (Button) findViewById(R.id.btn_agregarm);
        buscar = (Button) findViewById(R.id.btn_buscarm);
        eliminar = (Button) findViewById(R.id.btn_eliminarm);
        actualizar = (Button) findViewById(R.id.btn_actualizarm);
        desloguear = (Button) findViewById(R.id.btn_desloguear);


        agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String nombre = edtNombre.getText().toString();
                String apellido = edtApellido.getText().toString();
                String dni = edtDni.getText().toString();
                String email = edtEmail.getText().toString();
                String direccion = edtDireccion.getText().toString();
                String postal = edtCodigoPostal.getText().toString();

                // Validar que todos los campos estén completos
                if (nombre.isEmpty() || apellido.isEmpty() || dni.isEmpty() || email.isEmpty() || direccion.isEmpty() || postal.isEmpty()) {
                    Toast.makeText(ActivityMenu.this, "Todos los campos menos id son requeridos", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Validacion nombre y apellido
                if (nombre.length() < 2 || apellido.length() < 2) {
                    Toast.makeText(ActivityMenu.this, "Nombre y apellido deben tener al menos 2 letras", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Validacion dni
                if (dni.length() < 8 || dni.length() > 15) {
                    Toast.makeText(ActivityMenu.this, "El DNI debe tener entre 8 y 15 caracteres", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Validacion para el formato de email
                if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    Toast.makeText(ActivityMenu.this, "Formato de email inválido", Toast.LENGTH_SHORT).show();
                    return;
                }

                SQLiteDatabase bd = dbHelper.getWritableDatabase();

                ContentValues registro = new ContentValues();
                registro.put("nombre", nombre);
                registro.put("apellido", apellido);
                registro.put("dni", dni);
                registro.put("email", email);
                registro.put("direccion", direccion);
                registro.put("codigo_postal", postal);

                bd.insert("empleado", null, registro);

                edtNombre.setText("");
                edtApellido.setText("");
                edtDni.setText("");
                edtEmail.setText("");
                edtDireccion.setText("");
                edtCodigoPostal.setText("");

                bd.close();

                Toast.makeText(ActivityMenu.this, "Se agrego correctamente en la base de datos", Toast.LENGTH_SHORT).show();

            }
        });
        buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SQLiteDatabase bd = dbHelper.getWritableDatabase();
                String  id = edtId.getText().toString();
                Cursor fila = bd.rawQuery(
                "SELECT id, nombre, apellido, dni, email, direccion, codigo_postal from empleado where id= '" + id + "'" , null );

                if (fila.moveToFirst()){
                    edtNombre.setText(fila.getString(1));
                    edtApellido.setText(fila.getString(2));
                    edtDni.setText(fila.getString(3));
                    edtEmail.setText(fila.getString(4));
                    edtDireccion.setText(fila.getString(5));
                    edtCodigoPostal.setText(fila.getString(6));

                }else{
                    Toast.makeText(ActivityMenu.this,"No existe un empleado con ese id" ,Toast.LENGTH_SHORT).show();
                    bd.close();
                }
            }
        });

        eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String id = edtId.getText().toString();

                if (id.isEmpty()) {
                    Toast.makeText(ActivityMenu.this, "Ingresa un ID válido", Toast.LENGTH_SHORT).show();
                    return;
                }

                SQLiteDatabase bd = dbHelper.getWritableDatabase();
                String Clause = "id = '" + id + "'";
                int registrosEliminados = bd.delete("empleado", Clause, null);
                bd.close();

                if (registrosEliminados > 0) {
                    Toast.makeText(ActivityMenu.this, "Empleado eliminado correctamente", Toast.LENGTH_SHORT).show();
                    edtNombre.setText("");
                    edtApellido.setText("");
                    edtDni.setText("");
                    edtEmail.setText("");
                    edtDireccion.setText("");
                    edtCodigoPostal.setText("");
                } else {
                    Toast.makeText(ActivityMenu.this, "No existe un empleado con ese ID", Toast.LENGTH_SHORT).show();
                }
            }
        });

        actualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SQLiteDatabase bd = dbHelper.getWritableDatabase();

                String id = edtId.getText().toString();
                String Clause = "id = '" + id + "'";

                String nombre = edtNombre.getText().toString();
                String apellido = edtApellido.getText().toString();
                String dni = edtDni.getText().toString();
                String email = edtEmail.getText().toString();
                String direccion = edtDireccion.getText().toString();
                String postal = edtCodigoPostal.getText().toString();

                // Validar que todos los campos estén completos
                if (nombre.isEmpty() || apellido.isEmpty() || dni.isEmpty() || email.isEmpty() || direccion.isEmpty() || postal.isEmpty()) {
                    Toast.makeText(ActivityMenu.this, "Todos los campos menos id son requeridos", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Validacion nombre y apellido
                if (nombre.length() < 2 || apellido.length() < 2) {
                    Toast.makeText(ActivityMenu.this, "Nombre y apellido deben tener al menos 2 letras", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Validacion dni
                if (dni.length() < 8 || dni.length() > 15) {
                    Toast.makeText(ActivityMenu.this, "El DNI debe tener entre 8 y 15 caracteres", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Validacion para el formato de email
                if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    Toast.makeText(ActivityMenu.this, "Formato de email inválido", Toast.LENGTH_SHORT).show();
                    return;
                }

                ContentValues datosActualizados = new ContentValues();
                datosActualizados.put("nombre", nombre);
                datosActualizados.put("apellido", apellido);
                datosActualizados.put("dni", dni);
                datosActualizados.put("email", email);
                datosActualizados.put("direccion", direccion);
                datosActualizados.put("codigo_postal", postal);

                int resultado = bd.update("empleado", datosActualizados, Clause, null);

                if (resultado == 1) {
                    Toast.makeText(ActivityMenu.this, "Se actualizó exitosamente los datos del empleado", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ActivityMenu.this, "No se encontró el registro del empleado con el id ingresado", Toast.LENGTH_SHORT).show();
                }
            }
        });

        desloguear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(inteninicio);
            }
        });



    }
    public void buscarmapa(View view){

        edtDireccion = findViewById(R.id.edt_direccionm);

        String dir = edtDireccion.getText().toString();
        Uri map = Uri.parse("geo:0,0?q=" + Uri.encode(dir));
        Intent intent = new Intent(Intent.ACTION_VIEW, map);
        startActivity(intent);

    }

}