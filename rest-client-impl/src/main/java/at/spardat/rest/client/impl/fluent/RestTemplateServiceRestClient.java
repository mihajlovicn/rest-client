package at.spardat.rest.client.impl.fluent;

import java.net.URI;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestOperations;
import org.springframework.web.util.UriComponentsBuilder;

import at.spardat.rest.client.api.fluent.ServiceRestClient;

/**
 * @since 28.10.2016
 */
public class RestTemplateServiceRestClient implements ServiceRestClient {

    private final RestOperations restOperations;

    public RestTemplateServiceRestClient(RestOperations restOperations) {
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
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString("http://appplust98.imc.lan.at:25351/rest-web/api/info/version");
        return uriBuilder.build().toUri();
    }

}
