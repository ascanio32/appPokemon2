// MainActivity.java
package com.example.pokemonapp.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pokemonapp.R;
import com.example.pokemonapp.adapter.ListaPokemonAdapter;
import com.example.pokemonapp.model.Pokemon;
import com.example.pokemonapp.presenter.MainContract;
import com.example.pokemonapp.presenter.MainPresenter;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MainContract.View {
    private static final String TAG = "POKEMONAPP";
    private RecyclerView recyclerView, rvHorizontal;
    private ListaPokemonAdapter listaPokemonAdapter, horizontalAdapter;
    private MainPresenter presenter;
    private int offSet;
    private boolean actoParaCargar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicializa las vistas para los favoritos
        TextView favoritesTV = findViewById(R.id.favoritesTV);
        ImageButton favoritesBt = findViewById(R.id.favoritesBt);

        // Configura los listeners para navegar a la actividad de favoritos
        favoritesTV.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, FavoritesActivity.class);
            startActivity(intent);
        });

        favoritesBt.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, FavoritesActivity.class);
            startActivity(intent);
        });
        //configuracion del recyclerview vertical
        recyclerView = findViewById(R.id.rv);
        listaPokemonAdapter = new ListaPokemonAdapter(this, false);
        recyclerView.setAdapter(listaPokemonAdapter);
        recyclerView.setHasFixedSize(true);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(layoutManager);

        // Configuración del RecyclerView horizontal
        rvHorizontal = findViewById(R.id.rvHorizontal);
        horizontalAdapter = new ListaPokemonAdapter(this, true);
        rvHorizontal.setAdapter(horizontalAdapter);
        LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rvHorizontal.setLayoutManager(horizontalLayoutManager);

        // Inicializa el presentador y carga los datos de Pokémon
        presenter = new MainPresenter(this);
        actoParaCargar = true;
        offSet = 0;
        presenter.loadPokemonData(offSet);

        // Configura el listener de scroll para cargar más datos cuando se llega al final de la lista
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (dy > 0) {// Verifica si el usuario está desplazándose hacia abajo
                    int visibleItemCount = layoutManager.getChildCount();
                    int totalItemCount = layoutManager.getItemCount();
                    int pastVisibleItems = layoutManager.findFirstVisibleItemPosition();

                    if (actoParaCargar) {
                        if ((visibleItemCount + pastVisibleItems) >= totalItemCount) {
                            Log.i(TAG, "Llegamos al final.");
                            actoParaCargar = false;
                            offSet += 20;
                            presenter.loadPokemonData(offSet);// Carga más datos de Pokémon
                        }
                    }
                }
            }
        });
    }

    @Override
    public void showPokemonList(List<Pokemon> pokemonList) {
        listaPokemonAdapter.adicionarListaPokemon(pokemonList);// Actualiza los adaptadores con la nueva lista de Pokémon
        horizontalAdapter.adicionarListaPokemon(pokemonList);
        actoParaCargar = true;// Permite cargar más datos
    }

    @Override
    public void showError(String message) { // Muestra un mensaje de error en el log y permite cargar más datos
        Log.e(TAG, message);
        actoParaCargar = true;
    }
}
