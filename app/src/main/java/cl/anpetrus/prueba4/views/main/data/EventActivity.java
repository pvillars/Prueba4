package cl.anpetrus.prueba4.views.main.data;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.Locale;

import cl.anpetrus.prueba4.R;
import cl.anpetrus.prueba4.Utils.UtilDate;
import cl.anpetrus.prueba4.Utils.UtilImage;
import cl.anpetrus.prueba4.models.Event;
import cl.anpetrus.prueba4.models.MarvelImage;

public class EventActivity extends AppCompatActivity {

    public final static String KEY_EVENT = "cl.anpetrus.prueba3.views.events.EventActivity.KEY_EVENT";
    public final static String KEY_NAME = "cl.anpetrus.prueba3.views.events.EventActivity.KEY_NAME";

  //  private EventValidator validator;
    private ImageView image;
    private TextView name, description, date, timeStart;
    private FloatingActionButton editFab;
    private String keyEvent;
  //  private LoadingFragment loadingFragment;
    private Toolbar toolbar;
    private String imageUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        final Event event = (Event) getIntent().getSerializableExtra(KEY_EVENT);

        getSupportActionBar().setTitle(event.getTitle());

        Toast.makeText(this, event.getTitle(), Toast.LENGTH_SHORT).show();

        image = (ImageView) findViewById(R.id.detailImageIv);
        date = (TextView) findViewById(R.id.eventDateTv);
        description = (TextView) findViewById(R.id.eventDescriptionTv);

        Picasso.with(this)
                .load(event.getThumbnail().getImageUrl(MarvelImage.Size.LANDSCAPE_INCREDIBLE))
                .into(image);

        String dateStr = new UtilDate(new UtilDate().toDate(event.getStart()),"MMMM d, yyyy", Locale.ENGLISH).toString();
        date.setText(dateStr);

        description.setText(event.getDescription());

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(EventActivity.this, ImageActivity.class);

                intent.putExtra(ImageActivity.KEY_URL, event.getThumbnail().getImageUrl(MarvelImage.Size.FULLSIZE));

                image.buildDrawingCache();
                Bitmap imageBitmap = image.getDrawingCache();
                Bundle extras = new Bundle();
                extras.putParcelable(ImageActivity.KEY_THUMBS_IMAGE, UtilImage.getResizedBitmap(imageBitmap,100));
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

    /**
     * Initializing collapsing toolbar
     * Will show and hide the toolbar title on scroll
     */
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

                Log.d("SCROLLL","scrollRange: "+scrollRange+" verticalOffset: "+verticalOffset);
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
