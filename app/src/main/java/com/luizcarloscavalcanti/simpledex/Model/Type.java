package com.luizcarloscavalcanti.simpledex.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Type {

    @SerializedName("slot")
    @Expose
    private String slot;
    @SerializedName("type")
    @Expose
    private Type_ type;


    /**
     * No args constructor for use in serialization
     *
     */
    public Type() {
    }

    /**
     *
     * @param slot
     * @param name
     */
    public Type(String slot, Type_ name) {
        super();
        this.slot = slot;
        this.type = name;
    }

    public String getSlot() {
        return slot;
    }

    public void setSlot(String slot) {
        this.slot = slot;
    }

    public Type withSlot(String slot) {
        this.slot = slot;
        return this;
    }

    public Type_ getType() {
        return type;
    }

    public void setType(Type_ type) {
        this.type = type;
    }

    public Type withType(Type_ type) {
        this.type = type;
        return this;
    }
}
