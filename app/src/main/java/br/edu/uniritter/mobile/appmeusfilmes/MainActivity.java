package br.edu.uniritter.mobile.appmeusfilmes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void onClickButton1(View view) {
        Intent it = new Intent(this, ActivityFilme.class);
        startActivity(it);
    }
}