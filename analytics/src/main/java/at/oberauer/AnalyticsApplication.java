package at.oberauer;

import at.oberauer.charts.ChartGenerator;
import at.oberauer.charts.ChartStore;
import at.oberauer.dal.CrawlConnector;
import at.oberauer.data.KeywordResult;
import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.converters.Auto;
import javafx.scene.chart.Chart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import org.springframework.hateoas.mvc.*;
import org.springframework.data.rest.webmvc.config.*;
import java.util.*;

@SpringBootApplication
@EnableEurekaClient
@EnableCircuitBreaker
public class AnalyticsApplication {

	@Bean("defaultCrawlConnector")
	@Autowired
	public CrawlConnector getDefaultCrawlConnector(@Qualifier("eurekaClient") EurekaClient client, RestTemplate restTemplate){
		return new CrawlConnector(client, restTemplate);
	}

	@Bean("chartStore")
	public ChartStore getDefaultChartStore(){
		return new ChartStore();
	}

	@Autowired
	@Qualifier("halJacksonHttpMessageConverter")
	private TypeConstrainedMappingJackson2HttpMessageConverter halConverter;

	@Bean
	public RestTemplate restTemplate() {
		RestTemplate restTemplate = new RestTemplate();
		List<HttpMessageConverter<?>> converters = restTemplate.getMessageConverters();
		converters.add(0, halConverter);
		restTemplate.setMessageConverters(converters);
		return restTemplate;
	}

	public static void main(String[] args) {
		SpringApplication.run(AnalyticsApplication.class, args);
	}
}
