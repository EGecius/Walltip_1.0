package com.martynaskairys.walltip.networking;

import com.martynaskairys.walltip.DataTypes.Folder;

import java.util.List;

import retrofit.Callback;
import retrofit.http.GET;

/**
 * List of server API calls
 */
public interface ApiService {

    @GET("/pictures.json")
    void getFolders(Callback<List<Folder>> callback);
}