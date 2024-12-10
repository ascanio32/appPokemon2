// FavoritesActivity.java
package com.example.pokemonapp.view;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.pokemonapp.R;
import com.example.pokemonapp.adapter.FavoritosAdapter;
import com.example.pokemonapp.model.Pokemon;
import com.example.pokemonapp.presenter.FavoritesContract;
import com.example.pokemonapp.presenter.FavoritesPresenter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class FavoritesActivity extends AppCompatActivity implements FavoritesContract.View {
    private RecyclerView rvFavorites;
    private FavoritosAdapter adapter;
    private ImageButton btAtras, ibfavoritos;
    private List<Pokemon> favoritePokemons;
    private FavoritesPresenter presenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        // Inicializa las vistas
        rvFavorites = findViewById(R.id.rvFavorites);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        rvFavorites.setLayoutManager(gridLayoutManager);

        favoritePokemons = new ArrayList<>();
        SharedPreferences sharedPreferences = getSharedPreferences("favorites", MODE_PRIVATE);
        presenter = new FavoritesPresenter(this, sharedPreferences);
        adapter = new FavoritosAdapter(this, favoritePokemons, presenter);
        rvFavorites.setAdapter(adapter);

        btAtras = findViewById(R.id.btAtras);
        btAtras.setOnClickListener(v -> finish());

        // Inicializar el presenter y cargar los favoritos


        presenter.loadFavorites();
    }
    @Override
    public void showFavoriteList(List<Pokemon> favoriteList) {
        // Actualiza la lista de Pokémon favoritos y notifica al adaptador
        favoritePokemons.clear();
        favoritePokemons.addAll(favoriteList);
        adapter.notifyDataSetChanged();

    }
    @Override
    public void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();// Muestra un mensaje de error usando un Toast

    }
    @Override
    public void showSuccess(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show(); // Muestra un mensaje de éxito usando un Toast
    }






}
