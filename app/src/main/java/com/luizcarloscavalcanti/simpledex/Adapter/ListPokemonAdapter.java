package com.luizcarloscavalcanti.simpledex.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.luizcarloscavalcanti.simpledex.R;
import com.luizcarloscavalcanti.simpledex.DetailActivity;
import com.luizcarloscavalcanti.simpledex.Model.Pokemon;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ListPokemonAdapter extends RecyclerView.Adapter<ListPokemonAdapter.ViewHolder> implements Filterable{

    private ArrayList<Pokemon> dataset;
    private ArrayList<Pokemon> datasetFull;
    private Pokemon p;

    private Context context;

    public ListPokemonAdapter(Context context) {
        this.context = context;
        dataset = new ArrayList<>();
        datasetFull = new ArrayList<>(dataset);
    }

    @Override
    public ListPokemonAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pokemon, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ListPokemonAdapter.ViewHolder holder, final int position) {
        p = dataset.get(position);
        holder.namePokemon.setText(p.getNumber() + " " +p.getName());

        Glide.with(context)
                .load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/" + p.getNumber() + ".png")
                .centerCrop()
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.photoPokemon);

    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

    public void addPokemonList(ArrayList<Pokemon> pokemonList) {
        dataset.addAll(pokemonList);
        datasetFull.addAll(pokemonList);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    private ImageView photoPokemon;
    private TextView namePokemon;
    private CardView tables;

    public ViewHolder(View itemView) {
        super(itemView);

        photoPokemon = itemView.findViewById(R.id.photoPokemon);
        namePokemon = itemView.findViewById(R.id.namePokemon);
        tables = itemView.findViewById(R.id.tables);
        itemView.setOnClickListener(this);

     }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.tables:
                    Intent intent = new Intent(v.getContext(), DetailActivity.class);
                    intent.putExtra("Pokemon", dataset.get(getAdapterPosition()));
                    v.getContext().startActivity(intent);
                    break;
            }
        }
    }

    @Override
    public Filter getFilter() {
        return searchFilter;
    }

    private Filter searchFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList<Pokemon> filteredList = new ArrayList<>();

            if (constraint.toString().isEmpty()) {
                filteredList.addAll(datasetFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (Pokemon item : datasetFull) {
                    if (item.getName().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            dataset.clear();
            dataset.addAll((ArrayList) results.values);
            notifyDataSetChanged();
        }
    };

    public void sortByName() {
        Collections.sort(dataset, new Comparator<Pokemon>() {
            @Override
            public int compare(Pokemon p1, Pokemon p2){
                return p1.getName().compareTo(p2.getName());
            }
        });
    }

    public void sortByNum() {
        Collections.sort(dataset, new Comparator<Pokemon>() {
            @Override
            public int compare(Pokemon p1, Pokemon p2){
                return - Integer.valueOf(p2.getNumber()).compareTo(Integer.valueOf(p1.getNumber()));
            }
        });
    }

}
