package at.oberauer.crawlers.rss;

import at.oberauer.data.RSSChannel;
import at.oberauer.data.RSSItem;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by michael on 12.09.17.
 */
public class RSSXMLHandler extends DefaultHandler {

    private enum ParserState{
        INIT,
        ITEM,
        TITLE,
        LINK,
        DESC,
        AUTHOR,
        GUID,
        PUBDATE
    }

    RSSChannel channel;

    private List<RSSItem> newitems = new ArrayList<>();
    private ParserState state = ParserState.INIT;
    private RSSItem current;

    private boolean inItem = false;

    public RSSXMLHandler(RSSChannel channel){
        super();
        this.channel = channel;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes){
        if(qName.equals("item")){
            inItem = true;
            state = ParserState.ITEM;
            current = new RSSItem();
            current.setChannel(channel);
        }
        else if(inItem){
            if(qName.equals("title")){
                state = ParserState.TITLE;
            }
            if(qName.equals("link")){
                state = ParserState.LINK;
            }
            if(qName.equals("description")){
                state = ParserState.DESC;
            }
            if(qName.equals("author")){
                state = ParserState.AUTHOR;
            }
            if(qName.equals("guid")){
                state = ParserState.GUID;
            }
            if(qName.equals("pubDate")){
                state = ParserState.PUBDATE;
            }
        }
    }

    @Override
    public void characters(char[] ch, int start, int length){
        if(!inItem)
            return;

        StringBuilder sb = new StringBuilder();
        for(int i = 0 ; i < length ;++i){
            sb.append(ch[start + i]);
        }
        if(state == ParserState.TITLE){
            current.setTitle(sb.toString());
        }
        if(state == ParserState.LINK){
            current.setLink(sb.toString());
        }
        if(state == ParserState.DESC){
            current.setDescription(sb.toString());
        }
        if(state == ParserState.AUTHOR){
            current.setAuthor(sb.toString());
        }
        if(state == ParserState.GUID){
            current.setGuid(sb.toString());
        }
        if(state == ParserState.PUBDATE){
            SimpleDateFormat parser = new SimpleDateFormat("dd MMM yyyy HH:mm:ss Z");
            try {

                Date date = parser.parse(sb.toString().substring(5));

                current.setPubDate(date);
            } catch (Exception e) {
                //Date could not be parsed ... use timestamp
                current.setPubDate(new Date());
            }
        }
    }


    @Override
    public void endElement(String uri, String localName, String qName){
        if(qName.equals("item")) {
            inItem = false;
            state = ParserState.INIT;
            newitems.add(current);
            current = null;
        }
        else
            state = ParserState.ITEM;
    }

    public List<RSSItem> getNewItems(){
        return newitems;
    }

}
