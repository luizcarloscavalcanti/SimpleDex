package com.luizcarloscavalcanti.simpledex.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Stat {

    @SerializedName("base_stat")
    @Expose
    private int baseStat;
    @SerializedName("effort")
    @Expose
    private int effort;
    @SerializedName("stat")
    @Expose
    private Stat_ stat;

    /**
     * No args constructor for use in serialization
     *
     */
    public Stat() {
    }

    /**
     *
     * @param stat
     * @param baseStat
     * @param effort
     */
    public Stat(int baseStat, int effort, Stat_ stat) {
        super();
        this.baseStat = baseStat;
        this.effort = effort;
        this.stat = stat;
    }

    public int getBaseStat() {
        return baseStat;
    }

    public void setBaseStat(int baseStat) {
        this.baseStat = baseStat;
    }

    public int getEffort() {
        return effort;
    }

    public void setEffort(int effort) {
        this.effort = effort;
    }

    public Stat_ getStat() {
        return stat;
    }

    public void setStat(Stat_ stat) {
        this.stat = stat;
    }

}
