package za.co.zantech.routes;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.http.javadsl.model.ContentTypes;
import akka.http.javadsl.model.HttpEntities;
import akka.http.javadsl.model.StatusCodes;
import akka.http.javadsl.server.AllDirectives;
import akka.http.javadsl.server.Route;

import static akka.pattern.PatternsCS.ask;

import akka.util.Timeout;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import scala.concurrent.duration.FiniteDuration;

import java.util.concurrent.TimeUnit;

/**
 * Created by zandrewitte on 2017/06/14.
 * ApiRoute
 */
abstract class ApiRoute extends AllDirectives {

    final Logger logger = LoggerFactory.getLogger(ApiRoute.class);

    protected final Timeout timeout = Timeout.durationToTimeout(FiniteDuration.apply(10, TimeUnit.SECONDS));

    public ApiRoute() {

    }

}
