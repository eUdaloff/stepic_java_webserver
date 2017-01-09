package main;


import accounts.AccountService;
import dbService.dataSets.UserProfile;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import servlets.SignInServlet;
import servlets.SignUpServlet;

/**
 * @author v.chibrikov
 *         <p>
 *         Пример кода для курса на https://stepic.org/
 *         <p>
 *         Описание курса и лицензия: https://github.com/vitaly-chibrikov/stepic_java_webserver
 */
public class Main {

//    public static void main2(String[] args) {
//        DBService dbService = new DBService();
//        dbService.printConnectInfo();
//
//        try {
//            long userId = dbService.addUser("tully");
//            System.out.println("Added user id: " + userId);
//
//            UsersDataSet dataSet = dbService.getUser(userId);
//            System.out.println("User data set: " + dataSet);
//
//        } catch (DBException e) {
//            e.printStackTrace();
//        }
//    }

    public static void main(String[] args) throws Exception {
        AccountService accountService = new AccountService();

        //accountService.addNewUser(new UserProfile("admin"));
        //accountService.addNewUser(new UserProfile("test"));

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);

        context.addServlet(new ServletHolder(new SignInServlet(accountService)), "/signin");
        context.addServlet(new ServletHolder(new SignUpServlet(accountService)), "/signup");

        ResourceHandler resource_handler = new ResourceHandler();
        resource_handler.setResourceBase("public_html");

        HandlerList handlers = new HandlerList();
        handlers.setHandlers(new Handler[]{resource_handler, context});

        Server server = new Server(8080);
        server.setHandler(handlers);

        server.start();
        System.out.println("Server started");
        server.join();
    }
}
