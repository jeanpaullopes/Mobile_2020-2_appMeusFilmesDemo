package br.edu.uniritter.mobile.appmeusfilmes.adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Collection;
import java.util.List;

import br.edu.uniritter.mobile.appmeusfilmes.ListaFilmes;
import br.edu.uniritter.mobile.appmeusfilmes.R;
import br.edu.uniritter.mobile.appmeusfilmes.databinding.LayoutCategoriaBinding;
import br.edu.uniritter.mobile.appmeusfilmes.model.Categoria;

public class CategoriasAdapter extends RecyclerView.Adapter<CategoriasAdapter.CategoriaViewHolder> {
    private List<Categoria> categorias;

    public CategoriasAdapter(List<Categoria> categorias) {
        this.categorias = categorias;
    }

    public CategoriasAdapter.CategoriaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        final LayoutCategoriaBinding itemBinding = LayoutCategoriaBinding.inflate(layoutInflater, parent, false);
        itemBinding.button.setOnClickListener(new View.OnClickListener() {
                                                          @Override
                                                          public void onClick(View view) {
                                                              Intent intent = new Intent(itemBinding.getRoot().getContext(), ListaFilmes.class);
                                                              intent.putExtra("idCategoria",itemBinding.getCategoria().id);
                                                              itemBinding.getRoot().getContext().startActivity(intent);
                                                          }
                                                      });
        return new CategoriaViewHolder(itemBinding);
    }

    @Override
    public int getItemCount() {
        return categorias.size();
    }

    @Override
    public void onBindViewHolder(@NonNull CategoriaViewHolder holder, int position) {
        holder.bind(categorias.get(position));
    }

    public void trocaCategorias(List<Categoria>lst){
        this.categorias = lst;
    }
    public static class CategoriaViewHolder extends RecyclerView.ViewHolder {
        //aqui colocamos um item para binding. Estas classes são automaticamente criadas pelo Android
        LayoutCategoriaBinding binding;
        // each data item is just a string in this case
        public View view;
        public CategoriaViewHolder(LayoutCategoriaBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
        public void bind(Categoria cat) {
            binding.setCategoria(cat);

            binding.executePendingBindings();
        }
    }
}
