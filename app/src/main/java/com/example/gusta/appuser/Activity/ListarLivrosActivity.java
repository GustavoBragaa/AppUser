package com.example.gusta.appuser.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.gusta.appuser.Adapter.FavoritosAdapter;
import com.example.gusta.appuser.Adapter.LivrosAdapter;
import com.example.gusta.appuser.DAO.FireBase;
import com.example.gusta.appuser.Entidades.Livros;
import com.example.gusta.appuser.Entidades.MeusLivros;
import com.example.gusta.appuser.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.UUID;

public class ListarLivrosActivity extends Activity {

    //Atributos
    private ListView listView;
    private ArrayAdapter<Livros> adapter;
    private ArrayList<Livros> livros;
    private DatabaseReference firebase;
    private ValueEventListener valueEventListenerLivros;
    private Button btFavoritos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_livros);
        firebase = FireBase.getFireBase().child("addlivros");
        //Instanciando novo Array
        livros = new ArrayList<>();
        //Pegando valor da lista do layout
        listView = (ListView) findViewById(R.id.listView);
        //Instanciando novo Adapter Incluindo um array
        adapter = new LivrosAdapter(this, livros);
        //Setando adapter
        listView.setAdapter(adapter);
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Livros livro = livros.get(i);

                final Intent intent = new Intent(ListarLivrosActivity.this, FavoritosActivity.class);


                intent.putExtra("ID", livro.getId());
                intent.putExtra("Livro", livro.getLivro());
                intent.putExtra("Genero", livro.getGenero());
                intent.putExtra("Autor", livro.getAutor());

                btFavoritos = (Button) findViewById(R.id.btFavoritos);
                btFavoritos.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(intent);
                    }
                });
                Toast.makeText(ListarLivrosActivity.this, "Livro favoritado com sucesso", Toast.LENGTH_SHORT).show();


                return true;
            }
        });


        //referenciando de onde vira os dados
        firebase = FireBase.getFireBase().child("addlivros");
        //Metodo que ira colher dados da refencia
        valueEventListenerLivros = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                livros.clear();

                for (DataSnapshot dados : dataSnapshot.getChildren()) {
                    Livros livroNovo = dados.getValue(Livros.class);
                    livros.add(livroNovo);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(ListarLivrosActivity.this, "Houve um Erro: " + databaseError.getCode(), Toast.LENGTH_SHORT).show();


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



}

