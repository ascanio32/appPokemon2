package com.example.pokemonapp.pokeApi;

import com.example.pokemonapp.model.Pokemon;
import com.example.pokemonapp.model.PokemonRespuesta;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface PokeApiService {  //Interfaz que define los métodos para interactuar con la API de Pokémon.
    //limit el número máximo de Pokémon a obtener
    //offset el desplazamiento desde el inicio de la lista de Pokémon
    //una llamada que devuelve un objeto PokemonRespuesta.
    @GET("pokemon")
    Call<PokemonRespuesta>obtenerListPokemon(@Query("limit") int limit, @Query("offset") int offset);


    //Obtiene los detalles de un Pokémon específico por su ID.
    //id el ID del Pokémon"paramtro"
    //una llamada que devuelve un objeto Pokemon "retorno"
    @GET("pokemon/{id}")
    Call<Pokemon> getPokemonDetails(@Path("id") int id);
}
