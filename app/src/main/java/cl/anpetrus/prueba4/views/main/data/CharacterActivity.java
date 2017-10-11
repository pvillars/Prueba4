package cl.anpetrus.prueba4.views.main.data;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import cl.anpetrus.prueba4.R;
import cl.anpetrus.prueba4.Utils.UtilImage;
import cl.anpetrus.prueba4.models.Character;
import cl.anpetrus.prueba4.models.MarvelImage;

public class CharacterActivity extends AppCompatActivity {

    public final static String KEY_CHARACTER = "cl.anpetrus.prueba4.views.main.data.CharacterActivity.KEY_CHARACTER";

    private ImageView image;
    private TextView description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final Character character = (Character) getIntent().getSerializableExtra(KEY_CHARACTER);

        getSupportActionBar().setTitle(character.getName());

        image = (ImageView) findViewById(R.id.detailImageIv);
        description = (TextView) findViewById(R.id.characterDescriptionTv);

        Picasso.with(this)
                .load(character.getThumbnail().getImageUrl(MarvelImage.Size.PORTRAIT_UNCANNY))
                .into(image);

        if (character.getDescription().trim().equals(""))
            description.setText("NO DESCRIPTION");
        else
            description.setText(character.getDescription());

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(CharacterActivity.this, ImageActivity.class);

                intent.putExtra(ImageActivity.KEY_URL, character.getThumbnail().getImageUrl(MarvelImage.Size.FULLSIZE));

                image.buildDrawingCache();
                Bitmap imageBitmap = image.getDrawingCache();
                Bundle extras = new Bundle();
                extras.putParcelable(ImageActivity.KEY_THUMBS_IMAGE, UtilImage.getResizedBitmap(imageBitmap, 100));
                intent.putExtras(extras);

                startActivity(intent);
            }
        });


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }
}
