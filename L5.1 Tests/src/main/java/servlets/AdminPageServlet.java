package servlets;

import accountServer.AccountServerI;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AdminPageServlet extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(AdminPageServlet.class.getName());

    public static final String PAGE_URL = "/admin";

    private final AccountServerI accountServer;

    public AdminPageServlet(AccountServerI accountServer) {
        this.accountServer = accountServer;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=utf-8");
        int limit = accountServer.getUsersLimit();
        logger.info("Limit: {}. ", limit);
        resp.getWriter().println(limit);
        resp.setStatus(HttpServletResponse.SC_OK);
    }
}
