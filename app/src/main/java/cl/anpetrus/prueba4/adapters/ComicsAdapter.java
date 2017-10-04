package cl.anpetrus.prueba4.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import cl.anpetrus.prueba4.R;
import cl.anpetrus.prueba4.models.Comic;
import cl.anpetrus.prueba4.models.MarvelImage;
import cl.anpetrus.prueba4.models.WrapperData;

/**
 * Created by USUARIO on 02-10-2017.
 */

public class ComicsAdapter extends RecyclerView.Adapter<ComicsAdapter.ViewHolder> {

    private List<Comic> comics;
    private Context context;

    public ComicsAdapter(Context context, List<Comic> comics) {
        this.comics = comics;
        this.context = context;
    }

    public void update(WrapperData<Comic> comicWrapperData){
        //characters.clear();
        comics.addAll(comicWrapperData.getResults());
        notifyDataSetChanged();

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_comic, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Comic comic = comics.get(position);
        holder.name.setText(comic.getTitle().toString());
        if(comic.getDescription()!=null)
        holder.description.setText(comic.getDescription().toString());

        Picasso.with(context)
                .load(comic.getThumbnail().getImageUrl(MarvelImage.Size.LANDSCAPE_XLARGE))
                .into(holder.thumbnail);
    }

    @Override
    public int getItemCount() {
        return comics.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView name, description;
        private ImageView thumbnail;

        public ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.nameListTv);
            description = itemView.findViewById(R.id.descriptionListTv);
            thumbnail = itemView.findViewById(R.id.imageListIv);
        }
    }
}
