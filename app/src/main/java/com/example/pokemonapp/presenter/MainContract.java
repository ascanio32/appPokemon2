package com.example.pokemonapp.presenter;

import com.example.pokemonapp.model.Pokemon;
import java.util.ArrayList;
import java.util.List;

public interface MainContract {
    interface View {// Interfaz que define los métodos que la vista debe implementar
        void showPokemonList(List<Pokemon> pokemonList);// Muestra la lista de Pokémon
        void showError(String message);// Muestra un mensaje de error
    }

    interface Presenter {// Interfaz que define los métodos que el presentador debe implementar
        void loadPokemonData(int offset);// Carga los datos de Pokémon con un desplazamiento específico
    }
}

