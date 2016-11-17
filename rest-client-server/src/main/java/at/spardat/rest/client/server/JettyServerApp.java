package at.spardat.rest.client.server;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.session.HashSessionManager;
import org.eclipse.jetty.server.session.SessionHandler;
import org.eclipse.jetty.webapp.WebAppContext;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.util.concurrent.TimeUnit;

public class JettyServerApp {

    private static final int PORT = 8981;

    public static void main(String[] args) throws Exception {
        System.out.println("Current working directory: " + new File("").getAbsolutePath());
        validatePortNotInUse();
        Server server = prepareServer();
        run(server);
    }

    private static void run(Server server) throws Exception {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(System.in))) {
            server.start();

            System.out.println("ReST Client started.");
            in.readLine();
            System.out.println("Stopping ReST Client ...");
            server.stop();
            server.join();
        }
    }

    private static Server prepareServer() throws IOException {
        Server server = new Server();
        ServerConnector connector = new ServerConnector(server);
        connector.setIdleTimeout((int) TimeUnit.HOURS.toMillis(1));
        connector.setSoLingerTime(-1);
        connector.setPort(PORT);
        server.addConnector(connector);

        HashSessionManager sessionManager = new HashSessionManager();
        sessionManager.setStoreDirectory(new File(System.getProperty("java.io.tmpdir") + "/restclient/jettysessions"));
        sessionManager.setDeleteUnrestorableSessions(true);

        WebAppContext context = new WebAppContext();
        context.setServer(server);
        context.setContextPath("/");
        context.setWar("src/main/webapp");
        context.setSessionHandler(new SessionHandler(sessionManager));

        server.setHandler(context);
        return server;
    }

    private static void validatePortNotInUse() {
        try {
            new ServerSocket(PORT, 50, InetAddress.getByName("localhost")).close();
        } catch (Exception e) {
            System.out.println(String.format("Could not open port %d, already in use?", PORT));
            System.exit(1);
        }
    }
}
