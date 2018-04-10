package com.example.gusta.appuser.Adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.gusta.appuser.Entidades.Livros;
import com.example.gusta.appuser.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gusta on 02/04/2018.
 */

public class LivrosAdapter extends ArrayAdapter<Livros> {

    private ArrayList<Livros> livros;
    private Context context;
    List<Livros> objects;



    public LivrosAdapter(Context c, @LayoutRes int resource, ArrayList<Livros> objects) {
        super(c, 0, objects);
        this.context = c;
        this.livros = objects;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = null;

        if (livros != null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

            view = inflater.inflate(R.layout.listalivros, parent, false);

            TextView txtNomeView = (TextView) view.findViewById(R.id.txtNomelivro);
            TextView txtgeneroView = (TextView) view.findViewById(R.id.txtGenero);
            TextView txtAutorView = (TextView) view.findViewById(R.id.txtAutor);


            Livros livros2 = livros.get(position);

            txtNomeView.setText("Nome Livro: " + livros2.getLivro());
            txtgeneroView.setText("Genero: " + livros2.getGenero());
            txtAutorView.setText("Autor: " + livros2.getAutor());

        }

        return view;




    }
    public List<Livros> getDados() {
        return objects;
    }

    public void setDados(List<Livros> objects){
        this.clear();
        this.addAll(objects);
        this.objects = objects;
        this.notifyDataSetChanged();
    }

}


