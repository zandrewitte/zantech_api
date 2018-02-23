package za.co.zantech.actors;

import akka.actor.AbstractActor;
import akka.actor.Status;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.japi.pf.ReceiveBuilder;
import javassist.NotFoundException;
import za.co.zantech.dao.PermissionDAO;
import za.co.zantech.entities.Permission;
import za.co.zantech.messagespec.PermissionSpec.*;

import java.util.Optional;

/**
 * Created by zandrewitte on 2017/06/06.
 * PermissionActor
 */
@SuppressWarnings("unchecked")
public class PermissionActor  extends AbstractActor {

    private final LoggingAdapter logger = Logging.getLogger(context().system(), this);
    private final PermissionDAO permissions = new PermissionDAO();

    public PermissionActor() {
        receive(ReceiveBuilder.
                match(GetPermission.class, this::getPermissionRoute).
                matchAny(o -> {
                    logger.info("Invalid message {}", o);
                    sender().tell(new Status.Failure(new NotFoundException("Incorrect Message Type")), self());
                }).
                build()
        );
    }

    private void getPermissionRoute(GetPermission getPermission) {
        logger.info("Route: " + getPermission.getRouteMethod().getPath() + ", Method: " + getPermission.getRouteMethod().getMethod());

        Optional<Permission> response = permissions.getPermission(getPermission.getRouteMethod().getPath(), getPermission.getRouteMethod().getMethod(), getPermission.getUserRole());
        if (response.isPresent())
            sender().tell(new Status.Success(response.get()), self());
        else
            sender().tell(new Status.Failure(new NotFoundException("User could not be found")), self());
    }

}
