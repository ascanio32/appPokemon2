package com.example.pokemonapp;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pokemonapp.model.Pokemon;
import com.example.pokemonapp.model.PokemonRespuesta;
import com.example.pokemonapp.pokeApi.PokeApiService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "POKEMONAPP";
    private Retrofit retrofit;

    private RecyclerView recyclerView;
    private ListaPokemonAdapter listaPokemonAdapter;

    private int offSet;

    private boolean actoParaCargar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        recyclerView = (RecyclerView) findViewById(R.id.rv);
        listaPokemonAdapter = new ListaPokemonAdapter(this);
        recyclerView.setAdapter(listaPokemonAdapter);
        recyclerView.setHasFixedSize(true);
        GridLayoutManager layoutManager = new GridLayoutManager(this,3);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (dy > 0){
                  int visibleItemAcount = layoutManager.getChildCount();
                  int totalItemCount = layoutManager.getItemCount();
                    int pastVisibleItems = layoutManager.findFirstVisibleItemPosition();

                  if (actoParaCargar){
                      if ((visibleItemAcount + pastVisibleItems) >= totalItemCount){
                          Log.i (TAG, "Llegamos al final. ");

                          actoParaCargar=false;
                          offSet +=20;
                          obtenerDatos(offSet);

                      }
                  }
                }
            }
        });

        retrofit = new Retrofit.Builder()
                .baseUrl("https://pokeapi.co/api/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        actoParaCargar = true;
        offSet=0;
        obtenerDatos(offSet);

    }
    private void obtenerDatos(int offSet){
        PokeApiService service = retrofit.create(PokeApiService.class);
        Call<PokemonRespuesta> pokemonRespuestaCall =service.obtenerListPokemon(20,offSet);
        pokemonRespuestaCall.enqueue(new Callback<PokemonRespuesta>() {
            @Override
            public void onResponse(Call<PokemonRespuesta> call, Response<PokemonRespuesta> response) {
                actoParaCargar = true;
               if (response.isSuccessful()){
                   PokemonRespuesta pokemonRespuesta =   response.body();
                   ArrayList<Pokemon> listapokemon = pokemonRespuesta.getResults();

                   listaPokemonAdapter.adicionarListaPokemon(listapokemon);


               }else {
                   Log.e(TAG,"onResponse: " + response.errorBody());

               }
            }

            @Override
            public void onFailure(Call<PokemonRespuesta> call, Throwable t) {
                actoParaCargar = true;
                Log.e(TAG, "onFailure: " + t.getMessage());

            }
        });

    }
}