package com.nikgrozev.trafficcounter;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;

public class App {

    public static void main(String[] args) {
        try {
            // Create an embedded Jetty server
            Server server = new Server(8080);

            // Register the path and the handler
            ServletContextHandler handler = new ServletContextHandler(server, "/");
            handler.addServlet(TrafficCounterServelet.class, "/trafficData");

            // Start the server - it should use a thread pool for all the servelets
            server.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
