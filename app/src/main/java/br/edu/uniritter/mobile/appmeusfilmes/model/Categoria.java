package br.edu.uniritter.mobile.appmeusfilmes.model;

public class Categoria {
    public final int id;
    public final String descricao;

    public static final int TIPO_CINEMA = 1;
    public static final int TIPO_TV = 2;
    public static final int TIPO_SERIE = 3;

    public Categoria(int id, String descricao) {
        this.id = id;
        this.descricao = descricao;
    }

    public int getId() {
        return id;
    }

    public String getDescricao() {
        return descricao;
    }
}
