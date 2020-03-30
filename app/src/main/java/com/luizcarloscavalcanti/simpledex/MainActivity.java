package com.luizcarloscavalcanti.simpledex;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;

import com.luizcarloscavalcanti.simpledex.Adapter.ListPokemonAdapter;
import com.luizcarloscavalcanti.simpledex.R;
import com.luizcarloscavalcanti.simpledex.Model.Pokemon;
import com.luizcarloscavalcanti.simpledex.Model.PokemonAnswer;
import com.luizcarloscavalcanti.simpledex.Retrofit.PokeService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "POKEDEX";

    private Toolbar mToolbar;
    private Toolbar mToolbarBottom;

    private Retrofit retrofit;

    private RecyclerView recyclerView;
    private ListPokemonAdapter listPokemonAdapter;

    private int offset;

    private boolean aptCharge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToolbar = findViewById(R.id.tb_main);
        mToolbar.setTitle("Simple Dex");
        mToolbar.setSubtitle("PokéDex");
        mToolbar.setLogo(R.drawable.ic_pokedex);
        setSupportActionBar(mToolbar);

        mToolbarBottom = findViewById(R.id.inc_tb_bottom);
        mToolbarBottom.findViewById(R.id.iv_top).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerView.smoothScrollToPosition(0);
            }
        });

        mToolbarBottom.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.action_alphabetic:
                        listPokemonAdapter.sortByName();
                        listPokemonAdapter.notifyDataSetChanged();
                    break;
                    case R.id.action_numeric:
                        listPokemonAdapter.sortByNum();
                        listPokemonAdapter.notifyDataSetChanged();
                        break;
                }

                return true;
            }
        });

        mToolbarBottom.inflateMenu(R.menu.menu_bottom);

        recyclerView = findViewById(R.id.recyclerView);
        listPokemonAdapter = new ListPokemonAdapter(this);
        recyclerView.setAdapter(listPokemonAdapter);
        recyclerView.setHasFixedSize(true);
        final GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (dy >0) {
                    int visibleItemcount = layoutManager.getChildCount();
                    int totalItemCount = layoutManager.getItemCount();
                    int pastVisibleItems = layoutManager.findFirstVisibleItemPosition();

                    if (aptCharge) {
                        if ((visibleItemcount + pastVisibleItems) >= totalItemCount) {
                            Log.i(TAG, " Final");

                            aptCharge = false;
                            offset += 20;
                            getData(offset);
                        }
                    }
                }
            }
        });

        retrofit = new Retrofit.Builder()
                    .baseUrl("https://pokeapi.co/api/v2/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            aptCharge = true;
            offset = 0;
            getData(offset);
        }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        final MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                listPokemonAdapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }

    private void getData(int offset) {
            PokeService service = retrofit.create(PokeService.class);
            final Call<PokemonAnswer> pokemonAnswerCall = service.getPokemonList(2000, offset);

            pokemonAnswerCall.enqueue(new Callback<PokemonAnswer>() {
                @Override
                public void onResponse(Call<PokemonAnswer> call, Response<PokemonAnswer> response) {
                    aptCharge = true;
                    if (response.isSuccessful()){

                        PokemonAnswer pokemonAnswer = response.body();
                        ArrayList<Pokemon> pokemonList = pokemonAnswer.getResults();

                        listPokemonAdapter.addPokemonList(pokemonList);

                        for (int i = 0; i < pokemonList.size(); i++) {
                            Pokemon p = pokemonList.get(i);
                            Log.i(TAG, p.getNumber() + " Pokemon: " + p.getName());
                        }

                    } else {
                        Log.e(TAG, " onResponse: " + response.errorBody());
                    }
                }

                @Override
                public void onFailure(Call<PokemonAnswer> call, Throwable t) {
                    aptCharge = true;
                    Log.e(TAG, " onFailure: " + t.getMessage());
                }
            });
        }
    }
