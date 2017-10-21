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
import cl.anpetrus.prueba4.models.Comic;
import cl.anpetrus.prueba4.models.MarvelImage;

public class ComicActivity extends AppCompatActivity {

    public final static String KEY_COMIC = "cl.anpetrus.prueba4.views.main.data.ComicActivity.KEY_COMIC";
    private ImageView image;
    private TextView description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comic);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final Comic comic = (Comic) getIntent().getSerializableExtra(KEY_COMIC);

        getSupportActionBar().setTitle(comic.getTitle());

        image = (ImageView) findViewById(R.id.detailImageIv);
        description = (TextView) findViewById(R.id.comicDescriptionTv);

        Picasso.with(this)
                .load(comic.getThumbnail().getImageUrl(MarvelImage.Size.PORTRAIT_UNCANNY))
                .into(image);

        if (comic.getDescription() == null || comic.getDescription().trim().equals("")) {
            description.setText("NO DESCRIPTION");
        } else {
            description.setText(comic.getDescription());
        }

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(ComicActivity.this, ImageActivity.class);

                intent.putExtra(ImageActivity.KEY_URL, comic.getThumbnail().getImageUrl(MarvelImage.Size.FULLSIZE));

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
