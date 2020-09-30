package br.edu.uniritter.mobile.appmeusfilmes;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
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
        FilmeServices.buscaFilmePorId(40096, this);
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