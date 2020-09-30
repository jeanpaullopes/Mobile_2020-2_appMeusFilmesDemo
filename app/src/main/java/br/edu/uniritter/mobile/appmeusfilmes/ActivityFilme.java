package br.edu.uniritter.mobile.appmeusfilmes;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.android.volley.Response;

import org.json.JSONObject;

import br.edu.uniritter.mobile.appmeusfilmes.services.FilmeServices;


public class ActivityFilme extends AppCompatActivity implements Response.Listener<JSONObject> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filme);
        FilmeServices.buscaFilmePorId(40096, this);
    }

    @Override
    public void onResponse(JSONObject response) {
        TextView tv = findViewById(R.id.textViewJsonFilme);
        tv.setText(response.toString());
    }
}