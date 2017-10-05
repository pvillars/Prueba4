package cl.anpetrus.prueba4.models;

import java.io.Serializable;

/**
 * Created by USUARIO on 04-10-2017.
 */

public class Event implements Serializable{

    private long id;
    private String title,description,start,end;
    private MarvelImage thumbnail;

    public Event() {
    }

    public String getStart() {
        return start;
    }

    public String getEnd() {
        return end;
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
