package br.edu.uniritter.mobile.appmeusfilmes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import br.edu.uniritter.mobile.appmeusfilmes.adapters.CategoriasAdapter;
import br.edu.uniritter.mobile.appmeusfilmes.adapters.FilmesAdapter;
import br.edu.uniritter.mobile.appmeusfilmes.model.Categoria;
import br.edu.uniritter.mobile.appmeusfilmes.model.Filme;
import br.edu.uniritter.mobile.appmeusfilmes.services.FilmeServices;

public class ListaFilmes extends AppCompatActivity implements  Response.Listener<JSONObject> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_filmes);
        RecyclerView rv = findViewById(R.id.recyclerFilmes);
        StaggeredGridLayoutManager lmm = new StaggeredGridLayoutManager(3, LinearLayout.VERTICAL);
        // trocado pelo StaggerGridLayout
        LinearLayoutManager lm = new LinearLayoutManager(this);
        rv.setLayoutManager(lm);
        for (String k:getIntent().getExtras().keySet()) {
            Log.d("intent",k);
            Log.d("intent",getIntent().getExtras().getInt(k)+"");

        }
       FilmeServices.buscaFilmesPorCategoria(this.getIntent().getIntExtra("idCategoria",1),this);
        Toast.makeText(this,this.getIntent().getExtras().getInt("idCategoria")+"",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onResponse(JSONObject response) {
        System.out.println(response);
        Log.d("response", response.toString());
        List<Filme> lista = new ArrayList<>();

        try {
            JSONArray filmes = response.getJSONArray("results");
            for(int i = 0; i < filmes.length(); i++) {
                lista.add(new Filme(filmes.getJSONObject(i)));
            }
        } catch (JSONException ex) {}
        FilmesAdapter adap = new FilmesAdapter(lista);
        RecyclerView rv = findViewById(R.id.recyclerFilmes);
        rv.setAdapter(adap);

    }
}