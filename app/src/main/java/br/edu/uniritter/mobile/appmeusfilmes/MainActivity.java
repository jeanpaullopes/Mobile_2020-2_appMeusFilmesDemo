package br.edu.uniritter.mobile.appmeusfilmes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import br.edu.uniritter.mobile.appmeusfilmes.adapters.CategoriasAdapter;
import br.edu.uniritter.mobile.appmeusfilmes.model.Categoria;
import br.edu.uniritter.mobile.appmeusfilmes.services.CategoriasFW;
import br.edu.uniritter.mobile.appmeusfilmes.services.Constantes;

// implementada a Interface Listener para tratar a requisição da categorias
public class MainActivity extends AppCompatActivity implements Response.Listener<JSONObject> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //adicionado a chamada para a lista de categorias de acordo com a seleção de tipo
        Constantes.requestQueue.start();
        String url = "";
        int tipo = this.getIntent().getIntExtra("tipoFilme",1);
        switch (tipo) {
            case Categoria.TIPO_CINEMA: url = Constantes.URLAPI + "genre/movie/list?language=pt-BR&api_key=" + Constantes.URLAPIKEY;
                                        break;
        }
        // Formulate the request and handle the response.
        JsonObjectRequest jor = new JsonObjectRequest(Request.Method.GET, url,
                null,this,null);
        // Add the request to the RequestQueue.
        Constantes.requestQueue.add(jor);
        //fim da requisição

        LinearLayout layout = findViewById(R.id.layoutPrincipalCategorias);
        CategoriasFW cfw = CategoriasFW.getInstance();
        RecyclerView rv = findViewById(R.id.reclyclerCategorias);
        StaggeredGridLayoutManager lmm = new StaggeredGridLayoutManager(3,LinearLayout.VERTICAL);
        // trocado pelo StaggerGridLayout
        //LinearLayoutManager lm = new LinearLayoutManager(this);
        rv.setLayoutManager(lmm);
        // retirado daqui e colocado no onResponse
        //CategoriasAdapter adap = new CategoriasAdapter(new ArrayList<Categoria>(cfw.getCategorias()));
        //rv.setAdapter(adap);
    }
    public void onClickButton1(View view) {
        Intent it = new Intent(this, ActivityFilme.class);
        startActivity(it);
    }

    @Override
    public void onResponse(JSONObject response) {
        CategoriasFW cfw = CategoriasFW.getInstance();
        try {
            JSONArray genres = response.getJSONArray("genres");
            for(int i = 0; i < genres.length(); i++) {
                cfw.addCategoria(new Categoria(genres.getJSONObject(i).getInt("id"),genres.getJSONObject(i).getString("name")));
            }
        } catch (JSONException ex) {}
        CategoriasAdapter adap = new CategoriasAdapter(new ArrayList<Categoria>(cfw.getCategorias()));
        RecyclerView rv = findViewById(R.id.reclyclerCategorias);
        rv.setAdapter(adap);
    }
}