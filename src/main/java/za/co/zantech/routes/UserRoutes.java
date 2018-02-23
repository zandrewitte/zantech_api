package za.co.zantech.routes;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.http.javadsl.marshallers.jackson.Jackson;
import akka.http.javadsl.model.StatusCodes;
import akka.http.javadsl.server.AllDirectives;
import akka.http.javadsl.server.Route;
import akka.util.Timeout;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import za.co.zantech.messagespec.UserSpec.*;
import za.co.zantech.messagespec.MessageSpec.*;

import static akka.http.javadsl.server.PathMatchers.longSegment;
import static akka.http.javadsl.server.PathMatchers.segment;
import static akka.pattern.PatternsCS.ask;
import static za.co.zantech.messagespec.MessageSpec.Actors.*;

/**
 * Created by zandrewitte on 2017/05/31.
 * UserRoutes
 */
@SuppressWarnings("unchecked")
class UserRoutes extends ApiRoute {

    private ActorRef userActor;

    public UserRoutes(ActorSystem actorSystem) {
        this.userActor = getActorByName(actorSystem, userActorName);
    }

    public Route routes() {
        return route(
            pathPrefix("users", () -> route(
                pathEndOrSingleSlash(() -> route(
                    get(() -> route(
                        completeOKWithFuture(ask(this.userActor, new GetAll(), timeout).thenApply(ListResponse::getResponse), Jackson.marshaller())
                    )),
                    post(() ->
                        entity(Jackson.unmarshaller(User.class), user ->
                            completeOKWithFuture(ask(this.userActor, new Save(user), timeout).thenApply(SingleResponse::getResponse), Jackson.marshaller())
                        )
                    )
                )),
                path(longSegment(), (id) -> route(
                    get(() ->
                        completeOKWithFuture(ask(this.userActor, new GetById(id), timeout).thenApply(SingleResponse::getResponse), Jackson.marshaller())
                    ),
                    put(() ->
                        entity(Jackson.unmarshaller(User.class), user ->
                                complete(StatusCodes.OK, user, Jackson.marshaller())
                        )
                    )
                ))
            ))

        );
    }

}
