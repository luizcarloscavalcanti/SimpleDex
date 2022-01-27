package com.luizcarloscavalcanti.simpledex.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Type_ {

    @SerializedName("name")
    @Expose
    private String name;

    /**
     * No args constructor for use in serialization
     *
     */
    public Type_() {
    }

    /**
     *
     * @param name
     */
    public Type_(String name, String url) {
        super();
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Type_ withName(String name) {
        this.name = name;
        return this;
    }
}
