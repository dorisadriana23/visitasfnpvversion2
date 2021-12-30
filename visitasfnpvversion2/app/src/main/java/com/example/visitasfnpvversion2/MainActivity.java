package com.example.visitasfnpvversion2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "";
    EditText editemail, editcontrasena;
    private Button btninicio,Registroboton;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firebaseAuth = firebaseAuth.getInstance();


        editemail = (EditText) findViewById(R.id.emailMain);
        editcontrasena = (EditText) findViewById(R.id.passwordmain);
        btninicio = (Button) findViewById(R.id.btninicio);
        btninicio.setOnClickListener(this);
    }
/*
        public void pasarregistrosusuarios(View view) {
            Intent pasar=new Intent(Login_users.this, Sing_up.class);
            startActivity(pasar);
        }

        public void pasarregistrosusuariosu(View view) {
            Intent pasar=new Intent(Login_users.this, Composicio_Familiar.class);
            startActivity(pasar);
        }
*/
        private void inicialize() {
            firebaseAuth = FirebaseAuth.getInstance();
            FirebaseAuth.AuthStateListener authStateListener = new FirebaseAuth.AuthStateListener() {
                @Override
                public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                    FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                    if (firebaseUser != null) {
                        Log.w(TAG, "onAuthStateChanged - Logueado");

                    } else {
                        Log.w(TAG, "onAuthStateChanged - Cerro sesion");
                    }
                }
            };
        }

        private void Iniciosesion() {


            String EmailMain = editemail.getText().toString().trim();
            String contrasenaMain = editcontrasena.getText().toString().trim();

            if (TextUtils.isEmpty(EmailMain)) {
                Toast.makeText(this, "se debe ingresar un email", Toast.LENGTH_SHORT).show();
                return;

            }
            if (TextUtils.isEmpty(contrasenaMain)) {
                Toast.makeText(this, "se debe ingresar una contraseña", Toast.LENGTH_SHORT).show();
                return;
            }
            //loguear usuario
            //noinspection Convert2Lambda
            firebaseAuth.signInWithEmailAndPassword(EmailMain, contrasenaMain)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(MainActivity.this, "Bienvenido", Toast.LENGTH_SHORT).show();
                                Intent intencion = new Intent(getApplication(), DatosGeneralesAS.class);
                                intencion.putExtra(DatosGeneralesAS.user, EmailMain);
                                startActivity(intencion);

                            } else {
                                if (task.getException() instanceof FirebaseAuthUserCollisionException) {//se presenta una colision

                                    Toast.makeText(MainActivity.this, "Ese usuario ya existe", Toast.LENGTH_SHORT).show();

                                } else {
                                    Toast.makeText(MainActivity.this, "No se pudo registrar el usuari", Toast.LENGTH_LONG).show();

                                }
                            }

                        }

                    });
        }

        @Override
        public void onClick(View view) {

            switch (view.getId()) {
                case R.id.Registroboton:
                    setBtnregistrar();
                    break;
                case R.id.btninicio:
                    Iniciosesion();
                    break;

            }
        }

        private void setBtnregistrar() {

        }


    public void registro(View view) {
        Intent pasar=new Intent(MainActivity.this, Registro.class);
        startActivity(pasar);
    }
}


