package com.example.myapplication;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    private EditText nome, email, password;
    private Button botao_registar;
    private MyDatabaseHelper dbHelper;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        email = findViewById(R.id.editTextRegisterEmail);
        password = findViewById(R.id.editTextRegisterPassword);
        nome = findViewById(R.id.editTextRegisterName);
        botao_registar = findViewById(R.id.botaoRegistar);

        dbHelper = new MyDatabaseHelper(this);

        botao_registar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = RegisterActivity.this.email.getText().toString();
                String password = RegisterActivity.this.password.getText().toString();
                String nome = RegisterActivity.this.nome.getText().toString();

                if (dbHelper.existeUser(email)) {
                    Toast.makeText(RegisterActivity.this, "Já existe uma conta registada com este email", Toast.LENGTH_SHORT).show();
                    return; // Impede a criação da conta
                }

                User user = new User(email, password, nome);

                long newRowId = dbHelper.inserirUser(user);
                if (newRowId != -1) {
                    Toast.makeText(RegisterActivity.this, "Conta registada para " + email, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(RegisterActivity.this, "Falha ao registar a conta", Toast.LENGTH_SHORT).show();
                }

                finish();
            }
        });
    }
}
