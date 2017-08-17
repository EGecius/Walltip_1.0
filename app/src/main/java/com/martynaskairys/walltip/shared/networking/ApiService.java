package com.martynaskairys.walltip.shared.networking;

import com.martynaskairys.walltip.shared.datatypes.Folder;

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