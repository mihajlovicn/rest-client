package at.spardat.rest.client.impl.fluent;

import at.spardat.rest.client.api.fluent.ServiceRestClient;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestOperations;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

/**
 * @since 28.10.2016
 */
public class ServiceRestTemplateClient implements ServiceRestClient {

    private final RestOperations restOperations;

    public ServiceRestTemplateClient(RestOperations restOperations) {
        this.restOperations = restOperations;
    }

    @Override
    public String forService(String serviceName) {
        HttpHeaders headers = createHeaders();
        HttpEntity<String> parameters = new HttpEntity<>(headers);
        final ResponseEntity<String> forEntity = restOperations.exchange(dummyRestUri(), HttpMethod.GET, parameters, String.class);
        return forEntity.toString();
    }

    private static HttpHeaders createHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }

    private static URI dummyRestUri() {
        // http://appplust98.imc.lan.at:25351/rest-web/api/info/version
        // String url = "http://appplust98.imc.lan.at:25351/rest-web/api/info/version";
        String url = "http://localhost:8981/json/version.json";
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(url);
        return uriBuilder.build().toUri();
    }

}
