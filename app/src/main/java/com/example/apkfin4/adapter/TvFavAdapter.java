package com.example.apkfin4.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.apkfin4.R;
import com.example.apkfin4.model.favorite.Favorite;

import java.util.ArrayList;

import static com.example.apkfin4.api.ApiUtils.IMAGE_URL;

public class TvFavAdapter extends RecyclerView.Adapter<TvFavAdapter.ListViewHolder> {
    private final ArrayList<Favorite> fav = new ArrayList<>();
    private Activity activity;
    private CustomItemListener itemListener;

    public TvFavAdapter(Activity activity, CustomItemListener customItemListener){
        this.activity = activity;
        this.itemListener = customItemListener;}

    public ArrayList<Favorite> getList() {return fav;}

    public void setList(ArrayList<Favorite> list) {
        if(list.size()>0) {this.fav.clear();}
        this.fav.addAll(list);
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public TvFavAdapter.ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_row_favorite,parent,false);

        return new ListViewHolder(view,this.itemListener);
    }

    @Override
    public void onBindViewHolder(@NonNull TvFavAdapter.ListViewHolder holder, int position) {
        holder.bind(fav.get(position));
    }

    @Override
    public int getItemCount() {
        return fav.size();
    }
    private Favorite getItem(int adapterPosition) {return fav.get(adapterPosition);}

    public interface CustomItemListener {void onCustomClick(int mId);}

    public class ListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final CustomItemListener itemListener;
        ImageView img;
        TextView tvName, tvDate;

        public ListViewHolder(@NonNull View itemView, CustomItemListener itemListener) {
            super(itemView);
            img = itemView.findViewById(R.id.img_item_photo);
            tvName = itemView.findViewById(R.id.tv_item_name);
            tvDate = itemView.findViewById(R.id.tv_date);

            this.itemListener = itemListener;
            itemView.setOnClickListener(this);
        }
        public void bind(Favorite favorite) {
            tvDate.setText(favorite.getTitle());
            tvName.setText(favorite.getTitle());
            Glide.with(itemView).load(IMAGE_URL + favorite.getPosterPath()).into(img);
        }

        @Override
        public void onClick(View view) {
            Favorite item = getItem(getAdapterPosition());
            this.itemListener.onCustomClick(item.getId());
            notifyDataSetChanged();
        }
    }
}
