package com.example.gusta.appuser.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.gusta.appuser.Adapter.LivrosAdapter;
import com.example.gusta.appuser.DAO.FireBase;
import com.example.gusta.appuser.Entidades.Livros;
import com.example.gusta.appuser.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ListarLivrosActivity extends AppCompatActivity {

    private ListView listView;
    private ArrayAdapter<Livros> adapter;
    private ArrayList<Livros> livros;
    private DatabaseReference firebase;
    private ValueEventListener valueEventListenerLivros;
    private Button btVoltarTela, btFavoritos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_livros);


        livros = new ArrayList<>();
        listView = (ListView) findViewById(R.id.listView);
        adapter = new LivrosAdapter(this, livros);
        listView.setAdapter(adapter);

        firebase = FireBase.getFireBase().child("addlivros");

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


}
