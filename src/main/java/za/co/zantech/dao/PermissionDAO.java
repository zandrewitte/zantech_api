package za.co.zantech.dao;

import za.co.zantech.dao.common.BaseDAO;
import za.co.zantech.entities.Permission;

import java.util.Optional;

/**
 * Created by zandrewitte on 2017/06/06.
 * PermissionDAO
 */
public class PermissionDAO extends BaseDAO<Permission> {

    public Optional<Permission> getPermission(String routeName, String method, Long roleId) {
        return executeQuery(session -> session.createQuery(
                "SELECT permission " +
                        "FROM Route route " +
                        "JOIN Permission permission ON permission.routeId = route.id " +
                        "JOIN Role role ON permission.roleId = role.id " +
                        "WHERE route.name = :routeName AND upper(route.method) = upper(:method) AND role.id = :roleId",
                Permission.class)
                .setParameter( "routeName", routeName )
                .setParameter( "method", method )
                .setParameter( "roleId", roleId )
                .uniqueResult()
        );
    }

}
