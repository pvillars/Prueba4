package cl.anpetrus.prueba4.network;

import android.os.AsyncTask;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import cl.anpetrus.prueba4.Auth;
import cl.anpetrus.prueba4.AuthHashGenerator;
import cl.anpetrus.prueba4.CharactersQuery;
import cl.anpetrus.prueba4.MarvelApiException;
import cl.anpetrus.prueba4.TimeProvider;
import cl.anpetrus.prueba4.models.Event;
import cl.anpetrus.prueba4.models.Wrapper;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by USUARIO on 02-10-2017.
 */

public class GetEvents extends AsyncTask<CharactersQuery, Integer, Wrapper<Event>> {

    @Override
    protected Wrapper doInBackground(CharactersQuery... charactersQueries) {

        Events events = new EventInterceptor().get();

        String publicKey = Auth.PUBLIC_KEY;
        String privateKey = Auth.PRIVATE_KEY;
        String timestamp = String.valueOf(new TimeProvider().currentTimeMillis());
        String hash = "";
        try {
            hash = new AuthHashGenerator().generateHash(timestamp,publicKey,privateKey);
        } catch (MarvelApiException e) {
            e.printStackTrace();
        }

        Map<String,String> authMap = new HashMap<>();
        authMap.put("ts",timestamp);
        authMap.put("apikey",publicKey);
        authMap.put("hash",hash);

        Map<String, Object> queryAsMap = charactersQueries[0].toMap();

        Call<Wrapper<Event>> callEvents = events.events(queryAsMap,authMap);

        try{
            Response<Wrapper<Event>> response = callEvents.execute();
            if(200==response.code() && response.isSuccessful()){
                return response.body();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}