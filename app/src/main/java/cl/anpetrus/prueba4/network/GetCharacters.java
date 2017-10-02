package cl.anpetrus.prueba4.network;

import android.os.AsyncTask;

import java.io.IOException;
import java.util.Map;

import cl.anpetrus.prueba4.CharactersQuery;
import cl.anpetrus.prueba4.models.Wrapper;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by USUARIO on 02-10-2017.
 */

public class GetCharacters extends AsyncTask<CharactersQuery, Integer, Wrapper> {
    @Override
    protected Wrapper doInBackground(CharactersQuery... charactersQueries) {
        Map<String, Object> queryAsMap = charactersQueries[0].toMap();

        Characters characters = new CharacterInterceptor().get();

        Call<Wrapper> callCharacters = characters.characters(queryAsMap);

        try{
            Response<Wrapper> response = callCharacters.execute();
            if(200==response.code() && response.isSuccessful()){
                return response.body();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
