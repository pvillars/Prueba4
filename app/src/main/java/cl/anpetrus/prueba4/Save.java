package cl.anpetrus.prueba4;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Petrus on 22-10-2017.
 */

public class Save {

    public static int saveImage(Bitmap image, String nameImage, Context context) {

        nameImage = cleanName(nameImage);
        //Se guarda la imagen en la SDCARD
        File f;
        try {
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            image.compress(Bitmap.CompressFormat.JPEG, 100, bytes);

            String file_path = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "MARVEL";
            File dir = new File(file_path);

            if (!dir.exists()) {
                dir.mkdirs();
            }

            f = new File(file_path +File.separator + nameImage + ".jpg");

            f.createNewFile();

            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
            bytes.flush();
            bytes.close();
            MediaScannerConnection.scanFile(context,
                    new String[] { f.toString() } , null,
                    new MediaScannerConnection.OnScanCompletedListener() {

                        public void onScanCompleted(String path, Uri uri) {
                        }
                    });
            return 0;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return -1;
    }

    private static String cleanName(String name){
        String clean = name.replaceAll("[^A-Za-z0-9() \\[\\]]","");

        return clean;
    }

}
