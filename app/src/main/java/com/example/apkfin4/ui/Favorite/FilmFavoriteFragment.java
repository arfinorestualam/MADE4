package com.example.apkfin4.ui.Favorite;


import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.HandlerThread;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.apkfin4.R;
import com.example.apkfin4.adapter.FavAdapter;
import com.example.apkfin4.db.FavHelper;
import com.example.apkfin4.model.favorite.Favorite;
import com.example.apkfin4.ui.Detail.DetailFilmActivity;
import com.example.apkfin4.ui.Detail.DetailTvActivity;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Objects;

import static com.example.apkfin4.db.DbContract.Columns.C_URI;
import static com.example.apkfin4.db.FavHelper.getFilmFavorite;


/**
 * A simple {@link Fragment} subclass.
 */
public class FilmFavoriteFragment extends Fragment {
    private static final String BLUE = "EXTRA_STATE";
    private RecyclerView rvCategory;
    private FavAdapter favAdapter;
    private FavHelper favHelper;
    private FavoritesCallback callback;


    public FilmFavoriteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_film_favorite, container, false);

        return view;}

    @Override
    public void onViewCreated(@NonNull View v, @Nullable Bundle bundle) {
        super.onViewCreated(v,bundle);
        rvCategory = v.findViewById(R.id.rvCategory);
        setView();

        if(bundle == null) {new FavAsync(getContext(),callback).execute();}
        else {
            ArrayList<Favorite> favorites = bundle.getParcelableArrayList(BLUE);
            if (favorites != null) {favAdapter.setList(favorites);}
        }}

    private void setView() {
        callback = new FavoritesCallback() {
            @Override
            public void preExecute() {}

            @Override
            public void postExecute(Cursor cursor) {
                ArrayList<Favorite> arrayList = getFilmFavorite(cursor);
                if (arrayList.size() > 0) {
                    favAdapter.setList(arrayList);
                    rvCategory.setVisibility(View.VISIBLE);
                }
                else {
                    favAdapter.setList(new ArrayList<>());
                    rvCategory.setVisibility(View.GONE);
                }}};
        HandlerThread handler = new HandlerThread("DataObserver");
        handler.start();
        Handler handlerl = new Handler(handler.getLooper());
        DataObserver observer = new DataObserver(handlerl, getContext(),callback);
        Objects.requireNonNull(getActivity()).getContentResolver().registerContentObserver(C_URI, true, observer);

        rvCategory.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvCategory.setHasFixedSize(true);

        favHelper = FavHelper.getInstance(getContext());
        favHelper.open();

        favAdapter = new FavAdapter(getActivity(), id -> {
            Intent i = new Intent(getActivity(), DetailFilmActivity.class);
            i.putExtra(DetailFilmActivity.ID, id);
            startActivity(i);
        });
        rvCategory.setAdapter(favAdapter);}

    @Override
    public void onSaveInstanceState (Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putParcelableArrayList(BLUE, favAdapter.getList());
    }



    interface FavoritesCallback {
        void preExecute();
        void postExecute(Cursor cursor);
    }

    private static class FavAsync extends AsyncTask<Void,Void,Cursor> {
        private WeakReference<Context> contextWeakReference;
        private WeakReference<FavoritesCallback> callbackWeakReference;

        private FavAsync(Context context,FavoritesCallback favoritesCallback) {
            contextWeakReference = new WeakReference<>(context);
            callbackWeakReference = new WeakReference<>(favoritesCallback);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            callbackWeakReference.get().preExecute();
        }

        @Override
        protected Cursor doInBackground(Void... voids) {
            Context context = contextWeakReference.get();
            return context.getContentResolver().query(C_URI,null,null,null,null);
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            super.onPostExecute(cursor);
            callbackWeakReference.get().postExecute(cursor);
        }}

    static class DataObserver extends ContentObserver {
        Context context;
        FavoritesCallback callback;
        DataObserver(Handler handler, Context context, FavoritesCallback callback) {
            super(handler);
            this.context = context;
            this.callback = callback;

        }

        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);
            new FavAsync(context,callback).execute();
        }}

}
