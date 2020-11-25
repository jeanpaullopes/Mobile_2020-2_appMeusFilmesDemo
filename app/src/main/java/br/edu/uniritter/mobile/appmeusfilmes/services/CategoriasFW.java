package br.edu.uniritter.mobile.appmeusfilmes.services;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import br.edu.uniritter.mobile.appmeusfilmes.model.Categoria;

public class CategoriasFW {
    private static CategoriasFW instancia;
    private Map<Integer, Categoria> categorias;

    private CategoriasFW() {
        this.categorias = new HashMap();
    }
    public static CategoriasFW getInstance() {
        if (instancia == null) {
            instancia = new CategoriasFW();
        }
        return instancia;
    }
    public void addCategoria(Categoria categoria) {
        categorias.put(categoria.getId(),categoria);

    }
    public Collection<Categoria> getCategorias() {
        return categorias.values();
    }
    public Categoria getCategoriaById(int id){
        return categorias.get(id);
    }
    public Categoria getCategoriaByDescricao(String descricao) {
        Categoria ret = null;
        for(Categoria cat : categorias.values()) {
            if (cat.getDescricao().equals(descricao)) {
                ret = cat;
            }
        }
        return ret;
    }
    public int getQtdCategorias() {
        return categorias.size();
    }
}
