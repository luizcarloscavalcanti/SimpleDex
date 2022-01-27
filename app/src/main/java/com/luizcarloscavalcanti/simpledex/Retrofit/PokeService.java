package com.luizcarloscavalcanti.simpledex.Retrofit;

import com.luizcarloscavalcanti.simpledex.Model.PokemonAnswer;
import com.luizcarloscavalcanti.simpledex.Model.StatsAnswer;
import com.luizcarloscavalcanti.simpledex.Model.TypeAnswer;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface PokeService {

    @GET("pokemon")
    Call<PokemonAnswer> getPokemonList(
            @Query("limit") int limit,
            @Query("offset") int offset);

    @GET("{id}")
    Call<TypeAnswer> getInfoList(
            @Path("id") int id);

    @GET("{id}")
    Call<StatsAnswer> getStatList(
            @Path("id") int id);

}
