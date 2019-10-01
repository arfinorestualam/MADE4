package com.example.apkfin4.ui.Detail;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.Glide;
import com.example.apkfin4.R;
import com.example.apkfin4.db.FavHelper;
import com.example.apkfin4.model.favorite.Favorite;
import com.example.apkfin4.model.film.Film;
import com.example.apkfin4.ui.Film.FilmViewModel;

import java.util.Locale;

import static com.example.apkfin4.api.ApiUtils.IMAGE_URL;
import static com.example.apkfin4.db.DbContract.Columns.BACKDROPPATH;
import static com.example.apkfin4.db.DbContract.Columns.CATEGORY;
import static com.example.apkfin4.db.DbContract.Columns.C_URI;
import static com.example.apkfin4.db.DbContract.Columns.FAVID;
import static com.example.apkfin4.db.DbContract.Columns.OVERVIEW;
import static com.example.apkfin4.db.DbContract.Columns.POSTERPATH;
import static com.example.apkfin4.db.DbContract.Columns.RELEASE_DATE;
import static com.example.apkfin4.db.DbContract.Columns.TITLE;


public class DetailFilmActivity extends AppCompatActivity {

    public static final String ID = "movie_id";


    ImageView imgDrop, imgPost;
    TextView title, date;
    me.biubiubiu.justifytext.library.JustifyTextView sinopsis;
    ProgressBar progressBar;
    private int id,Favid;
    private FavHelper favHelper;
    private String backdrop, poster, overView, tittle, releasedate, category;
    private Menu menu;


    private final Observer<Film> getMovie = new Observer<Film>() {
        @Override
        public void onChanged(Film film) {
            if (film != null) {

                imgDrop.setVisibility(View.VISIBLE);
                imgPost.setVisibility(View.VISIBLE);
                date.setVisibility(View.VISIBLE);
                sinopsis.setVisibility(View.VISIBLE);
                Glide.with(DetailFilmActivity.this).load(IMAGE_URL + film.getBackdrop())
                        .into(imgDrop);
                Glide.with(DetailFilmActivity.this).load(IMAGE_URL + film.getPosterPath())
                        .into(imgPost);
                title.setText(film.getTitle());
                date.setText(film.getReleaseDate());
                sinopsis.setText(film.getOverView());

                Favid = film.getId();
                tittle = film.getTitle();
                poster = film.getPosterPath();
                backdrop = film.getBackdrop();
                releasedate = film.getReleaseDate();
                overView = film.getOverView();

                cekFav();
            }
        }
    };

    private Favorite favorite = new Favorite();
    private FilmViewModel filmViewModel;
    private int MOVIE_ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_film);

        imgDrop = findViewById(R.id.imageView);
        imgPost = findViewById(R.id.imageView2);
        title = findViewById(R.id.etName);
        date = findViewById(R.id.etDate);
        sinopsis = findViewById(R.id.textFrom);
        progressBar = findViewById(R.id.progress);

        connection();
        setupView();
        setupData();
    }

    private void connection() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = connectivityManager.getActiveNetworkInfo();

        if (info != null && info.isConnected()) { progressBar.setVisibility(View.GONE);
        } else { progressBar.setVisibility(View.VISIBLE);
        }
    }

    private void setupView() {
        filmViewModel = ViewModelProviders.of(this).get(FilmViewModel.class);
        filmViewModel.getMovie().observe(this, getMovie);
    }

    private void setupData() {
        favHelper = new FavHelper(getApplicationContext());
        favHelper.open();
        MOVIE_ID = getIntent().getIntExtra(ID,MOVIE_ID);
        String language = Locale.getDefault().toString();
        if (language.equals("in_ID")) {
            language = "id_ID";
        }
        filmViewModel.setMovie(MOVIE_ID, language);
    }
    @Override
    public boolean onCreateOptionsMenu (Menu menu) {
        getMenuInflater().inflate(R.menu.fav,menu);
        this.menu = menu;
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(cekFav()) {
            Uri uri = Uri.parse(C_URI + "/" + id);
            getContentResolver().delete(uri,null,null);
            item.setIcon(R.drawable.ic_favorite_border_24dp);
            Toast.makeText(this,getString(R.string.unFav), Toast.LENGTH_LONG).show();}
        else {
            favorite.setId(Favid);
            favorite.setTitle(tittle);
            favorite.setPosterPath(poster);
            favorite.setBackdropPath(backdrop);
            favorite.setReleasedate(releasedate);
            favorite.setOverView(overView);
            favorite.setCategoty("movie");

            ContentValues contentValues = new ContentValues();
            contentValues.put(FAVID,Favid);
            contentValues.put(TITLE,tittle);
            contentValues.put(OVERVIEW,overView);
            contentValues.put(BACKDROPPATH,backdrop);
            contentValues.put(RELEASE_DATE,releasedate);
            contentValues.put(POSTERPATH,poster);
            contentValues.put(CATEGORY,"movie");

            if (getContentResolver().insert(C_URI,contentValues) != null) {
                Toast.makeText(this,tittle + " " + getString(R.string.Fav), Toast.LENGTH_LONG).show();
                item.setIcon(R.drawable.ic_favorite);
            } else { Toast.makeText(this, tittle + " " + getString(R.string.favEror), Toast.LENGTH_LONG).show();}
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean cekFav() {
        Uri uri = Uri.parse(C_URI +  "");
        boolean favorite = false;
        Cursor cursor = getContentResolver().query(uri,null,null,null,null);
        int getFavid;
        if (cursor.moveToFirst()) {
            do {
                getFavid = cursor.getInt(1);
                if (getFavid == Favid) {
                    id = cursor.getInt(0);
                    menu.getItem(0).setIcon(ContextCompat.getDrawable(this,R.drawable.ic_favorite));
                    favorite = true;
                }
            } while (cursor.moveToNext());
        }
        return favorite;
    }
}
