package accounts;

import dbService.DBException;
import dbService.DBService;
import dbService.dataSets.UserProfile;

import java.util.HashMap;
import java.util.Map;

/**
 * @author v.chibrikov
 *         <p>
 *         Пример кода для курса на https://stepic.org/
 *         <p>
 *         Описание курса и лицензия: https://github.com/vitaly-chibrikov/stepic_java_webserver
 */
public class AccountService {

    private final Map<String, UserProfile> sessionIdToProfile;

    public AccountService() {
        sessionIdToProfile = new HashMap<>();
    }

    public void addNewUser(UserProfile userProfile) throws DBException {
        DBService.getInstance().addUser(userProfile);
    }

    public UserProfile getUserByLogin(String login) throws DBException {
        return DBService.getInstance().getUser(login);
    }

    public UserProfile getUserBySessionId(String sessionId) {
        return sessionIdToProfile.get(sessionId);
    }

    public void addSession(String sessionId, UserProfile userProfile) {
        sessionIdToProfile.put(sessionId, userProfile);
    }

    public void deleteSession(String sessionId) {
        sessionIdToProfile.remove(sessionId);
    }
}
