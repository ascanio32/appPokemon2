package com.example.pokemonapp.pokeApi;

import com.example.pokemonapp.model.PokemonRespuesta;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface PokeApiService {
    @GET("pokemon")
    Call<PokemonRespuesta>obtenerListPokemon(@Query("limit") int limit, @Query("offset") int offset);
}
