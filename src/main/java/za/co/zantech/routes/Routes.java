package za.co.zantech.routes;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.http.javadsl.model.ContentTypes;
import akka.http.javadsl.model.HttpEntities;
import akka.http.javadsl.model.StatusCodes;
import akka.http.javadsl.server.Route;
import io.jsonwebtoken.*;
import za.co.zantech.utils.JWT;

import za.co.zantech.entities.Permission;

import static akka.pattern.PatternsCS.ask;

import java.util.Date;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

import static za.co.zantech.messagespec.MessageSpec.Actors.*;
import static za.co.zantech.messagespec.PermissionSpec.*;

/**
 * Created by zandrewitte on 2017/05/09.
 * Routes
 */
public class Routes extends ApiRoute {

    private final ActorSystem actorSystem;
    private final ActorRef permissionActor;

    public Routes(ActorSystem actorSystem) {
        this.actorSystem = actorSystem;
        this.permissionActor = getActorByName(this.actorSystem, permissionActorName);
    }

    public Route buildRoutes() {
        return route(
            new LoginRoutes(this.actorSystem, timeout).routes(),
            authenticate((claims) -> Optional.ofNullable((Number) claims.get("role")).map(userRole -> authorizeUser(userRole.longValue(), () ->
                route(
                    new UserRoutes(this.actorSystem).routes()
                )
            )).orElse(complete(StatusCodes.UNAUTHORIZED)))
        );
    }

    private Route authenticate(Function<Claims, Route> innerRoute) {
        return optionalHeaderValueByName("Authorization", (authToken) ->
            authToken.flatMap(authenticationToken ->{
                try {
                    return Optional.of(
                            Jwts.parser()
                                    .setSigningKey(JWT.getKey())
                                    .parseClaimsJws(authenticationToken)
                                    .getBody()
                    );
                } catch (SignatureException se) {
                    logger.error(se.getMessage(), se);
                    return Optional.empty();
                } catch (MissingClaimException me) {
                    logger.error(me.getMessage(), me);
                    return Optional.empty();
                }
            })
            .filter(claims -> claims.getExpiration().after(new Date()))
            .map(innerRoute)
            .orElseGet(() -> complete(StatusCodes.UNAUTHORIZED, HttpEntities.create(ContentTypes.APPLICATION_JSON, "{\"Status\": \"Unauthorised to access the API\"}")))
        );
    }

    private Route authorizeUser(Long userRole, Supplier<Route> innerRoute){

        Route fallBack = complete(StatusCodes.UNAUTHORIZED, HttpEntities.create(ContentTypes.APPLICATION_JSON, "{\"Status\": \"User not authorised to access this endpoint\"}"));

        return Optional.of(userRole).map(l ->
                extract(
                        ctx -> new GetPermission(new RouteMethod((ctx.getRequest().getUri().path().substring(1, 2).toUpperCase() + ctx.getRequest().getUri().path().substring(2)).toUpperCase().replaceAll("\\/[\\d]", "/*"),
                                ctx.getRequest().method().name()), userRole),
                        route -> onComplete(ask(this.permissionActor, route, timeout).thenApply(Permission.class::cast), response -> response.fold(
                                error -> fallBack,
                                permission -> innerRoute.get()
                        ))
                )
        ).orElse(fallBack);
    }

}
