package at.oberauer.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.xml.txw2.annotation.XmlAttribute;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.time.LocalDate;
import java.util.Date;

/**
 * Created by michael on 07.09.17.
 */

@Entity
@XmlRootElement
public class RSSItem {

    /*
    Channel:
    <title>Titel des Feeds</title>
    <link>URL der Webpräsenz</link>
    <description>Kurze Beschreibung des Feeds</description>
    <language>Sprache des Feeds (z. B. "de-de")</language>
        Item(s):
            <title>Titel des Eintrags</title>
            <description>Kurze Zusammenfassung des Eintrags</description>
            <link>Link zum vollständigen Eintrag</link>
            <author>Autor des Artikels, E-Mail-Adresse</author>
            <guid>Eindeutige Identifikation des Eintrages</guid>
            <pubDate>Datum des Items</pubDate>
    */
    @JsonIgnore
    @ManyToOne
    RSSChannel channel;
    String title;
    String description;
    String link; //URL
    String author;
    @Id
    String guid;
    @Temporal(TemporalType.DATE)
    Date pubDate;

    @XmlElement
    public RSSChannel getChannel() {
        return channel;
    }

    public void setChannel(RSSChannel channel) {
        this.channel = channel;
    }

    @XmlElement
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) { this.title = safe(title); }

    @XmlElement
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = safe(description);
    }

    @XmlElement
    public String getLink() {
        return link;
    }

    public void setLink(String link) { this.link = safe(link); }

    @XmlElement
    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = safe(author);
    }

    @XmlAttribute
    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) { this.guid = safe(guid); }

    @XmlElement
    public Date getPubDate() {
        return pubDate ;
    }

    public void setPubDate(Date pubDate) {
        this.pubDate = pubDate;
    }

    private String safe(String s){
        return s.substring(0, s.length() > 255 ? 255 : s.length());
    }
}
