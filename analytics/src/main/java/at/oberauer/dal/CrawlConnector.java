package at.oberauer.dal;

import at.oberauer.data.KeywordResult;
import at.oberauer.data.RSSItem;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.mvc.TypeReferences;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Created by michael on 06.12.17.
 */
@Component
public class CrawlConnector{
    EurekaClient eurekaClient;
    RestTemplate restTemplate;

    private static final class PagedResourceReturnType extends TypeReferences.PagedResourcesType<RSSItem>{}

    @Autowired
    public CrawlConnector(@Qualifier("eurekaClient") EurekaClient eurekaClient, RestTemplate restTemplate){
        this.eurekaClient = eurekaClient;
        this.restTemplate = restTemplate;
    }

    public ArrayList<String> getCrawlInstances(int num){
        ArrayList<String> instances = new ArrayList<>();
        for(int i = 0 ; i < num ; ++i) {
            InstanceInfo nextServerInfo = eurekaClient.getNextServerFromEureka("webcrawler", false);
            instances.add("Got Instance from Eureka: " + nextServerInfo.getIPAddr() + ":" + nextServerInfo.getPort() +
            " | " + nextServerInfo.getHostName() + ", " + nextServerInfo.getHomePageUrl() + "\n");
        }
        return instances;
    }
    @HystrixCommand(fallbackMethod = "findByKeywordFallback")
    public Iterable<RSSItem> findByKeyword(String keyword){
        try{
            InstanceInfo nextServerInfo = eurekaClient.getNextServerFromEureka("webcrawler", false);
            String searchUrl = nextServerInfo.getHomePageUrl() + "rssitems/search/findByKeyword?keyword=" + keyword;

            ResponseEntity<PagedResources<RSSItem>> responseEntity =
            restTemplate.exchange(searchUrl, HttpMethod.GET, null, new PagedResourceReturnType());

            PagedResources<RSSItem> resources = responseEntity.getBody();
            List<RSSItem> items = new ArrayList<>(resources.getContent());

            return items;

        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
    public Iterable<RSSItem> findByKeywordFallback(String keyword){
        //Fallback
        ArrayList<RSSItem> items = new ArrayList<>();
        RSSItem item = new RSSItem();
        item.setPubDate(new Date());
        item.setAuthor("FALLBACK");
        item.setDescription("FALLBACK");
        item.setLink("FALLBACK");
        item.setTitle("FALLBACK");
        items.add(item);

        return items;
    }
}
