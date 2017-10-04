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
import cl.anpetrus.prueba4.models.Event;
import cl.anpetrus.prueba4.models.MarvelImage;

/**
 * Created by USUARIO on 02-10-2017.
 */

public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.ViewHolder> {

    private List<Event> events;
    private Context context;


    public EventsAdapter(Context context, List<Event> events) {
        this.events = events;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_event, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Event event = events.get(position);
        holder.name.setText(event.getTitle().toString());
        if(event.getDescription()!=null)
        holder.description.setText(event.getDescription().toString());

        Picasso.with(context)
                .load(event.getThumbnail().getImageUrl(MarvelImage.Size.LANDSCAPE_XLARGE))
                .into(holder.thumbnail);
    }

    @Override
    public int getItemCount() {
        return events.size();
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
