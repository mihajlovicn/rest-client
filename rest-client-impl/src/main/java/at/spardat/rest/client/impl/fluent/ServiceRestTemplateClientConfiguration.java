package at.spardat.rest.client.impl.fluent;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Proxy.Type;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

import at.spardat.rest.client.api.fluent.ServiceRestClient;

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
        requestFactory.setProxy(new Proxy(Type.HTTP, new InetSocketAddress("proxy-sd.s-mxs.net", 8080)));
        RestTemplate restTemplate = new RestTemplate(requestFactory);
        return restTemplate;
    }

}
