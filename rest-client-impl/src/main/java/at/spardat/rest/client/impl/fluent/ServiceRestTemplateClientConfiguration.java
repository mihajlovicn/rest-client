package at.spardat.rest.client.impl.fluent;

import at.spardat.rest.client.api.fluent.ServiceRestClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

/**
 * @since 28.10.2016
 */
@Configuration
public class ServiceRestTemplateClientConfiguration {

    @Bean
    ServiceRestClient serviceRestClient() {
        return new ServiceRestTemplateClient(springRestTemplate());
    }

    @Bean
    RestOperations springRestTemplate() {
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        //        requestFactory.setProxy(new Proxy(Type.HTTP, new InetSocketAddress("proxy-sd.s-mxs.net", 8080)));
        RestTemplate restTemplate = new RestTemplate(requestFactory);
        return restTemplate;
    }

}
