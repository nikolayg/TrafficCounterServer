package com.nikgrozev.trafficcounter;

import java.util.Timer;

import com.nikgrozev.trafficcounter.db.Db;
import com.nikgrozev.trafficcounter.job.ReportTask;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

public class App {

    public static void main(String[] args) throws Exception {
        Db db = new Db();

        // Create an embedded Jetty server
        Server server = new Server(8080);

        // Register the path and the handler
        ServletContextHandler handler = new ServletContextHandler(server, "/");
        handler.addServlet(new ServletHolder(new TrafficCounterServelet(db)), "/trafficData");

        // Start the server - it should use a thread pool for all the servelets
        server.start();

        // Schedule the job every 60 secs
        Timer timer = new Timer();
        timer.schedule(new ReportTask(db), 0, 60 * 1000);
        // timer.schedule(new ReportTask(db), 0, 5 * 1000);
    }
}
