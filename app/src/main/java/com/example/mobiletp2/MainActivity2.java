package com.example.mobiletp2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity2 extends AppCompatActivity {

    Intent inten1;
    Button regist;

    Intent inten2;
    Button login;
    EditText edtUsuario, edtContrasenia;
    private dbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inten1 = new Intent(MainActivity2.this, ActivityRegistro.class);
        regist = (Button) findViewById(R.id.btn_registrar);

        inten2 = new Intent(MainActivity2.this, ActivityMenu.class);
        login = (Button) findViewById(R.id.btn_login);

        edtUsuario = findViewById(R.id.edt_usuario);
        edtContrasenia = findViewById(R.id.edt_contrasenia);

        dbHelper = new dbHelper(this, "db1", null, 1);

        regist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(inten1);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String usuario = edtUsuario.getText().toString();
                String contrasenia = edtContrasenia.getText().toString();


                Cursor fila = null;

                try {
                    SQLiteDatabase bd = dbHelper.getWritableDatabase();
                    fila = bd.rawQuery("select nombre_usuario, contrasenia from usuario where nombre_usuario= '" + usuario + "'" +
                            "and  contrasenia = '" + contrasenia + "'", null);

                    if (fila.moveToFirst()) {
                        String usua = fila.getString(0);
                        String pass = fila.getString(1);

                        if (usuario.equals(usua) && contrasenia.equals(pass)) {

                            Toast.makeText(getApplicationContext(), "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show();
                            startActivity(inten2);
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "Error al realizar el inicio de sesión", Toast.LENGTH_SHORT).show();
                }



            }
        });
    }
}