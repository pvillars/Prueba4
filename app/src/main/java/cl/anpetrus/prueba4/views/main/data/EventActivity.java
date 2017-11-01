package cl.anpetrus.prueba4.views.main.data;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.Date;
import java.util.Locale;

import cl.anpetrus.prueba4.R;
import cl.anpetrus.prueba4.models.Event;
import cl.anpetrus.prueba4.models.MarvelImage;
import cl.anpetrus.prueba4.utils.UtilDate;
import cl.anpetrus.prueba4.utils.UtilImage;

public class EventActivity extends AppCompatActivity {

    public final static String KEY_EVENT = "cl.anpetrus.prueba4.views.main.data.EventActivity.KEY_EVENT";

    private ImageView image;
    private TextView  description, date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        final Event event = (Event) getIntent().getSerializableExtra(KEY_EVENT);

        getSupportActionBar().setTitle(event.getTitle());

        image = (ImageView) findViewById(R.id.detailImageIv);
        date = (TextView) findViewById(R.id.eventDateTv);
        description = (TextView) findViewById(R.id.eventDescriptionTv);

        Picasso.with(this)
                .load(event.getThumbnail().getImageUrl(MarvelImage.Size.LANDSCAPE_INCREDIBLE))
                .into(image);

        String dateStr = "";

        if (event.getStart() != null)
            dateStr = new UtilDate(event.getStart(), "MMMM d, yyyy", Locale.ENGLISH).toString();
        if (event.getStart() != null && event.getEnd() != null)
            dateStr += " - ";
        if (event.getEnd() != null)
            dateStr += new UtilDate(event.getEnd(), "MMMM d, yyyy", Locale.ENGLISH).toString();

        if(dateStr==null || dateStr.trim().equals("")){
            date.setVisibility(View.GONE);
        }else {
            date.setText(dateStr);
        }
        description.setText(event.getDescription());

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewCompleteImage(event);
            }
        });

        findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewCompleteImage(event);
            }
        });
    }

    private void viewCompleteImage(Event event){
        Intent intent = new Intent(EventActivity.this, ImageActivity.class);

        intent.putExtra(ImageActivity.KEY_URL, event.getThumbnail().getImageUrl(MarvelImage.Size.FULLSIZE));
        String nameImage;
        if(event.getTitle().trim().equals(""))
            nameImage ="Marvel "+new Date().getTime();
        else
            nameImage = event.getTitle().trim();

        intent.putExtra(ImageActivity.KEY_NAME_IMAGE,nameImage);

        image.buildDrawingCache();
        Bitmap imageBitmap = image.getDrawingCache();
        Bundle extras = new Bundle();
        extras.putParcelable(ImageActivity.KEY_THUMBS_IMAGE, UtilImage.getResizedBitmap(imageBitmap, 50));
        intent.putExtras(extras);
        startActivity(intent);
    }

    private void initCollapsingToolbar() {
        final CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        collapsingToolbar.setTitle(" ");
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.app_bar);
        appBarLayout.setExpanded(true);

        // hiding & showing the title when toolbar expanded & collapsed
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;


            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }

                Log.d("SCROLLL", "scrollRange: " + scrollRange + " verticalOffset: " + verticalOffset);
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbar.setTitle(getString(R.string.app_name));
                    isShow = true;
                } else if (isShow) {
                    collapsingToolbar.setTitle(" ");
                    isShow = false;
                }
            }
        });
    }

}
