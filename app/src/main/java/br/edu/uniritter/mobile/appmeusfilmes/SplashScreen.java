package br.edu.uniritter.mobile.appmeusfilmes;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import br.edu.uniritter.mobile.appmeusfilmes.model.Categoria;
import br.edu.uniritter.mobile.appmeusfilmes.services.CategoriasFW;
import br.edu.uniritter.mobile.appmeusfilmes.services.Constantes;
//Inicialmente a SplashScreen estava buscando as categorias ao entrar e só depois chamando a tela principal
// agora troque para chamar antes a tela de seleção do tipo de filme

public class SplashScreen extends AppCompatActivity implements Response.Listener<JSONObject> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        //escondendo a ActionBar
        ActionBar ab = getSupportActionBar();
        ab.hide();

        View decorView = getWindow().getDecorView();
        // Esconde tanto a barra de navegação e a barra de status .
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);

        //retrirado a busca das categorias daqui para colocar para depois da seleção do tipo
        /*
        Constantes.requestQueue.start();

        String url = Constantes.URLAPI+"genre/movie/list?language=pt-BR&api_key="+Constantes.URLAPIKEY;

        // Formulate the request and handle the response.
        JsonObjectRequest jor = new JsonObjectRequest(Request.Method.GET, url,
                null,this,null);

        // Add the request to the RequestQueue.
        Constantes.requestQueue.add(jor);
        */
        new Handler().postDelayed(new Runnable() {
            /*
             * Exibindo splash com um timer.
             */
            @Override
            public void run() {
                // Esse método será executado sempre que o timer acabar
                // E inicia a activity principal
                Intent i = new Intent(SplashScreen.this,
                        SelecaoTipo.class);
                startActivity(i);

                // Fecha esta activity
                finish();
            }
        }, 3000);

    }

    // era usado quando a splashScreen buscava as categorias antes de ir para a Ativity principal
    @Override
    @Deprecated
    public void onResponse(JSONObject response) {
        CategoriasFW cfw = CategoriasFW.getInstance();
        try {
            JSONArray genres = response.getJSONArray("genres");
            for(int i = 0; i < genres.length(); i++) {
                cfw.addCategoria(new Categoria(genres.getJSONObject(i).getInt("id"),genres.getJSONObject(i).getString("name")));
            }
        } catch (JSONException ex) {}
        new Handler().postDelayed(new Runnable() {
            /*
             * Exibindo splash com um timer.
             */
            @Override
            public void run() {
                // Esse método será executado sempre que o timer acabar
                // E inicia a activity principal
                Intent i = new Intent(SplashScreen.this,
                        MainActivity.class);
                startActivity(i);

                // Fecha esta activity
                finish();
            }
        }, 3000);

    }
}