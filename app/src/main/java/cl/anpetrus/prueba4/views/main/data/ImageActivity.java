package cl.anpetrus.prueba4.views.main.data;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import cl.anpetrus.prueba4.R;
import cl.anpetrus.prueba4.Save;

public class ImageActivity extends AppCompatActivity {

    public static final String KEY_URL = "cl.anpetrus.prueba4.views.main.data.ImageActivity.KEY_URL";
    public static final String KEY_THUMBS_IMAGE = "cl.anpetrus.prueba4.views.main.data.ImageActivity.KEY_THUMBS_IMAGE";
    public static final String KEY_NAME_IMAGE = "cl.anpetrus.prueba4.views.main.data.ImageActivity.KEY_NAME_IMAGE";
    private ImageView imageView;
    private String nameImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        imageView = (ImageView) findViewById(R.id.imageBtn);

        Bundle extras = getIntent().getExtras();
        Bitmap bitmap = extras.getParcelable(KEY_THUMBS_IMAGE);
        nameImage = extras.getString(KEY_NAME_IMAGE);


        String url = getIntent().getStringExtra(KEY_URL);

        Picasso.with(this).invalidate(url);
        Picasso.with(this).load(url).networkPolicy(NetworkPolicy.NO_CACHE).memoryPolicy(MemoryPolicy.NO_CACHE);
        Picasso.with(this)
                .load(url)
                .placeholder(new BitmapDrawable(getResources(), bitmap))
                .into(imageView);

        findViewById(R.id.backBnv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int MyVersion = Build.VERSION.SDK_INT;
                if (MyVersion > Build.VERSION_CODES.LOLLIPOP_MR1) {
                    ActivityCompat.requestPermissions(ImageActivity.this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                } else {
                    saveImage();
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    saveImage();
                } else {
                    Toast.makeText(ImageActivity.this, "Permiso denegado para guardar imagen en almacenamiento externo, favor permitir acceso.", Toast.LENGTH_LONG).show();
                }
                return;
            }
        }
    }
    private void saveImage(){
        int code = Save.saveImage(getImage(),nameImage,this);
        String message = "Imagen guardada en el dispositivo";
        if(code !=0){
            message = "Error al gurdar imagen";
        }
        Toast.makeText(ImageActivity.this, message, Toast.LENGTH_LONG).show();
    }

    private Bitmap getImage() {
        imageView.setDrawingCacheEnabled(true);
        imageView.buildDrawingCache();
        Bitmap bm = imageView.getDrawingCache();
        //ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        //bm.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        return bm;
    }



}
