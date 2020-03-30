package com.luizcarloscavalcanti.simpledex.Adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.luizcarloscavalcanti.simpledex.Model.Stat;
import com.luizcarloscavalcanti.simpledex.R;

import java.util.ArrayList;

public class ListStatsAdapter extends RecyclerView.Adapter<ListStatsAdapter.ViewHolder> {

    private ArrayList<Stat> dataset;
    private Context context;
    private Stat stat;

    public ListStatsAdapter(Context context) {
        this.context = context;
        dataset = new ArrayList<>();
    }

    @Override
    public ListStatsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context.getApplicationContext()).inflate(R.layout.item_stats, parent, false);

        return new ListStatsAdapter.ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(ListStatsAdapter.ViewHolder holder, int position) {
        stat = dataset.get(position);
        holder.stats.setText(stat.getStat().getName());
        holder.value.setText(Integer.toString(stat.getBaseStat()));

        Glide.with(context)
                .load(Uri.parse("file:///android_asset/stats/") + stat.getStat().getName() + ".png")
                .centerCrop()
                .crossFade()
                .fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.photoStats);

    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

    public void addStatsList(ArrayList<Stat> pokemonStats) {
        dataset.addAll(pokemonStats);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView photoStats;
        TextView stats;
        TextView value;

        public ViewHolder(View itemView) {
            super(itemView);

            photoStats = itemView.findViewById(R.id.imgStats);
            stats = itemView.findViewById(R.id.nameStats);
            value = itemView.findViewById(R.id.valueStats);
        }
    }
}
