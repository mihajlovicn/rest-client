package at.spardat.rest.client.app;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import at.spardat.rest.client.demo.DemoApp;
import at.spardat.rest.client.impl.fluent.ServiceRestClientConfiguration;

/**
 * @since 28.10.2016
 */
public class MainApp {

    public static void main(String[] args) {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(ServiceRestClientConfiguration.class);
        DemoApp demoApp = new DemoApp(ctx);
        demoApp.execute();
    }

}
