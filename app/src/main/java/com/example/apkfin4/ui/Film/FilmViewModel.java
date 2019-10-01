package com.example.apkfin4.ui.Film;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


import com.example.apkfin4.api.ApiInterface;
import com.example.apkfin4.api.ApiUtils;
import com.example.apkfin4.model.film.Film;
import com.example.apkfin4.model.film.FilmResponse;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


import static com.example.apkfin4.api.ApiUtils.API_KEY;

public class FilmViewModel extends ViewModel {

    private MutableLiveData<ArrayList<Film>> listFilm = new MutableLiveData<>();
    private MutableLiveData<Film> film = new MutableLiveData<>();

    private ApiInterface api = new ApiUtils().getApi();

    public LiveData<ArrayList<Film>> getMovies() {return listFilm;}

   public void setMovies(final String language) {
        api.getMovies(API_KEY,language).enqueue(new Callback<FilmResponse>() {
            @Override
            public void onResponse(Call<FilmResponse> call, Response<FilmResponse> response) {
                if (response.isSuccessful()) {
                    ArrayList<Film> films = new ArrayList<>();
                    films.addAll(response.body().getMovies());
                    listFilm.postValue(films);
                    Log.d("film","sukses");
                } else  { int status = response.code(); }
            }

            @Override
            public void onFailure(Call<FilmResponse> call, Throwable t) {
                Log.d("film", "gagal");
            }
        });
   }
    public LiveData<Film> getMovie() {
        return film;
    }

    public void setMovie(int tv_id,String language) {
        api.getMovie(tv_id,API_KEY,language).enqueue(new Callback<Film>() {
            @Override
            public void onResponse(Call<Film> call, Response<Film> response) {
                if (response.isSuccessful()) {
                    film.postValue(response.body()); }
                else { int status = response.code();}
            }

            @Override
            public void onFailure(Call<Film> call, Throwable t) {
                Log.d("Detail", "gagal load api");
            }
        });
    }
}