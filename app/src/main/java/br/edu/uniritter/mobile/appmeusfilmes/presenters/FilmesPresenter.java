package br.edu.uniritter.mobile.appmeusfilmes.presenters;

import android.security.identity.AccessControlProfileId;

public class FilmesPresenter implements FilmesContract.FilmesPresenter{
    private FilmesContract.FilmesView view;

    public FilmesPresenter(FilmesContract.FilmesView view) {
        setView(view);
    }

    @Override
    public void buscaFilmesFavoritos() {
        //aqui eu busco na API;
        // buisquei os filmes em um List<Filme>
        view.colocaFilmesnoAdapterPreferidos(moviesList);
    }

    @Override
    public void disparaAviso(String aviso) {
        view.trocaTexto(aviso);
    }

    @Override
    public void setView(FilmesContract.FilmesView view) {
        this.view = view;
    }

    @Override
    public void destruir() {
        this.view = null;

    }
}
