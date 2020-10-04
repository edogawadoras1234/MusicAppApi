package com.example.musicappwithapi.data.network;

import com.example.musicappwithapi.data.db.model.Track;


import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {
    @GET("track")
    Observable<Track> getTrack(
    );
    @GET("search")
    Observable<Track> searchTrack(

    );
}
