package com.example.pokemonapp.presenter;

import com.example.pokemonapp.model.Pokemon;
import java.util.List;

public interface FavoritesContract {
    interface View {// Interfaz que define los métodos que la vista debe implementar
        void showFavoriteList(List<Pokemon> favoriteList);// Muestra la lista de Pokémon favoritos
        void showError(String message);// Muestra un mensaje de error
        void showSuccess(String message);// Muestra un mensaje de éxito
    }

    interface Presenter {// Interfaz que define los métodos que el presentador debe implementar
        void loadFavorites(); // Carga la lista de Pokémon favoritos
        void addFavorite(Pokemon pokemon); // Agrega un Pokémon a la lista de favoritos
        void removeFavorite(Pokemon pokemon); // Elimina un Pokémon de la lista de favoritos

    }
}

