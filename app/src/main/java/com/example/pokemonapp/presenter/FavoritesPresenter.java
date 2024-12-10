package com.example.pokemonapp.presenter;

import android.content.SharedPreferences;
import com.example.pokemonapp.model.Pokemon;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class FavoritesPresenter implements FavoritesContract.Presenter {
    private FavoritesContract.View view;
    private SharedPreferences sharedPreferences;
    private  List<Pokemon> favoritePokemon;


    public FavoritesPresenter(FavoritesContract.View view, SharedPreferences sharedPreferences) {// Constructor del presentador que inicializa la vista y las preferencias compartidas
        this.view = view;
        this.sharedPreferences = sharedPreferences;
        this.favoritePokemon = loadFavoritesFromPreferences();// Carga los favoritos desde las preferencias
    }

    @Override
    public void loadFavorites() {
        List<Pokemon> favoriteList = loadFavoritesFromPreferences();// Carga la lista de favoritos
        if (favoriteList.isEmpty()) {
            view.showError("No hay favoritos guardados.");// Muestra un error si no hay favoritos
        } else {
            view.showFavoriteList(favoriteList);// Muestra la lista de favoritos
        }
    }
    public void addFavorite(Pokemon pokemon) {
        if (!isFavorite(pokemon)) {// Verifica si el Pokémon no está ya en favoritos
            favoritePokemon.add(pokemon); // Agrega el Pokémon a la lista de favoritos
            saveFavorites();// Guarda la lista de favoritos en las preferencias
            view.showFavoriteList(favoritePokemon);// Actualiza la vista con la nueva lista de favoritos
            view.showSuccess("Pokémon agregado a favoritos.");// Muestra un mensaje de éxito
        } else {
            view.showError("Este Pokémon ya está en tus favoritos.");// Muestra un error si el Pokémon ya está en favoritos
        }
    }
    public void removeFavorite(Pokemon pokemon) {
        if (isFavorite(pokemon)) {// Verifica si el Pokémon está en favoritos
            favoritePokemon.remove(pokemon);// Elimina el Pokémon de la lista de favoritos
            saveFavorites();// Guarda la lista actualizada de favoritos en las preferencias
            view.showFavoriteList(favoritePokemon);// Actualiza la vista con la nueva lista de favoritos
            view.showSuccess("Pokémon eliminado de favoritos.");// Muestra un mensaje de éxito
        } else {
            view.showError("Este Pokémon no está en tus favoritos.");// Muestra un error si el Pokémon no está en favoritos
        }
    }
    private boolean isFavorite(Pokemon pokemon) {
        for (Pokemon fav : favoritePokemon) {// Recorre la lista de favoritos
            if (fav.getNumber() == pokemon.getNumber()) {// Compara el número del Pokémon
                return true;// Retorna true si el Pokémon está en la lista de favoritos
            }
        }
        return false;// Retorna false si el Pokémon no está en la lista de favoritos
    }
    private void saveFavorites() {
        SharedPreferences.Editor editor = sharedPreferences.edit();// Obtiene el editor de preferencias
        Gson gson = new Gson();// Crea una instancia de Gson para convertir objetos a JSON
        String json = gson.toJson(favoritePokemon);// Convierte la lista de favoritos a JSON
        editor.putString("favoriteList", json);// Guarda la lista de favoritos en las preferencias
        editor.apply();// Aplica los cambios
    }
    private List<Pokemon> loadFavoritesFromPreferences() {
        Gson gson = new Gson();// Crea una instancia de Gson para convertir JSON a objetos
        String json = sharedPreferences.getString("favoriteList", null);// Obtiene la lista de favoritos en formato JSON
        Type type = new TypeToken<ArrayList<Pokemon>>() {}.getType();// Define el tipo de la lista de favoritos
        List<Pokemon> favoriteList = gson.fromJson(json, type);// Convierte el JSON a una lista de Pokémon
        return favoriteList != null ? favoriteList : new ArrayList<>();// Retorna la lista de favoritos o una lista vacía si no hay favoritos
    }







}









