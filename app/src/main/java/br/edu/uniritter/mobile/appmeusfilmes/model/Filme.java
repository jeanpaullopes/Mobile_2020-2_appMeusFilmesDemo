package br.edu.uniritter.mobile.appmeusfilmes.model;

import android.graphics.Bitmap;
import android.widget.ImageView;
import android.os.Parcel;
import android.os.Parcelable;

import com.android.volley.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import br.edu.uniritter.mobile.appmeusfilmes.services.FilmeServices;

public class Filme implements Parcelable {

    private String titulo;
    private String[] generos;
    private int id;
    private String poster;
    private Bitmap posterImagem;

    public Filme(JSONObject jsonObject) {
        try {
            this.titulo = jsonObject.getString("original_title");
            this.id = jsonObject.getInt("id");
            this.poster = jsonObject.getString("poster_path");
            JSONArray jarr = jsonObject.getJSONArray("genres");
            this.generos = new String[jarr.length()];
            for (int i=0; i < jarr.length(); i++) {
                this.generos[i] = jarr.getJSONObject(i).getString("name");

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    protected Filme(Parcel in) {
        id = in.readInt();
        titulo = in.readString();
        generos = in.createStringArray();
        poster = in.readString();
    }

    public static final Creator<Filme> CREATOR = new Creator<Filme>() {
        @Override
        public Filme createFromParcel(Parcel in) {
            return new Filme(in);
        }

        @Override
        public Filme[] newArray(int size) {
            return new Filme[size];
        }
    };

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String[] getGeneros() {
        return generos;
    }
    public int getQtdGeneros() {
        return generos.length;
    }
    public void setGeneros(String[] generos) {
        this.generos = generos;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPoster() {
        return poster;
    }
    public Bitmap getPosterImagem() {
        if (this.posterImagem == null) {
            Response.Listener<Bitmap> listener = new Response.Listener<Bitmap>() {
                @Override
                public void onResponse(Bitmap response) {
                    posterImagem = response;
                }
            };

            FilmeServices.buscaImagemFilme("http://image.tmdb.org/t/p/w154/" + getPoster(), listener);
        }
        return posterImagem;

    }

    public void setPosterImagem(ImageView iv) {
        this.posterImagem = iv.getDrawingCache();
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(titulo);
        parcel.writeStringArray(generos);
        parcel.writeString(poster);


    }
}

/*
https://api.themoviedb.org/3/movie/40096?api_key=b881ca47490d5f5879a4cbd0a0b3a94c&language=pt-BR
{
  "adult": false,
  "backdrop_path": "/alQqTpmEkxSLgajfEYTsTH6nAKB.jpg",
  "belongs_to_collection": null,
  "budget": 0,
  "genres": [
    {
      "id": 12,
      "name": "Aventura"
    },
    {
      "id": 35,
      "name": "Comédia"
    }
  ],
  "homepage": "",
  "id": 40096,
  "imdb_id": "tt0271383",
  "original_language": "pt",
  "original_title": "O Auto da Compadecida",
  "overview": "O Auto da Compadecida: As aventuras dos nordestinos João Grilo (Matheus Natchergaele), um sertanejo pobre e mentiroso, e Chicó (Selton Mello), o mais covarde dos homens. Ambos lutam pelo pão de cada dia e atravessam por vários episódios enganando a todos do pequeno vilarejo de Taperoá, no sertão da Paraíba. A salvação da dupla acontece com a aparição da Nossa Senhora (Fernanda Montenegro). Adaptação da obra de Ariano Suassuna.",
  "popularity": 10.09,
  "poster_path": "/m8eFedsS7vQCZCS8WGp5n1bVD0q.jpg",
  "production_companies": [
    {
      "id": 11446,
      "logo_path": null,
      "name": "Lereby Productions",
      "origin_country": ""
    },
    {
      "id": 13969,
      "logo_path": "/vYbpU7yDkLvx4ehvOxuU5BTsFKi.png",
      "name": "Globo Filmes",
      "origin_country": "BR"
    }
  ],
  "production_countries": [
    {
      "iso_3166_1": "BR",
      "name": "Brazil"
    }
  ],
  "release_date": "2000-09-15",
  "revenue": 0,
  "runtime": 104,
  "spoken_languages": [
    {
      "iso_639_1": "pt",
      "name": "Português"
    }
  ],
  "status": "Released",
  "tagline": "",
  "title": "O Auto da Compadecida",
  "video": false,
  "vote_average": 8.4,
  "vote_count": 661
}
 */
