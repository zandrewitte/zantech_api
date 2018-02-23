package za.co.zantech.messagespec;

/**
 * Created by zandrewitte on 2017/06/19.
 * PermissionSpec
 */
public interface PermissionSpec {
    static class GetPermission {
        private RouteMethod routeMethod;
        private Long userRole;

        public GetPermission(RouteMethod routeMethod, Long userRole) {
            this.routeMethod = routeMethod;
            this.userRole = userRole;
        }

        public Long getUserRole() {
            return userRole;
        }

        public RouteMethod getRouteMethod() {
            return routeMethod;
        }
    }

    static class RouteMethod {
        private String path;
        private String method;

        public RouteMethod(String path, String method) {
            this.path = path;
            this.method = method;
        }

        public String getPath() {
            return path;
        }

        public String getMethod() {
            return method;
        }
    }
}
