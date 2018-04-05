package com.example.gusta.appuser.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gusta.appuser.DAO.FireBase;
import com.example.gusta.appuser.Entidades.Usuarios;
import com.example.gusta.appuser.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    private EditText edtEmail;
    private EditText edtSenha;
    private Button btLogin;
   private TextView txtNovoCadastro;
    private FirebaseAuth autenticacao;
    private Usuarios usuarios;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        //Recebendo id's
        edtEmail = (EditText) findViewById(R.id.edtEmail);
        edtSenha = (EditText) findViewById(R.id.edtSenha);
        btLogin = (Button) findViewById(R.id.btLogin);


        txtNovoCadastro = (TextView) findViewById(R.id.txtNovoCadastro);
        txtNovoCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ChamaCadastrar = new Intent(LoginActivity.this, CadatrarActivity.class);
                startActivity(ChamaCadastrar);
            }
        });

        //Criando evento de click para o botão
        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(LoginActivity.this, "Aguarde um Momento.", Toast.LENGTH_SHORT).show();

                //Se email e senha não forem vazios
                if (!edtEmail.getText().toString().equals("") && !edtSenha.getText().toString().equals("")) {

                    //Instanciando Objeto
                    usuarios = new Usuarios();
                    //Mandando valores para classe Usuarios
                    usuarios.setEmail(edtEmail.getText().toString());
                    usuarios.setSenha(edtSenha.getText().toString());
                    //Chamando Metodo
                    validarLogin();

                } else {
                    //Se forem vazios
                    Toast.makeText(LoginActivity.this, "Campo E-mail ou Senha esta vazio, por favor, Preencha!", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    //autenticação de email e senha
    private void validarLogin() {
        autenticacao = FireBase.getFirebaseAutenticacao();
        autenticacao.signInWithEmailAndPassword(usuarios.getEmail(), usuarios.getSenha()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {

                    ChamarMain();
                    Toast.makeText(LoginActivity.this, "Login efetuado com sucesso!", Toast.LENGTH_SHORT).show();


                }else{
                    Toast.makeText(LoginActivity.this, "E-mail ou senha invalidos! ", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

    public void ChamarMain(){

        Intent ChamaMain = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(ChamaMain);
    }



}
