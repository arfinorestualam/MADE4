package com.example.apkfin4.ui.TvShow;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.apkfin4.api.ApiInterface;
import com.example.apkfin4.api.ApiUtils;
import com.example.apkfin4.model.tv.Tv;
import com.example.apkfin4.model.tv.TvResponse;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.apkfin4.api.ApiUtils.API_KEY;

public class TvShowViewModel extends ViewModel {

    private MutableLiveData<ArrayList<Tv>> listTv = new MutableLiveData<>();
    private MutableLiveData<Tv> tv = new MutableLiveData<>();
    private ApiInterface api = new ApiUtils().getApi();

    public LiveData<ArrayList<Tv>> getTv() {return  listTv;}

    public void setTv (final String language) {
        api.getTv(API_KEY, language).enqueue(new Callback<TvResponse>() {
            @Override
            public void onResponse(Call<TvResponse> call, Response<TvResponse> response) {
                if(response.isSuccessful()) {
                    ArrayList<Tv> tv = new ArrayList<>();
                    tv.addAll(response.body().getTvs());
                    listTv.postValue(tv);
                    Log.d("Tv","Sukses");
                } else { int status = response.code();}
            }

            @Override
            public void onFailure(Call<TvResponse> call, Throwable t) { Log.d("Tv","gagal");}
        });
    }
    public LiveData<Tv> getTvs(){ return tv;}

    public void setTvs(int tv_id, String language) {
        api.getTvs(tv_id,API_KEY,language).enqueue(new Callback<Tv>() {
            @Override
            public void onResponse(Call<Tv> call, Response<Tv> response) {
                if (response.isSuccessful()){
                    tv.postValue(response.body());
                } else {int status = response.code();}
            }

            @Override
            public void onFailure(Call<Tv> call, Throwable t) {

                Log.d("Detail", "gagal load api");
            }
        });
}}

