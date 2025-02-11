// MainPresenter.java
package com.example.pokemonapp.presenter;

import com.example.pokemonapp.model.Pokemon;
import com.example.pokemonapp.model.PokemonRespuesta;
import com.example.pokemonapp.pokeApi.PokeApiService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainPresenter implements MainContract.Presenter {
    private MainContract.View view;
    private Retrofit retrofit;

    public MainPresenter(MainContract.View view) {// Constructor del presentador que inicializa la vista y configura Retrofit
        this.view = view;
        this.retrofit = new Retrofit.Builder()
                .baseUrl("https://pokeapi.co/api/v2/") // URL base de la API de Pokémon
                .addConverterFactory(GsonConverterFactory.create())// Convertidor para JSON
                .build();
    }

    @Override
    public void loadPokemonData(int offset) {
        PokeApiService service = retrofit.create(PokeApiService.class);// Crea una instancia del servicio de la API
        Call<PokemonRespuesta> call = service.obtenerListPokemon(20, offset);// Llama al método para obtener la lista de Pokémon con paginacion
        call.enqueue(new Callback<PokemonRespuesta>() {
            @Override
            public void onResponse(Call<PokemonRespuesta> call, Response<PokemonRespuesta> response) {
                if (response.isSuccessful()) {
                    PokemonRespuesta pokemonRespuesta = response.body();// Obtiene la respuesta del cuerpo
                    List<Pokemon> pokemonList = pokemonRespuesta.getResults();// Obtiene la lista de Pokémon
                    view.showPokemonList(pokemonList);// Muestra la lista de Pokémon en la vista
                } else {
                    view.showError("Error: " + response.errorBody());// Muestra un mensaje de error si la respuesta no es exitosa
                }
            }

            @Override
            public void onFailure(Call<PokemonRespuesta> call, Throwable t) {
                view.showError("Failure: " + t.getMessage());// Muestra un mensaje de error si la llamada falla
            }
        });
    }
}

