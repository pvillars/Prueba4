package cl.anpetrus.prueba4.models;

import java.io.Serializable;

/**
 * Created by USUARIO on 04-10-2017.
 */

public class Comic implements Serializable {

    private long id;
    private String title,description;
    private MarvelImage thumbnail;

    public Comic() {
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public MarvelImage getThumbnail() {
        return thumbnail;
    }
}
