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
    FirebaseService firebaseService = new FirebaseService();


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        email = findViewById(R.id.editTextRegisterEmail);
        password = findViewById(R.id.editTextRegisterPassword);
        nome = findViewById(R.id.editTextRegisterName);
        botao_registar = findViewById(R.id.botaoRegistar);


        botao_registar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = RegisterActivity.this.email.getText().toString();
                String password = RegisterActivity.this.password.getText().toString();
                String nome = RegisterActivity.this.nome.getText().toString();


                User user = new User(email, password, nome);
                firebaseService.saveUser(user);

                Toast.makeText(RegisterActivity.this, "Conta registrada para " + nome, Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}
