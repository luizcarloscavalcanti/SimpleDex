package com.luizcarloscavalcanti.simpledex.Adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.luizcarloscavalcanti.simpledex.Model.RepositorieType;
import com.luizcarloscavalcanti.simpledex.Model.Type;
import com.luizcarloscavalcanti.simpledex.R;

import java.util.ArrayList;

public class ListTypeAdapter extends RecyclerView.Adapter<ListTypeAdapter.ViewHolder> {

    private ArrayList<Type> dataset;
    private Context context;
    private Type type;

    public ListTypeAdapter(Context context) {
        this.context = context;
        dataset = new ArrayList<>();
    }

    @NonNull
    @Override
    public ListTypeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context.getApplicationContext()).inflate(R.layout.item_type, parent, false);

        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ListTypeAdapter.ViewHolder holder, int position) {
        type = dataset.get(position);
        holder.type.setText(type.getType().getName());

        Glide.with(context)
                .load(Uri.parse("file:///android_asset/") + type.getType().getName() + ".png")
                .centerCrop()
                .crossFade()
                .fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.photoType);

    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

    public void addInfoList(ArrayList<Type> pokemonInfo) {
        dataset.addAll(pokemonInfo);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView photoType;
        TextView type;

        public ViewHolder(View itemView) {
            super(itemView);

            photoType = itemView.findViewById(R.id.imgType);
            type = itemView.findViewById(R.id.nameType);
        }
    }
}
