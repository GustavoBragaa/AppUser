package com.example.gusta.appuser.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.gusta.appuser.Adapter.FavoritosAdapter;
import com.example.gusta.appuser.Adapter.LivrosAdapter;
import com.example.gusta.appuser.DAO.FireBase;
import com.example.gusta.appuser.Entidades.Livros;
import com.example.gusta.appuser.Entidades.MeusLivros;
import com.example.gusta.appuser.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FavoritosActivity extends AppCompatActivity {


    ListView lvFavoritos;
    private ArrayAdapter<MeusLivros> adapterMeusLivros;
    private ArrayList<MeusLivros> meusLivros;
    private DatabaseReference firebase;
    private ValueEventListener valueEventListenerLivros;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favoritos);

        meusLivros = new ArrayList<>();
        lvFavoritos = (ListView) findViewById(R.id.lvFavoritos);
        adapterMeusLivros = new FavoritosAdapter(this, meusLivros);
        lvFavoritos.setAdapter(adapterMeusLivros);

        firebase = FireBase.getFireBase().child("favoritos");

        valueEventListenerLivros = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                meusLivros.clear();

                for (DataSnapshot dados : dataSnapshot.getChildren()) {
                    MeusLivros meuLivroNovo = dados.getValue(MeusLivros.class);
                    meusLivros.add(meuLivroNovo);
                }
                adapterMeusLivros.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

    }
}
