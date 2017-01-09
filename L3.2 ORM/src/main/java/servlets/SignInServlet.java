package servlets;

import accounts.AccountService;
import dbService.DBException;
import dbService.dataSets.UserProfile;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Udalov.EV on 09.01.2017.
 */
public class SignInServlet extends HttpServlet {

    private final AccountService accountService;

    public SignInServlet(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");

        if (login == null || password == null || login.isEmpty() || password.isEmpty()) {
            resp.setContentType("text/html;charset=utf-8");
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        UserProfile profile = null;
        try {
            profile = accountService.getUserByLogin(login);
        } catch (DBException e) {
            resp.setContentType("text/html;charset=utf-8");
            resp.getWriter().println(e.getMessage());
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return;
        }

        if (profile != null) {
            if (password.equals(profile.getPass())) {
                resp.setContentType("text/html;charset=utf-8");
                resp.setStatus(HttpServletResponse.SC_OK);
                resp.getWriter().println("Authorized: " + profile.getLogin());
                return;
            }
        }
        resp.setContentType("text/html;charset=utf-8");
        resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        resp.getWriter().println("Unauthorized");
        return;
    }
}
