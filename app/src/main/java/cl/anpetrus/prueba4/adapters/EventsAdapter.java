package cl.anpetrus.prueba4.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Locale;

import cl.anpetrus.prueba4.R;
import cl.anpetrus.prueba4.listeners.ActionFragmentListener;
import cl.anpetrus.prueba4.models.Event;
import cl.anpetrus.prueba4.models.MarvelImage;
import cl.anpetrus.prueba4.models.WrapperData;
import cl.anpetrus.prueba4.utils.UtilDate;

/**
 * Created by USUARIO on 02-10-2017.
 */

public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.ViewHolder> {

    private List<Event> events;
    private Context context;
    private ActionFragmentListener actionFragmentListener;


    public EventsAdapter(Context context, List<Event> events, ActionFragmentListener actionFragmentListener) {
        this.events = events;
        this.context = context;
        this.actionFragmentListener = actionFragmentListener;
    }

    public void update(WrapperData<Event> eventWrapperData) {
        events.addAll(eventWrapperData.getResults());
        notifyDataSetChanged();
    }

    public void clear() {
        events.clear();
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_event, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        final Event event = events.get(position);

        String dateStr = "";

        if (event.getStart() != null)
            dateStr = new UtilDate(event.getStart(), "MMMM d, yyyy", Locale.ENGLISH).toString();
        if (event.getStart() != null && event.getEnd() != null)
            dateStr += " - ";
        if (event.getEnd() != null)
            dateStr += new UtilDate(event.getEnd(), "MMMM d, yyyy", Locale.ENGLISH).toString();

        if(dateStr==null || dateStr.trim().equals("")){
            holder.dates.setVisibility(View.GONE);
            RelativeLayout.LayoutParams marginLayoutParams = (RelativeLayout.LayoutParams) holder.name.getLayoutParams();
            marginLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            holder.name.setLayoutParams(marginLayoutParams);
        }else{
            holder.dates.setText(dateStr);
        }

        holder.name.setText(event.getTitle().toString());

        Picasso.with(context)
                .load(event.getThumbnail().getImageUrl(MarvelImage.Size.LANDSCAPE_XLARGE))
                .into(holder.thumbnail);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                actionFragmentListener.clicked(event);
            }
        });
    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView name, dates;
        private ImageView thumbnail;

        public ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.nameListTv);
            dates = itemView.findViewById(R.id.datesListTv);
            thumbnail = itemView.findViewById(R.id.imageListIv);
        }
    }


}
