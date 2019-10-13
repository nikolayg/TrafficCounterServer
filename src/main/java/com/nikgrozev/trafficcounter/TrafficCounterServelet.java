package com.nikgrozev.trafficcounter;

import java.io.IOException;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Serves calls to the 
 */
public class TrafficCounterServelet extends HttpServlet {
 
    private static final long serialVersionUID = 1L;

    private static final String SUCCESS_JSON = "{ \"status\": \"processed\"}";
    private static final String GENERIC_FAIL_JSON = "{ \"status\": \"failed\"}";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // TODO: Can use AsyncContex for further parallelisation - https://www.baeldung.com/jetty-embedded

        resp.setContentType("application/json");
        try {
            // Read the body
            String reqBody = req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));

            // Signal success to the caller
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.getWriter().println(SUCCESS_JSON);
        } catch (IOException e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().println(GENERIC_FAIL_JSON);
        }
    }
}