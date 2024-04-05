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
    private MyDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.editTextEntrarEmail);
        password = findViewById(R.id.editTextEntrarPassword);
        botao_login = findViewById(R.id.botao_entrar);

        dbHelper = new MyDatabaseHelper(this);

        botao_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailText = email.getText().toString();
                String passwordText = password.getText().toString();

                if (dbHelper.verificarLogin(emailText, passwordText)) {
                    Toast.makeText(LoginActivity.this, "Login bem sucedido", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(LoginActivity.this, "Dados inválidos", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
