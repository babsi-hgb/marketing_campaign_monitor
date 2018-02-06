package at.oberauer.ui;

import at.oberauer.HTMLFragments.HTMLFragments;
import at.oberauer.charts.ChartGenerator;
import at.oberauer.dal.CrawlConnector;
import at.oberauer.data.KeywordResult;
import at.oberauer.data.RSSItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by michael on 06.12.17.
 */
@RestController
@RequestMapping("/api")
public class HomeController {

    private CrawlConnector crawlConnector;

    @Autowired
    public HomeController(CrawlConnector crawlConnector){
        this.crawlConnector = crawlConnector;
    }

    @RequestMapping("instances")
    public ArrayList<String> getIndex(){
        return crawlConnector.getCrawlInstances(10);
    }

    @RequestMapping(value = "/image", method = RequestMethod.GET, produces = "image/jpg")
    public @ResponseBody byte[] getFile(@RequestParam("keyword") String keyword,
                                        @RequestParam("w") int w,
                                        @RequestParam("h") int h)  {
        try {
            //Generate Image for Keyword

            Iterable<RSSItem> results = crawlConnector.findByKeyword(keyword);
            ArrayList<KeywordResult> chartData = new ArrayList<>();

            for(RSSItem i : results){
                KeywordResult r = new KeywordResult();
                if(i.getPubDate() == null){
                    Date dt = new Date();
                    Calendar c = Calendar.getInstance();
                    c.setTime(dt);
                    c.add(Calendar.DATE, 1);
                    dt = c.getTime();
                    r.setDate(dt);
                }
                else{
                    r.setDate(i.getPubDate());
                }
                r.setLocation(KeywordResult.KeywordLocation.Other);
                r.setChannel("Channel");
                chartData.add(r);
            }

            BufferedImage img;
            img = ChartGenerator.generatePerChannelAreaChart(chartData, w,h,"Results for " + keyword);

            // Create a byte array output stream.
            ByteArrayOutputStream bao = new ByteArrayOutputStream();

            // Write to output stream
            ImageIO.write(img, "jpg", bao);

            return bao.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
