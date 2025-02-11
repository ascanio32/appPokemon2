// DetailActivity.java
package com.example.pokemonapp.view;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.example.pokemonapp.R;
import com.example.pokemonapp.model.Ability;
import com.example.pokemonapp.model.Pokemon;
import com.example.pokemonapp.presenter.DetailContract;
import com.example.pokemonapp.presenter.DetailPresenter;
import java.util.List;

public class DetailActivity extends AppCompatActivity implements DetailContract.View {
    private ImageView imageView;
    private ImageButton favoriteButton, ibatras;
    private TextView nameTextView, baseExperienceTextView, heightTextView, weightTextView, abilitiesTextView,identificacionTextView;
    private TextView orderTextView;
    private DetailPresenter presenter;
    private Pokemon pokemon;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // Inicializa las vistas
        imageView = findViewById(R.id.ivdetalles);
        favoriteButton = findViewById(R.id.favoriteButton);
        nameTextView = findViewById(R.id.nameTextView);
        baseExperienceTextView = findViewById(R.id.baseExperienceTextView);
        heightTextView = findViewById(R.id.heightTextView);
        weightTextView = findViewById(R.id.weightTextView);
        abilitiesTextView = findViewById(R.id.abilitiesTextView);
        ibatras = findViewById(R.id.ibatras);
        identificacionTextView = findViewById(R.id.idTextView);


        pokemon = (Pokemon) getIntent().getSerializableExtra("pokemon"); // Obtiene el Pokémon pasado a través del Intent

        SharedPreferences sharedPreferences = getSharedPreferences("favorites", MODE_PRIVATE); // Inicializa las preferencias compartidas y el presentador
        presenter = new DetailPresenter(this, sharedPreferences, this.getApplicationContext());

        presenter.loadPokemonDetails(pokemon.getNumber()); // Carga los detalles del Pokémon y verifica si es favorito
        presenter.checkIfFavorite(pokemon);

        favoriteButton.setOnClickListener(v -> presenter.toggleFavorite(pokemon, this.getApplicationContext() )); // Configura los listeners para los botones
        ibatras.setOnClickListener(v -> finish());
    }

    @Override
    public void showPokemonDetails(Pokemon detailedPokemon) {
        // Muestra los detalles del Pokémon en las vistas correspondientes
        nameTextView.setText(detailedPokemon.getName());
        baseExperienceTextView.setText("Experiencia: " + detailedPokemon.getBase_experience());
        heightTextView.setText("Altura: " + detailedPokemon.getHeight());
        weightTextView.setText("Peso: " + detailedPokemon.getWeight());

        List<Ability> abilities = detailedPokemon.getAbilities();// Construye una cadena con las habilidades del Pokémon
        StringBuilder abilitiesString = new StringBuilder();
        for (Ability ability : abilities) {
            abilitiesString.append(ability.getAbility().getName()).append(", ");
        }
        if (abilitiesString.length() > 0) {
            abilitiesString.setLength(abilitiesString.length() - 2);// Elimina la última coma y espacio
        }
        abilitiesTextView.setText("Habilidades: " + abilitiesString.toString());
        identificacionTextView.setText("Identificacion: " + detailedPokemon.getIdentificacion());

        // Carga la imagen del Pokémon usando Glide
        String imageUrl ="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/" + pokemon.getNumber()+".png";

        Glide.with(this)
                .load( imageUrl)
                .centerCrop()
                .transition(DrawableTransitionOptions.withCrossFade())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);
    }

    @Override
    public void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();// Muestra un mensaje de error usando un Toast
    }

    @Override
    public void updateFavoriteStatus(boolean isFavorite) {// Actualiza el icono del botón de favoritos según el estado
        if (isFavorite) {
            favoriteButton.setImageResource(R.drawable.corazon);
        } else {
            favoriteButton.setImageResource(R.drawable.corazonvacio);

        }
    }
    @Override
    public void navigateToFavorites() {
        // Navegar a la actividad de favoritos
        Intent intent = new Intent(this, FavoritesActivity.class);
        startActivity(intent);
    }

    @Override
    public void showSuccess(String message) { // Método para mostrar mensajes de éxito (actualmente vacío)

    }

}

