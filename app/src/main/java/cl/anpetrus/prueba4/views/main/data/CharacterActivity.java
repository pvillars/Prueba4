package cl.anpetrus.prueba4.views.main.data;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.Date;

import cl.anpetrus.prueba4.R;
import cl.anpetrus.prueba4.models.Character;
import cl.anpetrus.prueba4.models.MarvelImage;
import cl.anpetrus.prueba4.utils.UtilImage;

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
                viewCompleteImage(character);
            }
        });

        findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewCompleteImage(character);
            }
        });
    }

    private void viewCompleteImage(Character character) {
        Intent intent = new Intent(CharacterActivity.this, ImageActivity.class);

        intent.putExtra(ImageActivity.KEY_URL, character.getThumbnail().getImageUrl(MarvelImage.Size.FULLSIZE));

        String nameImage;
        if (character.getName().trim().equals(""))
            nameImage = "Marvel " + new Date().getTime();
        else
            nameImage = character.getName().trim();

        intent.putExtra(ImageActivity.KEY_NAME_IMAGE, nameImage);

        image.buildDrawingCache();
        Bitmap imageBitmap = image.getDrawingCache();
        Bundle extras = new Bundle();
        extras.putParcelable(ImageActivity.KEY_THUMBS_IMAGE, UtilImage.getResizedBitmap(imageBitmap, 50));
        intent.putExtras(extras);

        startActivity(intent);
    }


}
