package com.example.apkfin4.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.apkfin4.R;
import com.example.apkfin4.model.film.Film;

import java.util.List;

import static com.example.apkfin4.api.ApiUtils.IMAGE_URL;

public class FilmAdapter extends RecyclerView.Adapter<FilmAdapter.ListViewHolder> {
   
    private final Context context;
    private final CustomItemListener itemListener;
    private List<Film> item;
    
    public FilmAdapter(Context context, List<Film> films, CustomItemListener listener) {

        this.context = context;
        this.item = films;
        this.itemListener = listener;
    }
    
    
   
   
    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_row_film, parent,false );
        
        return new ListViewHolder(view, this.itemListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, int position) {
        holder.bind(item.get(position));
    }

    @Override
    public int getItemCount() { return item.size(); }

    public void addFilm(List<Film> each) {
        item = each;
        notifyDataSetChanged();
    }

    public Film getItem(int adapterPosition) {return item.get(adapterPosition);}

    public interface CustomItemListener{
        void onCustomClick(int id);
    }

    public class ListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private final CustomItemListener itemListener;
        ImageView imgPhoto;
        TextView tvName,tvFrom,tvDate;

        public ListViewHolder(@NonNull View itemView, CustomItemListener item) {
            super(itemView);
            imgPhoto = itemView.findViewById(R.id.img_item_photo);
            tvName = itemView.findViewById(R.id.tv_item_name);
            tvFrom = itemView.findViewById(R.id.tv_item_from);
            tvDate = itemView.findViewById(R.id.tv_date);

            this.itemListener = item;
            itemView.setOnClickListener(this);
        }

        public void bind(Film film) {
            tvName.setText(film.getTitle());
            tvFrom.setText(film.getOverView());
            tvDate.setText(film.getReleaseDate().split("-")[0]);
            Glide.with(itemView)
                    .load(IMAGE_URL + film.getPosterPath())
                    .apply(RequestOptions.overrideOf(100,100))
                    .into(imgPhoto);
        }

        @Override
        public void onClick(View view) {
            Film item = getItem(getAdapterPosition());
            this.itemListener.onCustomClick(item.getId());
            notifyDataSetChanged();
        }
    }
}
