package at.oberauer;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.netflix.zuul.filters.ProxyRequestHelper;
import org.springframework.cloud.netflix.zuul.filters.route.FallbackProvider;
import org.springframework.cloud.netflix.zuul.filters.route.ZuulFallbackProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.ROUTE_TYPE;


@SpringBootApplication
@EnableEurekaClient
@EnableZuulProxy
public class GatewayApplication {


	//Uncomment this bean to cause Hystrix to raise Exception on timeout
	//Second Bean with other getRoute can also be added for additional routes!
	@Bean
	public FallbackProvider getFallbackProvider(){

		return new FallbackProvider() {
			@Override
			public ClientHttpResponse fallbackResponse(Throwable throwable) {
				return fallbackResponse();
			}

			@Override
			public String getRoute() {
				return "analytics";
			}

			@Override
			public ClientHttpResponse fallbackResponse() {
				System.out.println("Fallback Response!");
				return new ClientHttpResponse() {
					@Autowired
					private ServletContext servletContext;
					@Override
					public HttpStatus getStatusCode() throws IOException {
						return HttpStatus.OK;
					}

					@Override
					public int getRawStatusCode() throws IOException {
						return HttpStatus.OK.value();
					}

					@Override
					public String getStatusText() throws IOException {
						return HttpStatus.OK.toString();
					}

					@Override
					public void close() {

					}

					@Override
					public InputStream getBody() throws IOException {
						ClassPathResource imgFile = new ClassPathResource("static/images/default.png");
						return imgFile.getInputStream();
					}

					@Override
					public HttpHeaders getHeaders() {
						HttpHeaders headers = new HttpHeaders();
						headers.setContentType(MediaType.IMAGE_PNG);
						return headers;
					}
				};
			}
		};
	}

	@Bean

	public MonitorFilter simpleFilter() {

		return new MonitorFilter();

	}

	public static void main(String[] args) {
		SpringApplication.run(GatewayApplication.class, args);
	}

	public class MonitorFilter extends ZuulFilter {
		@Autowired
		@Qualifier("proxyRequestHelper")
		private ProxyRequestHelper helper;
		@Override

		public String filterType() {

			return ROUTE_TYPE;

		}

		@Override

		public int filterOrder() {

			return 1;

		}

		@Override

		public boolean shouldFilter() {

			return true;

		}

		@Override

		public Object run() {

			RequestContext ctx = RequestContext.getCurrentContext();

			HttpServletRequest request = ctx.getRequest();
			String uri = this.helper.buildZuulRequestURI(request);
			System.out.println(String.format("%s request to %s | %s", request.getMethod(), request.getRequestURL().toString(), uri));

			return null;

		}

	}

}
