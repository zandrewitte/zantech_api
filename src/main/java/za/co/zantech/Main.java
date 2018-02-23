package za.co.zantech;

import akka.actor.ActorSystem;
import za.co.zantech.actors.Actors;

/**
 * Created by zandrewitte on 2017/05/05.
 * Main
 */
public class Main {

    public static void main(String[] args) throws Exception {
        ActorSystem system = ActorSystem.create("Zantech_API");
        Actors.create(system);
        HttpServer.run(system);
    }

}