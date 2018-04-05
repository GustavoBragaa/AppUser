package com.example.gusta.appuser.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.gusta.appuser.R;

public class MainActivity extends AppCompatActivity {


    private Button btListarLivros;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        btListarLivros = (Button) findViewById(R.id.btListarLivros);
        btListarLivros.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent chamaListarlivros = new Intent(MainActivity.this, ListarLivrosActivity.class);
                startActivity(chamaListarlivros);

                Toast.makeText(MainActivity.this, "Aguarde, enquanto carregamos os dados!", Toast.LENGTH_SHORT).show();

            }


        });
    }
}

