package at.oberauer.controller;

import at.oberauer.data.TestConfiguration;
import at.oberauer.data.TestResult;
import at.oberauer.stresstest.StressTest;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by michael on 07.01.18.
 */
@Controller
public class HomeController {

    @Autowired
    @Qualifier(value = "eurekaClient")
    private EurekaClient eurekaClient;

    @GetMapping("/")
    public String getHome(Model model){
        model.addAttribute("configuration", new TestConfiguration());
        return "configuration";
    }

    @PostMapping("/")
    public String getHome(@ModelAttribute TestConfiguration configuration, Model model){
        model.addAttribute("configuration", configuration);

        List<InstanceInfo> instances;
        String path = "";
        TestResult result = new TestResult();

        switch(configuration.getTargetService()){
            case Gateway:{
                instances = eurekaClient.getApplication("GATEWAY").getInstances();
                path = "analytics/api/image?keyword=Trump&w=3840&h=2160";
            }
                break;
            case Analytics:{
                instances = eurekaClient.getApplication("ANALYTICS").getInstances();
                path = "api/image?keyword=Trump&w=3840&h=2160";
            }
                break;
            case Webcrawler:{
                instances = eurekaClient.getApplication("WEBCRAWLER").getInstances();
                path = "rssitems?sort=pubDate,desc&page=0&size=500";
            }
                break;
            default:{
                instances = new ArrayList<>();
            }
                break;
        }

        if(configuration.getMultiplicity().equals(TestConfiguration.Multiplicity.Single)){
            if(instances.size() > 1){
                InstanceInfo i = instances.get(0);
                instances.clear();
                instances.add(i);
            }
        }
        if(instances.size() < 1){
            // empty info
            result.setNumRequests(-1);
        }

        /* Run Stress tests on Instances */
        ArrayList<StressTest> tests = new ArrayList<>();
        for(InstanceInfo info : instances) {
            tests.add(new StressTest(info.getHomePageUrl() + path, 5));
        }

        for(StressTest t : tests){
            Thread thread = new Thread(t);
            thread.start();
        }

        try {
            Thread.sleep(5000 * tests.size() + 500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        long req = 0;
        for(StressTest t : tests){
            req += t.getNumRequests();
        }

        result.setNumRequests(req);

        model.addAttribute("result", result);

        return "test";
    }

}
