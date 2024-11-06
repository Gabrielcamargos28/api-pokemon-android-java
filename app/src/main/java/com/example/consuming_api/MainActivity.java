package com.example.consuming_api;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.squareup.picasso.Picasso;
import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Response;
import retrofit2.Call;
import retrofit2.Callback;


public class MainActivity extends AppCompatActivity {

    private EditText etPokemonName;
    private Button btnSearch;
    private TextView tvPokemonName;
    private ImageView ivPokemonImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        etPokemonName = findViewById(R.id.etPokemonName);
        btnSearch = findViewById(R.id.btnSearch);
        tvPokemonName = findViewById(R.id.tvPokemonName);
        ivPokemonImage = findViewById(R.id.ivPokemonImage);


        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String pokemonName = etPokemonName.getText().toString().toLowerCase().trim();


                if (!TextUtils.isEmpty(pokemonName)) {

                    getPokemonByName(pokemonName);
                } else {
                    Toast.makeText(MainActivity.this, "Por favor, digite o nome de um Pokémon", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void getPokemonByName(String name) {

        PokemonService service = RetrofitClient.getRetrofitInstance().create(PokemonService.class);

        Call<Pokemon> call = service.getPokemonByName(name);


        call.enqueue(new Callback<Pokemon>() {
            @Override
            public void onResponse(Call<Pokemon> call, Response<Pokemon> response) {

                if (response.isSuccessful() && response.body() != null) {
                    Pokemon pokemon = response.body();


                    tvPokemonName.setText(pokemon.getName());


                    Picasso.get().load(pokemon.getSprites().getFront_default()).into(ivPokemonImage);
                } else {

                    Toast.makeText(MainActivity.this, "Pokémon não encontrado", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Pokemon> call, Throwable t) {

                Toast.makeText(MainActivity.this, "Erro ao buscar Pokémon: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}