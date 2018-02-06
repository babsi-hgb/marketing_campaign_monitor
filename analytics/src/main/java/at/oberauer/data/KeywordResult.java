package at.oberauer.data;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by michael on 06.12.17.
 */
public class KeywordResult {

    public enum KeywordLocation{
        Description,
        Title,
        Other
    }

    private String channel;
    private KeywordLocation location;
    private Date date;
    private String id;

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public KeywordLocation getLocation() {
        return location;
    }

    public void setLocation(KeywordLocation location) {
        this.location = location;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
