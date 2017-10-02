package cl.anpetrus.prueba4.data;

import android.app.ProgressDialog;

import cl.anpetrus.prueba4.models.Wrapper;
import cl.anpetrus.prueba4.network.GetCharacters;

/**
 * Created by USUARIO on 02-10-2017.
 */

public class Querys {

    private class BackgroundUF extends GetCharacters{

        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            //progressDialog = new ProgressDialog(MainActivity.this);
            //progressDialog.show();
        }

        @Override
        protected void onPostExecute(Wrapper wrapper) {
            if(wrapper!=null){
                //TextView textView = (TextView) findViewById(R.id.ufTv);
                //textView.setText(String.valueOf(wrapper.getSerie()[0].getValor()));
            }
            //progressDialog.dismiss();
        }
    }
}
