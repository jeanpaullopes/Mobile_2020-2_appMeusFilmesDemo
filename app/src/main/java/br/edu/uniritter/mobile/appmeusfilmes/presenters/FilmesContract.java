package br.edu.uniritter.mobile.appmeusfilmes.presenters;

import java.util.List;

import br.edu.uniritter.mobile.appmeusfilmes.model.Filme;

public interface FilmesContract {
    interface FilmesView {
        void trocaTexto(String texto);
        void colocaFilmesnoAdapterPreferidos(List<Filme> limes);

    }
     interface FilmesPresenter {
        void buscaFilmesFavoritos();
        void disparaAviso(String aviso);
        void setView(FilmesContract.FilmesView view); //cria a vinculação do presenter com o View
        void destruir(); // destruir a referência a FilmesView
     }

}
