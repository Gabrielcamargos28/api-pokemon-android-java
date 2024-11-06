package com.example.consuming_api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
public interface PokemonService {
    @GET("pokemon/{id}")
    Call<Pokemon> getPokemon(@Path("id") int id);
    @GET("pokemon/{name}")
    Call<Pokemon> getPokemonByName(@Path("name") String name);
}
