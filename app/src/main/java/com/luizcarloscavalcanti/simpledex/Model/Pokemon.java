package com.luizcarloscavalcanti.simpledex.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class Pokemon implements Parcelable {

    private int number;
    private String name;
    private String url;

    public Pokemon(int mNumber, String mName, String mUrl) {
        number = mNumber;
        name = mName;
        url = mUrl;
    }

    protected Pokemon(Parcel in) {
        number = in.readInt();
        name = in.readString();
        url = in.readString();
    }

    public static final Creator<Pokemon> CREATOR = new Creator<Pokemon>() {
        @Override
        public Pokemon createFromParcel(Parcel in) {
            return new Pokemon(in);
        }

        @Override
        public Pokemon[] newArray(int size) {
            return new Pokemon[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getNumber() {
        String[] urlParts = url.split("/");
        return Integer.parseInt(urlParts[urlParts.length - 1]);
    }

    public void setNumber(int number) {
        this.number = number;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(number);
        dest.writeString(name);
        dest.writeString(url);
    }
}
