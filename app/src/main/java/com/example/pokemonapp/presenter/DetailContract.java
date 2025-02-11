// DetailContract.java
package com.example.pokemonapp.presenter;

import android.content.Context;

import com.example.pokemonapp.model.Pokemon;

public interface DetailContract {  //Contrato que define la comunicación entre la vista y el presentador para la pantalla de detalles de un Pokémon.
    interface View {//Interfaz que define los métodos que la vista debe implementar.
        void showPokemonDetails(Pokemon pokemon); //Muestra los detalles de un Pokémon
        void showError(String message);//Muestra un mensaje de error.
        void updateFavoriteStatus(boolean isFavorite);//Actualiza el estado de favorito del Pokémon.
        void navigateToFavorites();//Navega a la pantalla de favoritos.
        void showSuccess(String message);//Muestra un mensaje de éxito.

    }

    interface Presenter {//Interfaz que define los métodos que el presentador debe implementar.
        void loadPokemonDetails(int pokemonNumber);//Carga los detalles de un Pokémon.
        void toggleFavorite(Pokemon pokemon, Context context); //Alterna el estado de favorito de un Pokémon.
        void checkIfFavorite(Pokemon pokemon);//Verifica si un Pokémon es favorito.
    }
}

