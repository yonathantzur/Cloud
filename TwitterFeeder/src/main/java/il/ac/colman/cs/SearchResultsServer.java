package il.ac.colman.cs;

import com.fasterxml.jackson.databind.ObjectMapper;
import il.ac.colman.cs.util.CloudWatch;
import il.ac.colman.cs.util.DataStorage;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.AbstractHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class SearchResultsServer extends AbstractHandler {
    public static void main(String[] args) throws Exception {
        // Start the http server on port 8080
        Server server = new Server(8080);
        server.setHandler(new SearchResultsServer());
        server.start();
        server.join();
    }

    private DataStorage storage;
    private CloudWatch cw;

    SearchResultsServer() throws ClassNotFoundException {
        storage = new DataStorage();
        cw = new CloudWatch();
    }

    public void handle(String s, Request request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException {
        long startTime = System.nanoTime();

        cw.SendMetric("search_tweet", 1.0);
        // Set the content type to JSON
        httpServletResponse.setContentType("application/json;charset=UTF-8");

        // Set the status to 200 OK
        httpServletResponse.setStatus(HttpServletResponse.SC_OK);

        // Build data from request
        List<ExtractedLink> results = null;

        try {
            // Get query parameter from request.
            String searchQuery = httpServletRequest.getParameter("query");

            if (searchQuery == null) {
                searchQuery = "";
            }

            results = storage.search(searchQuery);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        // Notify that this request was handled
        request.setHandled(true);

        // Convert data to JSON string and write to output
        ObjectMapper mapper = new ObjectMapper();

        long endTime = (System.nanoTime() - startTime) / 1000000; // In millisecond
        cw.SendMetric("search_tweet_time", (double)endTime);

        mapper.writeValue(httpServletResponse.getWriter(), results);
    }
}
