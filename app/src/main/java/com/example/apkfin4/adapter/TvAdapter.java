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
import com.example.apkfin4.model.tv.Tv;

import java.util.List;

import static com.example.apkfin4.api.ApiUtils.IMAGE_URL;

public class TvAdapter extends RecyclerView.Adapter<TvAdapter.ListViewHolder> {

    private final Context context;
    private final CustomItemListener itemListener;
    private List<Tv> item;

    public TvAdapter(Context context, List<Tv> tvs, CustomItemListener listener) {
        this.context = context;
        this.item = tvs;
        this.itemListener = listener;
    }
    @NonNull
    @Override
    public TvAdapter.ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_row_film,parent,false);
        return new ListViewHolder(view,this.itemListener);

    }

    @Override
    public void onBindViewHolder(@NonNull TvAdapter.ListViewHolder holder, int position) {
        holder.bind(item.get(position));
    }


    @Override
    public int getItemCount() { return item.size(); }


    public void addTv(List<Tv> each) {
        item = each;
        notifyDataSetChanged(); }

    public Tv getItem(int adapterPosition) { return item.get(adapterPosition);}

    public interface CustomItemListener {void onCustomClick(int id);}

    public class ListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

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

        public void bind(Tv tv) {
            tvName.setText(tv.getTitle());
            tvFrom.setText(tv.getOverView());
            tvDate.setText(tv.getReleaseDate().split("-")[0]);
            Glide.with(itemView)
                    .load(IMAGE_URL + tv.getPosterPath())
                    .apply(RequestOptions.overrideOf(100,100))
                    .into(imgPhoto);
        }

        @Override
        public void onClick(View view) {

            Tv item = getItem(getAdapterPosition());
            this.itemListener.onCustomClick(item.getId());
            notifyDataSetChanged();

        }
    }
}
