package at.spardat.rest.client.demo;

import org.springframework.context.ApplicationContext;

import at.spardat.rest.client.api.fluent.ServiceRestClient;

/**
 * @since 28.10.2016
 */
public class DemoApp {

    private final ApplicationContext ctx;

    public DemoApp(ApplicationContext ctx) {
        this.ctx = ctx;
    }

    public void execute() {
        final ServiceRestClient restClient = ctx.getBean(ServiceRestClient.class);
        final String result = restClient.forService("foobar");
        System.out.println(result);
    }
}
