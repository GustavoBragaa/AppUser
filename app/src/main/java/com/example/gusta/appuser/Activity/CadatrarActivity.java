package com.example.gusta.appuser.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.gusta.appuser.DAO.FireBase;
import com.example.gusta.appuser.Entidades.Usuarios;
import com.example.gusta.appuser.Helper.Base64Custom;
import com.example.gusta.appuser.Helper.Preferencias;
import com.example.gusta.appuser.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;

public class CadatrarActivity extends AppCompatActivity {
    private EditText edtNome;
    private EditText edtEmail;
    private EditText edtSenha;
    private Button btCdastrar;
    private EditText edtConfirmarSenha;
    private Usuarios usuarios;
    private FirebaseAuth autenticacao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadatrar);


        edtNome = (EditText) findViewById(R.id.edtNome);
        edtEmail = (EditText) findViewById(R.id.edtEmail);
        edtSenha = (EditText) findViewById(R.id.edtSenha);
        edtConfirmarSenha = (EditText) findViewById(R.id.edtConfirmarSenha);
        btCdastrar = (Button) findViewById(R.id.btCadastrar);

        btCdastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(CadatrarActivity.this, " Por favor, Aguarde!", Toast.LENGTH_SHORT).show();
                if (edtSenha.getText().toString().equals(edtConfirmarSenha.getText().toString())) {
                    usuarios = new Usuarios();
                    usuarios.setNome(edtNome.getText().toString());
                    usuarios.setEmail(edtEmail.getText().toString());
                    usuarios.setSenha(edtSenha.getText().toString());

                    cadastrarUsuario();
                } else
                    Toast.makeText(CadatrarActivity.this, " As Senha não correspondem, Reveja!", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void cadastrarUsuario() {

        autenticacao = FireBase.getFirebaseAutenticacao();
        autenticacao.createUserWithEmailAndPassword(
                usuarios.getEmail(),
                usuarios.getSenha()).addOnCompleteListener(CadatrarActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                //Se der certo a criação
                if (task.isSuccessful()) {
                    Toast.makeText(CadatrarActivity.this, "Usuario Cadastrado com sucesso!", Toast.LENGTH_SHORT).show();

                    String identificadorUsuario = Base64Custom.codificarbase64(usuarios.getEmail());
                    FirebaseUser usuarioFirebase = task.getResult().getUser();
                    usuarios.setId(identificadorUsuario);
                    usuarios.salvar();

                    Preferencias preferencias = new Preferencias(CadatrarActivity.this);
                    preferencias.gravarUsuarioPreferencias(identificadorUsuario, usuarios.getNome());

                    abrirLogin();
                } else {

                    String erroExcecao = "";

                    try {

                        throw task.getException();
                    } catch (FirebaseAuthWeakPasswordException e) {
                        erroExcecao = "Por favor, Digite uma senha mais forte, com pelo menos 8 caracteres!";

                    } catch (FirebaseAuthInvalidCredentialsException e) {
                        erroExcecao = "O email digitado é invalido, digite um novo email!";

                    } catch (FirebaseAuthUserCollisionException e) {
                        erroExcecao = "Este email ja existe no sistema!";

                    } catch (NullPointerException e) {
                        erroExcecao = "Algum campo Esta vazio!";

                    } catch (Exception e) {
                        erroExcecao = "Erro ao Efetuar cadastro";
                        e.printStackTrace();

                        Toast.makeText(CadatrarActivity.this, "Erro: " + erroExcecao, Toast.LENGTH_SHORT).show();

                    }
                }
            }

        });
    }


    public void abrirLogin() {
        Intent intent = new Intent(CadatrarActivity.this, LoginActivity.class);
        startActivity(intent);
    }


}
