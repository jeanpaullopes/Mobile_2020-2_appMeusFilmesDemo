package br.edu.uniritter.mobile.appmeusfilmes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import br.edu.uniritter.mobile.appmeusfilmes.model.Categoria;

public class SelecaoTipo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selecao_tipo);
    }

    // no layout associei todos CardViews a este método em onClick
    public void onClickCard(View view) {
        Intent it = new Intent(this,MainActivity.class) ;
        switch (view.getId()) {
            case R.id.cardFilmeSelecao: {
                it.putExtra("tipoFilme", Categoria.TIPO_CINEMA);
                break;
            }
            case R.id.cardTvSelecao: {
                it.putExtra("tipoFilme", Categoria.TIPO_TV);
                break;
            }
            case R.id.cardSerieSelecao: {
                it.putExtra("tipoFilme", Categoria.TIPO_SERIE);
                break;
            }

        }
        startActivity(it);
    }
}