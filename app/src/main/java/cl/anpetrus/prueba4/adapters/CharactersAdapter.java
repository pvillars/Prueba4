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
import cl.anpetrus.prueba4.listeners.ActionFragmentListener;
import cl.anpetrus.prueba4.models.Character;
import cl.anpetrus.prueba4.models.MarvelImage;
import cl.anpetrus.prueba4.models.WrapperData;

/**
 * Created by USUARIO on 02-10-2017.
 */

public class CharactersAdapter extends RecyclerView.Adapter<CharactersAdapter.ViewHolder> {

    private List<Character> characters;
    private Context context;
    private ActionFragmentListener actionFragmentListener;

    public CharactersAdapter(Context context, List<Character> characters, ActionFragmentListener actionFragmentListener) {
        this.characters = characters;
        this.context = context;
        this.actionFragmentListener = actionFragmentListener;
    }

    public void update(WrapperData<Character> characterWrapperData){
        //characters.clear();
        characters.addAll(characterWrapperData.getResults());
        notifyDataSetChanged();

    }

    public void clear(){
        characters.clear();
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_character, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        final Character character = characters.get(position);
        holder.name.setText(character.getName().toString());

        Picasso.with(context)
                .load(character.getThumbnail().getImageUrl(MarvelImage.Size.LANDSCAPE_XLARGE))
                .into(holder.thumbnail);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                actionFragmentListener.clicked(character);
            }
        });
    }

    @Override
    public int getItemCount() {
        return characters.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView name;
        private ImageView thumbnail;

        public ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.nameListTv);
            thumbnail = itemView.findViewById(R.id.imageListIv);
        }
    }
}
