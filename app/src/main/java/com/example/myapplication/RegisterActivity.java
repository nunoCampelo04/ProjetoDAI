package com.example.myapplication;

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

public class RegisterActivity extends AppCompatActivity {

    private EditText nome, email, password;
    private Button botao_registar;
    private FirebaseAuth mAuth;
    FirebaseService firebaseService = new FirebaseService();

    User user;
    String finalEmailText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Inicializa o Firebase Authentication
        mAuth = FirebaseAuth.getInstance();

        email = findViewById(R.id.editTextRegisterEmail);
        password = findViewById(R.id.editTextRegisterPassword);
        nome = findViewById(R.id.editTextRegisterName);
        botao_registar = findViewById(R.id.botaoRegistar);

        botao_registar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });
    }

    private void registerUser() {
        String emailText = email.getText().toString();
        String passwordText = password.getText().toString();
        String nomeText = nome.getText().toString();

        if (emailText.isEmpty() || passwordText.isEmpty() || nomeText.isEmpty()) {
            Toast.makeText(RegisterActivity.this, "Por favor, preencha todos os campos.", Toast.LENGTH_SHORT).show();
            return;
        }

        //guardar so ate ao arroba
        int indexOfAt = emailText.indexOf("@");
        if (indexOfAt != -1) { // Se "@" encontrado
            finalEmailText = emailText.substring(0, indexOfAt);
        }

        mAuth.createUserWithEmailAndPassword(emailText, passwordText)
                .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Registo bem-sucedido
                            FirebaseUser firebaseUser = mAuth.getCurrentUser();
                            if (firebaseUser != null) {
                                user = new User(finalEmailText, passwordText, nomeText);

                                firebaseService.saveUser(user);
                            }
                            Toast.makeText(RegisterActivity.this, "Conta registrada para " +finalEmailText, Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            // Registo falhou
                            String errorMessage = "Falha no registro.";
                            if (task.getException() != null) {
                                // Mensagens de erro
                                String errorCode = ((FirebaseAuthException) task.getException()).getErrorCode();
                                switch (errorCode) {
                                    case "ERROR_INVALID_EMAIL":
                                        errorMessage = "Endereço de e-mail inválido!";
                                        break;
                                    case "ERROR_WEAK_PASSWORD":
                                        errorMessage = "Senha fraca. A senha deve ter pelo menos 6 caracteres!";
                                        break;
                                    case "ERROR_EMAIL_ALREADY_IN_USE":
                                        errorMessage = "Este Endereço de e-mail já está a ser utilizado!";
                                }
                            }
                            Toast.makeText(RegisterActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

}
