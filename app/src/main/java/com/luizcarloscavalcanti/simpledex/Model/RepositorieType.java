package com.luizcarloscavalcanti.simpledex.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class RepositorieType<T> {

    @SerializedName("types")
    @Expose
    private ArrayList<Type> types = null;

    /**
     * No args constructor for use in serialization
     *
     */
    public RepositorieType() {
    }

    /**
     *
     * @param types
     */
    public RepositorieType(ArrayList<Type> types) {
        super();
        this.types = types;
    }

    public ArrayList<Type> getTypes() {
        return types;
    }

    public void setTypes(ArrayList<Type> types) {
        this.types = types;
    }

    public RepositorieType<T> withTypes(ArrayList<Type> types) {
        this.types = types;
        return this;
    }

}
