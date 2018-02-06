package at.oberauer.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by michael on 16.09.17.
 */

@Controller
public class RSSChannelXMLProvider {

    private RSSChannelRepository channelRepo;

    @Autowired
    public RSSChannelXMLProvider(RSSChannelRepository channelRepo) {
        this.channelRepo = channelRepo;
    }

    @RequestMapping(value = "/xml/channels", produces = {MediaType.APPLICATION_XML_VALUE})
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody List<RSSChannel> getChannelList(){
        List<RSSChannel> l = new ArrayList<>();
        channelRepo.findAll().forEach((channel -> {
            l.add(channel);
        }));
        return l;
    }


}
