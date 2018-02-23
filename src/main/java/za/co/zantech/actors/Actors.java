package za.co.zantech.actors;

import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.routing.FromConfig;

import static za.co.zantech.messagespec.MessageSpec.Actors.*;

/**
 * Created by zandrewitte on 2017/07/29.
 * Actors
 */
public class Actors {

    public static void create(ActorSystem system) {
        system.actorOf(FromConfig.getInstance().props(Props.create(UserActor.class)), userActorName);
        system.actorOf(FromConfig.getInstance().props(Props.create(PermissionActor.class)), permissionActorName);
    }
}
