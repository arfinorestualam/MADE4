package com.example.apkfin4.ui.Film;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apkfin4.R;
import com.example.apkfin4.adapter.FilmAdapter;
import com.example.apkfin4.model.film.Film;
import com.example.apkfin4.ui.Detail.DetailFilmActivity;

import java.util.ArrayList;
import java.util.Locale;

public class FilmFragment extends Fragment {


    View view;
    RecyclerView rvCategory;
    ProgressBar progressBar;
    FilmAdapter filmAdapter;

    private Observer<ArrayList<Film>> getMovies = new Observer<ArrayList<Film>>() {
        @Override
        public void onChanged(ArrayList<Film> films) {
            if (films != null) {
                filmAdapter.addFilm(films);
                show(false);
            }
        }
    };


    private FilmViewModel homeViewModel;

    public FilmFragment() {
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = ViewModelProviders.of(this).get(FilmViewModel.class);
        view = inflater.inflate(R.layout.fragment_film, container, false);
        rvCategory = view.findViewById(R.id.rv1);
        progressBar = view.findViewById(R.id.progress);



        return view;
    }
    @Override
    public void onViewCreated(@NonNull View v, @Nullable Bundle bundle) {
        super.onViewCreated(v,bundle);

        filmAdapter = new FilmAdapter(getContext(), new ArrayList<>(), id -> {
            Intent i = new Intent(getContext(), DetailFilmActivity.class);
            i.putExtra(DetailFilmActivity.ID,id);
            startActivity(i);
        });
        rvCategory.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvCategory.setHasFixedSize(true);
        rvCategory.setAdapter(filmAdapter);
        filmAdapter.notifyDataSetChanged();

        connection();
        show(true);
        viewModel();
        setData();

    }

    private void connection() {
        ConnectivityManager connectivity = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = connectivity.getActiveNetworkInfo();

        if (info != null && info.isConnected()) {
        } else {
            show(false);

        }
    }

    private void setData() {
        String LANGUAiGE = Locale.getDefault().toString();
        if (LANGUAiGE.equals("in_ID")) {
            LANGUAiGE = "id_ID";
        }

        homeViewModel.setMovies(LANGUAiGE);
    }

    private void viewModel() {
        homeViewModel = ViewModelProviders.of(this).get(FilmViewModel.class);
        homeViewModel.getMovies().observe(this, getMovies);
    }

    private void show(Boolean state) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }



}