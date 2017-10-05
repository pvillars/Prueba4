package cl.anpetrus.prueba4.Utils;

import android.graphics.Bitmap;
import android.graphics.Matrix;

/**
 * Created by USUARIO on 05-10-2017.
 */

public class UtilImage {
    public static Bitmap getResizedBitmap(Bitmap bm, int newWidth) {
        int width = bm.getWidth();
        int height = bm.getHeight();

        float propor = (float) width / newWidth;
        float newHeight = (float) height / propor;


        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = newHeight / height;

        // CREATE A MATRIX FOR THE MANIPULATION
        Matrix matrix = new Matrix();
        // RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleHeight);

        // "RECREATE" THE NEW BITMAP
        Bitmap resizedBitmap = Bitmap.createBitmap(
                bm, 0, 0, width, height, matrix, false);
        return resizedBitmap;
    }
}
