package com.luizcarloscavalcanti.simpledex;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.luizcarloscavalcanti.simpledex.Adapter.ListStatsAdapter;
import com.luizcarloscavalcanti.simpledex.Adapter.ListTypeAdapter;
import com.luizcarloscavalcanti.simpledex.Model.Pokemon;
import com.luizcarloscavalcanti.simpledex.Model.Stat;
import com.luizcarloscavalcanti.simpledex.Model.StatsAnswer;
import com.luizcarloscavalcanti.simpledex.Model.Type;
import com.luizcarloscavalcanti.simpledex.Model.TypeAnswer;
import com.luizcarloscavalcanti.simpledex.Retrofit.PokeService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DetailActivity extends AppCompatActivity {

    private static final String TAG = "POKEDEX";
    private Retrofit retrofit;
    private ListTypeAdapter listTypeAdapter;
    private ListStatsAdapter listStatsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView imageView = findViewById(R.id.imageView);
        TextView textView = findViewById(R.id.textView);
        RecyclerView recyclerView = findViewById(R.id.recyclerType);
        RecyclerView recyclerViewStats = findViewById(R.id.recyclerStats);

        final GridLayoutManager layoutManagerType = new GridLayoutManager(this, 3);
        final GridLayoutManager layoutManagerStat = new GridLayoutManager(this, 3);

        recyclerView.setHasFixedSize(true);
        recyclerViewStats.setHasFixedSize(true);

        recyclerView.setLayoutManager(layoutManagerType);
        recyclerViewStats.setLayoutManager(layoutManagerStat);

        Intent intent = getIntent();
        final Pokemon pokemon = intent.getParcelableExtra("Pokemon");

        assert pokemon != null;
        int number = pokemon.getNumber();
        String t1 = pokemon.getName();

        Glide.with(this)
                .load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/" + number + ".png")
                .placeholder(R.drawable.ic_sync)
                .error(R.mipmap.ic_launcher)
                .into(imageView);

        textView.setText(" #" + number + " - " + t1.toUpperCase());

        listTypeAdapter = new ListTypeAdapter(this);
        listStatsAdapter = new ListStatsAdapter(this);
        recyclerView.setAdapter(listTypeAdapter);
        recyclerViewStats.setAdapter(listStatsAdapter);

        retrofit = new Retrofit.Builder()
                .baseUrl("https://pokeapi.co/api/v2/pokemon/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        getDataType(number);
        getDataStats(number);

    }

    private void getDataType(int id) {
        PokeService service = retrofit.create(PokeService.class);
        final Call<TypeAnswer> typeAnswerCall = service.getInfoList(id);

        typeAnswerCall.enqueue(new Callback<TypeAnswer>() {
            @Override
            public void onResponse(@NonNull Call<TypeAnswer> call, @NonNull Response<TypeAnswer> response) {

                if (response.isSuccessful()){

                    TypeAnswer typeAnswer = response.body();
                    assert typeAnswer != null;
                    ArrayList<Type> pokemontype = typeAnswer.getTypes();

                    listTypeAdapter.addInfoList(pokemontype);

                    for (int i = 0; i < pokemontype.size(); i++) {
                        Type t = pokemontype.get(i);
                        Log.i(TAG, " Type: " + t.getType());
                    }

                } else {
                    Log.e(TAG, " onResponse: " + response.errorBody());
                }
            }

            @Override
            public void onFailure(@NonNull Call<TypeAnswer> call, @NonNull Throwable t) {

                Log.e(TAG, " onFailure: " + t.getMessage());
            }
        });
    }

    private void getDataStats(int id) {
        PokeService service = retrofit.create(PokeService.class);
        final Call<StatsAnswer> statsAnswerCall = service.getStatList(id);

        statsAnswerCall.enqueue(new Callback<StatsAnswer>() {
            @Override
            public void onResponse(@NonNull Call<StatsAnswer> call, @NonNull Response<StatsAnswer> response) {

                if (response.isSuccessful()){

                    StatsAnswer statsAnswer = response.body();
                    assert statsAnswer != null;
                    ArrayList<Stat> pokemonStat = statsAnswer.getStats();

                    listStatsAdapter.addStatsList(pokemonStat);

                    for (int i = 0; i < pokemonStat.size(); i++) {
                        Stat s = pokemonStat.get(i);
                        Log.i(TAG, " Type: " + s.getStat());
                    }

                } else {
                    Log.e(TAG, " onResponse: " + response.errorBody());
                }
            }

            @Override
            public void onFailure(@NonNull Call<StatsAnswer> call, @NonNull Throwable t) {

                Log.e(TAG, " onFailure: " + t.getMessage());
            }
        });
    }

}
