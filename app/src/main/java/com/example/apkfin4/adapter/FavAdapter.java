package com.example.apkfin4.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.apkfin4.R;
import com.example.apkfin4.model.favorite.Favorite;

import java.util.ArrayList;

import static com.example.apkfin4.api.ApiUtils.IMAGE_URL;

public class FavAdapter extends RecyclerView.Adapter<FavAdapter.ListViewHolder> {
    private final ArrayList<Favorite> fav = new ArrayList<>();
    private Activity activity;
    private CustomItemListener itemListener;


    public FavAdapter(Activity activity, CustomItemListener customItemListener){
        this.activity = activity;
        this.itemListener = customItemListener;}



    public ArrayList<Favorite> getList() {return fav;}

    public void setList(ArrayList<Favorite> list) {
        if(list.size()>0) {this.fav.clear();}
        fav.addAll(list);
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public FavAdapter.ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_row_favorite,parent,false);

        return new ListViewHolder(view,this.itemListener);
    }

    @Override
    public void onBindViewHolder(@NonNull FavAdapter.ListViewHolder holder, int position) {
        Favorite favorite = fav.get(position);

        holder.tvName.setText(favorite.getTitle());
        holder.tvFrom.setText(favorite.getOverView());
        Glide.with(activity).load(IMAGE_URL + favorite.getPosterPath()).into(holder.img);
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
        TextView tvName, tvFrom;

        public ListViewHolder(@NonNull View itemView, CustomItemListener itemListener) {
            super(itemView);
            img = itemView.findViewById(R.id.img_item_photo);
            tvName = itemView.findViewById(R.id.tv_item_name);
            tvFrom = itemView.findViewById(R.id.tv_item_from);

            this.itemListener = itemListener;
            itemView.setOnClickListener(this);
        }



        @Override
        public void onClick(View view) {
            Favorite item = getItem(getAdapterPosition());
            this.itemListener.onCustomClick(item.getmId());
            notifyDataSetChanged();
        }
    }
}
