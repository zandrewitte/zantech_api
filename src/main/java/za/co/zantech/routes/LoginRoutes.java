package za.co.zantech.routes;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.http.javadsl.marshallers.jackson.Jackson;
import akka.http.javadsl.model.*;
import akka.http.javadsl.model.headers.RawHeader;
import akka.http.javadsl.server.AllDirectives;
import akka.http.javadsl.server.Route;
import akka.util.Timeout;

import io.jsonwebtoken.CompressionCodecs;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.crypto.MacProvider;
import scala.concurrent.duration.FiniteDuration;

import za.co.zantech.messagespec.LoginSpec.*;
import za.co.zantech.messagespec.MessageSpec.*;
import za.co.zantech.messagespec.UserSpec.*;
import za.co.zantech.utils.JWT;
import za.co.zantech.utils.PasswordEncrypter;
import static za.co.zantech.messagespec.MessageSpec.Actors.*;

import java.util.concurrent.TimeUnit;

import static akka.pattern.PatternsCS.ask;

/**
 * Created by zandrewitte on 2017/05/10.
 * LoginRoutes
 */
public class LoginRoutes extends AllDirectives {

    private ActorRef userActor;
    private final Timeout timeout;

    public LoginRoutes(ActorSystem actorSystem, Timeout timeout) {
        this.userActor = getActorByName(actorSystem, userActorName);
        this.timeout = timeout;
    }

    public Route routes() {
        return route(
            path("login", () -> route(
                post(() ->
                    entity(Jackson.unmarshaller(Login.class), login ->
                        onComplete(ask(this.userActor, new GetByUserName(login.getUsername()), Timeout.durationToTimeout(FiniteDuration.apply(5, TimeUnit.SECONDS))), maybeResult -> maybeResult.fold(
                            err -> complete(StatusCodes.UNAUTHORIZED, HttpEntities.create(ContentTypes.APPLICATION_JSON, "{\"Status\": \"User not authorised to access the API\"}")),
                            response -> {
                                User user = (User) SingleResponse.getResponse(response);

                                return PasswordEncrypter.matches(user.getPassword(), login.getPassword()) ?
                                    respondWithHeader(RawHeader.create("Authorization", JWT.createJTWToken(user)), () -> route(
                                        complete(StatusCodes.OK, HttpEntities.create(ContentTypes.APPLICATION_JSON, "{\"Status\": \"OK\"}"))
                                    ))
                                    :
                                    complete(StatusCodes.UNAUTHORIZED, HttpEntities.create(ContentTypes.APPLICATION_JSON, "{\"Status\": \"User not authorised to access the API\"}"));
                            }
                        ))
                    )
                )
            ))
        );
    }
}