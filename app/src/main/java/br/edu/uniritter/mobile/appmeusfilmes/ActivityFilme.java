package br.edu.uniritter.mobile.appmeusfilmes;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.widget.Toolbar;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;


import com.android.volley.Response;

import org.json.JSONException;
import org.json.JSONObject;

import br.edu.uniritter.mobile.appmeusfilmes.services.FilmeServices;


public class ActivityFilme extends AppCompatActivity implements Response.Listener<JSONObject> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filme);
        Toolbar tb = (Toolbar) findViewById(R.id.toolbarFilmes);
        // veja o método onCreateOptionsMenu
        setSupportActionBar(tb);

        //aqui vai colocar o botão para voltar à Activity pai.
        // para isto tem que indicar qual é a ParentActivity no Manifest
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        //aqui busca um filme na TMDB API
        FilmeServices.buscaFilmePorId(40096, this);
    }

    // importante para apresentar o menu na tela da Activity
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_filmes, menu);
        return true;
    }


    @Override
    public void onResponse(JSONObject response) {
        TextView tv = findViewById(R.id.textViewJsonFilme);
        tv.setText(response.toString());
 //       try {
 //           FilmeServices.buscaImagemFilme("http://image.tmdb.org/t/p/w154/" + response.getString("poster_path"), this);
 //       } catch (JSONException je) {}
    }

/*
    @Override
    public void onResponse(Object response) {
        Log.v("request",response.getClass().getCanonicalName());

        if( response.getClass() == JSONObject.class) {
            Log.v("request","Recebi JSON"+((JSONObject)response).toString());

            TextView tv = findViewById(R.id.textViewJsonFilme);
            tv.setText(response.toString());
            try {
                Log.v("request","vou chamar imagem http://image.tmdb.org/t/p/w154"+((JSONObject)response).getString("poster_path"));
                FilmeServices.buscaImagemFilme("https://image.tmdb.org/t/p/w300" + ((JSONObject)response).getString("poster_path"), this);
            } catch (JSONException je) {}
        }

        if( response.getClass() == Bitmap.class) {
            Log.v("request","recebi imagem "+(response));

            ImageView tv = findViewById(R.id.imageViewFilme);
            tv.setImageBitmap((Bitmap) response);
        }
    }
    */

}