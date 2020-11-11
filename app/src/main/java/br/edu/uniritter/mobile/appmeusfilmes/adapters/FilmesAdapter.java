package br.edu.uniritter.mobile.appmeusfilmes.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.edu.uniritter.mobile.appmeusfilmes.databinding.LayoutCategoriaBinding;
import br.edu.uniritter.mobile.appmeusfilmes.databinding.LayoutFilmeViewHolderBinding;
import br.edu.uniritter.mobile.appmeusfilmes.model.Categoria;
import br.edu.uniritter.mobile.appmeusfilmes.model.Filme;

public class FilmesAdapter extends RecyclerView.Adapter<FilmesAdapter.FilmeViewHolder>{
    private List<Filme> filmes;

    public FilmesAdapter(List<Filme> filmes) {
        this.filmes = filmes;
    }



    @NonNull
    @Override
    public FilmeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        LayoutFilmeViewHolderBinding itemBinding = LayoutFilmeViewHolderBinding.inflate(layoutInflater, parent, false);
        return new FilmesAdapter.FilmeViewHolder(itemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull FilmeViewHolder holder, int position) {
        holder.bind(filmes.get(position));
    }

    @Override
    public int getItemCount() {
        return filmes.size();
    }

    public static class FilmeViewHolder extends RecyclerView.ViewHolder {
        //aqui colocamos um item para binding. Estas classes são automaticamente criadas pelo Android
        LayoutFilmeViewHolderBinding binding;
        // each data item is just a string in this case
        public View view;
        public FilmeViewHolder(LayoutFilmeViewHolderBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
        public void bind(Filme filme) {
            binding.setFilme(filme);
            binding.executePendingBindings();
        }
    }
}
