package cl.anpetrus.prueba4.network;

import java.util.Map;

import cl.anpetrus.prueba4.models.Comic;
import cl.anpetrus.prueba4.models.Wrapper;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

/**
 * Created by USUARIO on 02-10-2017.
 */

public interface Comics {

    @GET("comics")
    Call<Wrapper<Comic>> comics(@QueryMap Map<String, Object> characterFilter, @QueryMap Map<String, String> auth);
}
