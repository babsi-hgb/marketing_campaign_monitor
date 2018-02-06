package at.oberauer.crawlers.rss;

import at.oberauer.data.RSSChannel;
import at.oberauer.data.RSSItem;
import at.oberauer.data.RSSItemRepository;
import com.sun.org.apache.xerces.internal.jaxp.SAXParserFactoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by michael on 12.09.17.
 */
public class RSSChannelReader implements Runnable {

    private RSSChannel channel;
    final RSSItemRepository repo;

    @Autowired
    public RSSChannelReader(RSSChannel channel, RSSItemRepository repo){
        this.channel = channel;
        this.repo = repo;
    }

    @Override
    public void run() {
        try {

            SAXParserFactory spf = SAXParserFactory.newInstance();
            SAXParser parser = spf.newSAXParser();

            URL url = new URL(channel.getLink());
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            RSSXMLHandler handler = new RSSXMLHandler(channel);

            parser.parse(con.getInputStream(), handler);

            con.disconnect();

            List<RSSItem> l = handler.getNewItems();
            l.forEach((rssItem -> {
                if(!repo.existsByGuid(rssItem.getGuid()))
                    repo.save(rssItem);
            }));



        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
    }
}
