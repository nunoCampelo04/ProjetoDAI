package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private EditText email, password;
    private Button botao_login;
    FirebaseService firebaseService = new FirebaseService();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.editTextEntrarEmail);
        password = findViewById(R.id.editTextEntrarPassword);
        botao_login = findViewById(R.id.botao_entrar);


        botao_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailText = email.getText().toString();
                String passwordText = password.getText().toString();

                firebaseService.loginUser(emailText, passwordText, new FirebaseService.LoginCallback() {
                    @Override
                    public void onLogin(User user) {
                        if (user != null && user.getPassword().equals(passwordText)) {
                            // Login bem-sucedido
                            Toast.makeText(LoginActivity.this, "Login bem-sucedido para " + user.getNome(), Toast.LENGTH_SHORT).show();
                        } else {
                            // Login falhou
                            Toast.makeText(LoginActivity.this, "Credenciais inválidas", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}
