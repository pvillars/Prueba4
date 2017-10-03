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
import cl.anpetrus.prueba4.models.Character;
import cl.anpetrus.prueba4.models.MarvelImage;

/**
 * Created by USUARIO on 02-10-2017.
 */

public class CharactersAdapter extends RecyclerView.Adapter<CharactersAdapter.ViewHolder> {

    private List<Character> characters;
    private Context context;

    private static CharcaterInteface charcaterInteface;
   /* public static void load(Context context){
        //charcaterInteface = context;
    }*/

    public CharactersAdapter(Context context, List<Character> characters) {
        this.characters = characters;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_character, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Character character = characters.get(position);
        holder.name.setText(character.getName().toString());
        holder.description.setText(character.getDescription().toString());

        Picasso.with(context)
                .load(character.getThumbnail().getImageUrl(MarvelImage.Size.LANDSCAPE_XLARGE))
                .into(holder.thumbnail);
    }

    @Override
    public int getItemCount() {
        return characters.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView name, description;
        private ImageView thumbnail;

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }

   /* private class BackgroundUF extends GetCharacters {

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected void onPostExecute(Wrapper<Character> wrapper) {
            if(wrapper!=null){
                //TextView textView = (TextView) findViewById(R.id.ufTv);
                //textView.setText(String.valueOf(wrapper.getSerie()[0].getValor()));

                WrapperData<Character> characterWrapperData = wrapper.getData();
                characters = characterWrapperData.getResults();

                charcaterInteface.load();
            }
        }
    }*/
}
