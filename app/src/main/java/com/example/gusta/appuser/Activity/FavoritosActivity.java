package com.example.gusta.appuser.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.gusta.appuser.Adapter.FavoritosAdapter;
import com.example.gusta.appuser.Adapter.LivrosAdapter;
import com.example.gusta.appuser.DAO.FireBase;
import com.example.gusta.appuser.Entidades.Livros;
import com.example.gusta.appuser.Entidades.MeusLivros;
import com.example.gusta.appuser.Entidades.Usuarios;
import com.example.gusta.appuser.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.EventListener;
import java.util.UUID;

public class FavoritosActivity extends AppCompatActivity {


    private ArrayList<MeusLivros> meusLivrosArray;
    private DatabaseReference firebase;
    private ValueEventListener valueEventListenerLivros;
    private ListView lvFavoritos;
    private ArrayAdapter<MeusLivros> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favoritos);

        Intent intent = getIntent();

        String id = intent.getStringExtra("ID");
        String livro = intent.getStringExtra("Livro");
        String genero = intent.getStringExtra("Genero");
        String autor = intent.getStringExtra("Autor");


        //Instanciando Livros
        MeusLivros meuslivros = new MeusLivros();
        //Enviando dados recebidos pelos atributos do layout

        meuslivros.setLivro(livro);
        meuslivros.setGenero(genero);
        meuslivros.setAutor(autor);


        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        String user = currentFirebaseUser.getUid();

        firebase = FireBase.getFireBase().child("favoritos").child(user);

        firebase.child(id).setValue(meuslivros);


        //Instanciando novo Array
        meusLivrosArray = new ArrayList<>();
        //Pegando valor da lista do layout
        lvFavoritos = (ListView) findViewById(R.id.lvFavoritos);
        //Instanciando novo Adapter Incluindo um array
        adapter = new FavoritosAdapter(this, meusLivrosArray);
        //Setando adapter
        lvFavoritos.setAdapter(adapter);


        valueEventListenerLivros = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                meusLivrosArray.clear();

                for (DataSnapshot dados : dataSnapshot.getChildren()) {


                    MeusLivros meuNovoLivro = dados.getValue(MeusLivros.class);
                    meusLivrosArray.add(meuNovoLivro);

                }
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(FavoritosActivity.this, "Houve um Erro: " + databaseError.getCode(), Toast.LENGTH_SHORT).show();


            }
        };


    }

    @Override
    protected void onStop() {
        super.onStop();
        firebase.removeEventListener(valueEventListenerLivros);
    }

    @Override
    protected void onStart() {
        super.onStart();
        firebase.addValueEventListener(valueEventListenerLivros);
    }


    public static String EncodeString(String string) {
        return string.replace(".", ",");
    }

}





