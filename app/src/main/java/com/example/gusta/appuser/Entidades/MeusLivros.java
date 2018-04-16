package com.example.gusta.appuser.Entidades;

/**
 * Created by gusta on 07/04/2018.
 */

public class MeusLivros {

    private String livro;
    private String genero;
    private String autor;
    private String id;

    public String getId() {

        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public MeusLivros() {
    }


    public String getLivro() {
        return livro;
    }

    public void setLivro(String livro) {
        this.livro = livro;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }


}
