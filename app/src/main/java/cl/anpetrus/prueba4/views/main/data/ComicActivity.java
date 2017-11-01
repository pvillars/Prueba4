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
import cl.anpetrus.prueba4.models.Comic;
import cl.anpetrus.prueba4.models.MarvelImage;
import cl.anpetrus.prueba4.utils.UtilImage;

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
                viewCompleteImage(comic);
            }
        });

        findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewCompleteImage(comic);
            }
        });
    }

    private void viewCompleteImage(Comic comic) {
        Intent intent = new Intent(ComicActivity.this, ImageActivity.class);

        intent.putExtra(ImageActivity.KEY_URL, comic.getThumbnail().getImageUrl(MarvelImage.Size.FULLSIZE));
        String nameImage;
        if(comic.getTitle().trim().equals(""))
            nameImage ="Marvel "+new Date().getTime();
        else
            nameImage = comic.getTitle().trim();

        intent.putExtra(ImageActivity.KEY_NAME_IMAGE,nameImage);
        image.buildDrawingCache();
        Bitmap imageBitmap = image.getDrawingCache();
        Bundle extras = new Bundle();
        extras.putParcelable(ImageActivity.KEY_THUMBS_IMAGE, UtilImage.getResizedBitmap(imageBitmap, 50));
        intent.putExtras(extras);

        startActivity(intent);
    }
}
