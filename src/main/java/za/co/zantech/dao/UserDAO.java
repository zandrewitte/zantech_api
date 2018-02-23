package za.co.zantech.dao;

import za.co.zantech.dao.common.CrudDAO;
import za.co.zantech.entities.User;

import java.util.Optional;

/**
 * Created by zandrewitte on 2017/05/31.
 * UserDAO
 */
public class UserDAO extends CrudDAO<User, Long> {
    public Optional<User> getByUserName(String userName) {
        return executeQuery(session ->
                (User) session.createQuery(
                        "select u " +
                                "from " + getTableName() + " u " +
                                "where u.userName = :userName" )
                        .setParameter( "userName", userName )
                        .uniqueResult()
        );
    }
}
