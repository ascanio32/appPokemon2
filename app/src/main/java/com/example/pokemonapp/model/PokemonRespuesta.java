package com.example.pokemonapp.model;

import java.util.ArrayList;
import java.util.List;

public class PokemonRespuesta {// Representa la respuesta de la API que contiene una lista de Pokémon.
    private List<Pokemon> results;   // Lista de Pokémon obtenida de la respuesta de la API

    public List<Pokemon> getResults() { //Obtiene la lista de Pokémon de la respuesta.
        return results;
    }

    public void setResults(ArrayList<Pokemon> results) { //Establece la lista de Pokémon de la respuesta.
        this.results = results;
    }
}
