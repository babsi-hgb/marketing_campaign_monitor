package at.oberauer.ui;

import at.oberauer.data.RSSChannel;
import at.oberauer.data.RSSChannelRepository;
import at.oberauer.data.RSSItem;
import at.oberauer.data.RSSItemRepository;
import at.oberauer.util.HTMLSkeletons;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.lang.reflect.Field;


/**
 * Created by michael on 11.09.17.
 */

@Controller
public class HTMLProvider {
    private RSSItemRepository itemRepo;
    private RSSChannelRepository channelRepo;

    @Autowired
    public HTMLProvider(RSSItemRepository itemRepo, RSSChannelRepository channelRepo) {
        this.itemRepo = itemRepo;
        this.channelRepo = channelRepo;
    }

    @RequestMapping("/html/channels")
    @ResponseBody
    public String channelList() {
        StringBuilder sb = new StringBuilder();
        sb.append(HTMLSkeletons.openBodyTitle("List of channels"));
        sb.append("<table>");
        sb.append("<tr>");
        for (Field f : RSSChannel.class.getDeclaredFields()) {
            sb.append("<td>" + f.getName() + "</td>");
        }
        sb.append("</tr>");
        channelRepo.findAll().forEach((channel) -> {
            sb.append(
                    HTMLSkeletons.tableRow(
                            channel.getId().toString(),
                            channel.getTitle(),
                            channel.getLink(),
                            channel.getDescription(),
                            channel.getLanguage(),
                            channel.getLastUpdate() != null ? channel.getLastUpdate().toString() : ""
                    )
            );
        });
        sb.append("</table>");
        sb.append(HTMLSkeletons.closeBody());
        return sb.toString();
    }

    @RequestMapping("/html/items")
    @ResponseBody
    public String itemList(@RequestParam(required = false) Integer page) {
        page = page == null ? 0 : page;
        StringBuilder sb = new StringBuilder();
        sb.append(HTMLSkeletons.openBodyTitle("List of items"));
        sb.append("<table>");
        sb.append("<tr>");
        for (Field f : RSSItem.class.getDeclaredFields()) {
            sb.append("<td>" + f.getName() + "</td>");
        }
        sb.append("</tr>");

        itemRepo.findAll(new PageRequest(page, 10)).forEach((item) -> {
            sb.append(
                    HTMLSkeletons.tableRow(
                            item.getChannel().getTitle(),
                            item.getTitle(),
                            item.getDescription(),
                            item.getLink(),
                            item.getAuthor(),
                            item.getGuid(),
                            item.getPubDate()!=null ? item.getPubDate().toString() : ""
                    )
            );
        });
        sb.append("</table>");
        sb.append("<a href='items?page=" + (page+1) + "'>next</a>");
        sb.append(HTMLSkeletons.closeBody());
        return sb.toString();
    }
}