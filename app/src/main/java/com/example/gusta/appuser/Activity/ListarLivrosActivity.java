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

    private ListView listView;
    private ArrayAdapter<Livros> adapter;
    private ArrayList<Livros> livros;
    private DatabaseReference firebase;
    private ValueEventListener valueEventListenerLivros;
    private Button btFavoritos;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference favoritos;
    LivrosAdapter livrosAdapter;
    DatabaseReference databaseReference;
    String idLivros;
    String codigoLivro;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_livros);


        livros = new ArrayList<>();
        listView = (ListView) findViewById(R.id.listView);
        adapter = new LivrosAdapter(this,0, livros);
        listView.setAdapter(adapter);
      listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
          @Override
          public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
              databaseReference = firebaseDatabase.getReference("addlivros");
              codigoLivro = livrosAdapter.getDados().get(i).getKey();
              databaseReference.addListenerForSingleValueEvent(valueEventListenerFavoritos);

              return true;
          }
      });


        firebase = FireBase.getFireBase().child("addlivros");

        valueEventListenerLivros = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                livros.clear();

                for (DataSnapshot objects : dataSnapshot.getChildren()) {
                    Livros livroNovo = objects.getValue(Livros.class);
                    livros.add(livroNovo);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        btFavoritos = (Button) findViewById(R.id.btFavoritos);

        btFavoritos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ChamaFavoritos = new Intent(ListarLivrosActivity.this, FavoritosActivity.class);
                startActivity(ChamaFavoritos);
            }
        });


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




    ValueEventListener valueEventListenerFavoritos = new ValueEventListener() {

        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            String id = UUID.randomUUID().toString();
            Livros livro = dataSnapshot.getValue(Livros.class);
            MeusLivros meusLivros = new MeusLivros();
            meusLivros.setIdLivro(idLivros);
            meusLivros.setLivro(livro.getLivro());
            meusLivros.setGenero(livro.getGenero());
            meusLivros.setAutor(livro.getAutor());
            meusLivros.setCodigoUsuario(id);
            favoritos = firebaseDatabase.getReference("favoritos");
            favoritos.child(id).setValue(livro);
            Toast.makeText(ListarLivrosActivity.this, "Livro favoritado com sucesso!", Toast.LENGTH_LONG).show();



        }

        @Override
        public void onCancelled(DatabaseError databaseError) {


        }
    };

}

