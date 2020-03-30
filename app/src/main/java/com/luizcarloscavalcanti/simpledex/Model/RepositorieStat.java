package com.luizcarloscavalcanti.simpledex.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class RepositorieStat {

    @SerializedName("stats")
    @Expose
    private ArrayList<Stat> stats = null;

    /**
     * No args constructor for use in serialization
     *
     */
    public RepositorieStat() {
    }

    /**
     *
     * @param stats
     */
    public RepositorieStat(ArrayList<Stat> stats) {
        super();
        this.stats = stats;
    }

    public ArrayList<Stat> getStats() {
        return stats;
    }

    public void setStats(ArrayList<Stat> stats) {
        this.stats = stats;
    }
}
