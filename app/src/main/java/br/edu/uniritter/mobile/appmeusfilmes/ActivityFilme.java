package br.edu.uniritter.mobile.appmeusfilmes;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;


import com.android.volley.Response;

import org.json.JSONException;
import org.json.JSONObject;

import br.edu.uniritter.mobile.appmeusfilmes.databinding.ActivityFilmeBinding;
import br.edu.uniritter.mobile.appmeusfilmes.model.Filme;
import br.edu.uniritter.mobile.appmeusfilmes.services.FilmeServices;


public class ActivityFilme extends AppCompatActivity implements Response.Listener {
    ActivityFilmeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_filme);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_filme);

       // Toolbar tb = (Toolbar) findViewById(R.id.toolbarFilmes);
        // veja o método onCreateOptionsMenu
        //setSupportActionBar(tb);

        //aqui vai colocar o botão para voltar à Activity pai.
        // para isto tem que indicar qual é a ParentActivity no Manifest
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);


        //controle para chamada externa a activity

        int idFilme = 0;

        Intent intent = getIntent();
        if (Intent.ACTION_VIEW.equals(intent.getAction())) {
            Uri uri = intent.getData();
            String id = uri.getQueryParameter("id");
            idFilme = Integer.parseInt(id);
        } else {
            if (intent.getType().equals("application/json")) {
                try {

                    JSONObject json = new JSONObject(intent.getExtras().getString("json"));
                    idFilme = json.getInt("id");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                idFilme = this.getIntent().getIntExtra("idFilme", 0);
            }
        }



        //aqui busca um filme na TMDB API
        FilmeServices.buscaFilmePorId(idFilme, this);  //487242 40096
    }

    // importante para apresentar o menu na tela da Activity
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_filmes, menu);
        return true;
    }

/*
    @Override
    public void onResponse(JSONObject response) {
        TextView tv = findViewById(R.id.textViewJsonFilme);
        Filme filme = new Filme(response.toString());
        tv.setText(filme.getTitulo());
 //       try {
 //           FilmeServices.buscaImagemFilme("http://image.tmdb.org/t/p/w154/" + response.getString("poster_path"), this);
 //       } catch (JSONException je) {}
    }

*/
    @Override
    public void onResponse(Object response) {
        Log.v("request",response.getClass().getCanonicalName());

        if( response.getClass() == JSONObject.class) {
            Log.v("request","Recebi JSON"+((JSONObject)response).toString());
            Filme filme = new Filme(((JSONObject) response));
            binding.setFilme(filme);
            //TextView tv = findViewById(R.id.textViewJsonFilme);
            //tv.setText(filme.getTitulo());
            //Log.v("request","vou chamar imagem http://image.tmdb.org/t/p/w154"+filme.getPoster());
            FilmeServices.buscaImagemFilme("https://image.tmdb.org/t/p/w300" + filme.getPoster(), this);

        }

        if( response.getClass() == Bitmap.class) {
            Log.v("request","recebi imagem "+(response));

            ImageView tv = findViewById(R.id.imageViewFilme);
            tv.setImageBitmap((Bitmap) response);
        }
    }

}