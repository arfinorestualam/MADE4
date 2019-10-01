package com.example.apkfin4.model.favorite;

import android.os.Parcel;
import android.os.Parcelable;

public class Favorite implements Parcelable {
    private int id;
    private int mId;
    private String backdropPath;
    private String posterPath;
    private String title;
    private String overview;
    private String releasedate;
    private String category;

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public int getmId() { return mId;  }

    public void setmId(int mId) { this.mId = mId; }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOverView() {
        return overview;
    }

    public void setOverView(String overview) {
        this.overview = overview;
    }

    public String getReleasedate() { return releasedate; }

    public void setReleasedate(String releasedate) { this.releasedate = releasedate; }

    public String getCategory() {return category;}
    public void setCategoty(String categoty) {
        this.category = categoty;
    }

    public Favorite() {
    }
    public Favorite(int id, int mId, String title, String backdrop, String posterpath, String overview, String release, String category) {
        this.id = id;
        this.mId = mId;
        this.backdropPath = backdrop;
        this.posterPath = posterpath;
        this.overview = overview;
        this.title = title;
        this.category = category;
    }

    protected Favorite(Parcel in) {
        id = in.readInt();
        mId = in.readInt();
        backdropPath = in.readString();
        posterPath = in.readString();
        title = in.readString();
        overview = in.readString();
        releasedate = in.readString();
        category = in.readString();
    }

    public static final Creator<Favorite> CREATOR = new Creator<Favorite>() {
        @Override
        public Favorite createFromParcel(Parcel in) {
            return new Favorite(in);
        }

        @Override
        public Favorite[] newArray(int size) {
            return new Favorite[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeInt(mId);
        parcel.writeString(backdropPath);
        parcel.writeString(posterPath);
        parcel.writeString(title);
        parcel.writeString(overview);
        parcel.writeString(releasedate);
        parcel.writeString(category);
    }
}
