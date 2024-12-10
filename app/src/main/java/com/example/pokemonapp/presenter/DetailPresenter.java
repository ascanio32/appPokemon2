// DetailPresenter.java
package com.example.pokemonapp.presenter;

import android.content.SharedPreferences;
import com.example.pokemonapp.model.Pokemon;
import com.example.pokemonapp.pokeApi.PokeApiService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DetailPresenter implements DetailContract.Presenter {// Presentador que maneja la lógica de negocio para la pantalla de detalles de un Pokémon.
    private DetailContract.View view; // Referencia a la vista
    private SharedPreferences sharedPreferences;// Preferencias compartidas para almacenar los favoritos
    private Retrofit retrofit;// Cliente Retrofit para realizar llamadas a la API
    private List<Pokemon> favoritePokemons;// Lista de Pokémon favoritos

    public DetailPresenter(DetailContract.View view, SharedPreferences sharedPreferences) {//Constructor que inicializa el presentador con la vista y las preferencias compartidas.
        //view la vista que implementa DetailContract.View. sharedPreferences las preferencias compartidas para almacenar los favoritos.
        this.view = view;
        this.sharedPreferences = sharedPreferences;
        this.retrofit = new Retrofit.Builder()
                .baseUrl("https://pokeapi.co/api/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        this.favoritePokemons = loadFavoritesFromPreferences();
    }


    @Override
    public void loadPokemonDetails(int pokemonNumber) {
        PokeApiService service = retrofit.create(PokeApiService.class);
        Call<Pokemon> call = service.getPokemonDetails(pokemonNumber);
        call.enqueue(new Callback<Pokemon>() {
            @Override
            public void onResponse(Call<Pokemon> call, Response<Pokemon> response) {
                if (response.isSuccessful()) {
                    Pokemon pokemon = response.body();// Obtiene el cuerpo de la respuesta
                    if (pokemon != null) {
                        view.showPokemonDetails(pokemon); // Muestra los detalles del Pokémon en la vista
                    }
                } else {
                    view.showError("Error: " + response.errorBody());// Muestra un mensaje de error si la respuesta no es exitosa
                }
            }

            @Override
            public void onFailure(Call<Pokemon> call, Throwable t) {
                if (t instanceof IOException){
                    view.showError("Error de red: " + t.getMessage());// Muestra un mensaje de error de red
                }else {
                    view.showError("Error en la llamada: " + t.getMessage());// Muestra un mensaje de error general
                }

            }
        });
    }

    @Override
    public void toggleFavorite(Pokemon pokemon) {
        if (isFavorite(pokemon)) {
           removeFavorite(pokemon);// Elimina el Pokémon de favoritos si ya es favorito
        } else {
           addFavorite(pokemon);// Añade el Pokémon a favoritos si no es favorito
        }
    }

    @Override
    public void checkIfFavorite(Pokemon pokemon) {
        view.updateFavoriteStatus(isFavorite(pokemon));// Actualiza el estado del botón de favoritos en la vista
    }
    private void addFavorite(Pokemon pokemon) {//Añade un Pokémon a la lista de favoritos.
        if (!isFavorite(pokemon)) {
            favoritePokemons.add(pokemon);
            saveFavorites();
            view.updateFavoriteStatus(true);
            view.showError("Añadido a favoritos ");
        }
    }
    private boolean isFavorite(Pokemon pokemon) {// Comprueba si un Pokémon está en la lista de favoritos.
        //pokemon el Pokémon a comprobar.
        // @return true si el Pokémon está en la lista de favoritos, false en caso contrario.
        for (Pokemon fav : favoritePokemons) {
            if (fav.getNumber() == pokemon.getNumber()) {
                return true;
            }
        }
        return false;
    }
    private void removeFavorite(Pokemon pokemon) {// Elimina un Pokémon de la lista de favoritos.
        //pokemon el Pokémon a eliminar de favoritos.
        if (isFavorite(pokemon)) {
            favoritePokemons.remove(pokemon);
            saveFavorites();
            view.updateFavoriteStatus(false); // Actualiza el estado del botón
            view.showError("Eliminado de favoritos");
        }
    }

    private void saveFavorites() {//Guarda la lista de favoritos en las preferencias compartidas.
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(favoritePokemons);
        editor.putString("favoriteList", json);
        editor.apply();
    }

    private List<Pokemon> loadFavoritesFromPreferences() {//Carga la lista de favoritos desde las preferencias compartidas.
        // @return la lista de Pokémon favoritos.
        String json = sharedPreferences.getString("favoriteList", null);
        Type type = new TypeToken<ArrayList<Pokemon>>() {}.getType();
        List<Pokemon> favoriteList = new Gson().fromJson(json, type);
        return favoriteList != null ? favoriteList : new ArrayList<>();

    }

}

