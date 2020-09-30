package br.edu.uniritter.mobile.appmeusfilmes.services;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONObject;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

// usaremos a biblioteca Volley para acessar a API REST
// é necessário colocar a dependência dentro do arquivo build.gradle
/*
dependencies {
        ...
        implementation 'com.android.volley:volley:1.1.1'
    }
 */

public class FilmeServices {
    public static final String URLAPI = "https://api.themoviedb.org/3/";
    public static final String URLAPIKEY = "b881ca47490d5f5879a4cbd0a0b3a94c";

    public static void carregaImagem(ImageView imageView, String urlImagem) {
        new DownloadImageTask(imageView).execute(urlImagem);
    }

    public static void buscaFilmePorId(int id, Response.Listener<JSONObject> listener) {
        RequestQueue requestQueue;

        Cache cache = new Cache() {
            private Map<String, Entry>  cache;
            @Override
            public Entry get(String key) {
                return cache.get(key);
            }

            @Override
            public void put(String key, Entry entry) {
                cache.put(key, entry);

            }

            @Override
            public void initialize() {
                cache = new HashMap<>();
            }

            @Override
            public void invalidate(String key, boolean fullExpire) {

            }

            @Override
            public void remove(String key) {
                cache.remove(key);

            }

            @Override
            public void clear() {
                cache.clear();
            }
        };
        // Set up the network to use HttpURLConnection as the HTTP client.
        Network network = new BasicNetwork(new HurlStack());

        // Instantiate the RequestQueue with the cache and network.
        requestQueue = new RequestQueue(cache, network);

        // Start the queue
        requestQueue.start();

        String url = FilmeServices.URLAPI+"movie/"+id+"?language=pt-BR&api_key="+FilmeServices.URLAPIKEY;

        // Formulate the request and handle the response.
        JsonObjectRequest jor = new JsonObjectRequest(Request.Method.GET, url,
                null, listener,null);

        // Add the request to the RequestQueue.
        requestQueue.add(jor);

        // ...

    }


    private static class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }
}


