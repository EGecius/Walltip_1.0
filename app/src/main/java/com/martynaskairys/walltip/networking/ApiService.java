package com.martynaskairys.walltip.networking;

import com.martynaskairys.walltip.DataTypes.Folder;

import java.util.List;
import retrofit.http.GET;
import retrofit.Callback;
import retrofit.http.Query;

/**
 * List of server API calls
 */
public interface ApiService {

//	@GET ("/folders.json.json?search=${searchParam}")
//	void getFolders(Callback<List<Folder>> callback, @Query("searchParam") String searchKeyword);

//	@GET ("/nuodaia")
	@GET ("/folders.json.json")
	void getFolders(Callback<List<Folder>> callback);
}
