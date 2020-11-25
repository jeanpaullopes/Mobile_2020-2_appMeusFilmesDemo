package br.edu.uniritter.mobile.appmeusfilmes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import br.edu.uniritter.mobile.appmeusfilmes.adapters.CategoriasAdapter;
import br.edu.uniritter.mobile.appmeusfilmes.model.Categoria;
import br.edu.uniritter.mobile.appmeusfilmes.model.Filme;
import br.edu.uniritter.mobile.appmeusfilmes.presenters.FilmesContract;
import br.edu.uniritter.mobile.appmeusfilmes.presenters.FilmesPresenter;
import br.edu.uniritter.mobile.appmeusfilmes.services.CategoriasFW;
import br.edu.uniritter.mobile.appmeusfilmes.services.Constantes;

// implementada a Interface Listener para tratar a requisição da categorias
public class MainActivity extends AppCompatActivity implements Response.Listener<JSONObject>,
        FilmesContract.FilmesView, SensorEventListener {

        SensorManager sensorManager;
        Sensor sensor;
        long tempoEvento;
        RecyclerView rv, rv2;
        FilmesContract.FilmesPresenter presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.presenter = new FilmesPresenter(this);
        presenter.disparaAviso("olá mundo");
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
        rv = findViewById(R.id.reclyclerCategorias);
        rv2 = findViewById(R.id.rv2);
        StaggeredGridLayoutManager lmm = new StaggeredGridLayoutManager(3,LinearLayout.VERTICAL);
        // trocado pelo StaggerGridLayout
        LinearLayoutManager lm = new LinearLayoutManager(this,RecyclerView.HORIZONTAL,true);
        rv.setLayoutManager(lm);
        rv2.setLayoutManager(lmm);
        // retirado daqui e colocado no onResponse
        //CategoriasAdapter adap = new CategoriasAdapter(new ArrayList<Categoria>(cfw.getCategorias()));
        //rv.setAdapter(adap);


        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        if (sensor == null){
            Log.e("Sensor","SENSOR não encontrado");
        }

    }
    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }


    public void onClickButton1(View view) {
        Intent it = new Intent(this, ActivityFilme.class);
        it.putExtra("idFilme",40097); //487242 40096
        it.putExtra("filme", new Filme(null));
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
        List<Categoria> l1 = new ArrayList<Categoria>(cfw.getCategorias());
        CategoriasAdapter adap = new CategoriasAdapter(l1.subList(0,10));
        CategoriasAdapter adap1 = new CategoriasAdapter(l1.subList(0,9));
        rv.setAdapter(adap);
        rv2.setAdapter(adap1);
    }

    @Override
    public void trocaTexto(String texto) {
        Toast.makeText(this,texto,Toast.LENGTH_LONG).show();
    }

    @Override
    public void colocaFilmesnoAdapterPreferidos(List<Filme> filmes) {
        //rv3.setAdapter(new ListaFilmesAdapter(filmes));
    }
public void clickBotao(View view) {
    //StaggeredGridLayoutManager lmm = new StaggeredGridLayoutManager(3,LinearLayout.VERTICAL);
    //rv.setLayoutManager(lmm);
    //
}

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.presenter.destruir();
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        Log.d("sensor",sensor.getName());
        if (sensorEvent.sensor == sensor && sensorEvent.timestamp - tempoEvento > 2000) {

            String d = "";
            float x,y,z;
            x = sensorEvent.values[0];
            y = sensorEvent.values[1];
            z = sensorEvent.values[2];

            for(float f: sensorEvent.values) {
                d += f+", ";
            }
            if ( y > 3) {
                Log.d("Sensor1", d);
                rv.scrollBy(300,0);
                tempoEvento = sensorEvent.timestamp;
            } else {
                if ( y < -3) {
                    Log.d("Sensor2",  ""+y);
                    rv.scrollBy(-300,0);
                    tempoEvento = sensorEvent.timestamp;
                }
            }



        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}