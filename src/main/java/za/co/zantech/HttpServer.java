package za.co.zantech;

import akka.NotUsed;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.http.javadsl.ConnectHttp;
import akka.http.javadsl.Http;
import akka.http.javadsl.ServerBinding;
import akka.http.javadsl.model.HttpRequest;
import akka.http.javadsl.model.HttpResponse;
import akka.http.javadsl.server.Route;
import akka.routing.FromConfig;
import akka.stream.ActorMaterializer;
import akka.stream.javadsl.Flow;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import za.co.zantech.actors.*;
import za.co.zantech.routes.Routes;

import static za.co.zantech.messagespec.MessageSpec.Actors.*;

/**
 * Created by zandrewitte on 2017/05/12.
 * HttpServer
 */
class HttpServer {

    static void run(ActorSystem system) {
        final LoggingAdapter log = Logging.getLogger(system, system);

        final Http http = Http.get(system);

        final ActorMaterializer materializer = ActorMaterializer.create(system);

        final Route routes = new Routes(system).buildRoutes();

        Config httpConf = ConfigFactory.load(HttpServer.class.getClassLoader()).getConfig("http");

        final Flow<HttpRequest, HttpResponse, NotUsed> routeFlow = routes.flow(system, materializer);
        http.bindAndHandle(routeFlow, ConnectHttp.toHost(httpConf.getString("host"), httpConf.getInt("port")), materializer)
            .thenAccept(c -> log.info("Server online at {}", c.localAddress()));
    }


}
