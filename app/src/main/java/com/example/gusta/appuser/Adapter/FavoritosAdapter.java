package com.example.gusta.appuser.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.gusta.appuser.Entidades.Livros;
import com.example.gusta.appuser.Entidades.MeusLivros;
import com.example.gusta.appuser.R;

import java.util.ArrayList;

/**
 * Created by gusta on 07/04/2018.
 */

public class FavoritosAdapter extends ArrayAdapter<MeusLivros> {

    private ArrayList<MeusLivros> meusLivros;
    private Context context;


    public FavoritosAdapter(Context context, ArrayList<MeusLivros> Data) {
        super(context, 0, Data);
        this.context = context;
        this.meusLivros = Data;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = null;

        if (meusLivros != null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

            view = inflater.inflate(R.layout.livrosfavoritos, parent, false);

            TextView txtNomeView = (TextView) view.findViewById(R.id.txtLivro);
            TextView txtgeneroView = (TextView) view.findViewById(R.id.txtGenero);
            TextView txtAutorView = (TextView) view.findViewById(R.id.txtAutor);


            MeusLivros meusLivros2 = meusLivros.get(position);

            txtNomeView.setText("Nome Livro: " + meusLivros2.getLivro());
            txtgeneroView.setText("Genero: " + meusLivros2.getGenero());
            txtAutorView.setText("Autor: " + meusLivros2.getAutor());

        }

        return view;
    }
}
