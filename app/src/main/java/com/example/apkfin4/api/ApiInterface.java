package com.example.apkfin4.api;

import com.example.apkfin4.model.film.Film;
import com.example.apkfin4.model.film.FilmResponse;
import com.example.apkfin4.model.tv.Tv;
import com.example.apkfin4.model.tv.TvResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface ApiInterface {

    @GET("discover/movie?")
    Call<FilmResponse> getMovies(
            @Query("api_key") String apiKey,
            @Query("language") String language
    );

    @GET("discover/tv?")
    Call<TvResponse> getTv(
            @Query("api_key") String apiKey,
            @Query("language") String language
    );

    @GET("movie/{movie_id}")
    Call<Film> getMovie(
            @Path("movie_id") int id,
            @Query("api_key") String apiKEy,
            @Query("language") String language
    );

    @GET("tv/{tv_id}")
    Call<Tv> getTvs(
            @Path("tv_id") int id,
            @Query("api_key") String apiKEy,
            @Query("language") String language
    );


}
