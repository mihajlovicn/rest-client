package at.spardat.rest.client.itest;

import at.spardat.rest.client.api.fluent.ServiceRestClient;
import at.spardat.rest.client.impl.fluent.ServiceRestTemplateClientConfiguration;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.http.MimeType;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.getRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.client.WireMock.verify;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.assertj.core.api.Assertions.assertThat;

@Test
public class SampleIntegrationTest {

    private static final int PORT = 8089; // could use wiremock's dynamic port; need to pass it to rest-client though
    private static final String JSON = MimeType.JSON.toString();

    private WireMockServer wireMockServer;

    @BeforeClass
    public void startWiremock() {
        wireMockServer = new WireMockServer(wireMockConfig().port(PORT));
        wireMockServer.start();
        WireMock.configureFor("localhost", PORT);
    }

    @AfterMethod
    public void resetWiremock() {
        WireMock.reset();
    }

    @AfterClass
    public void stopWiremock() {
        wireMockServer.stop();
    }

    public void requestDummyEndpoint() {
        String url = "/dummy";
        String mockedResponseBody = "Hello world!";

        stubFor(get(urlEqualTo(url))
            .willReturn(aResponse()
                .withHeader("Content-Type", JSON)
                .withBody(mockedResponseBody)));

        ApplicationContext ctx = new AnnotationConfigApplicationContext(ServiceRestTemplateClientConfiguration.class);
        final ServiceRestClient restClient = ctx.getBean(ServiceRestClient.class);
        final String result = restClient.forService("ignoredByImplementation");

        assertThat(result).isEqualTo(mockedResponseBody);
        verify(getRequestedFor(urlEqualTo(url))
            .withHeader("Content-Type", equalTo(JSON)));

    }

}
