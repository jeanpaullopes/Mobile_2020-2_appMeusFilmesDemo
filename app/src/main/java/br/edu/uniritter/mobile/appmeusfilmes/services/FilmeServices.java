package br.edu.uniritter.mobile.appmeusfilmes.services;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import org.json.JSONObject;

import java.io.InputStream;


// usaremos a biblioteca Volley para acessar a API REST
// é necessário colocar a dependência dentro do arquivo build.gradle
/*
dependencies {
        ...
        implementation 'com.android.volley:volley:1.1.1'
    }
 */

public class FilmeServices {



    public static void carregaImagem(ImageView imageView, String urlImagem) {
        new DownloadImageTask(imageView).execute(urlImagem);
    }
    public static void buscaImagemFilme(String url, Response.Listener<Bitmap> listener) {
        // Instantiate the RequestQueue with the cache and network.

        Constantes.requestQueue.start();
        Log.v("request","vou criar o request");

        ImageRequest imgReq = new ImageRequest(url, listener, 0, 0, null,
                null, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("request",error.getMessage());
            }
        });
        Constantes.requestQueue.add(imgReq);

    }
    public static void buscaFilmePorId(int id, Response.Listener<JSONObject> listener) {

        Constantes.requestQueue.start();

        String url = Constantes.URLAPI+"movie/"+id+"?language=pt-BR&api_key="+Constantes.URLAPIKEY;

        // Formulate the request and handle the response.
        JsonObjectRequest jor = new JsonObjectRequest(Request.Method.GET, url,
                null,listener,null);

        // Add the request to the RequestQueue.
        Constantes.requestQueue.add(jor);

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


