package servlets;


import resources.ResourceServer;
import resources.TestResource;
import sax.ReadXMLFileSAX;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ResourceServlet extends HttpServlet {

    private ResourceServer resourceServer;

    public ResourceServlet(ResourceServer resourceServer) {
        this.resourceServer = resourceServer;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        go(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        go(req, resp);
    }

    private void go(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html;charset=utf-8");
        String path = req.getParameter("path");
        if (path == null || path.isEmpty()) {
            resp.getWriter().println("path parameter is not set!");
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        TestResource obj = (TestResource) ReadXMLFileSAX.readXML(path);
        resourceServer.setTestResource(obj);
        resp.getWriter().println("resource was read");
        resp.setStatus(HttpServletResponse.SC_OK);
    }
}
