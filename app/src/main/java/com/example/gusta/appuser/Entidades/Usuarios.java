package com.example.gusta.appuser.Entidades;

import com.example.gusta.appuser.DAO.FireBase;
import com.example.gusta.appuser.DAO.FireBase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by gusta on 02/04/2018.
 */

public class Usuarios {

    private String id;
    private String email;
    private String senha;
    private String nome;

    public Usuarios() {

    }

    public void salvar() {
        DatabaseReference referenciaFirebase = FireBase.getFireBase();
        referenciaFirebase.child("usuario").child(String.valueOf(getId())).setValue(this);
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> hasMapUsuario = new HashMap<>();
        hasMapUsuario.put("id", getId());
        hasMapUsuario.put("email", getEmail());
        hasMapUsuario.put("senha", getSenha());
        hasMapUsuario.put("nome", getNome());

        return hasMapUsuario;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }


}
