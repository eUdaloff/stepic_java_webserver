package main;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import resources.ResourceServer;
import servlets.ResourceServlet;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;

public class Main {

    public static void main(String[] args) throws Exception {

        ResourceServer resourceServer = new ResourceServer();
        MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
        ObjectName name = new ObjectName("Admin:type=ResourceServerController");
        mbs.registerMBean(resourceServer, name);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.addServlet(new ServletHolder(new ResourceServlet(resourceServer)), "/resources");
        HandlerList handlers = new HandlerList();
        handlers.setHandlers(new Handler[]{context});
        Server server = new Server(8080);
        server.setHandler(handlers);
        server.start();
        System.out.println("Server started");
        server.join();
    }
}
