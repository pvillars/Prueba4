package cl.anpetrus.prueba4.network;

import cl.anpetrus.prueba4.models.Character;
import cl.anpetrus.prueba4.models.Wrapper;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by USUARIO on 02-10-2017.
 */

public interface Characters {

    @GET("characters?ts=1506973638473&apikey=8b312824cae040a1358d8fc0d366e0e8&hash=30161ac4b1673e47acbc0280da8694e0")
    Call<Wrapper<Character>> characters();//@QueryMap Map<String, Object> characterFilter);//, @QueryMap Map<String, String> auth);
}
