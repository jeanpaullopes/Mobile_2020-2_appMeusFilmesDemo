package br.edu.uniritter.mobile.appmeusfilmes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.Executor;

import br.edu.uniritter.mobile.appmeusfilmes.model.Categoria;
import br.edu.uniritter.mobile.appmeusfilmes.services.CategoriasFW;
import br.edu.uniritter.mobile.appmeusfilmes.services.Constantes;
//Inicialmente a SplashScreen estava buscando as categorias ao entrar e só depois chamando a tela principal
// agora troque para chamar antes a tela de seleção do tipo de filme

public class SplashScreen extends AppCompatActivity implements Response.Listener<JSONObject> {
    private BiometricPrompt biometricPrompt;
    private BiometricPrompt.PromptInfo promptInfo;
    private Executor executor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        //escondendo a ActionBar
        ActionBar ab = getSupportActionBar();
        ab.hide();

        View decorView = getWindow().getDecorView();
        // Esconde tanto a barra de navegação e a barra de status .
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);

        //retrirado a busca das categorias daqui para colocar para depois da seleção do tipo
        /*
        Constantes.requestQueue.start();

        String url = Constantes.URLAPI+"genre/movie/list?language=pt-BR&api_key="+Constantes.URLAPIKEY;

        // Formulate the request and handle the response.
        JsonObjectRequest jor = new JsonObjectRequest(Request.Method.GET, url,
                null,this,null);

        // Add the request to the RequestQueue.
        Constantes.requestQueue.add(jor);
        */


    }

    public void delayParaProximaActivity() {
        new Handler().postDelayed(new Runnable() {
            /*
             * Exibindo splash com um timer.
             */
            @Override
            public void run() {
                // Esse método será executado sempre que o timer acabar
                // E inicia a activity principal
                Intent i = new Intent(SplashScreen.this,
                        SelecaoTipo.class);
                startActivity(i);

                // Fecha esta activity
                finish();
            }
        }, 3000);

    }
    // era usado quando a splashScreen buscava as categorias antes de ir para a Ativity principal
    @Override
    @Deprecated
    public void onResponse(JSONObject response) {
        CategoriasFW cfw = CategoriasFW.getInstance();
        try {
            JSONArray genres = response.getJSONArray("genres");
            for(int i = 0; i < genres.length(); i++) {
                cfw.addCategoria(new Categoria(genres.getJSONObject(i).getInt("id"),genres.getJSONObject(i).getString("name")));
            }
        } catch (JSONException ex) {}
        new Handler().postDelayed(new Runnable() {
            /*
             * Exibindo splash com um timer.
             */
            @Override
            public void run() {
                // Esse método será executado sempre que o timer acabar
                // E inicia a activity principal
                Intent i = new Intent(SplashScreen.this,
                        MainActivity.class);
                startActivity(i);

                // Fecha esta activity
                finish();
            }
        }, 3000);

    }
    public void onClickIntent(View v) {
        // Create the text message with a string
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, "olá mundo");
        sendIntent.setType("text/plain");

        // Verify that the intent will resolve to an activity
        if (sendIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(sendIntent);
        }
    }

    public void onClickShareFilme(View v) {
        // Create the text message with a string
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra("json", "{\"id\":40096}");
        sendIntent.setType("application/json");

        // Verify that the intent will resolve to an activity
        if (sendIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(sendIntent);
        } else {
            Toast.makeText(getApplicationContext(),
                    "Sem Activity para abrir application/json", Toast.LENGTH_SHORT).show();
        }
    }
    public void onClickFinger(View v) {
        executor = ContextCompat.getMainExecutor(this);
        biometricPrompt = new BiometricPrompt(this,
                executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode,
                                              @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                Toast.makeText(getApplicationContext(),
                        "Authentication error: " + errString, Toast.LENGTH_SHORT)
                        .show();
            }

            @Override
            public void onAuthenticationSucceeded(
                    @NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                Toast.makeText(getApplicationContext(),
                        "Authentication succeeded!", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(SplashScreen.this,
                        SelecaoTipo.class);
                startActivity(i);
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                Toast.makeText(getApplicationContext(), "Authentication failed",
                        Toast.LENGTH_SHORT)
                        .show();
            }
        });

        promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("Biometric login for my app")
                .setSubtitle("Log in using your biometric credential")
                .setNegativeButtonText("Use account password")
                .build();

        // Prompt appears when user clicks "Log in".
        // Consider integrating with the keystore to unlock cryptographic operations,
        // if needed by your app.
        //Button biometricLoginButton = findViewById(R.id.biometric_login);
        //biometricLoginButton.setOnClickListener(view -> {
        biometricPrompt.authenticate(promptInfo);
        //});


    }
}