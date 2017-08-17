package com.martynaskairys.walltip.datatypes;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * A folder containing image urls
 */
public class Folder implements Parcelable {

    private String title;
    private List<String> urls;


    public Folder() {
    }

    protected Folder(Parcel in) {
        title = in.readString();
        urls = in.createStringArrayList();
    }

    public static final Creator<Folder> CREATOR = new Creator<Folder>() {
        @Override
        public Folder createFromParcel(Parcel in) {
            return new Folder(in);
        }

        @Override
        public Folder[] newArray(int size) {
            return new Folder[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeStringList(urls);
    }

    public Folder(String title, List<String> urls) {
        this.title = title;
        this.urls = urls;
    }

    public String getTitle() {
        return title;
    }

    public List<String> getUrls() {
        return urls;
    }
}
