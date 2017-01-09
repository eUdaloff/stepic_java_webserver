package dbService.dao;

import dbService.dataSets.UserProfile;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

/**
 * @author v.chibrikov
 *         <p>
 *         Пример кода для курса на https://stepic.org/
 *         <p>
 *         Описание курса и лицензия: https://github.com/vitaly-chibrikov/stepic_java_webserver
 */
public class UsersDAO {

    private Session session;

    public UsersDAO(Session session) {
        this.session = session;
    }

    public UserProfile get(long id) throws HibernateException {
        return (UserProfile) session.get(UserProfile.class, id);
    }

    public UserProfile get(String login) throws HibernateException {
        Criteria criteria = session.createCriteria(UserProfile.class);
        return (UserProfile) criteria.add(Restrictions.eq("login", login)).uniqueResult();
    }

    public long insertUser(UserProfile userProfile) throws HibernateException {
        return (Long) session.save(new UserProfile(userProfile.getLogin(), userProfile.getPass()));
    }
}
