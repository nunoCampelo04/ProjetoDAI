package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private EditText email, password;
    private Button botao_login;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        email = findViewById(R.id.editTextEntrarEmail);
        password = findViewById(R.id.editTextEntrarPassword);
        botao_login = findViewById(R.id.botao_entrar);

        botao_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailText = email.getText().toString();
                String passwordText = password.getText().toString();

                login(emailText, passwordText);
            }
        });
    }

    private void login(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(LoginActivity.this, "Login bem-sucedido para " + user.getEmail(), Toast.LENGTH_SHORT).show();
                            openMapaActivity();
                        } else {
                            String errorMessage = "Falha no login.";
                            if (task.getException() != null) {
                                // Mensagens de erro
                                String errorCode = ((FirebaseAuthException) task.getException()).getErrorCode();
                                switch (errorCode) {
                                    case "ERROR_INVALID_EMAIL":
                                        errorMessage = "Endereço de e-mail inválido!";
                                        break;
                                    case "ERROR_WRONG_PASSWORD":
                                        errorMessage = "Palavra-passe incorreta. Tente novamente!";
                                        break;
                                    case "ERROR_INVALID_CUSTOM_TOKEN":
                                        errorMessage = "Autenticação inválida!";
                                        break;
                                }
                            }
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void openMapaActivity(){
        Intent intent = new Intent(LoginActivity.this, MapaActivity.class);
        startActivity(intent);
        finish();
    }
}
