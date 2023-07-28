package com.example.mobiletp2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ActivityRegistro extends AppCompatActivity {

    Intent inten2;
    Button volver;
    EditText edtNombre, edtApellido, edtDni, edtEmail, edtUsuario, edtContrasenia;

    Intent intenregistcompletado;
    Button registrado;

    private dbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        edtNombre = findViewById(R.id.edt_nombre);
        edtApellido = findViewById(R.id.edt_apellido);
        edtDni = findViewById(R.id.edt_dni);
        edtEmail = findViewById(R.id.edt_email);
        edtUsuario = findViewById(R.id.edt_usuario1);
        edtContrasenia = findViewById(R.id.edt_contrasenia1);

        dbHelper = new dbHelper(this, "db1", null, 1);

        inten2 = new Intent(ActivityRegistro.this, MainActivity2.class);
        volver = (Button) findViewById(R.id.btn_volver);

        intenregistcompletado = new Intent(ActivityRegistro.this, MainActivity2.class);
        registrado = (Button) findViewById(R.id.btn_registro);

        volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(inten2);

            }
        });

        registrado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtener los valores ingresados en los campos
                String nombre = edtNombre.getText().toString();
                String apellido = edtApellido.getText().toString();
                String dni = edtDni.getText().toString();
                String email = edtEmail.getText().toString();
                String usuario = edtUsuario.getText().toString();
                String contrasenia = edtContrasenia.getText().toString();

                // Validar que todos los campos estén completos
                if (nombre.isEmpty() || apellido.isEmpty() || dni.isEmpty() || email.isEmpty() || usuario.isEmpty() || contrasenia.isEmpty()) {
                    Toast.makeText(ActivityRegistro.this, "Todos los campos son requeridos", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Validacion nombre y apellido
                if (nombre.length() < 2 || apellido.length() < 2) {
                    Toast.makeText(ActivityRegistro.this, "Nombre y apellido deben tener al menos 2 letras", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Validacion dni
                if (dni.length() < 8 || dni.length() > 15) {
                    Toast.makeText(ActivityRegistro.this, "El DNI debe tener entre 8 y 15 caracteres", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Validacion para el formato de email
                if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    Toast.makeText(ActivityRegistro.this, "Formato de email inválido", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Validacion de usuario
                if (usuario.length() < 5 || usuario.length() > 15) {
                    Toast.makeText(ActivityRegistro.this, "El usuario debe tener entre 5 y 15 caracteres", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Validacion de la contrasenia
                if (contrasenia.length() < 4 || contrasenia.length() > 20) {
                    Toast.makeText(ActivityRegistro.this, "La contraseña debe tener entre 4 y 20 caracteres", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Si todas las validaciones son exitosas, insertar en la base de datos
                SQLiteDatabase bd = dbHelper.getWritableDatabase();

                ContentValues registro = new ContentValues();
                registro.put("nombre", nombre);
                registro.put("apellido", apellido);
                registro.put("dni", dni);
                registro.put("email", email);
                registro.put("nombre_usuario", usuario);
                registro.put("contrasenia", contrasenia);

                bd.insert("usuario", null, registro);

                edtNombre.setText("");
                edtApellido.setText("");
                edtDni.setText("");
                edtEmail.setText("");
                edtUsuario.setText("");
                edtContrasenia.setText("");

                bd.close();

                Toast.makeText(ActivityRegistro.this, "Se insertó correctamente en la base de datos", Toast.LENGTH_SHORT).show();
                startActivity(intenregistcompletado);
            }
        });
    }
}